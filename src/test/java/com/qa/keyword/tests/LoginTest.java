package com.qa.keyword.tests;

import java.io.FileNotFoundException;

import org.testng.annotations.Test;

import com.qa.keyword.engine.KeyWordEngine;

public class LoginTest {
	
	
	public KeyWordEngine keyWordEngine;
	@Test
	public void loginTest() throws FileNotFoundException {
		keyWordEngine = new KeyWordEngine();
		keyWordEngine.startExecution("login");
		
	}

}
