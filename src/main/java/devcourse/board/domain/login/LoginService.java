package devcourse.board.domain.login;

import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long login(LoginRequest loginRequest) {
        Optional<Member> findMember = memberRepository.findByEmailAndPassword(
                loginRequest.email(),
                loginRequest.password()
        );

        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다.");
        }

        return findMember.get()
                .getId();
    }
}
