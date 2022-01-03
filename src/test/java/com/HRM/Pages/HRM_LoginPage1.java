package com.HRM.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HRM_LoginPage1 
{
	WebDriver driver;
	
	@FindBy(how = How.XPATH,using = "//input[@id='txtUsername']")
	WebElement username;
	
	@FindBy(how = How.XPATH,using = "//input[@id='txtPassword']")
	WebElement password;
	
	@FindBy(how = How.XPATH,using = "//input[@id='btnLogin']")
	WebElement loginButton;

  public HRM_LoginPage1(WebDriver d) 
  {
	  driver = d;
	  PageFactory.initElements(driver, this);
  }
  
  public void setUserName(String usernameValue)
  {
	  username.sendKeys(usernameValue);
  }
  
  public void setPassword(String passwordValue)
  {
	  password.sendKeys(passwordValue);
  }
  
  public void loginButton()
  {
	  loginButton.click();
  }
}
