Feature: create a meet
	As a logged in user
	I want to create a new meet
	
	Scenario: meet created succesffuly
		Given I am logged into the system
		When I press the button for a new meet
		And I fill the title, description and location field
		And I select a date in the calendar
		And I choose a category in the drop down menu
		And I press the button create meet
		Then display message that the meet has been successfully created
		And show the created meet
		
	Scenario: meet created with errors
		Given I am logged into the System
		When I press the new button for a new meet
		And I fail to fill all fields with correct data
		Then display error message
		And user has to fill out wrong fields again
		
		
	