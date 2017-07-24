package pl.dn.schoolsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.dn.schoolsystem.service.impl.UserSecurityService;

@RestController
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/user")
	public @ResponseBody String getUser() {
		LOG.debug("URL: /user");
		return "Dariusz Nurzyński";
	}
	
	@RequestMapping("/admin/info")
	public @ResponseBody String getAdmiInfo() {
		LOG.debug("URL: /admin/info");
		return "Jesteś adminem";
	}
}
