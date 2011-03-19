/**
 * 
 */
package com.appspot.dokoitter.server;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.appspot.dokoitter.server.service.DokoitterService;
import com.appspot.dokoitter.server.service.GoogleAuthService;
import com.appspot.dokoitter.server.service.JsonService;

/**
 * @author kiyoshiro
 *
 */
public abstract class AbstractController extends Controller {

	protected JsonService json = new JsonService();
	protected DokoitterService service = new DokoitterService();
	protected String account = GoogleAuthService.getAccount();
	
	/*
	 * (non-Javadoc)
	 * @see org.slim3.controller.Controller#run()
	 */
	@Override
	public Navigation run() throws Exception {
		json.setData(getResponseData());
		json.setSuccess(true);
		json.out(response);
		return null;
	}
	
	/*
	 * レスポンス用のデータを取得する。
	 */
	protected abstract Object getResponseData() throws Exception;
	
	/*
	 * (non-Javadoc)
	 * @see org.slim3.controller.Controller#handleError(java.lang.Throwable)
	 */
	@Override
	public Navigation handleError(Throwable error) throws Throwable {
		json.setData(error.getMessage());
		json.setSuccess(false);
		json.out(response);
		return null;
	}
}
