
var naver_apikey = $('#naver_apikey').val();
var naver_id_login = new naver_id_login(naver_apikey, "http://localhost:8000/member/loginNaver");
var state = naver_id_login.getUniqState();

naver_id_login.setButton("white", 3,40);
naver_id_login.setDomain("http://localhost:8000/member/login");
naver_id_login.setState(state);
naver_id_login.setPopup();
naver_id_login.init_naver_id_login();
