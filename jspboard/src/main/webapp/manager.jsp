<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name = "viewport" content ="width=device-width, initial-scale=1">
   <link rel="stylesheet" href = "css/bootstrap.css">
   <link rel="stylesheet" href = "css/custom.css">
   <title>DOBBY의 무엇이든 물어보살</title>
   <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
   <script src="js/bootstrap.js"></script>
   <script src="js/managerfunction.js"></script>
</head>
<body>
   <%      
      List<UserDTO> user = new UserDAO().AllList();
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
         <a class="navbar-brand" href="index.jsp">DOBBY의 무엇이든 물어보살</a>
      </div>
   </nav>
   <div class="container">
         <table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
            <thead>
               <tr>
                  <th colspan="11"><h4>관리자 페이지</h4></th>
               </tr>
            </thead>
            <tbody>
               <tr>
                  <th width="80">아이디</th>
                  <th width="100">비밀번호</th>
                  <th width="80">이름</th>
                  <th width="140">이메일</th>
                  <th width="140">전화번호</th>
                  <th width="80">우편번호</th>
                  <th width="200">도로명 주소</th>
                  <th width="200">상세 주소</th>
                  <th width="200">나머지 주소</th>
                  <th width="200">삭제</th>
                  <th width="200">수정</th>
               </tr>
               <%
               for(int i=0; i<user.size(); i++){
                  UserDTO users = (UserDTO)user.get(i);
               %>
                  <tr>
               <form id="form" name="form" method="post" action="./UserDeleteServlet">
                     <td><input class="form-control" id="userID" name="userID" maxlength="20"  value="<%=users.getUserID()%>" readonly></td>  
                     <td><%=users.getUserPassword()%></td>
                     <td><%=users.getUserName()%></td>
                     <td><%=users.getUserEmail()%></td>
                     <td><%=users.getUserNum()%></td>
                     <td><%=users.getzipNo()%></td>
                     <td><%=users.getroadAddrPart1()%></td>
                     <td><%=users.getroadAddrPart2()%></td>
                     <td><%=users.getaddrDetail()%></td>
                     <td><input class="btn btn-primary" type="submit" value="삭제"></td>
               </form>
                     <form id="form" name="form" method="post" action="mgmypage.jsp">
                        <td><input type="hidden" class="form-control" id="userID" name="userID" maxlength="20"  value="<%=users.getUserID()%>" readonly>
                        <input class="btn btn-primary" type="submit" value="수정"></td>                                    
                     </form>
                  </tr>
               <%
                  }               
               %>
               <tr> 
                  <td colspan="11"><a href="./mgjoin.jsp">회원 추가</a></button></td>
               </tr>
            </tbody>
         </table>                  
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