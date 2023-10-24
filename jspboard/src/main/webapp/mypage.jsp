<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name = "viewport" content ="width=device-width, initial-scale=1">
	<link rel="stylesheet" href = "css/bootstrap.css">
	<link rel="stylesheet" href = "css/custom.css">
	<title>게시판</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/joinupdatefunction.js"></script>
</head>
<body>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null ){
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "로그인 상태가 아닙니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		UserDTO user = new UserDAO().getUser(userID);
	%>	
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">게시판</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class = "nav navbar-nav">
				<li><a href="index.jsp">메인</a>
				<li><a href="bbs.jsp">게시판</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="buton" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
						<li class="active"><a href="mypage.jsp">내 정보</a></li>
						<li><a href="profileUpdate.jsp">프로필</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<form  id="form" name="form" method="post" action="./UserUpdateServlet">
			<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>내 정보</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5><%=user.getUserID()%></h5>
						<input type="hidden" name="userID" value="<%=user.getUserID()%>" readonly></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>비밀번호</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword1" name="userPassword1" maxlength="20" placeholder="비밀번호를 입력하세요."></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>비밀번호 확인</h5></td>
						<td colspan="2"><input onkeyup="passwordCheckFunction();" class="form-control" type="password" id="userPassword2" name="userPassword2" maxlength="20" placeholder="비밀번호 확인"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>이름</h5></td>
						<td colspan="2"><input class="form-control" type="text" id="userName" name="userName" maxlength="20" placeholder="이름을 입력하세요." value="<%=user.getUserName()%>"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>이메일</h5></td>
						<td colspan="2"><input class="form-control" type="email" id="userEmail" name="userEmail" maxlength="20" placeholder="이메일을 입력하세요." value="<%=user.getUserEmail()%>"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>전화번호</h5></td>
						<td colspan="2"><input class="form-control" type="number" id="userNum" name="userNum" max="2100000000" maxlength="11" placeholder="전화번호를 입력하세요."></td>
					</tr>
					<!--도로명 주소 등록 구간--> 
					<tr> 
						<td style="width: 110px;"><h5>우편번호</h5></td>
						<td colspan="2"><input type="hidden" id="confmKey" name="confmKey">
						<input type="text" class="form-control" id="zipNo" name="zipNo"  value="<%=user.getzipNo()%>" readonly></td>
   					</tr>
					<tr>  
						<td style="width: 110px;"><h5>우편번호 검색</h5></td>
						<td colspan="2"><button type="button" class="form-control" onclick="goPopup();">우편번호</button><br/></td>
					</tr>
					<tr>  
						<td style="width: 110px;"><h5>도로명 주소</h5></td>
						<td colspan="2"><input class="form-control" type="text" id="roadAddrPart1" name="roadAddrPart1" value="<%=user.getroadAddrPart1()%>" readonly><br/></td>
					</tr>	
					<tr> 
						<td style="width: 110px;"><h5>상세 주소</h5></td>
						<td colspan="2"><input class="form-control" type="text" id="roadAddrPart2" name="roadAddrPart2" value="<%=user.getroadAddrPart2()%>" readonly><br/></td>
					</tr>		
					<tr> 
						<td style="width: 110px;"><h5>나머지 주소</h5></td>
                		<td colspan="2"><input  class="form-control" type="text" id="addrDetail" name="addrDetail" value="<%=user.getaddrDetail()%>" readonly></td>
					</tr>		
					<tr>
						<td style="text-align: left;" colspan="3"><h5 style="color:red;" id="passwordCheckMessage"></h5><input class="btn btn-primary pull-right" type="submit" value="정보 수정"></td>
					</tr>
				</tbody>
			</table>						
		</form>
	</div>
	<%
		String messageContent = null;
		if(session.getAttribute("messageContent") != null) {
			messageContent = (String) session.getAttribute("messageContent");
		}
		String messageType = null;
		if(session.getAttribute("messageType") != null) {
			messageType = (String) session.getAttribute("messageType");
		}
		if(messageContent != null){
	%>
	<div class ="modal fade" id = "messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class = "vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class ="modal-content <% if(messageType.equals("오류메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
					<div class ="modal-header panel-heading">
						<button type="button" class = "close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<%= messageType %>
						</h4>
					</div>
					<div class="modal-body">
						<%= messageContent %>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#messageModal').modal("show");
	</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class = "vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="ckeckType" class ="modal-content panel-info">
					<div class ="modal-header panel-heading">
						<button type="button" class = "close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class = "modal-title">
							확인 메시지
						</h4>
					</div>
					<div id="checkMessage" class="modal-body">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>