package com.appspot.dokoitter.server.controller;

import com.appspot.dokoitter.server.AbstractController;
import com.appspot.dokoitter.server.meta.FollowMeta;
import static com.appspot.dokoitter.server.model.Follow.*;

public class StopController extends AbstractController {

	@Override
	protected Object getResponseData() throws Exception {
		return service.changeFollowStatus(account,
				asString(FollowMeta.get().key), Status.STOPED);
	}
}
