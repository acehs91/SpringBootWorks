-- ================================================
-- MVP-DevMatch 데이터베이스 스키마
-- 개발자 실력평가 및 채용 매칭 플랫폼
-- Oracle Database
-- ================================================

-- 기존 테이블 삭제 (개발용)
-- DROP TABLE dm_message CASCADE CONSTRAINTS;
-- DROP TABLE dm_application CASCADE CONSTRAINTS;
-- DROP TABLE dm_job_posting CASCADE CONSTRAINTS;
-- DROP TABLE dm_resume CASCADE CONSTRAINTS;
-- DROP TABLE dm_member CASCADE CONSTRAINTS;
-- DROP SEQUENCE dm_member_seq;
-- DROP SEQUENCE dm_resume_seq;
-- DROP SEQUENCE dm_job_posting_seq;
-- DROP SEQUENCE dm_application_seq;
-- DROP SEQUENCE dm_message_seq;

-- ================================================
-- 1. 회원 테이블 (dm_member)
-- ================================================
CREATE TABLE dm_member (
    member_id       NUMBER PRIMARY KEY,
    email           VARCHAR2(100) NOT NULL UNIQUE,
    password        VARCHAR2(255) NOT NULL,
    name            VARCHAR2(50) NOT NULL,
    member_type     VARCHAR2(20) NOT NULL,  -- 'DEVELOPER' 또는 'CLIENT'
    phone           VARCHAR2(20),
    profile_image   VARCHAR2(500),
    provider        VARCHAR2(20),           -- 소셜로그인: 'google', 'kakao', 'naver'
    provider_id     VARCHAR2(100),          -- 소셜로그인 ID
    status          VARCHAR2(20) DEFAULT 'ACTIVE',  -- 'ACTIVE', 'INACTIVE', 'BANNED'
    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE DEFAULT SYSDATE
);

CREATE SEQUENCE dm_member_seq START WITH 1 INCREMENT BY 1;

COMMENT ON TABLE dm_member IS '회원 정보';
COMMENT ON COLUMN dm_member.member_type IS '회원유형: DEVELOPER(개발자), CLIENT(의뢰인)';

-- ================================================
-- 2. 이력서 테이블 (dm_resume)
-- ================================================
CREATE TABLE dm_resume (
    resume_id       NUMBER PRIMARY KEY,
    member_id       NUMBER NOT NULL,
    title           VARCHAR2(200) NOT NULL,
    introduction    CLOB,                   -- 자기소개
    skills          VARCHAR2(1000),         -- 기술스택 (쉼표 구분)
    experience      CLOB,                   -- 경력사항
    education       VARCHAR2(500),          -- 학력
    portfolio_url   VARCHAR2(500),          -- 포트폴리오 링크
    github_url      VARCHAR2(500),          -- GitHub 링크
    salary_type     VARCHAR2(20),           -- 'MONTHLY', 'HOURLY', 'PROJECT'
    salary_amount   NUMBER,                 -- 희망 급여
    is_public       CHAR(1) DEFAULT 'Y',    -- 공개여부
    view_count      NUMBER DEFAULT 0,
    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE DEFAULT SYSDATE,
    CONSTRAINT fk_resume_member FOREIGN KEY (member_id) 
        REFERENCES dm_member(member_id) ON DELETE CASCADE
);

CREATE SEQUENCE dm_resume_seq START WITH 1 INCREMENT BY 1;

COMMENT ON TABLE dm_resume IS '개발자 이력서';

-- ================================================
-- 3. 채용공고 테이블 (dm_job_posting)
-- ================================================
CREATE TABLE dm_job_posting (
    posting_id      NUMBER PRIMARY KEY,
    member_id       NUMBER NOT NULL,        -- 의뢰인 ID
    title           VARCHAR2(200) NOT NULL,
    company_name    VARCHAR2(100),
    description     CLOB NOT NULL,          -- 업무 설명
    requirements    CLOB,                   -- 자격요건
    skills          VARCHAR2(1000),         -- 요구 기술스택 (쉼표 구분)
    location        VARCHAR2(200),          -- 근무지
    work_type       VARCHAR2(20),           -- 'REMOTE', 'ONSITE', 'HYBRID'
    employment_type VARCHAR2(20),           -- 'FULL_TIME', 'PART_TIME', 'CONTRACT', 'FREELANCE'
    salary_min      NUMBER,
    salary_max      NUMBER,
    deadline        DATE,                   -- 마감일
    status          VARCHAR2(20) DEFAULT 'OPEN',  -- 'OPEN', 'CLOSED', 'DRAFT'
    view_count      NUMBER DEFAULT 0,
    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE DEFAULT SYSDATE,
    CONSTRAINT fk_posting_member FOREIGN KEY (member_id) 
        REFERENCES dm_member(member_id) ON DELETE CASCADE
);

CREATE SEQUENCE dm_job_posting_seq START WITH 1 INCREMENT BY 1;

COMMENT ON TABLE dm_job_posting IS '채용공고';

-- ================================================
-- 4. 지원내역 테이블 (dm_application)
-- ================================================
CREATE TABLE dm_application (
    application_id  NUMBER PRIMARY KEY,
    posting_id      NUMBER NOT NULL,        -- 채용공고 ID
    member_id       NUMBER NOT NULL,        -- 지원자(개발자) ID
    resume_id       NUMBER,                 -- 제출 이력서 ID
    cover_letter    CLOB,                   -- 자기소개서
    status          VARCHAR2(20) DEFAULT 'PENDING',  -- 'PENDING', 'REVIEWED', 'ACCEPTED', 'REJECTED'
    match_score     NUMBER(5,2),            -- 매칭 점수 (%)
    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE DEFAULT SYSDATE,
    CONSTRAINT fk_app_posting FOREIGN KEY (posting_id) 
        REFERENCES dm_job_posting(posting_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_member FOREIGN KEY (member_id) 
        REFERENCES dm_member(member_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_resume FOREIGN KEY (resume_id) 
        REFERENCES dm_resume(resume_id) ON DELETE SET NULL,
    CONSTRAINT uk_app_unique UNIQUE (posting_id, member_id)  -- 중복 지원 방지
);

CREATE SEQUENCE dm_application_seq START WITH 1 INCREMENT BY 1;

COMMENT ON TABLE dm_application IS '지원내역';

-- ================================================
-- 5. 메시지 테이블 (dm_message)
-- ================================================
CREATE TABLE dm_message (
    message_id      NUMBER PRIMARY KEY,
    sender_id       NUMBER NOT NULL,
    receiver_id     NUMBER NOT NULL,
    application_id  NUMBER,                 -- 연관된 지원내역 (선택)
    content         CLOB NOT NULL,
    is_read         CHAR(1) DEFAULT 'N',
    created_at      DATE DEFAULT SYSDATE,
    CONSTRAINT fk_msg_sender FOREIGN KEY (sender_id) 
        REFERENCES dm_member(member_id) ON DELETE CASCADE,
    CONSTRAINT fk_msg_receiver FOREIGN KEY (receiver_id) 
        REFERENCES dm_member(member_id) ON DELETE CASCADE,
    CONSTRAINT fk_msg_app FOREIGN KEY (application_id) 
        REFERENCES dm_application(application_id) ON DELETE SET NULL
);

CREATE SEQUENCE dm_message_seq START WITH 1 INCREMENT BY 1;

COMMENT ON TABLE dm_message IS '1:1 메시지';

-- ================================================
-- 인덱스 생성 (검색 성능 향상)
-- ================================================
CREATE INDEX idx_resume_member ON dm_resume(member_id);
CREATE INDEX idx_resume_skills ON dm_resume(skills);
CREATE INDEX idx_posting_member ON dm_job_posting(member_id);
CREATE INDEX idx_posting_skills ON dm_job_posting(skills);
CREATE INDEX idx_posting_status ON dm_job_posting(status);
CREATE INDEX idx_app_posting ON dm_application(posting_id);
CREATE INDEX idx_app_member ON dm_application(member_id);
CREATE INDEX idx_msg_sender ON dm_message(sender_id);
CREATE INDEX idx_msg_receiver ON dm_message(receiver_id);

-- ================================================
-- 테스트 데이터 (개발용)
-- ================================================

-- 테스트 회원 (비밀번호: 1234 -> BCrypt 암호화 필요)
INSERT INTO dm_member (member_id, email, password, name, member_type, phone)
VALUES (dm_member_seq.NEXTVAL, 'dev@test.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '테스트개발자', 'DEVELOPER', '010-1234-5678');

INSERT INTO dm_member (member_id, email, password, name, member_type, phone)
VALUES (dm_member_seq.NEXTVAL, 'client@test.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '테스트의뢰인', 'CLIENT', '010-8765-4321');

COMMIT;

-- 확인
SELECT * FROM dm_member;
