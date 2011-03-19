package com.appspot.dokoitter.server.controller;

import com.appspot.dokoitter.server.AbstractController;
import com.appspot.dokoitter.server.meta.UserMeta;

public class SpotController extends AbstractController {

	@Override
	protected Object getResponseData() throws Exception {
		return service.updateSpot(account, asString(UserMeta.get().spot));
	}
}
