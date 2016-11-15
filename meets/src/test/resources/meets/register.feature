Feature: register
	As a guest
	I want to register a new account
	
	Scenario: register successfully
		Given I am not logged into the System
		When I press the register button
		And I write my Username into the username field
		And I write a password into the password field
		And I repeat my password in the confirm password field
		And I write my Email into the email field
		And I write my Location into the location field
		And I press the confirm button
		Then I should be automatically logged into the System
		And redirect to the main page
		
	Scenario: register failed
		Given I am not logged into the System
		When I press the register button
		And I fail to fill all fields with correct data
		Then display error message
		
		
	