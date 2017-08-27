Feature: Search and assert vehicle details

Scenario: Get vehicle details from file
Given I have a file containing numerous vehicle details
When I navigate to DVLA site to search for vehicle
Then I search and verify each vehicle record from the file in DVLA site
