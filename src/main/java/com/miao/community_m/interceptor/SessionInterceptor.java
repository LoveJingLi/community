package com.miao.community_m.interceptor;

import com.miao.community_m.mapper.UserMapper;
import com.miao.community_m.model.User;
import com.miao.community_m.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private NotificationService notificationService;
//    @Autowired
//    private AdService adService;

//    @Value("${github.redirect.uri}")
//    private String githubRedirectUri;
//
//    @Value("${gitee.redirect.uri}")
//    private String giteeRedirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
//        request.getServletContext().setAttribute("giteeRedirectUri", giteeRedirectUri);
//        request.getServletContext().setAttribute("githubRedirectUri", githubRedirectUri);
//        // 没有登录的时候也可以查看导航
//        for (AdPosEnum adPos : AdPosEnum.values()) {
//            request.getServletContext().setAttribute(adPos.name(), adService.list(adPos.name()));
//        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
//                    User user = userMapper.findByToken(token);
//                    if (user != null) {
                    if (users.size() != 0) {
//                        HttpSession session = request.getSession();
//                        session.setAttribute("user", users.get(0));
//                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
//                        session.setAttribute("unreadCount", unreadCount);
                        request.getSession().setAttribute("user",users.get(0));
//                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
