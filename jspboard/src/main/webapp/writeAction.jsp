<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DOBBY의 무엇이든 물어보살</title>
</head>
<body> 
    <%
        String userID = null;
        if(session.getAttribute("userID") != null)
        {
            userID = (String) session.getAttribute("userID");
        }
        
        if (userID == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 하세요.')");
            script.println("location.href = 'login.jsp'");
            script.println("</script>");
        } else 
        {
        	int bbsID = 0;
            if(request.getParameter("bbsID") != null) {
                bbsID = Integer.parseInt(request.getParameter("bbsID"));
            }
            if(bbsID == 0) {
                bbsID++;
            }
            
        	String saveFolder = "images";
            String encType = "utf-8";
            int maxSize = 10 * 1024 * 1024;

            ServletContext context = request.getServletContext();
            String realFolder = context.getRealPath("/") + saveFolder;

            MultipartRequest multi = null;

            multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
            
            String fileName = multi.getFilesystemName("fileName");
            if (fileName == null) {
                fileName = ""; // 파일이 선택되지 않은 경우 빈 문자열로 설정
            }

            String bbsTitle = multi.getParameter("bbsTitle");
            String bbsContent = multi.getParameter("bbsContent");
            bbs.setBbsTitle(bbsTitle);
            bbs.setBbsContent(bbsContent);

        
            if (bbs.getBbsTitle() == null || bbs.getBbsContent() == null) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('입력이 안 된 사항이 있습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                BbsDAO bbsDAO = new BbsDAO();
                int result = bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
                if (result == -1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('글쓰기에 실패했습니다.')");
                    script.println("history.back()");
                    script.println("</script>");
                } else {
                    PrintWriter script = response.getWriter();
                    if (fileName != null) {
                    	 ServletContext servletContext = request.getServletContext();
                         String real = servletContext.getRealPath("/images");
                         File delFile = new File(real + "/" + bbsID + "사진.jpg");
                         if (delFile.exists()) {
                             delFile.delete();
                    	}
                    }
                    script.println("<script>");
                    script.println("location.href = 'bbs.jsp'");
                    script.println("</script>");
                }
            }
        }
    %>
</body>
</html>
