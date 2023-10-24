package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		
		if(userID == null) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "선택한 회원이 없습니다.");
			response.sendRedirect("manager.jsp");
			return;
		}
		int result = new UserDAO().UserDelete(userID);
		if(result == 1) {
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "삭제 했습니다.");
			response.sendRedirect("manager.jsp");
		} 
		else if(result == 0 ){
			request.getSession().setAttribute("messageType", "실패 메세지");
			request.getSession().setAttribute("messageContent", "삭제에 실패했습니다.");
			response.sendRedirect("manager.jsp");
		}
		if(result == -1 ){
			request.getSession().setAttribute("messageType", "실패 메세지");
			request.getSession().setAttribute("messageContent", "DB 오류입니다.");
			response.sendRedirect("manager.jsp");
		} else {
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "삭제 했습니다.");
			response.sendRedirect("manager.jsp");
		}
	}
}
