// (1) 회원정보 수정
function update(userId, event) {
	let data = $("#profileUpdate").serialize();
	console.log(data);
	
	$.ajax(res => {
		type: "put",
		url: `/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res =>{
		
	}).fail(error =>{
		
	})
	

}