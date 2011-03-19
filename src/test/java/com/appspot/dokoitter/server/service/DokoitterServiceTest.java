package com.appspot.dokoitter.server.service;

import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.DokoitterService;
import com.appspot.dokoitter.server.meta.FollowMeta;
import com.appspot.dokoitter.server.meta.UserMeta;
import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DokoitterServiceTest extends AppEngineTestCase {

	private DokoitterService service = new DokoitterService();

	@Test
	public void test() throws Exception {
		assertThat(service, is(notNullValue()));
	}

	@Test
	public void getUser() throws Exception {
		String account = "test-account";

		User user = new User();
		user.setAccount(account);
		Datastore.put(user);

		User actual = service.getUser(account);

		assertThat(actual.getAccount(), is(user.getAccount()));
		assertThat(actual.getKey(), is(user.getKey()));
	}

	@Test(expected = IllegalStateException.class)
	public void getUser_notFound() throws Exception {
		String account = "test-account";
		service.getUser(account);
	}

	@Test
	public void getUser_filterTest() throws Exception {
		User user1 = new User();
		user1.setAccount("account1");

		User user2 = new User();
		user2.setAccount("account2");

		User user3 = new User();
		user3.setAccount("account3");

		Datastore.put(user1, user2, user3);
		assertThat(Datastore.query(User.class).count(), is(3));

		User actual = service.getUser(user2.getAccount());

		assertThat(actual.getAccount(), is(user2.getAccount()));
		assertThat(actual.getKey(), is(user2.getKey()));
	}

	@Test
	public void getOrPutUser_newUser() throws Exception {
		String account = "test-accout";
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(0));

		User actual = service.getOrPutUser(account);
		assertThat(actual.getAccount(), is(account));
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(1));
	}

	@Test
	public void getOrPutUser_existsUser() throws Exception {
		String account = "test-accout";
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(0));

		User expected = service.getOrPutUser(account);
		User actual = service.getOrPutUser(account);

		assertThat(actual.getAccount(), is(expected.getAccount()));
		assertThat(actual.getKey(), is(expected.getKey()));
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(1));
	}

	@Test
	public void getOrPutUser_putFailedRetry() throws Exception {
		String account = "test-accout";
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(0));

		tester.tearDown();

		try {
			service.getOrPutUser(account);
		} catch (Exception e) {
			// NOP
		}

		tester.setUp();
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(0));

		User actual = service.getOrPutUser(account);

		assertThat(actual.getAccount(), is(account));
		assertThat(Datastore.query(User.class).filter(
				UserMeta.get().account.equal(account)).count(), is(1));
	}

	@Test
	public void putFollow() throws Exception {
		
		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Datastore.put(user1, user2);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		
		service.putFollow(account1, account2);
		
		Follow follow = Datastore.query(Follow.class).asSingle();
		
		assertThat(follow.getUserRef().getKey(), is(user1.getKey()));
		assertThat(follow.getFollowerRef().getKey(), is(user2.getKey()));
	}
	
	@Test(expected = IllegalStateException.class)
	public void putFollow_followerNotFound() throws Exception {

		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		
		Datastore.put(user1);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		
		service.putFollow(account1, account2);
	}
	
	@Test(expected = IllegalStateException.class)
	public void putFollow_illegalFollowerAccount() throws Exception {

		String account1 = "account1";
		String account2 = "account2";
		String illegalFollowerAccount = "";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Datastore.put(user1, user2);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		
		service.putFollow(account1, illegalFollowerAccount);
	}

	@Test(expected = IllegalStateException.class)
	public void putFollow_existsFollow() throws Exception {

		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Datastore.put(user1, user2);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		
		service.putFollow(account1, account2);
		service.putFollow(account1, account2);
	}

	@Test
	public void putFollow_retrySuccessful() throws Exception {

		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Datastore.put(user1, user2);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());

		tester.tearDown();
		try {
			service.putFollow(account1, account2);
		} catch (Exception e) {
			// NOP
		}
		tester.setUp();
		
		Datastore.put(user1, user2);
		Datastore.delete(Datastore.query(Follow.class).asKeyList());

		service.putFollow(account1, account2);
		
		Follow follow = Datastore.query(Follow.class).asSingle();
		
		assertThat(follow.getUserRef().getKey(), is(user1.getKey()));
		assertThat(follow.getFollowerRef().getKey(), is(user2.getKey()));
	}
	
	@Test
	public void deleteFollow() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		Datastore.put(user1, user2, follow);
		
		service.deleteFollow(account1, follow.getKey());
		assertThat(Datastore.query(Follow.class).count(), is(0));
	}
	
	@Test
	public void deleteFollow_noEntry() throws Exception {
		String account1 = "account1";
		
		User user1 = new User();
		user1.setAccount(account1);
		
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		Datastore.put(user1);
		
		service.deleteFollow(account1, Datastore.allocateId(Follow.class));
		assertThat(Datastore.query(Follow.class).count(), is(0));
	}
	
	@Test
	public void deleteFollow_otherUser() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		Datastore.put(user1, user2, follow);
		
		service.deleteFollow(account2, follow.getKey());
		assertThat(Datastore.query(Follow.class).count(), is(1));
	}
	
	@Test
	public void deleteFollow_afterPutFollowSuccessful() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		
		Datastore.delete(Datastore.query(Follow.class).asKeyList());
		Datastore.put(user1, user2, follow);
		
		service.deleteFollow(account1, follow.getKey());
		assertThat(Datastore.query(Follow.class).count(), is(0));
		
		service.putFollow(account1, account2);
		assertThat(Datastore.query(Follow.class).count(), is(1));
	}

	@Test
	public void getFollower() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		follow.setStatus(Follow.Status.SENDED);
		
		Datastore.put(user1, user2, follow);
		
		List<Map<?, ?>> followers = service.getFollower(account1);
		assertThat(followers.size(), is(1));
		
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get(); 
		
		assertThat((Key)followers.get(0).get(followMeta.key), is(follow.getKey()));
		assertThat((Follow.Status)followers.get(0).get(followMeta.status), is(Follow.Status.SENDED));
		assertThat((String)followers.get(0).get(userMeta.account), is(account2));
		assertThat((String)followers.get(0).get(userMeta.spot), is(spot2));
	}
	
	@Test
	public void getFollower_statusSended() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot = "spot";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot);
		
		Follow follow = new Follow();
		follow.setStatus(Follow.Status.SENDED);
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		
		Datastore.put(user1, user2, follow);
		
		List<Map<?, ?>> followers = service.getFollower(account1);
		assertThat(followers.size(), is(1));
		
		UserMeta userMeta = UserMeta.get();
		assertThat((String)followers.get(0).get(userMeta.spot), is(spot));
	}
	
	@Test
	public void getFollower_statusStoped() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		
		User user1 = new User();
		user1.setAccount(account1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot1);
		
		Follow follow = new Follow();
		follow.setStatus(Follow.Status.STOPED);
		follow.setLastSpot(spot2);
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		
		Datastore.put(user1, user2, follow);
		
		List<Map<?, ?>> followers = service.getFollower(account1);
		assertThat(followers.size(), is(1));
		
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get(); 
		
		assertThat((Follow.Status)followers.get(0).get(followMeta.status), is(Follow.Status.SENDED));
		assertThat((String)followers.get(0).get(userMeta.spot), is(spot2));
	}
	
	@Test
	public void getFollowee() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		follow.setStatus(Follow.Status.SENDED);
		
		Datastore.put(user1, user2, follow);
		
		List<Map<?, ?>> followers = service.getFollowee(account2);
		assertThat(followers.size(), is(1));
		
		UserMeta userMeta = UserMeta.get();
		FollowMeta followMeta = FollowMeta.get(); 
		
		assertThat((Key)followers.get(0).get(followMeta.key), is(follow.getKey()));
		assertThat((Follow.Status)followers.get(0).get(followMeta.status), is(follow.getStatus()));
		assertThat((String)followers.get(0).get(userMeta.account), is(account1));
	}
	
	@Test
	public void updateSpot() throws Exception {
		String account1 = "account1";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		
		Datastore.delete(Datastore.query(User.class).asKeyList());
		Datastore.put(user1);
		
		service.updateSpot(account1, spot2);
		
		assertThat(Datastore.get(User.class, user1.getKey()).getSpot(), is(spot2));
	}
	
	@Test(expected=EntityNotFoundRuntimeException.class)
	public void changeFollowStatus_followNotFound() throws Exception {
		String account1 = "account1";
		User user1 = new User();
		user1.setAccount(account1);
		Datastore.put(user1);
		service.changeFollowStatus(account1,
				Datastore.allocateId(Follow.class), Follow.Status.SENDED);
	}
	
	@Test
	public void changeFollowStatus_pendingToSended() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		String spot3 = "spot3";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		follow.setStatus(Follow.Status.PENDING);
		follow.setLastSpot(spot3);
		
		Datastore.put(user1, user2, follow);
		
		service.changeFollowStatus(account2,
				follow.getKey(),
				Follow.Status.SENDED);
		
		Follow actual = Datastore.get(Follow.class, follow.getKey());
		
		assertThat(actual.getStatus(), is(Follow.Status.SENDED));
		assertThat(actual.getLastSpot(), is(spot3));
	}
	
	@Test
	public void changeFollowStatus_sendedToStoped() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		String spot3 = "spot3";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		follow.setStatus(Follow.Status.SENDED);
		follow.setLastSpot(spot3);
		
		Datastore.put(user1, user2, follow);
		
		service.changeFollowStatus(account2,
				follow.getKey(),
				Follow.Status.STOPED);
		
		Follow actual = Datastore.get(Follow.class, follow.getKey());
		
		assertThat(actual.getStatus(), is(Follow.Status.STOPED));
		assertThat(actual.getLastSpot(), is(spot2));
	}
	
	@Test
	public void changeFollowStatus_stopedToSended() throws Exception {
		String account1 = "account1";
		String account2 = "account2";
		
		String spot1 = "spot1";
		String spot2 = "spot2";
		String spot3 = "spot3";
		
		User user1 = new User();
		user1.setAccount(account1);
		user1.setSpot(spot1);
		User user2 = new User();
		user2.setAccount(account2);
		user2.setSpot(spot2);
		
		Follow follow = new Follow();
		follow.getUserRef().setModel(user1);
		follow.getFollowerRef().setModel(user2);
		follow.setStatus(Follow.Status.STOPED);
		follow.setLastSpot(spot3);
		
		Datastore.put(user1, user2, follow);
		
		service.changeFollowStatus(account2,
				follow.getKey(),
				Follow.Status.SENDED);
		
		Follow actual = Datastore.get(Follow.class, follow.getKey());
		
		assertThat(actual.getStatus(), is(Follow.Status.SENDED));
		assertThat(actual.getLastSpot(), is(spot3));
	}
}
