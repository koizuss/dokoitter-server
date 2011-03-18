package com.appspot.dokoitter.server.service;

import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.exception.ApplicationException;
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
	public void getUser() throws ApplicationException {
		String account = "test-account";

		User user = new User();
		user.setAccount(account);
		Datastore.put(user);

		User actual = service.getUser(account);

		assertThat(actual.getAccount(), is(user.getAccount()));
		assertThat(actual.getKey(), is(user.getKey()));
	}

	@Test(expected = IllegalStateException.class)
	public void getUser_notFound() throws ApplicationException {
		String account = "test-account";
		service.getUser(account);
	}

	@Test
	public void getUser_filterTest() throws ApplicationException {
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
}
