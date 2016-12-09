package feature.meets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import static org.junit.Assert.*;

public class FeatureTestRegisterSteps {

	WebDriver dr;
	

@Given("^I am not logged into the System$")
public void i_am_not_logged_into_the_System() throws Throwable {
	System.setProperty("webdriver.firefox.marionette","C:\\Users\\D064880\\Downloads\\geckodriver.exe");

             // if above property is not working or not opening the application in browser then try below property

             //System.setProperty("webdriver.gecko.driver","G:\\Selenium\\Firefox driver\\geckodriver.exe");

WebDriver driver = new FirefoxDriver();
driver.get("http://www.facebook.com");
System.out.println("Application title is ============="+driver.getTitle());
driver.quit();

	//dr=new FirefoxDriver();
	//dr.get("http://www.gmail.com");
    //driver = TestBench.createDriver(new FirefoxDriver());
    // baseUrl = "http://localhost:8080/";
    // driver.get(concatUrl(baseUrl, "/myapp"));
    System.out.println("executed not logged into the system");

}

@When("^I press the register button$")
public void i_press_the_register_button() throws Throwable {

}

@When("^I write my Username into the username field$")
public void il_write_my_Username_into_the_username_field() throws Throwable {

}

@When("^I write a password into the password field$")
public void i_write_a_password_into_the_password_field() throws Throwable {

}

@When("^I repeat my password in the confirm password field$")
public void i_repeat_my_password_in_the_confirm_password_field() throws Throwable {

}

@When("^I write my Email into the email field$")
public void i_write_my_Email_into_the_email_field() throws Throwable {

}

@When("^I write my Location into the location field$")
public void i_write_my_Location_into_the_location_field() throws Throwable {

}

@When("^I press the confirm button$")
public void i_press_the_confirm_button() throws Throwable {

}

@Then("^I should be automatically logged into the System$")
public void i_should_be_automatically_logged_into_the_System() throws Throwable {

}

@Then("^redirect to the main page$")
public void redirect_to_the_main_page() throws Throwable {

}

@When("^I fail to fill all fields with correct data$")
public void i_fail_to_fill_all_fields_with_correct_data() throws Throwable {

}

@Then("^display error message$")
public void display_error_message() throws Throwable {

}

}
