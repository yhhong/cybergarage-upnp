/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: Subscriber.java
*
*	Revision;
*
*	01/29/03
*		- first revision.
*	07/31/04
*		- Added isExpired().
*	10/26/04
*		- Oliver Newell <newell@media-rush.com>
*		- Added support the intinite time and fixed a bug in isExpired().
*	
******************************************************************/

package org.cybergarage.upnp.event;

import java.net.*;

public class Subscriber
{
	////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////
	
	public Subscriber()
	{
		renew();
	}

	////////////////////////////////////////////////
	//	SID
	////////////////////////////////////////////////

	private String SID = null;

	public String getSID() {
		return SID;
	}

	public void setSID(String sid) {
		SID = sid;
	}

	////////////////////////////////////////////////
	//	deliveryURL
	////////////////////////////////////////////////

	private String ifAddr = "";
	
	public void setInterfaceAddress(String addr)
	{
		ifAddr = addr;
	}
	
	public String getInterfaceAddress()
	{
		return ifAddr;
	}
	
	////////////////////////////////////////////////
	//	deliveryURL
	////////////////////////////////////////////////

	private String deliveryURL = "";

	public String getDeliveryURL() {
		return deliveryURL;
	}

	public void setDeliveryURL(String deliveryURL) {
		this.deliveryURL = deliveryURL;
		try {
			URL url = new URL(deliveryURL);
			deliveryHost = url.getHost();
			deliveryPath = url.getPath();
			deliveryPort = url.getPort();
		}
		catch (Exception e) {}
	}

	private String deliveryHost = "";
	private String deliveryPath = "";
	private int deliveryPort = 0;

	public String getDeliveryHost() {
		return deliveryHost;
	}

	public String getDeliveryPath() {
		return deliveryPath;
	}

	public int getDeliveryPort() {
		return deliveryPort;
	}

	
	////////////////////////////////////////////////
	//	Timeout
	////////////////////////////////////////////////

	private long timeOut = 0;
	
	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long value) {
		timeOut = value;
	}

	/**
	 * @Note 订阅者是否已过期
	 * @return true为已过期
     */
	public boolean isExpired()
	{
		long currTime = System.currentTimeMillis();
		
		// Thanks for Oliver Newell (10/26/04)
		if(timeOut == Subscription.INFINITE_VALUE ) 
			return false; 
			
		// Thanks for Oliver Newell (10/26/04)
		long expiredTime = getSubscriptionTime() + getTimeOut()*1000;	//@Note 订阅时间+有效时长 对比 当前时间
		if (expiredTime < currTime)
			return true;
			
		return false;
	}
	
	////////////////////////////////////////////////
	//	SubscriptionTIme	@Note 订阅事件
	////////////////////////////////////////////////

	private long subscriptionTime = 0;
	
	public long getSubscriptionTime() {
		return subscriptionTime;
	}

	public void setSubscriptionTime(long time) {
		subscriptionTime = time;
	}

	////////////////////////////////////////////////
	//	SEQ		@Note 计数"已通知次数"
	////////////////////////////////////////////////

	private long notifyCount = 0;

	public long getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(int cnt) {
		notifyCount = cnt;
	}

	public void incrementNotifyCount() {
		if (notifyCount == Long.MAX_VALUE) {
			notifyCount = 1;
			return;
		}
		notifyCount++;
	}
	
	////////////////////////////////////////////////
	//	renew	@Note 重置订阅时间与通知次数
	////////////////////////////////////////////////
	
	public void renew()
	{
		setSubscriptionTime(System.currentTimeMillis());
		setNotifyCount(0);
	}

}
