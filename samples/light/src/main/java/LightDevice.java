/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ClockDevice.java
*
******************************************************************/

import java.io.*;
import java.awt.*;

import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.*;
import org.cybergarage.upnp.device.*;
import org.cybergarage.upnp.control.*;
import org.cybergarage.util.Debug;

public class LightDevice extends Device implements ActionListener, QueryListener
{
	private final static String DESCRIPTION_FILE_NAME = "/Users/yinghuihong/GIT/JAVA/CyberLink4Java/samples/light/src/main/resources/description/description.xml";

	private StateVariable powerVar;

	public LightDevice() throws InvalidDescriptionException
	{
		super(new File(DESCRIPTION_FILE_NAME));

		Debug.on();

		setLeaseTime(30*60);

		Action getPowerAction = getAction("GetPower");
		getPowerAction.setActionListener(this);
		
		Action setPowerAction = getAction("SetPower");
		setPowerAction.setActionListener(this);
		
		ServiceList serviceList = getServiceList();
		Service service = serviceList.getService(0);
		service.setQueryListener(this);

		powerVar = getStateVariable("Power");

//		Argument powerArg = getPowerAction.getArgument("Power");
//		StateVariable powerState = powerArg.getRelatedStateVariable();
//		AllowedValueList allowList = powerState.getAllowedValueList();
//		for (int n=0; n<allowList.size(); n++) {
//			System.out.println("[" + n + "] = " + allowList.getAllowedValue(n).getValue());
//		}
//		AllowedValueRange allowRange = powerState.getAllowedValueRange();
//		System.out.println("maximum = " + allowRange.getMaximum());
//		System.out.println("minimum = " + allowRange.getMinimum());
//		System.out.println("step = " + allowRange.getStep());
	}

	////////////////////////////////////////////////
	//	Component
	////////////////////////////////////////////////

	private Component comp;
	
	public void setComponent(Component comp)
	{
		this.comp = comp;	
	}
	
	public Component getComponent()
	{
		return comp;
	}
	
	////////////////////////////////////////////////
	//	on/off
	////////////////////////////////////////////////

	/**
	 * @Note 标记当前状态
	 */
	private boolean onFlag = false;

	public boolean isOn()
	{
		return onFlag;
	}

	/**
	 * @Note 更改状态
	 * @param state "on" or "off"
	 * @return 成功或失败
     */
	public boolean setPowerState(String state)
	{
		if (state == null) {
			onFlag = false;
			powerVar.setValue("off");
			return false;
		}
		if (state.compareTo("on") == 0) {
			onFlag = true;
			powerVar.setValue("on");
			return true;
		}
		if (state.compareTo("off") == 0) {
			onFlag = false;
			powerVar.setValue("off");
			return true;
		}
		return false;
	}

	/**
	 * @Note 获取状态
	 * @return  "on" or "off"
     */
	public String getPowerState()
	{
		if (onFlag == true)
			return "on";
		return "off";
	}

	////////////////////////////////////////////////
	// ActionListener	@Note 接收到控制消息,执行操作
	////////////////////////////////////////////////

	public boolean actionControlReceived(Action action)
	{
		String actionName = action.getName();

		boolean ret = false;
		
		if (actionName.equals("GetPower") == true) {
			String state = getPowerState();
			Argument powerArg = action.getArgument("Power");// @Note 返回Power状态变量
			powerArg.setValue(state);// @Note 设置该action对象的数值,再将该action对象作为响应数据
			ret = true;
		}
		if (actionName.equals("SetPower") == true) {
			Argument powerArg = action.getArgument("Power");
			String state = powerArg.getValue();
			boolean rst = setPowerState(state);	// @Note 设置数值,并将变更通知订阅者们,返回操作结果

//			state = getPowerState(); // @Note edit by yinghuihong
			Argument resultArg = action.getArgument("Result");// @Note 返回Result状态变量,成功或失败的数值
			resultArg.setValue(rst? "成功" : "失败");	// @Note 取当前本地数值,作为响应数值
			ret = true;
		}

		comp.repaint();

		return ret;
	}

	////////////////////////////////////////////////
	// QueryListener	@Note 不同于Action,采用StateVariable
	////////////////////////////////////////////////

	public boolean queryControlReceived(StateVariable stateVar)
	{
		stateVar.setValue(getPowerState());// @Note 返回当前状态
		return true;
	}

	////////////////////////////////////////////////
	// update
	////////////////////////////////////////////////

	public void update()
	{
	}			
}

