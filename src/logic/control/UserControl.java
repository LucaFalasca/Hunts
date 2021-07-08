package logic.control;

import java.util.List;

import logic.bean.HuntBean;
import logic.bean.LoginBean;
import logic.model.dao.UserDao;

public class UserControl {

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
	
	public double calculateAvgRate(List<HuntBean> huntBeans) {
		var cont = 0;
		var somma = 0.00;
		
		for(HuntBean hb : huntBeans) {
			somma = hb.getAvgRating();
			cont++;
		}
		
		if(cont == 0) {
			return 0;
		} else {
			return somma/cont;
		}
	}
}
