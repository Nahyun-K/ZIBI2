--중고거래
create table second(
 sc_num number not null,           --PK
 sc_title varchar2(150) not null,
 sc_content clob not null,
 sc_category number(1) not null,
 sc_price number(9) not null,
 sc_status number(1) not null,
 sc_sellstatus number(1) default 0 not null, --판매 상태
 sc_way number(1) not null,
 sc_address varchar2(90),
 sc_place varchar2(100),
 sc_latitude number(10,7),
 sc_longitude number(10,7),
 sc_hit number(9) default 0,
 sc_reg_date date default sysdate not null,
 sc_modify_date date,
 sc_filename varchar2(200),
 sc_ip varchar2(40) not null,
 sc_show number(1) default 2 not null,
 mem_num number not null,                --FK
 constraint second_pk primary key (sc_num),
 constraint second_fk foreign key (mem_num) references member (mem_num)
);
 
create sequence second_seq;
 
-- 중고거래 거래 내역
create table second_order(
 sc_order_num number not null,
 sc_buyer_num number not null,
 sc_order_status number(1) default 1 not null,--1:대기, 2:예약중(확정), 3:거래완료 4:거절
 sc_order_reg_date date default sysdate not null,
 sc_num number not null,
 constraint second_order_pk primary key (sc_order_num),
 constraint second_order_fk foreign key (sc_num) references second (sc_num)
);
 
create sequence second_order_seq;

--중고거래 거래 후기
create table second_review(
 sc_rev_num number not null,      
 sc_rev_star number(9,2) not null,
 sc_rev_content varchar2(500),       
 sc_num number not null,
 reviewer_num number not null,
 sc_rev_regdate date default sysdate not null,
 sc_rev_ip varchar2(40) not null,
 constraint second_review_pk primary key (sc_rev_num),
 constraint second_review_fk1 foreign key (sc_num) references second (sc_num),
 constraint second_review_fk2 foreign key (reviewer_num) references member (mem_num)
);

create sequence second_review_seq;

--중고거래 찜
create table second_fav(
 sc_num number not null,
 mem_num number not null,
 constraint fav_second_fk1 foreign key (sc_num) references second (sc_num),
 constraint fav_pmember_fk2 foreign key (mem_num) references member (mem_num)
);


--채팅방
create table chatroom(
 chatroom_num number not null,  	--채팅방 식별 번호
 sc_num number not null,			--판매글 번호
 seller_num number not null,		--판매자 회원 번호
 buyer_num number not null,			--구매자 회원번호
 constraint chatroom_pk primary key (chatroom_num),
 constraint chatroom_fk1 foreign key (sc_num) references second (sc_num),
 constraint chatroom_fk2 foreign key (seller_num) references member (mem_num),
 constraint chatroom_fk3 foreign key (buyer_num) references member (mem_num)
);

create sequence chatroom_seq;

--채팅
create table chat (
 chat_num number not null,
 chat_message varchar2(900) not null,
 chat_reg_date date default sysdate,
 chat_read_check number(1) default 1 not null,
 chatroom_num number not null,
 mem_num number not null,
 constraint chat_pk primary key (chat_num),
 constraint chat_fk1 foreign key (chatroom_num) references chatroom (chatroom_num),
 constraint chat_fk2 foreign key (mem_num) references member (mem_num)
);

create sequence chat_seq;

