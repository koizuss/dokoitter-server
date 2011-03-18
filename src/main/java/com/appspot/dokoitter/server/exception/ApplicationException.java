/**
 * 
 */
package com.appspot.dokoitter.server.exception;

/**
 * @author kiyoshiro
 *
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	public enum Type{
		Unknown,
		InputInvalid,
		DatastoerAccessFailed,
		UserNotFound,
		FollowerNotFound, FollowerExists
	}
	
	private Type type = Type.Unknown;
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 
	 */
	public ApplicationException(Type type) {
		this.type = type;
	}

	/**
	 * @param message
	 */
	public ApplicationException(Type type, String message) {
		super(message);
		this.type = type;
	}

	/**
	 * @param cause
	 */
	public ApplicationException(Type type, Throwable cause) {
		super(cause);
		this.type = type;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationException(Type type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

}
