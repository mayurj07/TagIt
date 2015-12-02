package edu.sjsu.cmpe275.tagit.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        System.out.println(" ********************* in the interceptor");
        Cookie[] cookies = httpServletRequest.getCookies();
//        System.out.println(":::;;; getting the cookie value::::"+cookies[2].getValue());
        boolean loggedin=false;

        ObjectMapper mapper = new ObjectMapper();

        Map<String,String> cookieMap = new HashMap<String,String>();
        if(cookies!=null)
        {
           for(Cookie cookie:cookies) {
//               cookieMap.put(cookie.getName(), cookie.getValue());
               System.out.println(" key: "+cookie.getName()+", value: "+cookie.getValue());
           }

          User cookieUserObj = mapper.readValue(cookies[0].getValue(), User.class);

            long userid = cookieUserObj.getUserid();
            long sessionid = cookieUserObj.getSessionid();
            System.out.println("userid: " + userid + " ,sessionid: " + sessionid);
//            String user = cookieMap.get("user");
//            String[] userArr = user.split("%22");
//            for(String s:userArr)
//                System.out.print(s);
//            System.out.println("");


//           String userid = cookieMap.get("userid");
//           String sessionid = cookieMap.get("sessionid");
           if(userid == 0L || sessionid == 0L ){
              httpServletResponse.sendError(400, "Invalid session. Please Login again.");
               return false;
            }
            User loggedInUser = userDao.getUserByUseridAndSessionid(userid, sessionid);
            if(loggedInUser!=null)
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
