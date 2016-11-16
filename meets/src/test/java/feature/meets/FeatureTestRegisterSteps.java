package feature.meets;

import cucumber.api.java.en.Given;
import static org.junit.Assert.*;

public class FeatureTestRegisterSteps {

	
	@Given("^Der selbe Satz wie in dem entsprechenden Scenario und Given (.*) and (.*)$")
	public void createUser( String username, String password ) {
		
	}
	
	@Given("^Der selbe Satz wie in dem entsprechenden Scenario und When (.*) and (.*)$")
	public void checkLogin( String username, String password ) {
	//	assertEquals(this.member.getUsername(), username);
	//	assertEquals(this.member.getPassword(), password);
	}
	
}
