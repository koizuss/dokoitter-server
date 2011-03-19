package com.appspot.dokoitter.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.meta.FollowMeta;
import com.appspot.dokoitter.server.meta.UserMeta;

public class DokoitterService {

	public User getUser(String account) {
		List<User> users = Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).asList();

		if (users.size() < 1) {
			throw new IllegalStateException("user not found");
		}

		return users.get(0);
	}

	public User getOrPutUser(String accout) throws Exception {

		if (!Datastore.putUniqueValue(User.class.toString(), accout)) {
			return getUser(accout);
		}

		User user = new User();
		user.setAccount(accout);

		try {
			Datastore.put(user);
			return user;
		} catch (Exception e) {
			Datastore.deleteUniqueValue(User.class.toString(), accout);
			throw e;
		}
	}

	public Follow putFollow(String account, String followerAccount) throws Exception {

		User user = getUser(account);
		
		User follower;
		try{
			follower = getUser(followerAccount);
		}catch (IllegalStateException e) {
			throw new IllegalStateException("follower not found", e);
		}

		Follow follow = new Follow();
		follow.getUserRef().setModel(user);
		follow.getFollowerRef().setModel(follower);

		String uniqueValue = account + ">" + followerAccount;

		if (!Datastore.putUniqueValue(Follow.class.toString(), uniqueValue)) {
			throw new IllegalStateException("exists follow");
		}

		try {
			Datastore.put(follow);
			return follow;
		} catch (Exception e) {
			Datastore.deleteUniqueValue(Follow.class.toString(), uniqueValue);
			throw e;
		}
	}

	public List<User> getFollower(String account) {
		User user = getUser(account);
		List<User> followers = new ArrayList<User>();

		for (Follow follow : Datastore.query(Follow.class).filter(
				FollowMeta.get().userRef.equal(user.getKey())).asList()) {

			User follower = follow.getFollowerRef().getModel();
			Follow.Status status = follow.getStatus();

			if (status.equals(Follow.Status.PENDING)) {
				follower.setSpot(Follow.Status.PENDING.name());
			} else if (status.equals(Follow.Status.STOPED)) {
				follower.setSpot(follow.getLastSpot());
			}

			followers.add(follower);
		}

		return followers;
	}

	public List<Map<String, Object>> getFollowee(String account) {
		User user = getUser(account);
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get();
		List<Map<String, Object>> followees = new ArrayList<Map<String, Object>>();
		for (Follow follow : Datastore.query(Follow.class).filter(
				followMeta.followerRef.equal(user.getKey())).asList()) {
			Map<String, Object> followee = new HashMap<String, Object>();
			followee.put(userMeta.account.getName(), follow.getUserRef().getModel());
			followee.put(followMeta.status.getName(), follow.getStatus());
			followees.add(followee);
		}

		return followees;
	}

	public User updateSpot(String account, String spot) {
		User user = getUser(account);
		user.setSpot(spot);
		Datastore.put(user);
		return user;
	}

	public Follow changeFollowStatus(String account, String key,
			Follow.Status status) {
		GlobalTransaction gtx = Datastore.beginGlobalTransaction();
		Follow follow = gtx.get(Follow.class, Datastore.stringToKey(key));
		User user = getUser(account);

		if (!user.getKey().equals(follow.getFollowerRef().getKey())) {
			throw new IllegalStateException("authority failed");
		}

		if ((follow.getStatus() != Follow.Status.SENDED && status == Follow.Status.SENDED)
				|| (follow.getStatus() == Follow.Status.SENDED && status == Follow.Status.STOPED)) {
			follow.setStatus(status);
			if(status == Follow.Status.STOPED){
				follow.setLastSpot(user.getSpot());
			}
			gtx.put(follow);
			gtx.commit();
		}

		return follow;
	}
}
