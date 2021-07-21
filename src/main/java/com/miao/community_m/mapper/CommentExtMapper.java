package com.miao.community_m.mapper;


import com.miao.community_m.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}