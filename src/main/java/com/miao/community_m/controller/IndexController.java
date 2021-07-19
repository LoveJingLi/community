package com.miao.community_m.controller;

import com.miao.community_m.dto.PaginationDTO;
import com.miao.community_m.dto.QuestionDTO;
import com.miao.community_m.mapper.QuestionMapper;
import com.miao.community_m.mapper.UserMapper;
import com.miao.community_m.model.Question;
import com.miao.community_m.model.User;
import com.miao.community_m.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model, //HttpServletRequest request,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
//        Cookie[] cookies=request.getCookies();
//        if(cookies!=null && cookies.length!=0){
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    User user=userMapper.findByToken(token);
//                    if(user!=null){
//                        request.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
//        }

        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
