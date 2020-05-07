package com.edureka.casestudy;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Casestudy {

	WebDriver driver = null;
	WebDriverWait myWait;
	static String baseUrl = "https://www.flipkart.com/ ";

	@Test(groups = { "sanity", "regression" }, priority = 0)
	@Parameters({ "browserType" })
	public void launchBrowser(String browser) {
		if (browser.equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Kunal\\Desktop\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("FIREFOX")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Kunal\\Desktop\\chromedriver.exe");
			driver = new FirefoxDriver();
		} else {
			Reporter.log("Invalid browser selection");
			System.exit(0);
		}

		myWait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.get("https://www.gmail.com");

	}

	@Test(groups = { "sanity", "regression" }, priority = 1)
	public void step01_getBrowser() {
		// driver.manage().deleteAllCookies();
		// driver.manage().window().maximize();
		// Step1 - Navigate to flipkart url
		driver.get(baseUrl);
		Reporter.log("Step 01 - URL is launched.");
	}

	@Test(groups = { "sanity", "regression" }, priority = 2)
	public void step02_loginModalScreen() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Step2-close login modal screen.
		WebElement login = driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']"));
		if (login.isDisplayed()) {
			login.click();
			Reporter.log("Step 02 - Login screen is cancelled.");
		}

		Thread.sleep(3000);
	}

	@Test(groups = { "sanity", "regression" }, priority = 3)
	public void step03_hoverMenu() {
		// Step3 - Hover to Electronics menu and click MI
		WebElement electronics = driver
				.findElement(By.xpath("//li[@class='Wbt_B2 _1YVU3_']//span[text()='Electronics']"));
		Actions action = new Actions(driver);
		action.moveToElement(electronics).perform();
		Reporter.log("Step 03 - Mouse hover to Electronics menu.");
	}

	@Test(groups = { "sanity", "regression" }, priority = 4)
	public void step04_verifyLabel() {

		WebElement clickEle = driver.findElement(By.xpath("//a[@title='Mi']"));
		myWait = new WebDriverWait(driver, 10);
		myWait.until(ExpectedConditions.visibilityOf(clickEle));
		Actions action = new Actions(driver);
		action.moveToElement(clickEle).click().perform();

		// Step4 - Verify label on the search screen
		WebElement msgSearch = driver.findElement(By.xpath("//p[contains(text(),'Latest from MI')]"));
		Reporter.log("Step 04 - Label is " + msgSearch.getText());
		AssertJUnit.assertEquals(msgSearch.getText(), "Latest from MI : Redmi Go");
	}

	@Test(groups = { "regression" }, priority = 5)
	public void step05_priceSlider() throws InterruptedException {

		// Step5 - Change the Price slider

		WebElement slider = driver.findElement(By.xpath("//div[@class='_3G9WVX oVjMho']"));
		Thread.sleep(5000);
		Actions move = new Actions(driver);
		Action action1 = move.dragAndDropBy(slider, 90, 0).build();
		action1.perform();
		Thread.sleep(3000);
		Reporter.log("Step 5 - Changed the price slider using mouse events.");
	}

	@Test(groups = { "regression" }, priority = 6)
	public void step06_dropdownOption() {

		// Step 6 - Choose third option from Max Dropdown.
		WebElement maxOption = driver.findElement(By.xpath("//div[@class='_1YoBfV']//select"));
		myWait.until(ExpectedConditions.visibilityOf(maxOption));
		Select maxOptionList = new Select(maxOption);
		maxOptionList.selectByIndex(2);
		Reporter.log("Step 6 - Selected the third option from the max dropdown.");
	}

	@Test(groups = { "regression" }, priority = 7)
	@Parameters({ "searchText" })
	public void step07_search(String searchText) {

		// Step 7 - Search for “redmi go (black, 8 gb)” in the search bar
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(searchText);
		driver.findElement(By.className("vh79eN")).click();
		Reporter.log("Step 7 - Searched using the parameters passed from testng.xml.");
	}

	@Test(groups = { "regression" }, priority = 8, enabled = true)
	public void step08_selectPrd() throws InterruptedException {

		// Step 8 - Click on the first product displayed on the screen

		driver.findElement(By.xpath("//div[contains(text(),'Redmi Go (Black, 8 GB)')]")).click();
		Thread.sleep(3000);
		Reporter.log("Step 8 - Selected the first product from the result screen.");
	}

	@Test(groups = { "regression" }, priority = 9, enabled = true)
	public void step09_verifyPrdAmt() {
		String parentWindow = driver.getWindowHandle();
		String childWindow = null;
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(parentWindow)) {
				childWindow = handle;
			}

		}
		// String childWindow = driver.getWindowHandle();
		driver.switchTo().window(childWindow);

		// Step9 - Verify that the product amount should be greater than or equal to 0
		WebElement prdAmt = driver.findElement(By.xpath("//div[@class='_1vC4OE _3qQ9m1']"));
		// WebElement imgPhone = driver.findElement(By.xpath("//img[@class='_1Nyybr
		// Yun65Y OGBF1g _30XEf0']"));
		myWait.until(ExpectedConditions.titleContains("Mi Redmi"));
		String prdAmtS = (prdAmt.getText()).substring(1);
		String prdAmtS1 = prdAmtS.replaceAll(",", "");

		// System.out.println(prdAmtS1);
		int intprdAmt = Integer.parseInt(prdAmtS1);
		// System.out.println("integer value is "+intprdAmt);

		if (intprdAmt > 0) {
			Reporter.log("Step 9-Validation Passed : Product amount is greater than zero.");
		} else {
			Reporter.log("Step 9-Validation failed");
		}

	}

	@Test(groups = { "regression" }, priority = 10, enabled = true)
	public void step10_clickImgThmbnail() throws InterruptedException {
		// Step10-Click on the image thumbnail which displays play video icon (use
		// IFrame) -- no iFrame
		Actions action = new Actions(driver);
		WebElement imgThbnail = driver.findElement(By.className("_3wR-Kp"));
		action.moveToElement(imgThbnail).perform();
		Thread.sleep(5000);
		Reporter.log("Step 10 - Clicked on the thumbnail having video.");
	}

	@Test(groups = { "regression" }, priority = 11, enabled = true)
	public void step11_deliveryPincode() throws InterruptedException {
		// Step11-Send pincode using keyboard events
		Actions action = new Actions(driver);
		WebElement delPincode = driver.findElement(By.className("_3X4tVa"));
		Action act = action.moveToElement(delPincode).click().sendKeys(delPincode, "110001").build();
		act.perform();
		Thread.sleep(3000);
		WebElement deliverText = driver.findElement(By.xpath("//div[@class='_6xSBsc']"));
		action.moveToElement(deliverText).click().perform();
		Reporter.log("Step 11 - Sent pincode using keyboard event.");

	}

	@Test(groups = { "regression" }, priority = 12, enabled = true)
	public void step12_viewDelDetails() {
		// Step12-View details under delivery pincode
		WebElement viewDetails = driver.findElement(By.className("_3yGtFA"));
		viewDetails.click();

		WebElement cancelAlert = driver.findElement(By.xpath("//button[@class='_2AkmmA _2Rr3iH']"));
		if (cancelAlert.isDisplayed()) {
			myWait.until(ExpectedConditions.visibilityOf(cancelAlert));
			cancelAlert.click();
			Reporter.log("Step 12 - View details modal alert box was opened and closed.");
		} else {
			Reporter.log("Step 12 - View details modal alert box didn't open.");
		}

	}

	@Test(groups = { "regression" }, priority = 13, enabled = true)
	@Parameters({ "searchText" })
	public void step13_addToCart(String searchText) {
		// Step12-View details under delivery pincode
		WebElement addToCart = driver.findElement(By.xpath("//button[@class='_2AkmmA _2Npkh4 _2MWPVK']"));
		addToCart.click();

		myWait.until(ExpectedConditions.titleContains("Shopping Cart"));
		String actual = driver.findElement(By.xpath("//a[contains(text(),'Redmi Go')]")).getText();

		if (searchText.equalsIgnoreCase(actual)) {
			Reporter.log("Step 13 - Validation passed:  Message on the screen is " + actual
					+ " and it is same as given search text " + searchText);
		} else {
			Reporter.log("Step 13 - Validation failed: Message on the screen is " + actual
					+ " and the given search text was " + searchText);

		}

	}

	@Test(groups = { "sanity", "regression" }, priority = 14, enabled = true)
	public void step14_closeBrowsers() {
		Reporter.log("Step 14 - Closing all the browsers.");
		driver.quit();
	}

}



