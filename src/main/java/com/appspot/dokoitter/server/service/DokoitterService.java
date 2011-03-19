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
import com.google.appengine.api.datastore.Key;

public class DokoitterService {

	public User getUser(String account) {
		List<User> users = Datastore.query(User.class)
				.filter(UserMeta.get().account.equal(account)).asList();

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

	public Void putFollow(String account, String followerAccount)
			throws Exception {

		if (account.equals(followerAccount)) {
			throw new IllegalStateException("same account");
		}

		User user = getUser(account);

		User follower = null;
		try {
			follower = getUser(followerAccount);
		} catch (Exception e) {
			throw new IllegalStateException("follower not found");
		}

		Follow follow = new Follow();
		follow.getUserRef().setModel(user);
		follow.getFollowerRef().setModel(follower);

		String uniqueValue = follow.getUniqueValue();

		if (!Datastore.putUniqueValue(Follow.class.toString(), uniqueValue)) {
			throw new IllegalStateException("exists follow");
		}

		try {
			Datastore.put(follow);
		} catch (Exception e) {
			Datastore.deleteUniqueValue(Follow.class.toString(), uniqueValue);
			throw e;
		}

		return null;
	}
	
	public Void deleteFollow(String account, Key key){
		User user = getUser(account);
		FollowMeta followMeta = FollowMeta.get();
		for(Follow follow : Datastore.query(Follow.class)
								.filter(followMeta.userRef.equal(user.getKey()),
										followMeta.key.equal(key))
								.asList()){
			GlobalTransaction gtx = Datastore.beginGlobalTransaction();
			gtx.delete(follow.getKey());
			Datastore.deleteUniqueValue(Follow.class.toString(), follow.getUniqueValue());
			gtx.commit();
		}
		return null;
	}

	public List<Map<?, ?>> getFollower(String account) {
		User user = getUser(account);
		List<Map<?, ?>> followers = new ArrayList<Map<?, ?>>();
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get();

		for (Follow follow : Datastore.query(Follow.class)
				.filter(FollowMeta.get().userRef.equal(user.getKey())).asList()) {

			User follower = follow.getFollowerRef().getModel();

			Map<Object, Object> followerData = new HashMap<Object, Object>();
			followerData.put(followMeta.key, follow.getKey());
			followerData.put(followMeta.status,
								(follow.getStatus() == Follow.Status.PENDING ?
									Follow.Status.PENDING : Follow.Status.SENDED));
			followerData.put(userMeta.account, follower.getAccount());
			followerData.put(userMeta.spot,
				(follow.getStatus() == Follow.Status.SENDED ? follower.getSpot() :
					follow.getStatus() == Follow.Status.STOPED ? follow.getLastSpot() : ""));
			followerData.put(userMeta.updatedAt, follower.getUpdatedAt());
			
			followers.add(followerData);
		}

		return followers;
	}

	public List<Map<?, ?>> getFollowee(String account) {
		User user = getUser(account);
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get();
		List<Map<?, ?>> followees = new ArrayList<Map<?, ?>>();
		for (Follow follow : Datastore.query(Follow.class)
				.filter(followMeta.followerRef.equal(user.getKey())).asList()) {
			
			User followee = follow.getUserRef().getModel();
			
			Map<Object, Object> followeeData = new HashMap<Object, Object>();
			followeeData.put(followMeta.key, follow.getKey());
			followeeData.put(followMeta.status, follow.getStatus());
			followeeData.put(userMeta.account, followee.getAccount());
			followeeData.put(userMeta.updatedAt, followee.getUpdatedAt());
			
			followees.add(followeeData);
		}

		return followees;
	}

	public Void updateSpot(String account, String spot) {
		User user = getUser(account);
		user.setSpot(spot);
		Datastore.put(user);
		return null;
	}

	public Void changeFollowStatus(String account, Key key,
			Follow.Status status) {
		User user = getUser(account);
		
		GlobalTransaction gtx = Datastore.beginGlobalTransaction();
		Follow follow = gtx.get(Follow.class, key);
		// TODO:followがなかった場合の対応検討
		
		if (!user.getKey().equals(follow.getFollowerRef().getKey())) {
			throw new IllegalStateException("authority failed");
		}

		if ((follow.getStatus() != Follow.Status.SENDED && status == Follow.Status.SENDED)
				|| (follow.getStatus() == Follow.Status.SENDED && status == Follow.Status.STOPED)) {
			follow.setStatus(status);
			if (status == Follow.Status.STOPED) {
				follow.setLastSpot(user.getSpot());
			}
			gtx.put(follow);
			gtx.commit();
		}

		return null;
	}
}
