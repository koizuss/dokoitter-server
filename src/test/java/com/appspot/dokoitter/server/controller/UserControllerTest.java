package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.slim3.tester.TestEnvironment;
import org.junit.Before;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.UserController;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;
import com.google.apphosting.api.ApiProxy;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserControllerTest extends ControllerTestCase {

	TestEnvironment env = new TestEnvironment("test" + "@gmail.com"); 
	
	@Before
	public void setup(){
		ApiProxy.setEnvironmentForCurrentThread(env);
	}
	
    @Test
    public void run() throws Exception {
    	String account = "test-account";
    	User user = new User();
    	user.setAccount(account);
    	Datastore.put(user);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(account));
    	
        tester.start("/user");
        UserController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
