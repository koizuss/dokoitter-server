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
    	tester.environment.setEmail(GoogleAuthService.getEmail(account));
    	
    	String followerAccount = "follower-account";
    	
    	User user = new User();
    	user.setAccount(account);
    	User follower = new User();
    	follower.setAccount(followerAccount);
    	Follow follow = new Follow();
    	follow.getUserRef().setModel(user);
    	follow.getFollowerRef().setModel(follower);
    	
    	Datastore.put(user, follower, follow);
    	tester.start("/follower");
        
    	FollowerController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        System.out.println("FollowerControllerTest#run");
        System.out.println(tester.response.getOutputAsString());
        System.out.println("----------------------------------");
    }
}
