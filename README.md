# FinTrack — Personal Finance & Investment Portfolio Management System

## Project Overview

**FinTrack** is a command-line based Personal Finance and Investment Portfolio Management System built using Java.

The main idea behind the project is to make it easier to keep track of investments in one place. A user can add money, buy and sell assets, check holdings, see profit or loss, analyse basic portfolio risk, generate reports, and also try out future investment and financial-goal calculations.

I developed this project as a college Java project to apply the concepts we learn in Object-Oriented Programming to something practical. The project uses inheritance, abstraction, polymorphism, encapsulation, collections, enums, exception handling, file handling, input validation, and JUnit testing.

## Features

* **Portfolio Management** — View portfolio information, manage holdings, and update the current price of assets.
* **Transaction Management** — Perform Buy, Sell, Deposit, and Withdraw transactions.
* **Portfolio Analytics** — Check invested amount, current value, realised and unrealised profit/loss, return percentage, asset allocation, and portfolio concentration.
* **Risk Analysis** — Check concentration, equity exposure, diversification, and cash buffer using a rule-based scoring system.
* **Reports** — Generate portfolio summaries, holdings reports, and transaction history.
* **Investment Simulator** — Estimate how an investment can grow over time using monthly compound-growth calculations.
* **Financial Goal Planner** — Calculate the monthly amount required to reach a particular financial target.
* **Data Management** — Save portfolio information and load demonstration/sample data.
* **CSV Persistence** — Store portfolio data locally in CSV files.
* **Validation and Exception Handling** — Check user inputs and handle invalid transactions and other portfolio-related errors.
* **Automated Testing** — Includes JUnit 5 tests for important services and complete application workflows.
* **Asset Categories** — Supports stocks, mutual funds, ETFs, gold, and international fund classification.

## Technologies Used

| Technology            | Purpose                                                      |
| --------------------- | ------------------------------------------------------------ |
| Java 17               | Main programming language                                    |
| Maven                 | Project build and dependency management                      |
| JUnit 5               | Automated testing                                            |
| CSV Files             | Local data storage                                           |
| Git & GitHub          | Version control                                              |
| GitHub Actions        | Automated Maven workflow                                     |
| Java Standard Library | Collections, file handling, `BigDecimal`, input/output, etc. |

## Project Structure

```text
FinTrack/
├── .github/workflows/          # Maven workflow
├── .mvn/                       # Maven Wrapper configuration
├── portfolio-data/             # CSV portfolio/demo data
├── project-documentation/      # Project checklist and diagrams
├── src/
│   ├── main/java/com/fintrack/
│   │   ├── cli/                # Command-line menus
│   │   ├── enums/              # Application enums
│   │   ├── exception/          # Custom exceptions
│   │   ├── model/              # Portfolio and asset classes
│   │   ├── repository/         # Data persistence
│   │   ├── service/            # Main application logic
│   │   └── util/               # Validation and utility classes
│   └── test/java/com/fintrack/ # JUnit tests
├── README.md
├── statement.md
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Main Application Modules

The main menu provides the following options:

1. Portfolio Management
2. Transaction Management
3. Portfolio Analytics
4. Risk Analysis
5. Reports
6. Investment Simulator
7. Financial Goal Planner
8. Data Management
9. Exit

I divided the project into menus, services, models, repository/persistence, utilities, and exceptions. This made it easier to keep the user interaction separate from the actual calculations and data handling.

## Feature-to-Implementation Mapping

The major features are connected to the following parts of the source code:

| Feature                 | Main Classes                                      |
| ----------------------- | ------------------------------------------------- |
| Portfolio Management    | `PortfolioMenu` + `PortfolioService`              |
| Transaction Management  | `TransactionMenu` + `TransactionService`          |
| Portfolio Analytics     | `AnalyticsMenu` + `AnalyticsService`              |
| Risk Analysis           | `RiskMenu` + `RiskAnalysisService`                |
| Reports                 | `ReportMenu` + `ReportService`                    |
| Investment Simulator    | `SimulationMenu` + `SimulationService`            |
| Financial Goal Planner  | `GoalPlannerMenu` + `GoalPlannerService`          |
| Data Management         | `DataMenu` + `FilePortfolioRepository`            |
| CSV Persistence         | `FilePortfolioRepository` + `PortfolioRepository` |
| Input Validation        | `InputValidator`                                  |
| Console Formatting      | `ConsoleFormatter`                                |
| Date Handling           | `DateUtil`                                        |
| Application Entry Point | `Main` + `MainMenu`                               |

This structure gives each part of the application a specific responsibility instead of putting all the code into one large class.

## Object-Oriented Programming Concepts Used

### Encapsulation

Classes such as `Asset`, `Holding`, `Portfolio`, and `Transaction` keep their related data and behaviour together. Access to the data is controlled through class methods.

### Abstraction

`Asset` is used as the common abstract type for the different investment assets in the application. Common properties and behaviour are kept at the parent level.

### Inheritance

The main asset classes are structured as:

```text
Asset
├── Stock
├── MutualFund
├── ETF
└── Gold
```

The child classes inherit the common functionality from `Asset`.

### Polymorphism

The application can work with objects through the common `Asset` type even when the actual object is a `Stock`, `MutualFund`, `ETF`, or `Gold`. This allows the same portfolio and transaction logic to work with different asset types.

### Enums

Enums such as `AssetType`, `TransactionType`, and `RiskLevel` are used for fixed categories instead of relying on arbitrary strings.

### Collections

Java collections are used to store and manage holdings, assets, and transactions.

### Exception Handling

Custom exceptions are used for situations such as insufficient funds, insufficient units, invalid amounts, invalid transactions, and assets that cannot be found.

## Asset Categories and International Fund

The project supports the following asset categories:

* Stock
* Mutual Fund
* ETF
* Gold
* International Fund

International Fund is represented using the existing `MutualFund` implementation and is identified separately through the `INTERNATIONAL_FUND` value in `AssetType`.

I used the existing mutual-fund implementation for this instead of creating another almost identical class. The repository can also load international-fund data using this asset type.

## Financial Calculations

For financial calculations, I used Java's `BigDecimal` instead of relying on floating-point calculations. This is useful for avoiding unnecessary precision problems when working with money.

### Invested Amount

```text
Invested Amount = Quantity × Average Purchase Price
```

### Current Holding Value

```text
Current Value = Quantity × Current Market Price
```

### Total Profit/Loss

```text
Total P/L = Realised P/L + Unrealised P/L
```

When an asset is sold, realised profit/loss is calculated using the holding's running average cost basis.

For units that are still held, unrealised profit/loss is calculated by comparing the current market value with the applicable cost basis.

The portfolio return is calculated using the total profit/loss compared with the relevant active invested amount.

## Risk Analysis

The risk module uses a simple rule-based scoring system. It checks a few portfolio characteristics and adds points when certain conditions are met.

The current rules include:

* Concentration above 25% in one asset
* Concentration above 50% in one asset
* Equity exposure above 50%
* Equity exposure above 80%
* Fewer than three different assets
* Cash buffer below 5% of total portfolio value

The final score is classified as:

```text
0–1  → LOW
2–3  → MODERATE
4+   → HIGH
```

These rules are part of the project for demonstrating portfolio risk analysis. They are simplified rules and are not meant to replace professional investment or financial risk assessment.

## Technical Depth

Apart from the basic portfolio operations, the project includes:

* `BigDecimal` for monetary calculations
* Running average cost-basis calculation
* Realised and unrealised profit/loss
* Portfolio return calculation
* Asset allocation and concentration analysis
* Rule-based risk scoring
* CSV file persistence
* Repository abstraction
* Custom exceptions
* Input validation
* Separate service classes for application logic
* Investment growth simulation
* Financial goal calculations
* JUnit 5 unit and integration testing
* Maven build and testing
* GitHub Actions workflow
* Git version control

## Data Persistence

The project stores portfolio information locally using CSV files:

```text
portfolio-data/
├── assets.csv
├── holdings.csv
├── portfolio.csv
└── transactions.csv
```

`FilePortfolioRepository` is responsible for reading and writing the portfolio data, while `PortfolioRepository` provides the repository-level abstraction.

I chose CSV persistence because it keeps the project simple and easy to run without requiring a separate database installation.

## Setup

### Requirements

* JDK 17 or higher
* Maven is not required separately because the project includes the Maven Wrapper.

### Steps

1. Clone the repository.
2. Open a terminal in the project directory.
3. Use the Maven Wrapper commands shown below.

### Running Tests

**Windows**

```powershell
.\mvnw.cmd clean test
```

**macOS/Linux**

```bash
./mvnw clean test
```

### Running the Application

**Windows**

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd exec:java -Dexec.mainClass="com.fintrack.Main"
```

**macOS/Linux**

```bash
./mvnw clean compile
./mvnw exec:java -Dexec.mainClass="com.fintrack.Main"
```

## Testing

JUnit 5 tests are located in:

```text
src/test/java/com/fintrack/
```

The tests cover transaction processing, portfolio analytics, risk analysis, and important system-level workflows.

### Test Classes

* `TransactionServiceTest` — Tests deposits, withdrawals, buying, selling, and related transaction behaviour.
* `AnalyticsServiceTest` — Tests portfolio analytics and profit/loss calculations.
* `RiskAnalysisServiceTest` — Tests risk scoring and risk classification.
* `SystemIntegrationTest` — Tests important application workflows together.

The current test suite contains **15 tests** and all of them pass successfully:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

One of the tests also checks the realised profit/loss calculation when only part of a holding is sold. This was useful for checking the average-cost-basis calculation.

## Documentation

The `project-documentation/` folder contains:

* VITyarthi requirements checklist
* System architecture diagram
* Use case diagram
* Workflow diagram
* Class diagram
* Sequence diagram
* Storage design diagram

The detailed project report is maintained separately as the official submission document.

## Example Portfolio Result

During testing, an example portfolio scenario produced the following result:

```text
Invested Amount : ₹35,000
Realized P/L    : ₹1,000
Unrealized P/L  : ₹0
Total P/L       : ₹1,000
Return          : 2.86%
Risk Level      : MODERATE
```

This example shows the analytics, profit/loss, return, and risk-analysis parts of the application working together.

## Version Control

The project is maintained using Git and GitHub.

The repository contains:

* Java source code
* JUnit tests
* Maven configuration
* Maven Wrapper
* Sample portfolio CSV data
* Project documentation
* UML and system design diagrams
* GitHub Actions workflow

Generated build files such as the Maven `target/` directory and compiled `.class` files are excluded using `.gitignore`.

## Disclaimer

FinTrack is an educational software project. The calculations, simulations, and risk classifications are simplified for demonstrating Java programming and basic portfolio analysis. They should not be treated as professional financial, investment, tax, or risk advice.
