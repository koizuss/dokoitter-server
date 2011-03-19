package com.appspot.dokoitter.server.controller.follow;

import com.appspot.dokoitter.server.AbstractController;
import com.appspot.dokoitter.server.meta.FollowMeta;

public class DeleteController extends AbstractController {

	@Override
	protected Object getResponseData() throws Exception {
		return service.deleteFollow(account, asKey(FollowMeta.get().key));
	}
}
