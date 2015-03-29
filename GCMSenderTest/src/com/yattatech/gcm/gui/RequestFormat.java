/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.gui;

import org.apache.http.HttpEntity;

/**
 * Interface which represents an request format to be done to GCM service
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 *
 */
public interface RequestFormat {
	
	/**
	 * Method that returns a {@link HttpEntity} which embodies
	 * an specific message to gcm
	 * 
	 * @param registrationId
	 * @param message
	 * @return HttpEntity
	 * @exception if some error has happened while request was constructed
	 * 
	 */
	HttpEntity getRequest(String registrationId, String message)  throws Exception;
}
