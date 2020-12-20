package logic.control;

import logic.bean.LoginBean;
import logic.model.dao.UserDao;
import logic.model.entity.User;

public class LoginControl {

	
	public boolean verifyAccount(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		
		UserDao userDao = new UserDao();
		if(userDao.searchUserByUsername(username)) {
			User user = userDao.getUserByUsername(username);
			if(user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
		
	}
}
