package com.miao.community_m.controller;

import com.miao.community_m.dto.AccessTokenDTO;
import com.miao.community_m.dto.GithubUser;
import com.miao.community_m.mapper.UserMapper;
import com.miao.community_m.model.User;
import com.miao.community_m.provider.GithubProvider;
import com.miao.community_m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
//                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("user.getName() = " + githubUser.getName());
        if (githubUser!=null && githubUser.getId()!=null){
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
//            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));

            //??????????????????cookie ??? session
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
