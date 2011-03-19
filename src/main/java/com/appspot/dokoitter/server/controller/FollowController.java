package com.appspot.dokoitter.server.controller;

import com.appspot.dokoitter.server.AbstractController;
import com.appspot.dokoitter.server.meta.UserMeta;

public class FollowController extends AbstractController {

	@Override
	protected Object getResponseData() throws Exception {
		return service.putFollow(account, asString(UserMeta.get().account));
	}
}
