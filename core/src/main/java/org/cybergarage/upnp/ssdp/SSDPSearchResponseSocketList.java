/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002-2003
*
*	File: SSDPSearchResponseSocketList.java
*
*	Revision;
*
*	05/08/03
*		- first revision.
*	05/28/03
*		- Added post() to send a SSDPSearchRequest.
*
******************************************************************/

package org.cybergarage.upnp.ssdp;

import java.net.InetAddress;
import java.util.*;

import org.cybergarage.net.*;

import org.cybergarage.upnp.*;
import org.cybergarage.util.Debug;

public class SSDPSearchResponseSocketList extends Vector 
{
	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	private InetAddress[] binds = null;
	
	public SSDPSearchResponseSocketList() {
	}
	/**
	 * 
	 * @param binds The host to bind.Use <tt>null</tt> for the default behavior
	 */
	public SSDPSearchResponseSocketList(InetAddress[] binds) {
		this.binds = binds;
	}

	
	
	////////////////////////////////////////////////
	//	ControlPoint

	////////////////////////////////////////////////
	//	ControlPoint
	////////////////////////////////////////////////

	public void setControlPoint(ControlPoint ctrlPoint)
	{
		int nSockets = size();
		for (int n=0; n<nSockets; n++) {
			SSDPSearchResponseSocket sock = getSSDPSearchResponseSocket(n);
			sock.setControlPoint(ctrlPoint);
		}
	}

	////////////////////////////////////////////////
	//	get
	////////////////////////////////////////////////
	
	public SSDPSearchResponseSocket getSSDPSearchResponseSocket(int n)
	{
		return (SSDPSearchResponseSocket)get(n);
	}
	
	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////
	
	public boolean open(int port){
		InetAddress[] binds=this.binds ;
		String[] bindAddresses;
		if(binds!=null){			
			bindAddresses = new String[binds.length];
			for (int i = 0; i < binds.length; i++) {
				bindAddresses[i] = binds[i].getHostAddress();
			}
		}else{
			int nHostAddrs = HostInterface.getNHostAddresses();
			bindAddresses = new String[nHostAddrs]; 
			for (int n=0; n<nHostAddrs; n++) {
				bindAddresses[n] = HostInterface.getHostAddress(n);
			}
		}		
		try {
			for (int j = 0; j < bindAddresses.length; j++) {
				if (HostInterface.isIPv4Address(bindAddresses[j])) {	//@Note modify by yinghuihong, 限制为只在IP地址为IP4的情况,减少创建不必要的线程
					SSDPSearchResponseSocket socket = new SSDPSearchResponseSocket(bindAddresses[j], port);
					add(socket);
				}
			}
		}catch (Exception e) {
			stop();
			close();
			clear();
			return false;
		}
		return true;
	}

	public boolean open() 
	{
		return open(SSDP.PORT);
	}
		
	public void close()
	{
		int nSockets = size();
		for (int n=0; n<nSockets; n++) {
			SSDPSearchResponseSocket sock = getSSDPSearchResponseSocket(n);
			sock.close();
		}
		clear();
	}

	////////////////////////////////////////////////
	//	Methods
	////////////////////////////////////////////////
	
	public void start()
	{
		int nSockets = size();
		for (int n=0; n<nSockets; n++) {
			SSDPSearchResponseSocket sock = getSSDPSearchResponseSocket(n);
			sock.start();	//@Note 为获取到的每个ip(IP4或者IP6,这里有三个IP6,一个IP4的)都创建了线程.. 用于监听"设备发现消息响应"
		}
	}

	public void stop()
	{
		int nSockets = size();
		for (int n=0; n<nSockets; n++) {
			SSDPSearchResponseSocket sock = getSSDPSearchResponseSocket(n);
			sock.stop();
		}
	}

	////////////////////////////////////////////////
	//	Methods	@Note Control Point 发起搜索请求
	////////////////////////////////////////////////

	public boolean post(SSDPSearchRequest req)
	{
		boolean ret = true;
		int size = size();
		Debug.message("[SSDPSearchResponseSocketList.java] 发送单播'设备发现消息' POST SSDPSearchRequest, sockets size is "+ size);
		for (int n=0; n < size; n++) {
			SSDPSearchResponseSocket sock = getSSDPSearchResponseSocket(n);	// @Note 为什么有n个?开始时是怎么来的呢?通过获取局域网的ip构造的?
			String sockLocalAddress = sock.getLocalAddress();
//			Debug.message("[SSDPSearchResponseSocketList.java] socket " + n + " localAddress "+ sock.getLocalAddress() + ":" + sock.getLocalPort());
			req.setLocalAddress(sockLocalAddress);
			String ssdpAddr = SSDP.ADDRESS;
			if (HostInterface.isIPv6Address(sockLocalAddress)){
				ssdpAddr = SSDP.getIPv6Address();
//				Debug.message("[SSDPSearchResponseSocketList.java] socket " + n + " address is ipv6, ignore by yinghuihong");
				continue;
			}
			//sock.joinGroup(ssdpAddr, SSDP.PORT, bindAddr);
//			Debug.message("[SSDPSearchResponseSocketList.java] socket " + n + " address is ipv4, execute post");
			if (!sock.post(ssdpAddr, SSDP.PORT, req)){
				ret = false;
			}
			//sock.leaveGroup(ssdpAddr, SSDP.PORT, bindAddr);
		}
		return ret;
	}
	
}

