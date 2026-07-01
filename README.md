Automation Framework (UI - Selenium + TestNG)

This project is a UI test automation framework built with Java, Selenium WebDriver, TestNG, and Maven. The framework follows the Page Object Model (POM) design pattern and includes reusable utilities, reporting, retry logic, and cross-browser execution.

🛠 Built With
Maven – Dependency management and build tool
Java 17 – Programming language
TestNG – Test execution framework
Selenium WebDriver – UI automation
WebDriverManager – Automatic browser driver management
ExtentReports – HTML test reporting
Java Faker – Dynamic test data generation

🧪 Framework Structure
core

Contains the core framework components.

PropertiesManager

Loads configuration values from config.properties.

SetupWebDriver

Initializes and manages WebDriver instances using ThreadLocal, enabling safe browser execution and future parallel testing.

Supports:

Chrome
Firefox
Edge
UserActions

Reusable Selenium actions that reduce duplicated code across the framework.

Includes methods for:

clicking elements
typing text
explicit waits
scrolling
switching browser tabs
switching iframes
JavaScript execution
waiting for page load
taking screenshots
Utils

Common helper methods used throughout the framework.

ExtentReporterNG

Creates and configures ExtentReports HTML reports.

pages

Implements the Page Object Model (POM).

Each page object contains:

web element locators
page-specific actions
validations

Current Page Objects:

HomePage
SearchResultsPage
HotelDetailsPage
GuestDetailsPage
PaymentPage

Keeping UI interactions inside dedicated Page Objects improves maintainability, readability, and scalability.

testData

Contains reusable test data.

BookingTestData

Stores predefined test values such as:

destination
travel dates
number of guests
hotel index
maximum price
TestDataGenerator

Uses Java Faker to generate unique guest information for every execution:

first name
last name
email
phone number

This avoids hardcoded data and allows repeated executions without conflicts.

testComponents

Framework infrastructure.

BaseTest

Provides common test setup and teardown.

Responsibilities include:

WebDriver initialization
opening the application
Page Object initialization
cookie acceptance
screenshot creation
Listeners

Integrates TestNG with ExtentReports.

Automatically:

generates HTML reports
records test status
attaches screenshots on failures
Retry

Retries failed tests automatically to minimize failures caused by temporary UI instability.

tests

Contains the automated UI scenarios.

BookingTest

Automates an end-to-end Booking.com reservation flow:

search destination
select check-in and check-out dates
validate occupancy
apply filters
open a hotel
reserve the cheapest available room
fill guest information
validate the payment page

⚙️ Configuration

Configuration file:

src/main/resources/config.properties

Example:

browser=chrome
timeoutSeconds=10
base.url=https://booking.com

Supported browsers:

Chrome
Firefox
Edge

Browser can also be overridden from the command line:

-Dbrowser=firefox

or

-Dbrowser=edge

▶️ How to Run Tests

Run all tests

mvn clean test

Run TestNG suite

mvn clean test -DsuiteXmlFile=testng.xml

Run using Firefox

mvn clean test -Dbrowser=firefox

Run using Edge

mvn clean test -Dbrowser=edge

Command-line browser selection overrides the value defined in config.properties.

📊 Reporting

The framework generates ExtentReports after every execution.

Reports are generated inside:

reports/

The report includes:

PASS / FAIL / SKIP status
execution time
detailed logs
screenshots for failed tests

🔁 Retry Logic

Failed tests are automatically retried using TestNG Retry Analyzer, reducing failures caused by temporary UI instability.

🖥 WebDriver Management
ThreadLocal WebDriver
WebDriverManager
Cross-browser execution
Ready for future parallel execution

🧠 Design Patterns & Key Features
Page Object Model (POM)
ThreadLocal WebDriver
Reusable Action Layer
Explicit Waits
Dynamic Test Data Generation
Screenshot Capture on Failure
ExtentReports Integration
Retry Mechanism
Cross-Browser Support
Scalable Framework Architecture

📌 Notes

The framework is designed for UI automation of the Booking.com reservation flow.

Its architecture focuses on maintainability, reusability, and scalability, making it easy to extend with additional Page Objects, reusable components, and automated test scenarios.