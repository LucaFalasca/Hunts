package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.bean.LoginBean;
import logic.control.UserControl;
import logic.exception.DatabaseException;

//Test Falasca
public class TestController {

	@Test
	public void testLoginController() throws DatabaseException {
		var controller = new UserControl();
		var bean = new LoginBean();
		bean.setUsername("pippo");
		bean.setPassword("pippopass");
	
		assertTrue(controller.verifyAccount(bean));
	}
}
