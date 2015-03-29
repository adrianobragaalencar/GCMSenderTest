/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.format;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.yattatech.gcm.gui.RequestFormat;

/**
 * Class which returns a message in plain text format
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 *
 */
public final class PlainTextFormat implements RequestFormat {

	/*
	 * (non-Javadoc)
	 * @see gcm.test.RequestFormat#getRequest(java.lang.String, java.lang.String)
	 */
	@Override
	public HttpEntity getRequest(String registrationId, String message) throws Exception {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("registration_id",   registrationId));
	    nameValuePairs.add(new BasicNameValuePair("delay_while_idle", "true"));
	    nameValuePairs.add(new BasicNameValuePair("data.message",     message));
	    nameValuePairs.add(new BasicNameValuePair("data.time",        String.valueOf(System.currentTimeMillis())));
	    nameValuePairs.add(new BasicNameValuePair("data.agent",       "GCMSenderTest"));	    
	    return new UrlEncodedFormEntity(nameValuePairs);
	}
}
