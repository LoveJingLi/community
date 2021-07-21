package com.miao.community_m.controller;

import com.miao.community_m.dto.CommentDTO;
import com.miao.community_m.dto.QuestionDTO;
import com.miao.community_m.enums.CommentTypeEnum;
import com.miao.community_m.mapper.QuestionExtMapper;
import com.miao.community_m.service.CommentService;
import com.miao.community_m.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
//        Long questionId = null;
//        try {
//            questionId = Long.parseLong(id);
//        } catch (NumberFormatException e) {
//            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
//        }
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
//        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
//        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
