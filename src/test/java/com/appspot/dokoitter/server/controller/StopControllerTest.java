package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.meta.FollowMeta;
import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;
import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class StopControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
    	String account1 = "account1";
    	String account2 = "account2";
    	Key key = Datastore.allocateId(Follow.class);
    	
    	User user1 = new User();
    	user1.setAccount(account1);
    	User user2 = new User();
    	user2.setAccount(account2);
    	Follow follow = new Follow();
    	follow.getUserRef().setModel(user1);
    	follow.getFollowerRef().setModel(user2);
    	follow.setStatus(Follow.Status.SENDED);
    	
    	Datastore.put(user1, user2, follow);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(account2));
    	tester.request.setAttribute(FollowMeta.get().key.toString(), Datastore.keyToString(key));
    	
        tester.start("/stop");
        StopController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        System.out.println("StopControllerTest#run");
        System.out.println(tester.response.getOutputAsString());
        System.out.println("----------------------------------");
    }
}
