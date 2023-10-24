package user;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		HttpSession session = request.getSession();
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
			response.sendRedirect("mypage.jsp");
			return;
		}
		if(!userID.equals((String) session.getAttribute("userID"))) {
			session.setAttribute("messageType", "오류 메세지");
			session.setAttribute("messageContent", "접근할 수 없습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			response.sendRedirect("mypage.jsp");
			return;
		}
		if(userPassword1.length() < 8 || userPassword1.length() > 12) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호는 8자리 ~ 12자리 이내로 입력해야합니다.");
			response.sendRedirect("mypage.jsp");
			return;
		}
		Matcher matcher;
		String passwordPatternBlank = "(\\s)";
		String passwordPatternEngNumSpc = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]$";
	    matcher = Pattern.compile(passwordPatternBlank).matcher(userPassword1);
	    if (matcher.find()) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호는 공백 없이 입력해야합니다.");
			response.sendRedirect("mypage.jsp");
			return;
	    }
	   matcher = Pattern.compile(passwordPatternEngNumSpc).matcher(userPassword1);
	    if (!matcher.find()) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호는 영문, 숫자, 특수문자가 모두 포함되어야합니다.");
			response.sendRedirect("mypage.jsp");
			return;
	    }
		int result = new UserDAO().update(userID, userPassword1, userName, userEmail, userNum, zipNo, roadAddrPart1, roadAddrPart2, addrDetail);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "회원 정보가 수정되었습니다.");
			response.sendRedirect("mypage.jsp");
		} 
		else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("mypage.jsp");
		}
		
	}

}
