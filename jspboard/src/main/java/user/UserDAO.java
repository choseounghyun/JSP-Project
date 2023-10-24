package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class UserDAO {

		DataSource dataSource;
		
		public UserDAO() {
			try {
				InitialContext initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/odbo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		public int login(String userID, String userPassword) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT * FROM member WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("userPassword").equals(userPassword)) {
						return 1 ; // 로그인 성공
					}
					return 2; // 비밀번호 오류 
				} else {
					return 0; // 사용자 존재하지 않음
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
		
		public int registerCheck(String userID) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT * FROM member WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next() || userID.equals("")) {
					return 0; // 이미 존재하는 회원
				} else {
					return 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
		//
		public int register(String userID ,String userPassword, String userName, String userEmail, String userNum, String zipNo, String roadAddrPart1, String roadAddrPart2, String addrDetail,String userProfile) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			String SQL = "INSERT INTO member VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				pstmt.setString(2, userPassword);
				pstmt.setString(3, userName);
				pstmt.setString(4, userEmail);
				pstmt.setInt(5, Integer.parseInt(userNum));
				pstmt.setInt(6, Integer.parseInt(zipNo));
				pstmt.setString(7, roadAddrPart1);
				pstmt.setString(8, roadAddrPart2);
				pstmt.setString(9, addrDetail);
				pstmt.setString(10, userProfile);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
		
		public UserDTO getUser(String userID) {
			UserDTO user = new UserDTO();
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT * FROM member WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					user.setUserID(userID);
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserName(rs.getString("userName"));
					user.setUserEmail(rs.getString("userEmail"));
					user.setUserNum(rs.getInt("userNum"));
					user.setzipNo(rs.getInt("zipNo"));
					user.setroadAddrPart1(rs.getString("roadAddrPart1"));
					user.setroadAddrPart2(rs.getString("roadAddrPart2"));
					user.setaddrDetail(rs.getString("addrDetail"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return user;
		}
		
		public int update(String userID, String userPassword, String userName, String userEmail, String userNum, String zipNo, String roadAddrPart1, String roadAddrPart2, String addrDetail) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			String SQL = "UPDATE member SET userPassword = ?, userName = ?, userEmail = ?, userNum = ?, zipNo = ?, roadAddrPart1 = ?, roadAddrPart2 = ?, addrDetail = ? WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userPassword);
				pstmt.setString(2, userName);
				pstmt.setString(3, userEmail);
				pstmt.setInt(4, Integer.parseInt(userNum));
				pstmt.setInt(5, Integer.parseInt(zipNo));
				pstmt.setString(6, roadAddrPart1);
				pstmt.setString(7, roadAddrPart2);
				pstmt.setString(8, addrDetail);
				pstmt.setString(9, userID);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
		public int profile(String userID, String userProfile) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			String SQL = "UPDATE member SET userProfile = ? WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userProfile);
				pstmt.setString(2, userID);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
		public String getProfile(String userID) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT userProfile FROM member WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("userProfile").equals("")) {
						return "http://localhos:8080/UserChat/src/main/webapp/images/기본 이미지.png";
					}
					return "http://localhost:8080/UserChat/upload/" + rs.getString("userProfile");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return "http://localhos:8080/UserChat/src/main/webapp/images/기본 이미지.png";
		}
		
		public List<UserDTO> AllList() {
			List<UserDTO> list = new ArrayList<>();
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT * FROM member";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					UserDTO users= new UserDTO(); 
					users.setUserID(rs.getString(1));
					users.setUserPassword(rs.getString(2));
					users.setUserName(rs.getString(3));
					users.setUserEmail(rs.getString(4));
					users.setUserNum(Integer.parseInt(rs.getString(5)));
					users.setzipNo(Integer.parseInt(rs.getString(6)));
					users.setroadAddrPart1(rs.getString(7));
					users.setroadAddrPart2(rs.getString(8));
					users.setaddrDetail(rs.getString(9));
					users.setUserProfile(rs.getString(10));
				
					list.add(users);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return list;
		}
		
		public int UserDelete(String userID) {
			Connection conn = null; 
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String SQL = "SELECT * FROM member WHERE userID = ?";
			try {
				conn = dataSource.getConnection();
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();		
				if(rs.next()) {
					SQL="DELETE FROM member WHERE userID = ?";
					pstmt.setString(1, userID);
					return pstmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return -1; // 데이터베이스 오류
		}
}
