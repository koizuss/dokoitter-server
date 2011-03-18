package com.appspot.dokoitter.server.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.service.DokoitterService;
import com.appspot.dokoitter.server.service.GoogleAuthService;
import com.appspot.dokoitter.server.service.JsonService;

public class StopController extends Controller {

    @Override
    public Navigation run() throws Exception {
		new JsonService(new DokoitterService().changeFollowStatus(GoogleAuthService
				.getAccount(), asString("key"), Follow.Status.STOPED)).out(response);
        return null;
    }
}
