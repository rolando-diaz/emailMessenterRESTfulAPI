package com.emailManager.logicService;

import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

/**
 * it parses the remote user ip address and host name from the HttpServletRequest param
 */
@Service
public class IpParser {

    /**
     * Default constructor
     */
    public IpParser(){}

    /**
     * @param request
     * @return the remote user Ip address
     */
    public String getClientIpAddress(HttpServletRequest request) {
        String UNKNOWN = "unknown";
        String ip;
        for (String header : HEADERS) {
            ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
                if(ip.contains(",")){
                    ip = ip.split(",")[0];
                }
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * Possible request.getHeathers(HEADERS[i]) params to extract the remoteUser ip address
     */
    private static final String[] HEADERS = {
            "Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_FORWARDED",
            "REMOTE_ADDR",
            "HTTP_CLIENT_IP",
            "X-Forwarded-For"
    };
}
