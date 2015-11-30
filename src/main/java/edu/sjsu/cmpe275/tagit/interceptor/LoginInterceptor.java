package edu.sjsu.cmpe275.tagit.interceptor;

import edu.sjsu.cmpe275.tagit.models.Tag.Tag;
import edu.sjsu.cmpe275.tagit.models.Tag.TagDao;
import edu.sjsu.cmpe275.tagit.models.User.User;
import edu.sjsu.cmpe275.tagit.models.User.UserDao;
import edu.sjsu.cmpe275.tagit.services.Tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
           for(Cookie cookie:cookies)
               cookieMap.put(cookie.getName(),cookie.getValue());

            String userid = cookieMap.get("userid");
            System.out.println(" the userid is :: "+userid);
            if(userid==null)
                return false;

            User user = userDao.findOne(Long.getLong(userid));
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
