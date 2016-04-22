//package org.cybergarage.net;
//
///**
// * Created by yinghuihong on 16/4/14.
// */
//public class Test {
//    public static void main(String... args) throws InterruptedException {
//
//        int nHostAddrs = HostInterface.getNHostAddresses();
//        String[] bindAddresses = new String[nHostAddrs];
//        for (int n = 0; n < nHostAddrs; n++) {
//            bindAddresses[n] = HostInterface.getHostAddress(n);
//        }
//        for (int j = 0; j < bindAddresses.length; j++) {
//            System.out.println("bindAddress " + bindAddresses[j]);
//        }
//
////        System.out.println("time " + System.currentTimeMillis());
////        Thread.sleep(120 * 1000);
////        System.out.println("time " + System.currentTimeMillis());
//
//        long leaseTime = 30 * 60;
//        double random = Math.random();
//        long notifyInterval = (leaseTime / 4) + (long) ((float) leaseTime * (random * 0.25f));
//        System.out.println("random " + random);
//        System.out.println("notifyInterval " + notifyInterval);
//    }
//}
