package com.appspot.dokoitter.server.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.DokoitterService;
import com.appspot.dokoitter.server.meta.UserMeta;

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
	public void follow() throws Exception {

		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		User follower = new User();
		follower.setAccount(followerAccount);
		Datastore.put(user, follower);

		Follow actual = service.follow(account, followerAccount);

		assertThat(actual, is(not(nullValue())));
		assertThat(actual.getUserRef().getModel().getAccount(), is(account));
		assertThat(actual.getFollowerRef().getModel().getAccount(),
				is(followerAccount));
	}

	@Test(expected = IllegalStateException.class)
	public void follow_followerNotFound() throws Exception {

		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		Datastore.put(user);

		service.follow(account, followerAccount);
	}

	@Test(expected = IllegalStateException.class)
	public void follow_existsFollow() throws Exception {

		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		User follower = new User();
		follower.setAccount(followerAccount);
		Datastore.put(user, follower);

		service.follow(account, followerAccount);
		service.follow(account, followerAccount);
	}

	@Test
	public void follow_retrySuccessful() throws Exception {

		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		User follower = new User();
		follower.setAccount(followerAccount);
		Datastore.put(user, follower);

		tester.tearDown();
		try {
			service.follow(account, followerAccount);
		} catch (Exception e) {
			// NOP
		}
		tester.setUp();

		Datastore.put(user, follower);

		Follow actual = service.follow(account, followerAccount);

		assertThat(actual, is(not(nullValue())));
		assertThat(actual.getUserRef().getModel().getAccount(), is(account));
		assertThat(actual.getFollowerRef().getModel().getAccount(),
				is(followerAccount));
	}

	@Test
	public void getFollower() throws Exception {
		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		User follower = new User();
		follower.setAccount(followerAccount);
		Follow follow = new Follow();
		follow.getUserRef().setModel(user);
		follow.getFollowerRef().setModel(follower);
		Datastore.put(user, follower, follow);

		List<User> actual = service.getFollower(account);

		assertThat(actual.size(), is(1));
		assertThat(actual.get(0).getKey(), is(follower.getKey()));
	}
	
	@Test
	public void getFollower_changeSpotByFollowStaus() throws Exception {
		String account = "test-accout";
		String followerAccount = "follower-account";

		User user = new User();
		user.setAccount(account);
		User follower = new User();
		follower.setAccount(followerAccount);
		Follow follow = new Follow();
		follow.getUserRef().setModel(user);
		follow.getFollowerRef().setModel(follower);
		Datastore.put(user, follower, follow);

		List<User> actual;
		actual = service.getFollower(account);
		assertThat(follow.getStatus(), is(Follow.Status.PENDING));
		assertThat(actual.get(0).getSpot(), is(Follow.Status.PENDING.name()));
		
		String spot = "test-spot";
		follower.setSpot(spot);
		follow.setStatus(Follow.Status.SENDED);
		Datastore.put(follower, follow);
		
		actual = service.getFollower(account);
		assertThat(actual.get(0).getSpot(), is(spot));
		
		String lastSpot = "last-spot";
		follow.setStatus(Follow.Status.STOPED);
		follow.setLastSpot(lastSpot);
		Datastore.put(follow);
		
		actual = service.getFollower(account);
		assertThat(actual.get(0).getSpot(), is(lastSpot));
	}
}
