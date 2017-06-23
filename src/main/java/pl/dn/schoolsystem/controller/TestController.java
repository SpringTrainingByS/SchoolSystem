package pl.dn.schoolsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping("/user")
	public @ResponseBody String getUser() {
		return "Dariusz Nurzyński";
	}
	
	@RequestMapping("/admin/info")
	public @ResponseBody String getAdmiInfo() {
		return "Jesteś adminem";
	}
}
