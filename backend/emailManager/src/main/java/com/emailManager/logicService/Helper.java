package com.emailManager.logicService;

import com.emailManager.enums.ENUM;
import com.emailManager.enums.FAILED;
import com.emailManager.entity.Message;
import com.emailManager.entity.RemoteHost;
import com.emailManager.enums.SUCCESS;
import com.emailManager.security.CookieJasonWebToken;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class Helper {
    @Value("${properties.HOST_MAX_MSG}")
    private int HOST_MAX_MSG;
    @Value("${properties.todaysMessages}")
    int todaysMessages;
    @Value("${properties.MILLIS}")
    private int MILLIS;
    @Value("${properties.INBOX_DAILY_MAX_MSG}")
    private int INBOX_DAILY_MAX_MSG;
    @Value("${properties.ZERO}")
    private int ZERO;
    @Autowired
    CookieJasonWebToken cookieJasonWebToken;

    /**
     * Helper method to validate the controller's request and message params
     * @param request
     * @param message
     * @return
     */
    public ENUM validate(HttpServletRequest request, Message message){
        if (message == null || message.invalidParams()) {
            return FAILED.INVALID_PARAMETERS;
        }
        else if(hostDailyMaxExceeded(request)){
            return FAILED.REMOTE_HOST_DAILY_MAX_EXCEEDED;
        }

        return SUCCESS.THANKS;
    }


    /**
     * @param request
     * @return true if host max msg exceeded, else false
     */
    public boolean hostDailyMaxExceeded(HttpServletRequest request) {
        String jwts = request.getHeader(cookieJasonWebToken.getJWT_HEADER());
        RemoteHost remoteHost;
        if((remoteHost = cookieJasonWebToken.getJwtRemoteHost(jwts)) == null){
            remoteHost = cookieJasonWebToken.getCookieRemoteHost(request);
        }
        if(remoteHost != null && this.HOST_MAX_MSG <= remoteHost.getTodaysVisits()){
            return true;
        }
       return false;
    }

    /**
     * response: It is a json string wrapper
     * @param msg of type ENUM interface
     * @return {"status":"message"} in JSON format
     */
    public ImmutableMap<String, Object> response(ENUM msg){
        boolean success  = (msg instanceof SUCCESS)?true: false;
        return ImmutableMap.<String, Object>builder().put(SUCCESS.OK.toString(), success).put(SUCCESS.STATUS.toString(), msg.toString()).build();
    }


    /**
     * Adds JwtCookie to the Http response
     * @param request
     * @param response
     */
    public void responseAddJwtToken(HttpServletRequest request, HttpServletResponse response) {
        String header = cookieJasonWebToken.getJWT_HEADER();
        String jwts = request.getHeader(header);
        RemoteHost remoteHost;
        if((remoteHost = cookieJasonWebToken.getJwtRemoteHost(jwts)) == null){
            if((remoteHost = cookieJasonWebToken.getCookieRemoteHost(request))==null)
                remoteHost = new RemoteHost();
        }

        remoteHost.resetLastVisited();
        remoteHost.incrementVisits();
        response.addHeader(header, cookieJasonWebToken.creatJwtToken(remoteHost));
        response.addCookie(cookieJasonWebToken.creatCookieJwt(remoteHost));

        incrementTodayMax();
    }

      /**
     * Increments the todaysVisit variable.
     */
    public void incrementTodayMax() {
        this.todaysMessages++;
    }

}
