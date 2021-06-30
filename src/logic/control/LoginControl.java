package logic.control;

import logic.bean.LoginBean;
import logic.model.dao.UserDao;

public class LoginControl {

	public boolean verifyAccount(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		
		var dao = new UserDao();
		return dao.login(username, password);
	}
}
