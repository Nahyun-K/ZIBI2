-------------- helper 재능기부 ----------------
-- 재능기부 게시판 테이블
create table helper(
 helper_num number not null,
 helper_category number not null,
 helper_hit number default 0 not null,
 helper_title varchar2(120) not null,
 helper_content clob not null,
 helper_filename varchar2(200),
 helper_address1 varchar2(100) not null,
 helper_address2 varchar2(100),
 helper_ip varchar2(40) not null,
 helper_reg_date date default sysdate not null,
 helper_modify_date date,
 helper_solution number default 0 not null, --0:해결중,1:해결완료
 helper_select number not null,
 mem_num number not null,
 constraint helper_pk primary key (helper_num),
 constraint helper foreign key (mem_num) references member (mem_num)
); 

create sequence helper_seq;

-- 재능기부 게시판 스크랩 테이블
create table helper_scrap(
 helper_num number not null,
 mem_num number not null,
 constraint helper_scrap_fk1 foreign key (helper_num) references helper (helper_num),
 constraint helper_scrap_fk2 foreign key (mem_num) references member (mem_num)
);

-------------- helper 댓글 ----------------
create table helper_reply(
 re_num number not null,
 re_content varchar2(900) not null,
 re_date date default sysdate not null,
 re_mdate date,
 re_ip varchar2(40) not null,
 helper_num number not null,
 mem_num number not null,
 constraint helper_reply_pk primary key (re_num),
 constraint helper_reply_fk1 foreign key (helper_num) references helper (helper_num),
 constraint helper_reply_fk2 foreign key (mem_num) references member (mem_num)
);

create sequence helper_reply_seq;

-------------- helper 채팅 ----------------

--채팅방
create table talkroom(
 talkroom_num number not null,
 basic_name varchar2(900) not null,--기본 채팅방 이름
 talkroom_date date default sysdate not null,
 constraint talkroom_pk primary key (talkroom_num)
);
create sequence talkroom_seq;

--채팅방 멤버 / pk없기 때문에 sequence 없음
create table talk_member(
 talkroom_num number not null,
 mem_num number not null,
 room_name varchar2(900) not null, --멤버별 채팅방 이름
 member_date date default sysdate not null,
 constraint talkmember_fk1 foreign key (talkroom_num) references talkroom (talkroom_num),
 constraint talkmember_fk2 foreign key (mem_num) references member (mem_num)
);

--채팅 메세지
create table talk(
 talk_num number not null,
 talkroom_num number not null, --수신 그룹(여러명)
 mem_num number not null, -- 발신자(1명)
 message varchar2(4000) not null, -- 채팅 내용
 chat_date date default sysdate not null, -- 채팅 날짜
 constraint talk_pk primary key (talk_num),
 constraint talk_fk1 foreign key (talkroom_num) references talkroom (talkroom_num),
 constraint talk_fk2 foreign key (mem_num) references member (mem_num)
);
create sequence talk_seq;

--채팅 메세지 확인 / 1:1채팅이 아니라 여러명이기 때문에 테이블 따로 만들어서 읽었는지 확인해야함
create table talk_read(
 talkroom_num number not null,
 talk_num number not null,
 mem_num number not null,
 constraint read_fk1 foreign key (talkroom_num) references talkroom (talkroom_num),
 constraint read_fk2 foreign key (talk_num) references talk (talk_num),
 constraint read_fk3 foreign key (mem_num) references member (mem_num)
);
