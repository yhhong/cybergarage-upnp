import org.cybergarage.net.HostInterface;

import java.net.InetAddress;

/**
 * Created by yinghuihong on 16/4/14.
 */
public class Test {

    public static void main(String... args) {
        InetAddress[] addresses = HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null);
        for (InetAddress address : addresses) {
            System.out.println(address);
        }
        // print: /192.168.43.128
    }
}
