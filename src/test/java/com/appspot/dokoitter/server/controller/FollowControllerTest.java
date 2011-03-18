package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.FollowController;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FollowControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
    	User user = new User();
    	user.setAccount("user");
    	
    	User follower = new User();
    	follower.setAccount("follower");
    	
    	Datastore.put(user, follower);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(user.getAccount()));
    	
    	tester.request.addParameter("key", Datastore.keyToString(follower.getKey()));
        tester.start("/follow");
        FollowController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
