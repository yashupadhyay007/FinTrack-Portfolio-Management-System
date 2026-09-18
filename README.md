# FinTrack — Personal Finance & Investment Portfolio Management System

## Project Overview

**FinTrack** is a command-line based Personal Finance and Investment Portfolio Management System made using Java. It can be used to manage investment holdings, record transactions, check portfolio performance, analyse risk, generate reports, simulate future investments, and plan financial goals.

The project was developed as a college project to apply Java and Object-Oriented Programming concepts in a practical application. It uses concepts such as encapsulation, inheritance, abstraction, polymorphism, collections, exception handling, enums, file handling, and modular programming.

## Features

* **Portfolio Management** — Add cash, view holdings, and update current asset prices.
* **Transaction Management** — Perform Buy, Sell, Deposit, and Withdraw transactions.
* **Portfolio Analytics** — View invested amount, current value, realised and unrealised P/L, return percentage, asset allocation, and portfolio concentration.
* **Risk Analysis** — Analyse concentration, equity exposure, diversification, and cash buffer using rule-based scoring.
* **Reports** — Generate portfolio summaries, holdings reports, and transaction history, with export support.
* **Investment Simulator** — Estimate future investment value using monthly compound-growth calculations.
* **Financial Goal Planner** — Calculate the monthly contribution required to reach a target amount.
* **Data Management** — Save portfolio information and load demonstration/sample data.
* **CSV Persistence** — Store assets, holdings, portfolio details, and transactions in local CSV files.
* **Validation & Exception Handling** — Validate inputs and handle invalid transactions and other portfolio-related errors.
* **Automated Testing** — Includes JUnit 5 tests for important services and application workflows.

## Technologies Used

| Technology            | Purpose                                        |
| --------------------- | ---------------------------------------------- |
| Java 17               | Main programming language                      |
| Maven                 | Project build and dependency management        |
| JUnit 5               | Automated testing                              |
| CSV Files             | Local data storage                             |
| Git & GitHub          | Version control                                |
| Java Standard Library | Collections, file handling, `BigDecimal`, etc. |

## Project Structure

```text
FinTrack/
├── .github/workflows/          # Maven workflow
├── .mvn/                       # Maven Wrapper configuration
├── portfolio-data/             # CSV portfolio/demo data
├── project-documentation/      # VITyarthi checklist and diagrams
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

The main menu provides access to:

1. Portfolio Management
2. Transaction Management
3. Portfolio Analytics
4. Risk Analysis
5. Reports
6. Investment Simulator
7. Financial Goal Planner
8. Data Management
9. Exit

The project is divided into menus, services, models, repository/persistence, utilities, and custom exceptions. This keeps the user interface, application logic, and data handling separated from each other.

## Financial Calculations

FinTrack uses Java's `BigDecimal` for important monetary calculations.

**Invested Amount**

```text
Invested Amount = Quantity × Average Purchase Price
```

**Current Holding Value**

```text
Current Value = Quantity × Current Market Price
```

**Total Profit/Loss**

```text
Total P/L = Realised P/L + Unrealised P/L
```

Realised P/L is calculated when units are sold using the holding's running average cost basis. Unrealised P/L is based on the current market value compared with the applicable cost basis.

Portfolio return is calculated using the total profit/loss relative to the relevant active invested amount.

## Risk Analysis

The risk module uses a simple rule-based scoring system. It considers:

* Concentration above 25% in one asset
* Concentration above 50% in one asset
* Equity exposure above 50%
* Equity exposure above 80%
* Fewer than three different assets
* Cash buffer below 5% of total portfolio value

The final score is classified as **LOW**, **MODERATE**, or **HIGH** risk.

These rules are designed for demonstrating portfolio analysis in the project and are not intended to be a professional investment risk assessment.

## Data Persistence

Portfolio information is stored locally using CSV files:

```text
portfolio-data/
├── assets.csv
├── holdings.csv
├── portfolio.csv
└── transactions.csv
```

This provides simple file-based persistence without requiring an external database.

## Setup

1. Install **JDK 17 or higher**.
2. Clone the repository.
3. Open a terminal in the project directory.
4. Use the included Maven Wrapper to build, test, and run the project.

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

JUnit 5 tests are included for transaction processing, portfolio analytics, risk analysis, and system-level workflows.

The current test suite contains **15 tests**, all passing successfully:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

## Documentation

The `project-documentation/` folder contains the supporting project documentation, including:

* VITyarthi requirements checklist
* System architecture diagram
* Use case diagram
* Workflow diagram
* Class diagram
* Sequence diagram
* Storage design diagram

The detailed project report is maintained separately as the official submission document.

## Example Portfolio Result

Invested Amount : ₹35,000
Realized P/L    : ₹1,000
Unrealized P/L  : ₹0
Total P/L       : ₹1,000
Return          : 2.86%
Risk Level      : MODERATE

## Disclaimer

FinTrack is an educational software project. Its calculations, simulations, and risk classifications are simplified for demonstration purposes and do not constitute financial, investment, tax, or professional advice.
FinTrack is an educational software project. Its calculations, simulations, and risk classifications are simplified for demonstration purposes and do not constitute financial, investment, tax, or professional advice.
