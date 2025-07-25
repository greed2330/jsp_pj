--6. 계정생성(System 계정에서) 1. ~ 2.
-- 오라클 설치(SYSTEM/ORACLE) 
-- SYSTEM계정에서 계정 생성
-- hr : sql developer-system_01계정에서 계정생성안해도 되고, 3.락해제 5.비밀번호변경만 한다.

-- 1. 계정생성 : jsp_pj_ict05 계정생성
-- create user <계정이름> identified by <계정암호> default tablespace users;
create user jsp_pj_ict05 identified by tiger default tablespace users;

-- 2. 사용자 권한 부여
-- grant [시스템 권한] to [계정];
grant connect, resource, create view to jsp_pj_ict05;


-- 3. 락 해제
-- alter user <계정이름> account unlock;
alter user jsp_pj_ict05 account unlock;

-- 4. 계정 잘못만든 경우 계정, 객체 삭제하기 
-- drop user <계정이름> cascade;
-- drop user jsp_pj_ict05 cascade; 

-- 5. 패스워드 변경
-- alter user <계정이름> identified by <패스워드>;
-- alter user jsp_pj_ict05 identified by tiger;

--DBeaver에서는 Oracle 하위 Schemas를 새로고침하면 자동으로 생성된 계정이 활성화 된다.


/* [7. 테이블 생성]*/
-- scott_ict05 계정에서 작업
-- 회원정보 테이블
DROP TABLE mvc_customer_tbl  CASCADE CONSTRAINTS;

CREATE TABLE mvc_customer_tbl(
    user_id         VARCHAR2(20)    PRIMARY KEY,    	-- ID
	user_password   VARCHAR2(20)    NOT NULL,          -- 비밀번호
	user_name   VARCHAR2(50)    NOT NULL,          		-- 이름
	user_birthday   DATE            NOT NULL,          -- 생년월일    
	user_address    VARCHAR2(50)    NOT NULL,          -- 주소
	user_hp         VARCHAR2(13),                      -- 핸드폰      
	user_email      VARCHAR2(50)    NOT NULL,          -- 이메일
	user_regdate    TIMESTAMP       DEFAULT sysdate    -- 가입일
);

INSERT INTO mvc_customer_tbl(user_id, user_password, user_name, user_birthday, user_address, user_hp, user_email, user_regdate)
VALUES ('id', 'pass', 'name', sysdate, 'address', 'address', 'email@email.com', sysdate);

/* 왼쪽 스키마 새로고침하면 테이블 목록이 활성화된다.*/
SELECT * FROM MVC_CUSTOMER_TBL;

SELECT user_id
FROM mvc_customer_tbl
WHERE user_id = 'hong';

DELETE MVC_CUSTOMER_TBL WHERE USER_ID = 'jamesD';

-----------------게시판테이블-----------------------
DROP TABLE mvc_board_tbl CASCADE CONSTRAINTS;
CREATE TABLE mvc_board_tbl (
	b_num	NUMBER(7) PRIMARY KEY,	--글번호
	b_title VARCHAR2(50) NOT NULL,	--글제목
	b_content	CLOB NOT NULL,		--글내용
	b_writer	VARCHAR2(30) NOT NULL, --작성자
	b_password	VARCHAR2(30) NOT NULL,--수정, 삭제용 비밀번호
	b_readcnt	NUMBER(6)	DEFAULT 0,	--조회수
	b_regdate	DATE	DEFAULT SYSDATE, --작성일
	b_comment_count	NUMBER(6) DEFAULT 0	--댓글개수
);

SELECT *
FROM 
	(SELECT A.* 
		 , rownum AS rn
	   FROM (SELECT * FROM mvc_board_tbl
	ORDER BY B_NUM DESC) A
	)
WHERE rn BETWEEN 1 AND 10;	--1p => rn 1~10

-- 게시글 입력(다건)
DECLARE	--선언문
	i	NUMBER := 1;	--변수 i에 1을 대입
BEGIN
	WHILE i<=991 LOOP
		INSERT INTO mvc_board_tbl(b_num, b_title, b_content, b_writer, b_password, b_readcnt, b_regdate, b_comment_count)
		VALUES(i, '글제목'||i, '글내용'||i, '작성자'||i, 1234, 0, sysdate, 0);
		i := i+1;
	END LOOP;
END;

--조회수
UPDATE mvc_board_tbl SET b_readcnt = b_readcnt + 1 WHERE b_num = 991;
COMMIT;

SELECT COUNT(*) AS cnt FROM MVC_BOARD_TBL;

SELECT * FROM MVC_BOARD_TBL;

--게시글 수정삭제 버튼 클릭 시 - 비밀번호 인증
SELECT count(*) FROM MVC_BOARD_TBL WHERE b_num = 991 AND b_password = 1204;

--게시글 수정
UPDATE MVC_BOARD_TBL SET b_password = '12345', b_title = '글제목991_U', b_content = '글내용991_U' WHERE b_num = 991;
COMMIT;

INSERT INTO mvc_board_tbl(b_num, b_title, b_content, b_writer, b_password, b_readcnt, b_regdate, b_comment_count)
		VALUES((SELECT NVL(MAX(b_num)+1, 1) FROM MVC_BOARD_TBL), '글제목', '글내용', '작성자', 1234, 0, sysdate, 0);

INSERT INTO mvc_board_tbl(b_num, b_title, b_content, b_writer, b_password, b_readcnt, b_regdate, b_comment_count)
		VALUES(0, '글제목', '글내용', '작성자', 1234, 0, sysdate, 0);

DELETE FROM MVC_BOARD_TBL WHERE b_num = 991;
COMMIT;