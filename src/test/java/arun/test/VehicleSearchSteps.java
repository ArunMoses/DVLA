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
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;

public class VehicleSearchSteps {

	WebDriver driver;

	List<List<String>> vehicleReg;
	
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

		vehicleReg = new GetVehiclesData().getVehicleDetails();
	}

	@When("^I navigate to DVLA site to search for vehicle$")
	public void I_navigate_to_DVLA_site() {

		driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
	}

	@Then("^I search and verify each vehicle record from the file in DVLA site$")
	public void I_do_a_search_in_DVLA_site_with_each_vehicle_record_from_the_file() {

		driver.findElement(By.cssSelector("#get-started .button")).click();

		AtomicInteger index = new AtomicInteger(0);
		
		vehicleReg.get(0).stream().forEach(vehicle -> {

			driver.findElement(By.cssSelector("#Vrm")).sendKeys(vehicle);
			
			driver.findElement(By.cssSelector("[name='Continue']")).click();
			
			System.out.println("SEARCHING FOR " + vehicle + " " + vehicleReg.get(1).get(index.get()) + " " + vehicleReg.get(2).get(index.get()));
			
			Assert.assertEquals(driver.findElement(By.cssSelector(".reg-mark")).getText(), vehicle);
			
			Assert.assertEquals(driver.findElement(By.cssSelector("li.list-summary-item:nth-child(2) > span:nth-child(2) > strong:nth-child(1)")).getText(), vehicleReg.get(1).get(index.get()));
			
			Assert.assertEquals(driver.findElement(By.cssSelector("li.list-summary-item:nth-child(3) > span:nth-child(2) > strong:nth-child(1)")).getText(), vehicleReg.get(2).get(index.get()));
						
			driver.findElement(By.cssSelector(".back-to-previous")).click();

			index.incrementAndGet();
			
		});
	}
}
