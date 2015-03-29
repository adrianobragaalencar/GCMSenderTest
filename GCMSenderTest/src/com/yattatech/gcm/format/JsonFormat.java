/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.format;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.yattatech.gcm.gui.RequestFormat;

/**
 * Class which returns a message in json format
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 *
 */
public final class JsonFormat implements RequestFormat {

	/*
	 * (non-Javadoc)
	 * @see gcm.test.RequestFormat#getRequest(java.lang.String, java.lang.String)
	 */
	@Override
	public HttpEntity getRequest(String registrationId, String message) throws Exception {
		
	    final JSONObject json = new JSONObject();	    
	    final JSONObject data = new JSONObject();
	    json.element("registration_ids", new String[] { registrationId });
	    json.element("delay_while_idle", true);
	    data.element("message",          message);
	    data.element("time",             String.valueOf(System.currentTimeMillis()));
	    data.element("agent",            "GCMSenderTest");
	    json.element("data",             data);
	    return new StringEntity(json.toString(), ContentType.create("application/json"));
	}
}
