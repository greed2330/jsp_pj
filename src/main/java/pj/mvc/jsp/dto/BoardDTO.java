package pj.mvc.jsp.dto;

import java.sql.Date;	//주의

public class BoardDTO {
	private int b_num;
	private String b_title;
	private	String b_content;
	private String b_writer;
	private String b_password;
	private int b_readcnt;
	private Date b_regdate;
	private int b_comment_count;
	
	public BoardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoardDTO(int b_num, String b_title, String b_content, String b_writer, String b_password, int b_readcnt,
			Date b_regdate, int b_comment_count) {
		super();
		this.b_num = b_num;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_writer = b_writer;
		this.b_password = b_password;
		this.b_readcnt = b_readcnt;
		this.b_regdate = b_regdate;
		this.b_comment_count = b_comment_count;
	}

	public int getB_num() {
		return b_num;
	}

	public void setB_num(int b_num) {
		this.b_num = b_num;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_writer() {
		return b_writer;
	}

	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}

	public String getB_password() {
		return b_password;
	}

	public void setB_password(String b_password) {
		this.b_password = b_password;
	}

	public int getB_readcnt() {
		return b_readcnt;
	}

	public void setB_readcnt(int b_readcnt) {
		this.b_readcnt = b_readcnt;
	}

	public Date getB_regdate() {
		return b_regdate;
	}

	public void setB_regdate(Date b_regdate) {
		this.b_regdate = b_regdate;
	}

	public int getB_comment_count() {
		return b_comment_count;
	}

	public void setB_comment_count(int b_comment_count) {
		this.b_comment_count = b_comment_count;
	}

	@Override
	public String toString() {
		return "BoardDTO [b_num=" + b_num + ", b_title=" + b_title + ", b_content=" + b_content + ", b_writer="
				+ b_writer + ", b_password=" + b_password + ", b_readcnt=" + b_readcnt + ", b_regdate=" + b_regdate
				+ ", b_comment_count=" + b_comment_count + "]";
	}
}	
//	CREATE TABLE mvc_board_tbl (
//			b_num	NUMBER(7) PRIMARY KEY,	--글번호
//			b_title VARCHAR2(50) NOT NULL,	--글제목
//			b_content	CLOB NOT NULL,		--글내용
//			b_writer	VARCHAR2(30) NOT NULL, --작성자
//			b_password	VARCHAR2(30) NOT NULL,--수정, 삭제용 비밀번호
//			b_readcnt	NUMBER(6)	DEFAULT 0,	--조회수
//			b_regdate	DATE	DEFAULT SYSDATE, --작성일
//			b_comment_count	NUMBER(6) DEFAULT 0	--댓글개수
//		);

