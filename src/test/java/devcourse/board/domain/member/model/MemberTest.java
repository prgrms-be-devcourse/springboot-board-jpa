package devcourse.board.domain.member.model;

import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    void validation_test() {

        assertThrows(ConstraintViolationException.class, () -> {
            Member member = new Member(null);
            memberRepository.save(member); // persist
        });
    }
}