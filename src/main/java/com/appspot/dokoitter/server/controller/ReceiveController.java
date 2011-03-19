package com.appspot.dokoitter.server.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.dokoitter.server.meta.FollowMeta;
import com.appspot.dokoitter.server.model.Follow;
import com.appspot.dokoitter.server.service.DokoitterService;
import com.appspot.dokoitter.server.service.GoogleAuthService;
import com.appspot.dokoitter.server.service.JsonService;

public class ReceiveController extends Controller {

    @Override
    public Navigation run() throws Exception {
		new JsonService(new DokoitterService().changeFollowStatus(GoogleAuthService
				.getAccount(), asString(FollowMeta.get().key), Follow.Status.SENDED)).out(response);
        return null;
    }
}
