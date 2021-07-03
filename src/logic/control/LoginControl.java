package logic.control;

import java.util.List;

import logic.bean.LoginBean;
import logic.model.dao.UserDao;

public class LoginControl {

	public boolean verifyAccount(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		
		var dao = new UserDao();
		return dao.login(username, password);
	}
	
	public boolean registerAccount(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		var failed = true;
		
		var dao = new UserDao();
		
		List<String> allUsers = dao.getAllUser();
		
		for(String name : allUsers) {
			if(name.equals(username)) 
				failed = false;
		}
		
		if(failed) {
			dao.register(username, password);
		}
		
		return failed;
	}
}
