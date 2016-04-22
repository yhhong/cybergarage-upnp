/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002-2003
*
*	File : SampleClock.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import org.cybergarage.util.*;

import org.cybergarage.upnp.device.*;

public class LightFrame extends JFrame implements WindowListener 
{
	private final static String TITLE = "CyberLink Sample Light";
	private LightDevice lightDev;
	private LightPane lightPane;
	private MenuBar menuBar;
	
	public LightFrame()
	{
		super(TITLE);

		try {
			lightDev = new LightDevice();
		}
		catch (InvalidDescriptionException e) {
			Debug.warning(e);
		}
				
		getContentPane().setLayout(new BorderLayout());

		menuBar = new MenuBar(lightDev);
		setJMenuBar(menuBar);

		lightPane = new LightPane();
		lightPane.setDevice(lightDev);
		lightDev.setComponent(lightPane);
		getContentPane().add(lightPane, BorderLayout.CENTER);

		addWindowListener(this);
		
		pack();
		setVisible(true);
		
		lightDev.start();
	}

	public LightPane getClockPanel()
	{
		return lightPane;
	}

	public LightDevice getClockDevice()
	{
		return lightDev;
	}
		
	////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////
	
	public void windowActivated(WindowEvent e) 
	{
	}
	
	public void windowClosed(WindowEvent e) 
	{
	}
	
	public void windowClosing(WindowEvent e) 
	{
		lightDev.stop();
		System.exit(0);
	}
	
	public void windowDeactivated(WindowEvent e) 
	{
	}
	
	public void windowDeiconified(WindowEvent e) 
	{
	}
	
	public void windowIconified(WindowEvent e) 
	{
	}
	
	public void windowOpened(WindowEvent e)
	{
	}
	

	////////////////////////////////////////////////
	//	main
	////////////////////////////////////////////////

	public static void main(String args[]) throws IOException {
		//Debug.on();
//		System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
//		File directory = new File("");//设定为当前文件夹
//		System.out.println(directory.getCanonicalPath());//获取标准的路径
//		System.out.println(directory.getAbsolutePath());//获取绝对路径
		LightFrame sampClock = new LightFrame();
	}

}

