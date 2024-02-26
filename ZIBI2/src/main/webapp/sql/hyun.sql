-- 예매 --

--영화 정보 ent 테이블
create table performance(
 performance_num number not null,
 performance_title varchar2(60) not null,
 performance_poster varchar2(300) not null, -- file name
 performance_content clob not null,
 performance_start_date date not null,
 performance_age number not null,
 performance_category number not null,
 constraint ent_pk primary key(performance_num)
);

create sequence performance_seq;

--상영관 정보 cinema
create table cinema(
 cinema_num number not null,
 cinema_location1 varchar2(30) not null,
 cinema_location2 varchar2(40) not null,
 cinema_theater varchar2(40) not null,
 cinema_theater_num number not null,
 cinema_total number not null,
 cinema_row number not null,
 cinema_col number not null,
 cinema_adult number not null,
 cinema_teenage number not null,
 cinema_treatment number not null,
 constraint cinema_pk primary key(cinema_num)
);


create sequence cinema_seq;


--
create table ticketing(
 ticketing_num number not null,
 performance_num number not null,
 cinema_num number not null,
 ticketing_date date not null,
 ticketing_start_time varchar2(30) not null,
 constraint ticketing_pk primary key(ticketing_num),
 constraint ticketing_fk1 foreign key (performance_num) references performance (performance_num) on delete cascade, --영화를 지우면 영화에 해당되는 정보(상영관,날짜,시간) 삭제
 constraint ticketing_fk2 foreign key (cinema_num) references cinema (cinema_num)
);

create sequence ticketing_seq;


-- 테이블 변경
create table perform_choice(
 choice_num number not null,
 choice_row number not null,
 choice_col number not null,
 choice_adult number not null, -- 명
 choice_teenage number not null,
 choice_treatment number not null,
 mem_num number not null,
 ticketing_num number not null,
 constraint chioce_pk primary key(choice_num),
 constraint choice_fk1 foreign key (mem_num) references member (mem_num),
 constraint choice_fk2 foreign key (ticketing_num) references ticketing (ticketing_num)
);

create sequence perform_choice_seq;


--좌석 정보(행, 열)
create table perform_seat(
 seat_num number not null, -- max(choice_num) // 시퀀스 없음
 seat_row number not null,
 seat_col number not null
); -- insert join으로 perform_choice 값을 넣으면서 같이 넣을 값

-- 결제 정보
create table perform_payment(
 payment_num number not null,
 payment_uid varchar2(90) not null, -- 고유 주문 번호
 payment_type varchar2(30) not null,
 payment_price number not null,
 payment_state number not null,
 payment_date date not null,
 payment_modify_date date,
 mem_num number not null,
 choice_num number not null,
 constraint payment_pk primary key(payment_num),
 constraint payment_fk1 foreign key (mem_num) references member (mem_num),
 constraint payment_fk2 foreign key (choice_num) references perform_choice (choice_num)
);

create sequence perform_payment_seq;

