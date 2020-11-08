package org.springframework.security.itest.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/secure/file?with?special?chars.htm", method = RequestMethod.GET)
	public String sec1255TestUrl() {
		return "I'm file?with?special?chars.htm";
	}

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/secure/index")
	@ResponseBody
	public String secure() {
		return "A Secure Page";
	}

}
