package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.FollowController;
import com.appspot.dokoitter.server.meta.UserMeta;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FollowControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
    	String account1 = "account1";
    	String account2 = "account2";
    	User user1 = new User();
    	user1.setAccount(account1);
    	User user2 = new User();
    	user2.setAccount(account2);
    	
    	Datastore.put(user1, user2);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(account1));
    	tester.request.setAttribute(UserMeta.get().account.toString(), account2);
    	
        tester.start("/follow");
        FollowController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        System.out.println("FollowControllerTest#run");
        System.out.println(tester.response.getOutputAsString());
        System.out.println("----------------------------------");
    }
}
