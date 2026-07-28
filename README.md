# Selenium TestNG Automation Framework

Robust and Scalable Web UI Automation Framework built with Java, Selenium WebDriver, and TestNG. Implements Fluent Page Object Model (Fluent POM) and Component-based architecture. Features comprehensive test suites, parallel execution, and advanced assertions.

Automation testing framework built with **Java 25**, **Selenium WebDriver**, **TestNG**, and **SnakeYAML**, featuring detailed reporting via **Allure Reports**.

---
![Automation](https://img.shields.io/badge/Test_Automation-323330?style=for-the-badge&logo=robotframework&logoColor=white) 

**UI Testing** &nbsp; **Test Automation** &nbsp; **Selenium** &nbsp; **Java** &nbsp; **TestNG** &nbsp; **SnakeYAML Data Parsing** &nbsp; **Allure Interactive Reports** &nbsp; **Maven**

---

## 📊 Live Test Report

You can view the latest live Allure Execution Report hosted on GitHub Pages here:  
👉 **[View Allure Test Report](https://abdelrahman-aa.github.io/selenium-testng-automation-framework/Allure-Report.html)**

---

## Key Features & Architecture

- **Fluent Page Object Model (Fluent POM):** Enforces clean method chaining (`searchPage.clickSearch().verifyResult()`) and strict separation between elements, actions, and assertions.
- **Component-Based Architecture:** Modular design isolating reusable UI components (e.g., Static Navigation Bars, Header Panels) to eliminate redundant code across pages.
- **Java 25+ Ready:** Leverages modern Java features for streamlined execution and optimal performance.
- **Data-Driven Approach (YAML):** Centralized test data management using `SnakeYAML` parsing for clean configuration handling.
- **Parallel Execution Support:** Configured via `testng.xml` for faster parallel execution cycles.
- **AspectJ-Powered Allure Reporting:** Integrates `aspectjweaver` byte-code instrumentation during execution to capture test states, steps, and failure screenshots automatically.

---

## Tech Stack & Tooling

| Tool / Technology | Purpose |
| :--- | :--- |
| **Java 25** | Core Programming Language |
| **Selenium WebDriver (v4.x)** | Web Browser Automation Engine |
| **TestNG** | Test Execution, Groups, and Suite Orchestration |
| **SnakeYAML** | YAML Data Parsing for Test Parameters |
| **Maven** | Dependency Management and Build Lifecycle |
| **AspectJ Weaver** | Dynamic Bytecode Instrumentation for Allure Steps |
| **Allure Report** | Interactive Test Analytics and HTML Reporting |

---

## Project Structure
```
├── src
│   ├── main
│   │   └── java             # Page Objects, Base Pages, & UI Components
│   └── test
│       ├── java             # Test Classes, Groups & TestNG Listeners
│       └── resources        # YAML Test Data & Environment Configurations
├── testng.xml               # TestNG Suite Execution File
└── pom.xml                  # Maven Build Configurations & Plugins
```
---

## Prerequisites

Before running the framework, ensure you have the following installed:

- **JDK 25** (or JDK 21+)
- **Apache Maven 3.8+**
- **Git**

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/Abdelrahman-AA/selenium-testng-automation-framework.git
cd selenium-testng-automation-framework
```

### 2. Execute Test Suite
Run all configured TestNG suites via Maven Surefire Plugin:
```bash
mvn clean test
```
### 3. Generate & View Allure Report Locally
Generate and launch the interactive Allure Dashboard in your local browser:
```bash
mvn allure:serve
```
---

## License
This project is licensed under the Team License - see the [LICENSE](LICENSE) file for details.
