package array.web;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAddressGetter {

    public String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException exptn) {
            return "No wifi connection";
        }
    }

}
