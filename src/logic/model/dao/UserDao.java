package logic.model.dao;

import logic.model.entity.User;

public class UserDao {

	
	public boolean searchUserByUsername(String username) {
		
		return true;
	}
	
	public User getUserByUsername(String username) {
		
		return new User("Nome", "Password");
	}
}
