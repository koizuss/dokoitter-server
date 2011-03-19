package com.appspot.dokoitter.server.controller;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.UserController;
import com.appspot.dokoitter.server.service.GoogleAuthService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserControllerTest extends ControllerTestCase {
	
    @Test
    public void run() throws Exception {
    	String account = "account";
    	tester.environment.setEmail(GoogleAuthService.getEmail(account));
    	
        tester.start("/user");
        UserController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        System.out.println("UserControllerTest#run");
        System.out.println(tester.response.getOutputAsString());
        System.out.println("----------------------------------");
    }
}
