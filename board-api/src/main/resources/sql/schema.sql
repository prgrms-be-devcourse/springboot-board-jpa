-- 회원의 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS member (
    member_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- 각 회원의 고유 식별자
    email VARCHAR(25) NOT NULL UNIQUE, -- 회원 이메일 주소 (고유)
    password VARCHAR(255) NOT NULL, -- 회원 비밀번호
    name VARCHAR(15) NOT NULL, -- 회원 이름
    age INT NOT NULL, -- 회원 나이
    hobby VARCHAR(255) NOT NULL, -- 회원 취미
    is_deleted BIT(1) NOT NULL DEFAULT FALSE, -- 회원 삭제 여부
    last_updated_password TIMESTAMP(6), -- 회원의 마지막 비밀번호 변경 시점
    created_at TIMESTAMP(6), -- 회원 생성 시각
    updated_at TIMESTAMP(6), -- 회원 수정 시각
    updated_by VARCHAR(25), -- 회원을 마지막으로 수정한 수정자
    INDEX idx_email (email) -- 이메일을 참조하는 인덱스 추가
);

-- 게시물 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS post (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 각 게시물 고유 식별자
    title VARCHAR(50) NOT NULL, -- 게시물 제목
    content VARCHAR(255) NOT NULL, -- 게시물 내용
    view INT DEFAULT 0, -- 게시물 조회수
    created_at TIMESTAMP(6), -- 게시물 생성 시각
    updated_at TIMESTAMP(6), -- 게시물 수정 시각
    updated_by VARCHAR(25), -- 게시물을 마지막으로 수정한 수정자
    member_id INT UNSIGNED NOT NULL, -- 회원을 참조하는 외래 키
    INDEX idx_member_id (member_id), -- 회원을 참조하는 외래 키에 대한 인덱스 추가
    INDEX idx_title (title) -- 제목을 참조하는 인덱스 추가
);

-- 역할 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS role (
    role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- 각 역할의 고유 식별자
    role_type VARCHAR(10) NOT NULL -- 역할의 유형 (USER , ADMIN ..)
);

-- 회원과 역할 간의 관계를 저장하는 테이블
CREATE TABLE IF NOT EXISTS member_role (
    member_role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- 각 회원-역할 관계의 고유 식별자
    member_id INT UNSIGNED, -- 회원을 참조하는 외래 키
    role_id INT UNSIGNED -- 역할을 참조하는 외래 키
);

-- 댓글 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS comment (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, -- 각 댓글의 고유 식별자
     content VARCHAR(255) NOT NULL, -- 댓글 내용
     member_id INT, -- 회원을 참조하는 외래 키
     post_id INT, -- 게시물을 참조하는 외래 키
     parent_id INT, -- 부모 댓글을 참조하는 외래 키
     created_at TIMESTAMP(6), -- 댓글 생성 시각
     updated_at TIMESTAMP(6), -- 댓글 수정 시각
     updated_by VARCHAR(25), -- 댓글을 마지막으로 업데이트한 수정자
     INDEX idx_post_id (post_id) -- 게시물을 참조하는 외래 키에 대한 인덱스 추가
);

-- 이메일 인증 정보를 저장하는 테이블
CREATE TABLE IF NOT EXISTS email_auth (
    auth_key VARCHAR(255) NOT NULL PRIMARY KEY , -- 이메일 인증 키
    email VARCHAR(25) NOT NULL, -- 이메일 주소
    purpose VARCHAR(15) NOT NULL -- 인증 목적
);
