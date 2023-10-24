//아이디 중복체크
function registerCheckFunction(){
	var userID = $('#userID').val();
	$.ajax({
		type: 'POST',
		url: './UserRegisterCheckServlet',
		data: {userID: userID},
		success: function(result) {
		if(result == 1){
			$('#checkMessage').html('사용할 수 있는 아이디입니다.');
			$('#checkType').attr('class', 'modal-content panel-success');
		} else {
			$('#checkMessage').html('사용할 수 없는 아이디입니다.');
			$('#checkType').attr('class', 'modal-content panel-warning');
		}
		$('#checkModal').modal("show");
	  }
  });
}

//비밀번호 일치 체크
function passwordCheckFunction() {
	var userPassword1 = $('#userPassword1').val();
	var userPassword2 = $('#userPassword2').val();
	if(userPassword1 != userPassword2) {
		$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
	} else {
		var userPassword = $("#userPassword1").val();
		var numberCheck = userPassword.search(/[0-9]/g);
		var englishCheck = userPassword.search(/[a-z]/ig);
		var specialCheck = userPassword.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		if(userPassword.length < 8 || userPassword.length > 12){
			$('#passwordCheckMessage').html('비밀번호는 8자리 ~ 12자리 이내로 입력해야합니다.');
		}else if(userPassword.search(/\s/) != -1){
			$('#passwordCheckMessage').html('비밀번호는 공백 없이 입력해야합니다.');
		}else if(numberCheck < 0 || englishCheck < 0 || specialCheck < 0){
			$('#passwordCheckMessage').html('비밀번호는 영문, 숫자, 특수문자가 모두 포함되어야합니다.');
		}else {
			$('#passwordCheckMessage').html('');
		}  
	}
}

//주소 검색 api
document.domain = "abc.go.kr";
function goPopup(){
	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://business.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
    var pop = window.open("address.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
    
}
/** API 서비스 제공항목 확대 (2017.02) **/
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	document.form.roadAddrPart1.value = roadAddrPart1;
	document.form.roadAddrPart2.value = roadAddrPart2;
	document.form.addrDetail.value = addrDetail;
	document.form.zipNo.value = zipNo;
}