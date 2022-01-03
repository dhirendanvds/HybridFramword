package com.HRM.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HRM_HomePage1 
{
	WebDriver driver;
	
	@FindBy(xpath = "//a[@id='welcome']")
	WebElement Welcome;
	
	@FindBy(xpath = "//a[text()='Logout']")
	WebElement logout;
	
	public HRM_HomePage1(WebDriver d)
	{
		driver = d;
		PageFactory.initElements(driver, this);
	}
	
	public void WelcomeOption()
	{
		Welcome.click();
	}
	
	public void logout()
	{
		logout.click();
	}

}
