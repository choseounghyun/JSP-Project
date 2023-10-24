//아이디 중복체크
function DeleteFunction(){
	var userID = $('#users.getUserID()').val();
	$.ajax({
		type: 'POST',
		url: './UserDeleteServlet',
		data: {userID: userID},
		success: function(result) {
		if(result == 1){
			$('#checkMessage').html('삭제 되었습니다.');
			$('#checkType').attr('class', 'modal-content panel-success');
		} else {
			$('#checkMessage').html('실패 했습니다.');
			$('#checkType').attr('class', 'modal-content panel-warning');
		}
		$('#checkModal').modal("show");
	  }
  });
}

function submit2(frm) { 
    frm.action='./mgmypage'; 
    frm.submit(); 
    return true; 
  } 

