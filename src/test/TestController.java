package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.bean.LoginBean;
import logic.control.LoginControl;

public class TestController {

	@Test
	public void testLoginController() {
		LoginControl controller = new LoginControl();
		LoginBean bean = new LoginBean();
		bean.setUsername("pippo");
		bean.setPassword("pippopass");
	
		assertTrue(controller.verifyAccount(bean));
	}
}
