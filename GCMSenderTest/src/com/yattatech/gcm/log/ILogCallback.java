/*
 * Copyright (c) 2011, Yatta Tech and/or its affiliates. All rights reserved.
 * YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yattatech.gcm.log;

/**
 * Interface which represents an object interested to be notified about some
 * log operation
 * 
 * @author Adriano Braga Alencar (adrianobragaalencar@gmail.com)
 *
 */
public interface ILogCallback {
	
	void message(String message);
}
