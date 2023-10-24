package user;

public class UserDTO {
	
	String userID;
	String userPassword;
	String userName;
	String userEmail;
	int userNum;
	int zipNo;
	String roadAddrPart1;
	String roadAddrPart2;
	String addrDetail;
	String userProfile;
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getUserNum() {
		return userNum;
	}
	
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getzipNo() {
		return zipNo;
	}
	
	public void setzipNo(int zipNo) {
		this.zipNo = zipNo;
	}
	public String getroadAddrPart1() {
		return roadAddrPart1;
	}
	
	public void setroadAddrPart1(String roadAddrPart1) {
		this.roadAddrPart1 = roadAddrPart1;
	}
	public String getroadAddrPart2() {
		return roadAddrPart2;
	}
	
	public void setroadAddrPart2(String roadAddrPart2) {
		this.roadAddrPart2 = roadAddrPart2;
	}
	public String getaddrDetail() {
		return addrDetail;
	}
	
	public void setaddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getUserProfile() {
		return userProfile;
	}
	
	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
}
