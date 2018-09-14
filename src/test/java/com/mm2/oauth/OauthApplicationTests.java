package com.mm2.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class OauthApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(new BCryptPasswordEncoder().encode("admin"));
	}

}
