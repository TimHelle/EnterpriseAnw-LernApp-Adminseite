package Model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
	
	private String username;
	public static List<UserModel> userList = new ArrayList<UserModel>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public static List<UserModel> getUserList(){
		return userList;
	}
	public static boolean addUserToList(UserModel users) {
		try
		{
			userList.add(users);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static boolean removeUserFromList(UserModel users) {
		try
		{
			userList.remove(users);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public static String ToStringUserList() {
		StringBuilder sb = new StringBuilder();
		for(UserModel item : userList) {
			sb.append("Username: " + item.getUsername() + "\n");
		}
		return sb.toString();
	}
}
