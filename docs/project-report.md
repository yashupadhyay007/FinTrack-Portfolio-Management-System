# 1. Cover Page

**VIT Bhopal University**

**VITyarthi — Build Your Own Project**

**Programming in Java**

**Project Title:**  
FinTrack — Personal Finance & Investment Portfolio Management System

**Name:**  
Yash Upadhyay

**Registration No.:**  
25BAI10600

---

# 2. Introduction
Tracking investments across multiple asset classes (Stocks, Mutual Funds, ETFs, Gold) can be cumbersome when using standard spreadsheets. FinTrack provides a command-line interface (CLI) to handle portfolio management, giving users analytical insights into their wealth distribution and projected growth while storing data locally.

# 3. Problem Statement
Users need a tool to track asset purchases and sales, evaluate return on investment (ROI), determine portfolio risk levels, and calculate the contributions necessary to reach future financial goals. Existing platforms can be overly complex or demand personal data sharing. A simple, localized tool is needed to allow an individual to securely log their financial asset purchases and evaluate their overall portfolio health.

# 4. Objectives
1. Build a CLI application to manage a financial portfolio.
2. Ensure adherence to Java Object-Oriented paradigms (Encapsulation, Polymorphism, Interfaces).
3. Implement financial mathematics (ROI, P/L, Compound Interest).
4. Implement a rule-based risk analysis engine.
5. Provide localized flat-file persistence.

# 5. Functional Requirements
- **Module 1: Portfolio & Transaction Management**: The user shall be able to deposit/withdraw cash, and buy/sell specific financial assets. The system shall prevent selling assets the user does not own.
- **Module 2: Analytics & Risk**: The system shall calculate Total Invested, Current Value, and Profit/Loss. The system shall evaluate risk based on predefined concentration rules.
- **Module 3: Simulation & Reporting**: The system shall simulate compound growth for financial goals and generate exportable text reports.

# 6. Non-functional Requirements
- **Performance**: Mathematical calculations should execute instantly using efficient memory structures.
- **Reliability**: The system must persist data to flat-files locally so that data is not lost between sessions.
- **Precision**: `BigDecimal` must be used to reduce floating-point arithmetic errors.
- **Usability**: The CLI menus should be clearly structured and easy to navigate with input validation.

# 7. System Architecture
FinTrack utilizes a modular architecture:
- **CLI/View Layer**: Captures user input and displays text.
- **Service Layer**: Contains business rules, mathematics, and validation.
- **Repository Layer**: Handles reading and writing domain objects to the file system.
- **Domain Model**: POJOs representing the physical entities.

# 8. Design Diagrams

## 8.1 Use Case Diagram
Please refer to `docs/diagrams/use-case-diagram.md` for the visual Mermaid rendering.
It shows the User interacting with the 8 main modules of the FinTrack system (Portfolio, Transactions, Analytics, Risk, Reports, Simulator, Goal Planner, and Data).

## 8.2 Workflow Diagram
Please refer to `docs/diagrams/workflow-diagram.md` for the visual Mermaid rendering.
It maps the flow from launching the CLI, selecting a menu option, processing logic, and persisting updates to the file system.

## 8.3 Sequence Diagram
Please refer to `docs/diagrams/sequence-diagram.md` for the visual Mermaid rendering.
It demonstrates the object interaction flow when a User executes a 'Buy' transaction.

## 8.4 Class Diagram
Please refer to `docs/diagrams/class-diagram.md` for the visual Mermaid rendering.
It outlines the Inheritance of `Asset` (Stock, MutualFund, ETF, Gold) and the Composition of `Portfolio` containing `Holdings` and `Transactions`.

## 8.5 File Storage / Data Schema Design
Please refer to `docs/diagrams/storage-design-diagram.md` for the visual Mermaid rendering.
It documents the layout of the four local CSV files: `portfolio.csv`, `holdings.csv`, `transactions.csv`, and `assets.csv`.

# 9. Design Decisions & Rationale
- **Why Java 17**: Selected for its LTS stability, enhanced `switch` statements, and robust collection framework.
- **Why CLI instead of a web application**: A CLI ensures the project remains purely focused on core Java OOP and backend logic without the distraction of learning external UI frameworks or running web servers.
- **Why layered architecture**: Separating the CLI, Service, and Repository layers makes the code modular, easier to test, and allows for future UI replacements.
- **Why separate service and repository layers**: The Service layer handles financial math and validation, while the Repository handles only I/O, upholding the Single Responsibility Principle.
- **Why BigDecimal is used for financial calculations**: Primitive `double` types suffer from floating-point precision errors (e.g., 0.1 + 0.2 = 0.30000000000000004). `BigDecimal` guarantees exact monetary arithmetic.
- **Why HashMap is used for holdings**: `HashMap` provides O(1) average time complexity for looking up specific asset holdings by their ID (e.g., "AAPL"), making portfolio updates highly efficient.
- **Why local file persistence was selected**: It satisfies the requirement for data storage while keeping the application self-contained and easy to run without requiring a separate database server installation.
- **Why custom exceptions are used**: Exceptions like `InsufficientFundsException` allow the service layer to safely reject invalid operations and pass meaningful error messages back to the user interface.
- **Why JUnit tests were added**: Automated tests ensure the financial math and transaction logic remain correct even if the codebase is modified.
- **Why rule-based risk analysis was used**: It provides an accessible, deterministic way to evaluate portfolio health without relying on complex external market volatility APIs.

# 10. Implementation Details
The system was implemented using pure Java. The Domain Models use encapsulation to protect their state (e.g., restricting direct access to balances). The `FilePortfolioRepository` implements a standard Java interface and utilizes `BufferedReader` and `PrintWriter` from `java.io` to interact with comma-separated values. Data is written to a `data/` directory in the current working directory. The core CLI loop captures user choices via `Scanner` and delegates to specific service classes.

# 11. Screenshots / Results
The application successfully meets the project objectives. Below are placeholders for the genuine screenshots that demonstrate the working system:

- [ ] **Screenshot 1:** Main FinTrack dashboard and Main Menu.
- [ ] **Screenshot 2:** Portfolio / holding operation (e.g., viewing current holdings).
- [ ] **Screenshot 3:** Transaction operation (e.g., successfully buying a stock).
- [ ] **Screenshot 4:** Analytics result showing Total Invested, Value, and P/L.
- [ ] **Screenshot 5:** Risk analysis result showing the calculated risk score and category.
- [ ] **Screenshot 6:** Investment simulator or goal planner projection output.
- [ ] **Screenshot 7:** Generated report or verification of local data persistence files.
- [ ] **Screenshot 8:** Successful Maven test execution showing 14 tests passing.

*(Note to evaluator: These screenshots should be captured directly from the running FinTrack terminal interface).*

# 12. Testing Approach
JUnit 5 was used to construct unit tests for core service layer functionality, particularly `TransactionService` and `AnalyticsService`. Tests cover happy paths (successful buying and selling) as well as edge cases (attempting to buy with negative quantities or insufficient funds, which should correctly trigger custom exceptions).

# 13. Challenges Faced
- Managing the synchronization between the `Portfolio`'s cash balance and its transaction history during Buy/Sell operations.
- Handling file I/O safely and ensuring the program doesn't crash if the `data/` directory or files do not exist upon first launch.
- Formatting the CLI output cleanly in standard Java without external table-drawing libraries.

# 14. Learnings & Key Takeaways
- Gained practical experience implementing Object-Oriented design patterns and SOLID principles.
- Learned the importance of using `BigDecimal` over primitives for financial calculations.
- Developed a better understanding of how to separate business logic from data storage (Repository Pattern).
- Experienced writing and passing automated JUnit tests.

# 15. Future Enhancements
- Expand the application to use a relational database (like PostgreSQL or MySQL) instead of CSV files for better concurrency.
- Build a Graphical User Interface (GUI) using JavaFX or Spring Boot with a web frontend.
- Integrate a live stock market API (like Yahoo Finance) to automatically fetch current asset prices.

# 16. References
- Oracle, *Java SE 17 Documentation*, https://docs.oracle.com/en/java/javase/17/
- JUnit Team, *JUnit 5 User Guide*, https://junit.org/junit5/docs/current/user-guide/
- Apache Software Foundation, *Apache Maven Project*, https://maven.apache.org/
