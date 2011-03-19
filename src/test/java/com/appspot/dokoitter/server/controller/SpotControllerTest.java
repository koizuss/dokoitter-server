package com.appspot.dokoitter.server.controller;

import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTestCase;
import org.junit.Test;

import com.appspot.dokoitter.server.controller.SpotController;
import com.appspot.dokoitter.server.model.User;
import com.appspot.dokoitter.server.service.GoogleAuthService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SpotControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
    	String account = "test-account";
    	User user = new User();
    	user.setAccount(account);
    	Datastore.put(user);
    	
    	tester.environment.setEmail(GoogleAuthService.getEmail(account));
    	
        tester.start("/spot");
        SpotController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        System.out.println("SpotControllerTest#run");
        System.out.println(tester.response.getOutputAsString());
        System.out.println("----------------------------------");
    }
}
