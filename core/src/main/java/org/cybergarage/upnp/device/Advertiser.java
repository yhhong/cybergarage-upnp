/******************************************************************
 * CyberUPnP for Java
 * <p>
 * Copyright (C) Satoshi Konno 2002
 * <p>
 * File: Advertiser.java
 * <p>
 * Revision;
 * <p>
 * 12/24/03
 * - first revision.
 * 06/18/04
 * - Changed to advertise every 25%-50% of the periodic notification cycle for NMPR;
 ******************************************************************/

package org.cybergarage.upnp.device;

import org.cybergarage.upnp.Device;
import org.cybergarage.util.ThreadCore;

/**
 * @Note "刊登广告的客户"————广播
 */
public class Advertiser extends ThreadCore {
    ////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////

    public Advertiser(Device dev) {
        setDevice(dev);
    }

    ////////////////////////////////////////////////
    //	Member
    ////////////////////////////////////////////////

    private Device device;

    public void setDevice(Device dev) {
        device = dev;
    }

    public Device getDevice() {
        return device;
    }

    ////////////////////////////////////////////////
    //	Thread
    ////////////////////////////////////////////////

    public void run() {
        Device dev = getDevice();
        long leaseTime = dev.getLeaseTime();
        long notifyInterval;
        while (isRunnable()) {
//			notifyInterval = (leaseTime/4) + (long)((float)leaseTime * (Math.random() * 0.25f)); // @Note Math.random() < 1; leaseTime/4 < notifyInterval < leaseTime/2
            notifyInterval = leaseTime;
            notifyInterval *= 1000; // @Note 按照默认leaseTime值为30 * 60,则notifyInterval将在7.5分钟到15分钟之间
            try {
                Thread.sleep(notifyInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getThreadObject() != null && !getThreadObject().isInterrupted()) {
                dev.announce();
            }
        }
    }
}
