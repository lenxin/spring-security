package org.springframework.security.samples.mvc;

import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 *  <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to migrate</a>
 *  to <a href="https://openid.net/connect/">OpenID Connect</a>, which is supported by <code>spring-security-oauth2</code>.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	public String show(Model model, OpenIDAuthenticationToken authentication) {
		model.addAttribute("authentication", authentication);
		return "user/show";
	}
}
