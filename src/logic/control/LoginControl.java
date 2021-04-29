package logic.control;

import logic.bean.LoginBean;
import logic.model.dao.LoginDao;
import logic.model.dao.UserDao;
import logic.model.entity.User;

public class LoginControl {

	public boolean verifyAccount(LoginBean bean) {
		String username = bean.getUsername();
		String password = bean.getPassword();
		
		LoginDao dao = new LoginDao();
		return dao.login(username, password);
	}
}
