# Project Statement: FinTrack

## Problem Statement
Tracking personal investments manually across various asset classes (like stocks, mutual funds, ETFs, and gold) can be a time-consuming and error-prone process. While professional portfolio management software exists, these platforms are often overly complex for average users, require paid subscriptions, or demand that users upload sensitive personal financial data to third-party cloud servers. A simple, localized tool is needed to allow an individual to securely log their financial asset purchases, evaluate their overall portfolio health, track average buy prices, and simulate future goal projections without compromising their data privacy.

## Scope of the Project
FinTrack is a local, command-line-based (CLI) Java application that provides an isolated environment for tracking a financial portfolio. The project focuses on core investment management, encompassing the logging of transactions (buy/sell/deposit/withdraw), calculating basic financial metrics (current value, invested amount, profit/loss, and return on investment), and evaluating portfolio risk based on predefined concentration rules.

The scope is strictly limited to local operations and flat-file persistence. It does not include:
- A Graphical User Interface (GUI) or Web Interface.
- Integration with live stock market APIs or banking gateways.
- Multi-user authentication or centralized databases (e.g., SQL).

## Target Users
The target users for FinTrack are:
- Individual investors or hobbyists who want a simple, privacy-focused way to log their investments manually.
- Finance students or enthusiasts who wish to simulate compound growth and evaluate basic portfolio concentration rules.
- Anyone looking for an offline tracker without the overhead of enterprise software.

## High-Level Features
- **Portfolio Management**: View the current cash balance and a detailed list of all owned assets (Holdings) including their average purchase price and total quantity.
- **Transaction Processing**: Record operations such as depositing/withdrawing cash, and buying/selling stocks, mutual funds, ETFs, or gold. The system automatically updates the portfolio and logs the transaction history.
- **Portfolio Analytics**: Automatically calculate total invested capital, current market value, and the net profit or loss across the entire portfolio.
- **Risk Analysis**: Analyze the portfolio's asset concentration and equity exposure against predefined rules to assign a risk score (LOW, MODERATE, HIGH).
- **Simulation and Goal Planning**: Iteratively project compound growth over time using expected return rates, and calculate the required monthly contributions to reach a specific financial goal.
- **Local Persistence**: Save and load all financial data (holdings, cash balance, transactions) to/from local CSV text files.
- **Data Exporting**: Generate a formatted text report of the current portfolio status.
