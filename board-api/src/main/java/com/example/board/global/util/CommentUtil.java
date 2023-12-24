package com.example.board.global.util;

import com.example.board.domain.comment.dto.CommentResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommentUtil {

    private CommentUtil() {
    }

    public static List<CommentResponse> toHierarchyComments(List<CommentResponse> flatList) {
        Map<Long, List<CommentResponse>> groupedByParentId = flatList.stream()
            .filter(comment -> comment.getParentId() != null)
            .collect(Collectors.groupingBy(CommentResponse::getParentId));

        return flatList.stream()
            .filter(comment -> comment.getParentId() == null)
            .peek(comment -> setChildrenRecursively(comment, groupedByParentId))
            .collect(Collectors.toList());
    }

    private static void setChildrenRecursively(CommentResponse comment, Map<Long, List<CommentResponse>> groupedByParentId) {
        List<CommentResponse> children = groupedByParentId.get(comment.getCommentId());
        if (children != null && !children.isEmpty()) {
            comment.setChildren(children);
            children.forEach(child -> setChildrenRecursively(child, groupedByParentId));
        }
    }
}
