# FinTrack — Personal Finance & Investment Portfolio Management System

## Project Overview
**FinTrack** is a command-line Personal Finance and Investment Portfolio Management System built in Java. It allows users to track their financial assets, simulate future investments, manage transactions, and view analytical reports. This project was built for a college assignment to demonstrate the use of core Object-Oriented Programming (OOP) concepts, collection frameworks, exception handling, and file-based persistence without using external frameworks.

## Features
- **Portfolio Management**: Add cash, view holdings, and update current market prices.
- **Transactions**: Process Buy, Sell, Deposit, and Withdraw operations.
- **Analytics**: Calculate Return on Investment (ROI), Total Value, Profit/Loss, and Concentration.
- **Risk Analysis**: Rule-based evaluation of portfolio diversification and equity exposure.
- **Reports**: Generate and export text-based reports.
- **Investment Simulator**: Project future portfolio value based on compound growth formulas.
- **Financial Goal Planner**: Calculate the required monthly contribution to reach a financial milestone.
- **Data Persistence**: Load and save data using local text files.

## Technologies/Tools
- **Language**: Java 17
- **Build Tool**: Maven (with Maven Wrapper)
- **Testing**: JUnit 5
- **Persistence**: Local Flat Files (CSV)

## Project Structure
```
FinTrack/
├── pom.xml
├── README.md
├── statement.md
├── mvnw / mvnw.cmd
├── .github/workflows/maven.yml
├── data/ (Created automatically during runtime)
├── docs/
│   ├── project-report.md
│   ├── VITYARTHI_REQUIREMENTS_CHECKLIST.md
│   └── diagrams/
└── src/
    ├── main/java/com/fintrack/
    └── test/java/com/fintrack/
```

## Installation/Setup
1. Clone the repository to your local machine.
2. Ensure you have the Java Development Kit (JDK) 17 or higher installed and the `JAVA_HOME` environment variable configured.
3. Open a terminal (Command Prompt or PowerShell) in the project root directory.

## How to Test
You can run the automated JUnit test suite using the included Maven Wrapper:

**On Windows:**
```powershell
.\mvnw.cmd clean test
```

**On Mac/Linux:**
```bash
./mvnw clean test
```

## How to Run
Run the application using the Maven Wrapper:

**On Windows:**
```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd exec:java -Dexec.mainClass="com.fintrack.Main"
```

**On Mac/Linux:**
```bash
./mvnw clean compile
./mvnw exec:java -Dexec.mainClass="com.fintrack.Main"
```

## Relevant Usage Information
- **Sample Data**: Upon first launch, go to **Data Management** and select **Load Demo/Sample Data** to populate the system.
- **Formulas Used**: 
  - *Invested Amount* = Quantity × Average Purchase Price
  - *Return (%)* = (Current Value − Invested Amount) ÷ Invested Amount
- **Risk Scoring**: High concentration (>50% in one asset) or high equity exposure (>80%) will trigger a HIGH risk classification.
- **Data Storage**: Data is automatically written to `portfolio.csv`, `holdings.csv`, `transactions.csv`, and `assets.csv` in the `data/` directory.

## Disclaimer
*This application is an educational software project. Its calculations, simulations, and risk classifications are for demonstration purposes only and do not constitute financial, investment, tax, or professional advice.*
