package devcourse.board.domain.post;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.post.model.*;
import devcourse.board.global.errors.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;

    private final PostRepository postRepository;

    public PostService(MemberService memberService, PostRepository postRepository) {
        this.memberService = memberService;
        this.postRepository = postRepository;
    }

    @Transactional
    public Long createPost(Long memberId, PostCreationRequest creationRequest) {
        Member findMember = memberService.findById(memberId);
        Post newPost = Post.create(findMember, creationRequest.title(), creationRequest.content());
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostResponse getPost(Long postId) {
        return new PostResponse(findById(postId));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    public SimplePostResponses findWithPaging(int startPosition, int maxResultCount) {
        List<SimplePostResponse> simplePostResponses = postRepository.findWithPaging(startPosition, maxResultCount)
                .stream()
                .map(SimplePostResponse::new)
                .toList();

        return new SimplePostResponses(simplePostResponses);
    }

    @Transactional
    public PostResponse updatePost(Long loggedInMemberId, Long postId, PostUpdateRequest updateRequest) {
        Post post = findById(postId);

        if (!post.matchMember(loggedInMemberId)) {
            log.warn(
                    "An unauthorized Member attempted to update post. memberId={}, postId={}",
                    loggedInMemberId,
                    postId
            );
            throw new ForbiddenException(MessageFormat.format(
                    "Member ''{0}'' has no authority to update post.", loggedInMemberId
            ));
        }

        post.updateTitleAndContent(updateRequest.title(), updateRequest.content());

        return new PostResponse(post);
    }

    private Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Post doesn't exist for postId={0}", postId
                )));
    }
}
