/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002-2003
*
*	File: ST.java
*
*	Revision;
*
*	01/07/03
*		- first revision.
*
******************************************************************/

package org.cybergarage.upnp.device;

/**
 * @Note 设置服务查询的目标，它必须是下面的类型：
 *
 * -ssdp:all 搜索所有设备和服务
 *
 * -upnp:rootdevice 仅搜索网络中的根设备
 *
 * -uuid:device-UUID 查询UUID标识的设备
 *
 * -urn:schemas-upnp-org:device:device-Type:version 查询device-Type字段指定的设备类型，设备类型和版本由UPNP组织定义。
 *
 * -urn:schemas-upnp-org:service:service-Type:version 查询service-Type字段指定的服务类型，服务类型和版本由UPNP组织定义。
 */
public class ST 
{
	public final static String ALL_DEVICE = "ssdp:all";
	public final static String ROOT_DEVICE = "upnp:rootdevice";
	public final static String UUID_DEVICE = "uuid";
	public final static String URN_DEVICE = "urn:schemas-upnp-org:device:";
	public final static String URN_SERVICE = "urn:schemas-upnp-org:service:";

	public final static boolean isAllDevice(String value)
	{
		if (value == null)
			return false;
		if (value.equals(ALL_DEVICE) == true)
			return true;
		return value.equals("\"" + ALL_DEVICE + "\"");
	}
	
	public final static boolean isRootDevice(String value)
	{
		if (value == null)
			return false;
		if (value.equals(ROOT_DEVICE) == true)
			return true;
		return value.equals("\"" + ROOT_DEVICE + "\"");
	}

	public final static boolean isUUIDDevice(String value)
	{
		if (value == null)
			return false;
		if (value.startsWith(UUID_DEVICE) == true)
			return true;
		return value.startsWith("\"" + UUID_DEVICE);
	}

	public final static boolean isURNDevice(String value)
	{
		if (value == null)
			return false;
		if (value.startsWith(URN_DEVICE) == true)
			return true;
		return value.startsWith("\"" + URN_DEVICE);
	}

	public final static boolean isURNService(String value)
	{
		if (value == null)
			return false;
		if (value.startsWith(URN_SERVICE) == true)
			return true;
		return value.startsWith("\"" + URN_SERVICE);
	}
}

