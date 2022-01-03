package com.HRM.Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	ReadConfigFile pro = new ReadConfigFile();
	public String url = pro.getApplicationURL();
	public String username = pro.getUserName();
	public String password = pro.getPassWord();
	
	public Logger log;

	static String timestamp;
	static String ScreenShotName;

	public ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest logger;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {
		try {
			if (br.equalsIgnoreCase("Chrome")) {
				log = LogManager.getLogger();
				reporterOnStart();
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get(url);
				log.info("Browser lauched successfully");
			} else if (br.equalsIgnoreCase("firefox")) {
				log = LogManager.getLogger();
				reporterOnStart();
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.get(url);
				log.info("Browser lauched successfully");

			}
		} catch (Exception e) {
			System.out.println("Browser not launched due to error: " + e);
		}
	}

	@AfterClass
	public void teardown() {
		try {
			driver.quit();
			extent.flush();
			log.info("Browser closed Successfully");
			log.info("****End of Test Case****");

		} catch (Exception e) {
			log.error("Failed to close the Browser " + e);
		}

	}

	private void reporterOnStart() {
		String Timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "Custom-Test-Report-" + Timestamp + ".html";

		htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Test-output/extentreports/" + repName);// specify
																														// location
																														// of
																														// the
																														// report
		htmlreporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "ADMIN");

		htmlreporter.config().setDocumentTitle("Orange HRM User Test Project"); // Tile of report
		htmlreporter.config().setReportName("Deatiled Test Summary Report"); // name of the report
		htmlreporter.config().setTheme(Theme.DARK);

	}

	public void setLogger(String testName) {
		logger = extent.createTest(testName);
	}

	public void customLogger(Status status, String message, ExtentColor color) {
		logger.log(status, MarkupHelper.createLabel(message, color));
	}

	public void onFailure(String name, WebDriver driver) {
		try {
			takeScreenshot(name, driver);
			String screenshotPath = "C:\\Users\\dhire\\eclipse-workspace\\FramworkD\\ScreenShots\\" + name + ".png";
			logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotPath));
		} catch (Exception e) {
			System.out.println("Unable to locate the file " + e);
		}
	}

	public void takeScreenshot(String name, WebDriver driver) {
		try {
			timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
			ScreenShotName = name + "-" + timestamp;
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(".\\Screenshots\\" + ScreenShotName + ".png");
			FileHandler.copy(src, dest);
		} catch (Exception e) {
			System.out.println("Unable to capture the screenShot" + e);
		}

	}

}
