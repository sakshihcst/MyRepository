package com.tcs.test;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class MyFirstTest {
  @Test
  public void checkTestNGInstallation() {
	  String str = "TestNG is working fine";
      assertEquals("TestNG is working fine", str);
      
      assertNull(null);
      assertNotNull(null);
      assertTrue(true);
      assertFalse(true);
  }
  
  @Test
  public void checkTestNGInstallation1() {
	  String str = "TestNG is working fine";
	  assertNotEquals("TestNG is working fine", str);
  }
}
