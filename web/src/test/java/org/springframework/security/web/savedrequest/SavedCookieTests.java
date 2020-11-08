package org.springframework.security.web.savedrequest;

import java.io.Serializable;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SavedCookieTests {

	Cookie cookie;

	SavedCookie savedCookie;

	@Before
	public void setUp() {
		this.cookie = new Cookie("name", "value");
		this.cookie.setComment("comment");
		this.cookie.setDomain("domain");
		this.cookie.setMaxAge(100);
		this.cookie.setPath("path");
		this.cookie.setSecure(true);
		this.cookie.setVersion(11);
		this.savedCookie = new SavedCookie(this.cookie);
	}

	@Test
	public void testGetName() {
		assertThat(this.savedCookie.getName()).isEqualTo(this.cookie.getName());
	}

	@Test
	public void testGetValue() {
		assertThat(this.savedCookie.getValue()).isEqualTo(this.cookie.getValue());
	}

	@Test
	public void testGetComment() {
		assertThat(this.savedCookie.getComment()).isEqualTo(this.cookie.getComment());
	}

	@Test
	public void testGetDomain() {
		assertThat(this.savedCookie.getDomain()).isEqualTo(this.cookie.getDomain());
	}

	@Test
	public void testGetMaxAge() {
		assertThat(this.savedCookie.getMaxAge()).isEqualTo(this.cookie.getMaxAge());
	}

	@Test
	public void testGetPath() {
		assertThat(this.savedCookie.getPath()).isEqualTo(this.cookie.getPath());
	}

	@Test
	public void testGetVersion() {
		assertThat(this.savedCookie.getVersion()).isEqualTo(this.cookie.getVersion());
	}

	@Test
	public void testGetCookie() {
		Cookie other = this.savedCookie.getCookie();
		assertThat(other.getComment()).isEqualTo(this.cookie.getComment());
		assertThat(other.getDomain()).isEqualTo(this.cookie.getDomain());
		assertThat(other.getMaxAge()).isEqualTo(this.cookie.getMaxAge());
		assertThat(other.getName()).isEqualTo(this.cookie.getName());
		assertThat(other.getPath()).isEqualTo(this.cookie.getPath());
		assertThat(other.getSecure()).isEqualTo(this.cookie.getSecure());
		assertThat(other.getValue()).isEqualTo(this.cookie.getValue());
		assertThat(other.getVersion()).isEqualTo(this.cookie.getVersion());
	}

	@Test
	public void testSerializable() {
		assertThat(this.savedCookie instanceof Serializable).isTrue();
	}

}
