package user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/UserProfileServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 100, // 100MB
                 maxRequestSize = 1024 * 1024 * 200) // 200MB
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String userID = request.getParameter("userID");
        HttpSession session = request.getSession();
        if (!userID.equals((String) session.getAttribute("userID"))) {
            request.getSession().setAttribute("messageType", "오류 메세지");
            request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
            response.sendRedirect("index.jsp");
            return;
        }

        Part filePart = request.getPart("userProfile");
        String fileName = getSubmittedFileName(filePart);
        if (!isImageFile(fileName)) {
            request.getSession().setAttribute("messageType", "오류 메세지");
            request.getSession().setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
            response.sendRedirect("profileUpdate.jsp");
            return;
        }

        String savePath = request.getServletContext().getRealPath("/upload");
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        File file = new File(saveDir, fileName);
        Files.copy(filePart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 프로필 업로드 처리
        new UserDAO().profile(userID, fileName);

        request.getSession().setAttribute("messageType", "성공 메세지");
        request.getSession().setAttribute("messageContent", "성공적으로 프로필이 변경되었습니다.");
        response.sendRedirect("index.jsp");
    }

    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("content-disposition");
        String[] elements = header.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private boolean isImageFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("gif");
    }
}
