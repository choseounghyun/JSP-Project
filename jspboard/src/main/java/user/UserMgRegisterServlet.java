package user;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserMgRegisterServlet")
public class UserMgRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userNum = request.getParameter("userNum");
		String zipNo = request.getParameter("zipNo");
		String roadAddrPart1 = request.getParameter("roadAddrPart1");
		String roadAddrPart2 = request.getParameter("roadAddrPart2");
		String addrDetail = request.getParameter("addrDetail");
		
		if(userID == null || userID.equals("") || userPassword1 == null || userPassword1.equals("")
				||  userPassword2 == null || userPassword2.equals("") || userName == null || userName.equals("") 
				|| userEmail == null || userEmail.equals("") ||  userNum == null || userNum.equals("") 
				|| zipNo == null || zipNo.equals("") || roadAddrPart1 == null || roadAddrPart1.equals("") || roadAddrPart2 == null
				|| roadAddrPart2.equals("") || addrDetail == null || addrDetail.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			response.sendRedirect("mgjoin.jsp");
			return;
		}

		int registerCheck = new UserDAO().registerCheck(userID); //아이디 중복체크 변수 1 = 중복아님
		if(registerCheck != 1) { //아이디 중복체크 실패시
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "아이디 중복체크를 하셔야합니다.");
			response.sendRedirect("mgjoin.jsp");
			return;
		}
		if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			response.sendRedirect("mgjoin.jsp");
			return;
		}
		if(userPassword1.length() < 8 || userPassword1.length() > 12) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호는 8자리 ~ 12자리 이내로 입력해야합니다.");
			response.sendRedirect("mgjoin.jsp");
			return;
		}
		
		int result = new UserDAO().register(userID, userPassword1, userName, userEmail, userNum, zipNo, roadAddrPart1, roadAddrPart2, addrDetail, "");
		if(result == 1) {
			
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "회원추가에 성공했습니다.");
			response.sendRedirect("manager.jsp");
		} 
		else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			response.sendRedirect("mgjoin.jsp");
		}
	}

}
