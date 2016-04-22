/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: SSDPSearchResponseSocket.java
*
*	Revision;
*
*	11/20/02
*		- first revision.
*	05/28/03
*		- Added post() to send a SSDPSearchRequest.
*	01/31/08
*		- Changed start() not to abort when the interface infomation is null on Android m3-rc37a.
*	
******************************************************************/

package org.cybergarage.upnp.ssdp;

import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.cybergarage.upnp.*;
import org.cybergarage.util.Debug;

/**
 * @Note 属Control Point端;用于控制点端发起搜索后监听来自设备端的响应
 */
public class SSDPSearchResponseSocket extends HTTPUSocket implements Runnable
{
	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////

	public SSDPSearchResponseSocket()
	{
		setControlPoint(null);
	}
	
	public SSDPSearchResponseSocket(String bindAddr, int port) throws BindException
	{
		super(bindAddr, port);
		setControlPoint(null);
	}

	////////////////////////////////////////////////
	//	ControlPoint	
	////////////////////////////////////////////////

	private ControlPoint controlPoint = null;
	
	public void setControlPoint(ControlPoint ctrlp)
	{
		this.controlPoint = ctrlp;
	}

	public ControlPoint getControlPoint()
	{
		return controlPoint;
	}

	////////////////////////////////////////////////
	//	run	
	////////////////////////////////////////////////

	private Thread deviceSearchResponseThread = null;
		
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		
		ControlPoint ctrlPoint = getControlPoint();

		while (deviceSearchResponseThread == thisThread) {
			Thread.yield();
			Debug.message("[SSDPSearchResponseSocket.java] 堵塞监听单播消息中 (监听'设备发现消息响应') DatagramSocket receive ..." + getDatagramSocket().getLocalAddress() + ":" + getDatagramSocket().getLocalPort());
			SSDPPacket packet = receive();
			if (packet == null)
				break;
			if (ctrlPoint != null) {
				Debug.message("[SSDPSearchResponseSocket.java] 监听到'设备发现消息响应' SearchResponseReceived, SSDPPacket:\n" + packet.toString());
				ctrlPoint.searchResponseReceived(packet);
			}
		}
	}
	
	public void start()	{

		StringBuffer name = new StringBuffer("Cyber.SSDPSearchResponseSocket/");
		DatagramSocket s = getDatagramSocket();
		// localAddr is null on Android m3-rc37a (01/30/08)
		InetAddress localAddr = s.getLocalAddress();
		if (localAddr != null) {
			name.append(s.getLocalAddress()).append(':');
			name.append(s.getLocalPort());
		}
		deviceSearchResponseThread = new Thread(this,name.toString());
		deviceSearchResponseThread.start();		
	}
	
	public void stop()
	{
		deviceSearchResponseThread = null;
	}

	////////////////////////////////////////////////
	//	post	@Note 设备发送搜索响应,这里实际上还是通过DatagramPacket封装数据,而不是HTTPPacket,所以接收端也是DatagramPacket数据
	////////////////////////////////////////////////

	public boolean post(String addr, int port, SSDPSearchResponse res)
	{
		return post(addr, port, res.getHeader());// @Note 这里只取HTTP Header String
	}

	////////////////////////////////////////////////
	//	post
	////////////////////////////////////////////////

	public boolean post(String addr, int port, SSDPSearchRequest req)
	{
		return post(addr, port, req.toString());
	}
}

