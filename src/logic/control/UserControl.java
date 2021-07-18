package logic.control;

import java.util.List;

import logic.bean.HuntBean;
import logic.bean.LoginBean;
import logic.exception.DatabaseException;
import logic.model.dao.UserDao;

public class UserControl {

	public boolean verifyAccount(LoginBean bean) throws DatabaseException {
		String username = bean.getUsername();
		String password = bean.getPassword();
		
		var dao = new UserDao();
		return dao.login(username, password);
	}
	
	public boolean registerAccount(LoginBean bean) {
		try {
			String username = bean.getUsername();
			String password = bean.getPassword();
			
			var dao = new UserDao();
			dao.register(username, password);
			
			return true;
		}catch(DatabaseException e) {
			return false;
		}
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
