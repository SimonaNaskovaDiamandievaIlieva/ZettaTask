# Automation Framework (UI - Selenium + TestNG)

This project is a UI test automation framework built with **Java**, **Selenium WebDriver**, **TestNG**, and **Maven**.

The framework follows the **Page Object Model (POM)** design pattern and includes reusable utilities, reporting, retry logic, and cross-browser execution.

---

# 🛠 Built With

- **Maven** – Dependency management and build tool
- **Java 17** – Programming language
- **TestNG** – Test execution framework
- **Selenium WebDriver** – UI automation
- **WebDriverManager** – Automatic browser driver management
- **ExtentReports** – HTML reporting
- **Java Faker** – Dynamic test data generation

---

# 🧪 Framework Structure

## 📂 core

Contains the core framework components.

### PropertiesManager

Loads configuration values from `config.properties`.

### SetupWebDriver

Initializes and manages WebDriver instances using **ThreadLocal**, enabling thread-safe execution and future parallel testing.

**Supported browsers:**

- Chrome
- Firefox
- Edge

### UserActions

Provides reusable Selenium actions to reduce duplicated code.

Includes methods for:

- Click elements
- Enter text
- Explicit waits
- Scroll to elements
- Switch browser tabs
- Switch iframes
- Execute JavaScript
- Wait for page load
- Capture screenshots

### Utils

Contains reusable helper methods used throughout the framework.

### ExtentReporterNG

Creates and configures **ExtentReports** HTML reports.

---

## 📂 pages

Implements the **Page Object Model (POM)**.

Each page object contains:

- Web element locators
- Page-specific actions
- Validations

### Current Page Objects

- HomePage
- SearchResultsPage
- HotelDetailsPage
- GuestDetailsPage
- PaymentPage

Keeping UI interactions inside dedicated Page Objects improves maintainability, readability, and scalability.

---

## 📂 testData

Contains reusable test data.

### BookingTestData

Stores predefined values such as:

- Destination
- Travel dates
- Number of guests
- Hotel index
- Maximum price

### TestDataGenerator

Uses **Java Faker** to generate unique guest information for every execution:

- First name
- Last name
- Email
- Phone number

This avoids hardcoded data and allows repeated executions without conflicts.

---

## 📂 testComponents

Contains the framework infrastructure.

### BaseTest

Provides common setup and teardown functionality.

Responsibilities include:

- WebDriver initialization
- Opening the application
- Page Object initialization
- Cookie acceptance
- Screenshot creation

### Listeners

Integrates **TestNG** with **ExtentReports**.

Automatically:

- Generates HTML reports
- Records test execution status
- Attaches screenshots to failed tests

### Retry

Automatically retries failed tests to reduce failures caused by temporary UI instability.

---

## 📂 tests

Contains the automated UI test scenarios.

### BookingTest

Automates an end-to-end Booking.com reservation flow:

- Search destination
- Select check-in and check-out dates
- Validate occupancy
- Apply filters
- Open a hotel
- Reserve the cheapest available room
- Fill guest information
- Validate the payment page

---

# ⚙️ Configuration

Configuration file:

```text
src/main/resources/config.properties
```

Example:

```properties
browser=chrome
timeoutSeconds=10
base.url=https://booking.com
```

### Supported browsers

- Chrome
- Firefox
- Edge

You can override the browser from the command line:

```bash
mvn clean test -Dbrowser=firefox
```

or

```bash
mvn clean test -Dbrowser=edge
```

---

# ▶️ How to Run Tests

### Run all tests

```bash
mvn clean test
```

### Run TestNG suite

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run using Firefox

```bash
mvn clean test -Dbrowser=firefox
```

### Run using Edge

```bash
mvn clean test -Dbrowser=edge
```

The browser passed from the command line overrides the value defined in `config.properties`.

---

# 📊 Reporting

The framework generates **ExtentReports** after every execution.

Reports are generated inside:

```text
reports/
```

Each report includes:

- ✅ PASS / FAIL / SKIP status
- Execution duration
- Detailed logs
- Stack traces
- Screenshots for failed tests

---

# 🔁 Retry Logic

Failed tests are automatically retried using the **TestNG Retry Analyzer**, reducing failures caused by temporary UI instability.

---

# 🖥 WebDriver Management

- ThreadLocal WebDriver
- WebDriverManager
- Cross-browser execution
- Ready for future parallel execution

---

# 🧠 Design Patterns & Key Features

- ✅ Page Object Model (POM)
- ✅ ThreadLocal WebDriver
- ✅ Reusable Action Layer
- ✅ Explicit Waits
- ✅ Dynamic Test Data Generation
- ✅ Screenshot Capture on Failure
- ✅ ExtentReports Integration
- ✅ Retry Mechanism
- ✅ Cross-Browser Support
- ✅ Scalable Framework Architecture

---

# 📌 Notes

This framework is designed for UI automation of the **Booking.com reservation flow**.

Its architecture focuses on **maintainability**, **reusability**, and **scalability**, making it easy to extend with additional Page Objects, reusable components, and automated test scenarios.
