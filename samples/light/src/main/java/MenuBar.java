/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ActionDialog.java
*
******************************************************************/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener
{
	LightDevice lightDevice;

	JMenu fileMenu;
	JMenu searchMenu;
	JMenu logMenu;

	JMenuItem quitItem;
	JMenuItem searchRootDeviceItem;
	JMenuItem clearItem;
	
	public MenuBar(LightDevice lightDevice)
	{
		this.lightDevice = lightDevice;
		
		fileMenu = new JMenu("File");
		add(fileMenu);
		quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(this);
		fileMenu.add(quitItem);

		searchMenu = new JMenu("RESET");
		add(searchMenu);
		searchRootDeviceItem = new JMenuItem("upnp:rootdevice");
		searchRootDeviceItem.addActionListener(this);
		searchMenu.add(searchRootDeviceItem);

		logMenu = new JMenu("Log");
		add(logMenu);
		clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(this);
		logMenu.add(clearItem);

		setVisible(true);
	}

	////////////////////////////////////////////////
	//	actionPerformed
	////////////////////////////////////////////////

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == quitItem) {
			System.exit(0);
		}
		if (e.getSource() == searchRootDeviceItem) {
//			lightDevice.search("upnp:rootdevice");
			lightDevice.start();
		}
		if (e.getSource() == clearItem) {
//			lightDevice.clearConsole();
		}
	}
}
