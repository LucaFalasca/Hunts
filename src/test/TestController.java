package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.bean.LoginBean;
import logic.control.UserControl;
import logic.exception.DatabaseException;

public class TestController {

	@Test
	public void testLoginController() throws DatabaseException {
		UserControl controller = new UserControl();
		LoginBean bean = new LoginBean();
		bean.setUsername("pippo");
		bean.setPassword("pippopass");
	
		assertTrue(controller.verifyAccount(bean));
	}
}
