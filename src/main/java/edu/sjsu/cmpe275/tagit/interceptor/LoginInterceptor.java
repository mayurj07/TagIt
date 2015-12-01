package edu.sjsu.cmpe275.tagit.interceptor;

import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.models.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akanksha on 11/28/2015.
 */

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println(" in the interceptor");
        Cookie[] cookies = httpServletRequest.getCookies();
        boolean loggedin=false;
        Map<String,String> cookieMap = new HashMap<String,String>();
        if(cookies!=null)
        {
           for(Cookie cookie:cookies) {
               cookieMap.put(cookie.getName(), cookie.getValue());
               System.out.println(" cookie : "+cookie.getName()+" value :"+cookie.getValue());
           }

            String userid = cookieMap.get("userid");
            String sessionid = cookieMap.get("sessionid");
            if(userid == null || "".equals(userid.trim()) || sessionid == null || "".equals(sessionid.trim())){
                httpServletResponse.sendError(400, "invalid session");
                return false;
            }
            Long idLong = Long.parseLong(userid);
            System.out.println(" the userid is :: "+userid+ " long id is : "+idLong);
            User user = userDao.getUserByUseridAndSessionid(idLong,sessionid);
            if(user!=null)
                loggedin=true;

        }

        return loggedin;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
