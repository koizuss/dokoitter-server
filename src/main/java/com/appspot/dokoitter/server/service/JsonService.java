package com.appspot.dokoitter.server.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

import net.arnx.jsonic.JSON;

public class JsonService {

	private static final String TIME_STAMP_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String CONTENT_TYPE = "application/json";
	private static final String CHARSET = "UTF-8";

	private JSON json = new JSON() {
		// フォーマット可能なクラスに変換します（formatでのみ有効です）。
		// 例外が発生した場合、JSONExceptionでラップされ呼び出し元に通知されます。
		@SuppressWarnings("unchecked")
		protected Object preformat(Context context, Object value)
				throws Exception {
			if(value instanceof Key){
				return Datastore.keyToString((Key)value);
			}
			if(value instanceof Date){
				return new SimpleDateFormat(TIME_STAMP_FORMAT).format((Date)value);
			}
			if(value instanceof ModelRef){
				ModelRef modelRef = (ModelRef)value;
				return modelRef.getModelClass().cast(modelRef.getModel());
			}
			if(value instanceof Enum){
				return ((Enum)value).name();
			}
			
			return super.preformat(context, value);
		}
	};
	
	private Date timestamp = new Date();
	private boolean isSuccess = true;
	private Object data;
	
	public JsonService(){
	}
	
	public JsonService(Object data){
		this.data = data;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	public String toJson() {
		return json.format(this);
	}
	
	public void out(HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(CHARSET);
		response.getWriter().print(this.toJson());
		response.flushBuffer();
	}

}
