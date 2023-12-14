package com.example.board.domain.email.service;

import static com.example.board.global.exception.ErrorCode.ALREADY_AUTH_KEY_EXIST;
import static com.example.board.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.example.board.global.exception.ErrorCode.MESSAGING_FAILED;
import static com.example.board.global.exception.ErrorCode.SEND_MAIL_FAILED;
import static com.example.board.global.exception.ErrorCode.UNSUPPORTED_ENCODING;

import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.util.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private String hostEmail;

    @Value("${spring.mail.emailExpirationTimeInMilliseconds}")
    private long emailExpirationTime;

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final String authKey = createAuthKey();

    @Transactional
    public void sendMail(String email , String purpose) {
        if(purpose.equals("RESET-PASSWORD")) {
            memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        }
        if (redisService.existData(email + ":UPDATE-PASSWORD")) {
            throw new CustomException(ALREADY_AUTH_KEY_EXIST);
        }
        setMailMessageAndSendMail(email, purpose);
    }

    public MimeMessage createMessage(String email, String purpose) throws MessagingException, UnsupportedEncodingException {
        return setMessage(email, purpose);
    }

    private String createAuthKey() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new CustomException(SEND_MAIL_FAILED);
        }
    }

    private void setMailMessageAndSendMail(String email, String purpose){
        MimeMessage message;
        try {
            message = createMessage(email, purpose);
        } catch (MessagingException e) {
            throw new CustomException(MESSAGING_FAILED);
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(UNSUPPORTED_ENCODING);
        }

        try {
            javaMailSender.send(message);
            redisService.setEmail(email, authKey, emailExpirationTime, purpose);
        } catch (Exception e) {
            throw new CustomException(SEND_MAIL_FAILED);
        }
    }

    private MimeMessage setMessage(String email, String purpose) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        String msgg = "";

        switch (purpose) {
            case "RESET-PASSWORD":
                message.setSubject("[Zerozae] 비밀번호 재설정 인증코드 입니다.");
                msgg += "<h1>안녕하세요</h1>";
                msgg += "<h1>Zerozae의 게시판 입니다.</h1>";
                msgg += "<br>";
                msgg += "<p>아래 인증코드를 비밀번호 재설정 페이지에 입력해주세요</p>";
                msgg += "<br>";
                msgg += "<br>";
                msgg += "<div align='center' style='border:1px solid black'>";
                msgg += "<h3 style='color:blue'>비밀번호 재설정을 하기 위한 사전인증코드 입니다</h3>";
                msgg += "<div style='font-size:130%'>";
                msgg += "<strong>" + authKey + "</strong></div><br/>";
                msgg += "</div>";
                break;

            case "SIGN-UP":
                message.setSubject("[Zerozae] 게시판 회원가입 인증코드 입니다.");
                msgg += "<h1>안녕하세요</h1>";
                msgg += "<h1>Zerozae의 게시판 입니다.</h1>";
                msgg += "<br>";
                msgg += "<p>아래 인증코드를 회원가입 페이지에 입력해주세요</p>";
                msgg += "<br>";
                msgg += "<br>";
                msgg += "<div align='center' style='border:1px solid black'>";
                msgg += "<h3 style='color:blue'>회원가입 인증코드 입니다</h3>";
                msgg += "<div style='font-size:130%'>";
                msgg += "<strong>" + authKey + "</strong></div><br/>" ;
                msgg += "</div>";
                break;

            case "UPDATE-PASSWORD":
                message.setSubject("[Zerozae] 게시판 비밀번호 변경 주기 알림 메일 입니다.");
                msgg += "<h1>안녕하세요</h1>";
                msgg += "<h1>Zerozae의 게시판 입니다.</h1>";
                msgg += "<br>";
                msgg += "<p>회원님은 지난 6개월간 비밀번호 변경을 하지 않으셨습니다.</p>";
                msgg += "<br>";
                msgg += "<br>";
                msgg += "<div align='center' style='border:1px solid black'>";
                msgg += "<h3 style='color:blue'>비밀번호 변경을 권장 드립니다.</h3>";
                msgg += "</div>";
                break;
        }
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress(hostEmail, "zerozae"));
        return message;
    }
}
