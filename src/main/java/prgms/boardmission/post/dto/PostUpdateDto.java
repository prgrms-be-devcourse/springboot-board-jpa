package prgms.boardmission.post.dto;

import java.time.LocalDateTime;

import static prgms.boardmission.post.dto.PostUpdateDto.*;

public sealed interface PostUpdateDto permits Request, Response {
    record Request(String title, String content) implements PostUpdateDto {
    }

    record Response(long postId, LocalDateTime updatedAt) implements PostUpdateDto {
    }
}
