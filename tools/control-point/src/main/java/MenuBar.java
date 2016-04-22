/******************************************************************
*
*	CyberUPnP for Java
*
*	Copyright (C) Satoshi Konno 2002
*
*	File : ActionDialog.java
*
******************************************************************/

import java.awt.event.*;
import javax.swing.*;

public class MenuBar extends JMenuBar implements ActionListener
{
	CtrlPoint ctrlPoint;

	JMenu fileMenu;
	JMenu searchMenu;
	JMenu logMenu;
	JMenu networkChangedMenu;

	JMenuItem quitItem;
	JMenuItem searchRootDeviceItem;
	JMenuItem clearItem;
	JMenuItem networkChangedItem;
	
	public MenuBar(CtrlPoint ctrlPoint)
	{
		this.ctrlPoint = ctrlPoint;
		
		fileMenu = new JMenu("File");
		add(fileMenu);
		quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(this);
		fileMenu.add(quitItem);

		searchMenu = new JMenu("Search");
		add(searchMenu);
		searchRootDeviceItem = new JMenuItem("upnp:rootdevice");
		searchRootDeviceItem.addActionListener(this);
		searchMenu.add(searchRootDeviceItem);

		logMenu = new JMenu("Log");
		add(logMenu);
		clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(this);
		logMenu.add(clearItem);

		networkChangedMenu = new JMenu("Simulate NetworkChanged");
		add(networkChangedMenu);
		networkChangedItem = new JMenuItem("Restart");
		networkChangedItem.addActionListener(this);
		networkChangedMenu.add(networkChangedItem);

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
			ctrlPoint.search("upnp:rootdevice");
		}
		if (e.getSource() == clearItem) {
			ctrlPoint.clearConsole();
		}
		if (e.getSource() == networkChangedItem) {
			ctrlPoint.start();
		}
	}
}
