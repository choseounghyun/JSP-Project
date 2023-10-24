<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="bbs.Bbs" %>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.servlet.ServletContext" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DOBBY의 무엇이든 물어보살</title>
</head>
<body> 
    <%
        String userID = null;
        if(session.getAttribute("userID") != null) {
            userID = (String) session.getAttribute("userID");
        }
        if(userID == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('로그인을 하세요.')");
            script.println("location.href = 'login.jsp'");
            script.println("</script>");
        }
        
        int bbsID = 0;
        if(request.getParameter("bbsID") != null) {
            bbsID = Integer.parseInt(request.getParameter("bbsID"));
        }
        if(bbsID == 0) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('유효하지 않은 글입니다.')");
            script.println("location.href = 'bbs.jsp'");
            script.println("</script>");
        }
        
        Bbs bbs = new BbsDAO().getBbs(bbsID);
        String saveFolder = "images";
        String encType = "utf-8";
        int maxSize = 10 * 1024 * 1024;
        
        ServletContext context = this.getServletContext();
        String realFolder = context.getRealPath(saveFolder);
        
        MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
        
        String fileName = multi.getFilesystemName("fileName");
        if (fileName == null) {
            fileName = ""; // 파일이 선택되지 않은 경우 빈 문자열로 설정
        }
        
        String bbsTitle = multi.getParameter("bbsTitle");
        String bbsContent = multi.getParameter("bbsContent");
        bbs.setBbsTitle(bbsTitle);
        bbs.setBbsContent(bbsContent);
        
        if(!userID.equals(bbs.getUserID())) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('권한이 없습니다.')");
            script.println("location.href = 'bbs.jsp'");
            script.println("</script>");
        } else {
            if(bbsTitle == null || bbsContent == null || bbsTitle.equals("") || bbsContent.equals("")) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('입력되지 않은 사항이 있습니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                BbsDAO bbsDAO = new BbsDAO();
                int result = bbsDAO.update(bbsID, bbsTitle, bbsContent);
                if (result == -1) {
                    PrintWriter script = response.getWriter();
                    script.println("<script>");
                    script.println("alert('글 수정에 실패했습니다.')");
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
                        File oldFile = new File(realFolder + "/" + fileName);
                        File newFile = new File(real + "/" + bbsID + "사진.jpg");
                        oldFile.renameTo(newFile);
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
