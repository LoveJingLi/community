package com.miao.community_m.mapper;

import com.miao.community_m.model.Question;

import java.util.List;


public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
}