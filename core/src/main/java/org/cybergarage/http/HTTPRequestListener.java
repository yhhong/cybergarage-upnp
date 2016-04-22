/******************************************************************
*
*	CyberHTTP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: HTTPRequestListener.java
*
*	Revision;
*
*	12/13/02
*		- first revision.
*	
******************************************************************/

package org.cybergarage.http;

public interface HTTPRequestListener
{
	/**
	 * @Note HTTP Server接收到client发来的HTTP请求
	 * @param httpReq
     */
	public void httpRequestReceived(HTTPRequest httpReq);
}
