# FinTrack — Personal Finance & Investment Portfolio Management System

## Project Overview

**FinTrack** is a command-line Personal Finance and Investment Portfolio Management System developed in Java. It helps users manage investment holdings, record transactions, analyse portfolio performance, assess portfolio risk, generate reports, simulate future investments, and plan financial goals.

The project was developed as a college project to demonstrate core Java and Object-Oriented Programming concepts including encapsulation, inheritance, abstraction, polymorphism, collections, exception handling, enumerations, file handling, and modular software design.

## Features

* **Portfolio Management** — Manage cash, holdings, and current asset prices.
* **Transaction Management** — Perform Buy, Sell, Deposit, and Withdraw operations.
* **Portfolio Analytics** — Calculate invested amount, current value, realised/unrealised P&L, return percentage, asset allocation, and concentration.
* **Risk Analysis** — Evaluate concentration, equity exposure, diversification, and cash buffer using rule-based scoring.
* **Reports** — Generate portfolio summaries, holdings reports, and transaction history, with report export support.
* **Investment Simulator** — Estimate future investment value using monthly compound-growth calculations.
* **Financial Goal Planner** — Calculate the required monthly contribution to reach a target financial goal.
* **Data Management** — Save portfolio data and load demonstration/sample data.
* **CSV Persistence** — Store assets, holdings, portfolio information, and transactions locally.
* **Validation & Exception Handling** — Validate user input and handle invalid transactions and portfolio-related errors.
* **Automated Testing** — JUnit 5 tests cover important services and application workflows.

## Technologies

| Technology            | Usage                                          |
| --------------------- | ---------------------------------------------- |
| Java 17               | Application development                        |
| Maven                 | Build and dependency management                |
| JUnit 5               | Automated testing                              |
| CSV Files             | Local data persistence                         |
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
│   │   ├── model/              # Portfolio and asset models
│   │   ├── repository/         # Data persistence
│   │   ├── service/            # Business logic
│   │   └── util/               # Validation, formatting, utilities
│   └── test/java/com/fintrack/ # JUnit tests
├── README.md
├── statement.md
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Main Application Modules

The application provides the following main menu modules:

1. Portfolio Management
2. Transaction Management
3. Portfolio Analytics
4. Risk Analysis
5. Reports
6. Investment Simulator
7. Financial Goal Planner
8. Data Management
9. Exit

The application follows a layered structure with **CLI menus, services, models, repository/persistence, utilities, and custom exceptions**. This keeps user interaction separate from business logic and data handling.

## Financial Calculations

FinTrack uses `BigDecimal` for important monetary calculations.

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

Realised profit/loss is calculated when units are sold using the holding's running average cost basis. Unrealised profit/loss is based on the difference between the current market value and the applicable cost basis.

Portfolio return is calculated using total profit/loss relative to the relevant active invested amount.

## Risk Analysis

The risk analysis module uses a rule-based scoring system based on portfolio characteristics:

* Concentration above 25% in one asset adds risk.
* Concentration above 50% adds additional risk.
* Equity exposure above 50% adds risk.
* Equity exposure above 80% adds additional risk.
* Holding fewer than three different assets adds a diversification risk point.
* A cash buffer below 5% of total portfolio value adds a risk point.

The resulting score is classified as:

* **LOW**
* **MODERATE**
* **HIGH**

These rules are simplified for educational purposes and are not intended to represent professional investment advice.

## Data Persistence

Portfolio information is stored locally in CSV files:

```text
portfolio-data/
├── assets.csv
├── holdings.csv
├── portfolio.csv
└── transactions.csv
```

This demonstrates file-based persistence without requiring an external database.

## Setup

1. Install **JDK 17 or higher**.
2. Clone the repository.
3. Open a terminal in the project directory.
4. Use the included Maven Wrapper.

### Run Tests

**Windows**

```powershell
.\mvnw.cmd clean test
```

**macOS/Linux**

```bash
./mvnw clean test
```

### Run Application

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

The project includes JUnit 5 tests for transaction processing, portfolio analytics, risk analysis, and system-level workflows.

Current test result:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

## Documentation

Additional project documentation is available in `project-documentation/`, including:

* VITyarthi requirements checklist
* System architecture diagram
* Use case diagram
* Workflow diagram
* Class diagram
* Sequence diagram
* Storage design diagram

The detailed project report is maintained separately as the official submission document.

## Disclaimer

FinTrack is an educational software project. Its calculations, simulations, and risk classifications are simplified for demonstration purposes and do not constitute financial, investment, tax, or professional advice.
FinTrack is an educational software project. Its calculations, simulations, and risk classifications are simplified for demonstration purposes and do not constitute financial, investment, tax, or professional advice.
