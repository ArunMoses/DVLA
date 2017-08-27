package arun.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import org.junit.Assert;

public class VehicleSearchSteps {

	WebDriver driver;

	List<String> vehicleReg;
	
	@Before
	public void setUp() {
		
		driver = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		
		driver.close();
		driver.quit();
	}

	@Given("^I have a file containing numerous vehicle details$")
	public void Given_I_have_a_file_containing_numerous_vehicle_details() throws Exception {

		vehicleReg = new StepHelper().getVehicleDetails();
	}

	@When("^I navigate to DVLA site to search for vehicle$")
	public void I_navigate_to_DVLA_site() {

		driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
	}

	@Then("^I search and verify each vehicle record from the file in DVLA site$")
	public void I_do_a_search_in_DVLA_site_with_each_vehicle_record_from_the_file() {

		driver.findElement(By.cssSelector("#get-started .button")).click();

		vehicleReg.stream().forEach(vehicle -> {

			driver.findElement(By.cssSelector("#Vrm")).sendKeys(vehicle);
			driver.findElement(By.cssSelector("[name='Continue']")).click();

			if (verifyElementPresent(driver, By.cssSelector(".reg-mark"))) {

				Assert.assertEquals(driver.findElement(By.cssSelector(".reg-mark")).getText(), vehicle);
								
			} else {

				driver.findElement(By.cssSelector(".action > a:nth-child(1)")).click();	
			}

		});

	}

	private boolean verifyElementPresent(WebDriver driver, By locator) {

		boolean present = false;

		for (int sec = 0; sec <= 5; sec++) {

			if (sec == 5)
				break;

			try {

				if (driver.findElement(locator).isDisplayed()) {

					present = true;
				}
			} catch (Exception e) {

			}
		}

		return present;
	}
}
