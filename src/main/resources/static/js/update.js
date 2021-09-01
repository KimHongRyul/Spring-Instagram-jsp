// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault(); // 폼태그 액션 막기
	let data = $("#profileUpdate").serialize();
	console.log(data);
	
	$.ajax({
		type: "put",
		url: `/api/user/${userid}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json" //응답받을 때 제이슨으로 달라
	}).done(res=>{ // httpStatus 상태코드 200번대
		console.log("업데이트 성공",res);
		location.href=`/user/${userid}`;
	}).fail(error=>{ // httpStatus 상태코드 200번대가 아닐 때
		if(error.data == null) {
			alert(error.responseJSON.message);
		} else {
		alert(JSON.stringify(error.responseJSON.data));
		}
		console.log("업데이트 실패");
	});
}