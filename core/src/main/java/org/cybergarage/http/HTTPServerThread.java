/******************************************************************
*
*	CyberHTTP for Java
*
*	Copyright (C) Satoshi Konno 2002-2003
*
*	File: HTTPServerThread.java
*
*	Revision;
*
*	10/10/03
*		- first revision.
*
******************************************************************/

package org.cybergarage.http;

import org.cybergarage.util.Debug;

import java.net.Socket;

/**
 * @Note HTTPServer接收到client的HTTP请求后,另建立一线程专为该请求提供"服务",先进行数据报读取,通知请求监听者去执行相关业务逻辑,然后返回响应数据报
 */
public class HTTPServerThread extends Thread
{
	private HTTPServer httpServer;
	private Socket sock;

	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////

	public HTTPServerThread(HTTPServer httpServer, Socket sock)
	{
        super("Cyber.HTTPServerThread");
		this.httpServer = httpServer;
		this.sock = sock;
	}

	////////////////////////////////////////////////
	//	run
	////////////////////////////////////////////////

	public void run()
	{
		HTTPSocket httpSock = new HTTPSocket(sock);
		if (!httpSock.open())
			return;
		HTTPRequest httpReq = new HTTPRequest();
		httpReq.setSocket(httpSock);
		while (httpReq.read()) {
			Debug.message("[HTTPServerThread.java] 接收到HTTP请求, 读取并执行监听回调, HTTP请求内容 :\n" + httpReq.toString());
			httpServer.performRequestListener(httpReq);
			if (!httpReq.isKeepAlive())
				break;
		}
		httpSock.close();
	}
}
