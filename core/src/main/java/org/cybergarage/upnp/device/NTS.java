/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File: NTS.java
*
*	Revision;
*
*	12/09/02
*		- first revision.
*
******************************************************************/

package org.cybergarage.upnp.device;

/**
 * @Note 通知消息的子类型，为ssdp:alive 或 ssdp:byebye
 */
public class NTS 
{
	public final static String ALIVE = "ssdp:alive";
	public final static String BYEBYE = "ssdp:byebye";
	public final static String PROPCHANGE = "upnp:propchange";
	
	public final static boolean isAlive(String ntsValue)
	{
		if (ntsValue == null)
			return false;
		return ntsValue.startsWith(NTS.ALIVE);
	}

	public final static boolean isByeBye(String ntsValue)
	{
		if (ntsValue == null)
			return false;
		return ntsValue.startsWith(NTS.BYEBYE);
	}
}

