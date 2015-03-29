/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yattatech.gcm.log.ILogCallback;

final class GCMSender {

	private static final Properties PROPERTIES = new Properties();
	public static ILogCallback logCallback;
	
	public static void send(String channel, String message, String classKey) throws Exception {
				
		loadProperties();
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpPost httpPost     = new HttpPost("https://android.googleapis.com/gcm/send");
		final Class<?> formatClass  = Class.forName(PROPERTIES.getProperty(classKey));
		final RequestFormat format  = (RequestFormat)formatClass.newInstance();
		//HttpHost proxy    = new HttpHost(PROPERTIES.getProperty("proxy.host"), Integer.parseInt(PROPERTIES.getProperty("proxy.port")));						
		//httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);		
		httpPost.setHeader("Accept",        "application/json");
		httpPost.setHeader("Authorization", PROPERTIES.getProperty("auth.key"));					
	    httpPost.setEntity(format.getRequest(channel, message));	    
	    HttpResponse response       = httpClient.execute(httpPost);	    	    	    
	    final String content        = IOUtils.toString(response.getEntity().getContent());
	    System.out.println("Response status " + response.getStatusLine().getStatusCode());
	    if (logCallback != null) {
	    	logCallback.message(content);
	    }
	    System.out.println("Response " + content);	    
	}
	
	private static void loadProperties() throws IOException {
		
		InputStream inStream = null;
		try {
			inStream = GCMSender.class.getResourceAsStream("config.properties");
			PROPERTIES.load(inStream);	
		} finally {
			IOUtils.closeQuietly(inStream);
		}		
	}	
}
