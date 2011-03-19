package com.appspot.dokoitter.server.controller;

import com.appspot.dokoitter.server.AbstractController;

public class FollowerController extends AbstractController {

	@Override
	protected Object getResponseData() throws Exception {
		return service.getFollower(account);
	}
}
