package testNGConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

public class TestNGPrac {
	WebDriver driver;

	// By Class List
	By USER_NAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//strong[text()='Dashboard']");
	By CUSTOMERS_FIELD = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMERS_FIELD = By.xpath("//span[text()='Add Customer']");
	By NEW_CUSTOMER_HEADER_FIELD = By.xpath("//strong[text()='New Customer']");
	By FULL_NAME_FIELD = By.xpath("//input[@class='form-control name ']");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//input[@class='form-control email ']");
	By PHONE_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@name='address']");
	By CITY_FIELD = By.xpath("//input[@name='city']");
	By ZIPCODE_FIELD = By.xpath("//input[@id='port']");
	// By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@name='country']");
	By GROUP_DROPDOWN_FIELD = By.xpath("//select[@id='customer_group']");
	By SAVE_BUTTON_FIELD = By.xpath("//button[@id='save_btn']");
	By CUSTOMER_LIST_HEADER_FIELD = By.xpath("//strong[text()='Customer List']");
	By CUSTOMER_LIST_SEARCH_FIELD = By.xpath("//input[@type='search']");

	String browser;
	String url;
	String userName;
	String password;

	@BeforeClass
	public void readConfigFile() {
		Properties prop = new Properties(); // usually declared here but can also be used inside the try and catch block
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");// getProperty("browser")- This refers to the key value from the
													// property file
			url = prop.getProperty("url");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else
			System.out.println("Please enter a valid Browser");

		driver.manage().deleteAllCookies();
		driver.get(url);

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void loginTest() throws InterruptedException {

		driver.findElement(USER_NAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_FIELD).click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(DASHBOARD_HEADER_FIELD));
		
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(),"Dashboard","Dashboard Header Field not present");

	}
	
	@Test
	public void addCustTestCase() throws InterruptedException {
		loginTest();
		driver.findElement(CUSTOMERS_FIELD).click();
		Thread.sleep(3000);
		driver.findElement(ADD_CUSTOMERS_FIELD).click();
		
	}
}
