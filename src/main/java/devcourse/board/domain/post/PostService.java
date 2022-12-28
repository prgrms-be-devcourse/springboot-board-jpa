package devcourse.board.domain.post;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.post.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final MemberService memberService;

    private final PostRepository postRepository;

    public PostService(MemberService memberService, PostRepository postRepository) {
        this.memberService = memberService;
        this.postRepository = postRepository;
    }

    @Transactional
    public Long createPost(PostCreationRequest creationRequest) {
        Member findMember = memberService.findOne(creationRequest.memberId());
        Post newPost = Post.createPost(findMember, creationRequest.title(), creationRequest.content());
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostResponse findOneAsDto(Long postId) {
        return new PostResponse(findOne(postId));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    public MultiplePostResponse findWithPaging(int startPosition, int maxResultCount) {
        List<PostResponse> postResponses = postRepository.findWithPaging(startPosition, maxResultCount)
                .stream()
                .map(PostResponse::new)
                .toList();

        return new MultiplePostResponse(postResponses);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostUpdateRequest updateRequest) {
        Post post = findOne(postId);

        post.updateContents(updateRequest.title(), updateRequest.content());

        return new PostResponse(post);
    }

    private Post findOne(Long postId) {
        return postRepository.findOne(postId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Post doesn't exist for postId={0}", postId
                )));
    }
}
