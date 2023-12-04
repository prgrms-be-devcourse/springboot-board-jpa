package jehs.springbootboardjpa.controller;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostResponse;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/v1/posts/{id}")
    }

    @GetMapping("/api/v1/posts")
    }

    @GetMapping("/api/v1/posts/cursor")
    }

    @PostMapping("/api/v1/posts")
        postService.createPost(postCreateRequest);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 게시글이 생성되었습니다."), HttpStatus.CREATED);
    }

    @PatchMapping("/api/v1/posts/{id}")
        postService.updatePost(postId, postUpdateRequest);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 게시글이 수정되었습니다."), HttpStatus.OK);
    }
}
