/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: USN.java
*
*	Revision;
*
*	12/09/02
*		- first revision.
*
******************************************************************/

package org.cybergarage.upnp.device;

/**
 * @Note 表示不同服务的统一服务名，它提供了一种标识出相同类型服务的能力
 * @link http://www.cnblogs.com/debin/archive/2009/12/01/1614543.html
 */
public class USN 
{
	public final static String ROOTDEVICE = "upnp:rootdevice";
	
	public final static boolean isRootDevice(String usnValue)
	{
		if (usnValue == null)
			return false;
		return usnValue.endsWith(ROOTDEVICE);
	}
	
	public final static String getUDN(String usnValue)
	{
		if (usnValue == null)
			return "";
		int idx = usnValue.indexOf("::");
		if (idx < 0)
			return usnValue.trim();
		String udnValue = new String(usnValue.getBytes(), 0, idx);
		return udnValue.trim();
	}
}

