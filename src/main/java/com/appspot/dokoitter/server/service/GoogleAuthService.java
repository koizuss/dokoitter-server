/**
 * 
 */
package com.appspot.dokoitter.server.service;

import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author kiyoshiro
 *
 */
public class GoogleAuthService {

	private static final String GMAIL_DOMAIN = "@gmail.com";
	
	public static String getAccount(){
		
		return UserServiceFactory
					.getUserService()
					.getCurrentUser()
					.getEmail()
					.replace(GMAIL_DOMAIN, "");
	}
	
	public static String getEmail(String account){
		return account + GMAIL_DOMAIN;
	}
}
