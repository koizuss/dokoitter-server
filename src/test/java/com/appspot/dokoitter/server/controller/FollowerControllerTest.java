package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.FollowerController;
import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FollowerControllerTest extends ControllerTestCase {

	@Test
    public void run() throws Exception {
    	String account = "test-account";
    	User user = new User();
    	user.setAccount(account);
    	Datastore.put(user);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(account)); 
    	
    	Follow follower1 = new Follow();
    	follower1.getUserRef().setModel(user);
    	
    	Follow follower2 = new Follow();
    	follower2.getUserRef().setModel(user);
    	follower2.setStatus(Follow.Status.SENDED);
    	
    	Datastore.put(user, follower1, follower2);
    	
    	tester.start("/follower");
        FollowerController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
