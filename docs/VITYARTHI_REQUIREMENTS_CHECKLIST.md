# VITyarthi Requirements Checklist

This document maps the FinTrack project against the official VITyarthi evaluation rubric to demonstrate compliance.

## 1. Problem Understanding & Requirements
**Evidence of Compliance:**
- **Problem Statement provided:** Yes (`statement.md` and `docs/project-report.md`).
- **Scope and Target Users defined:** Yes (`statement.md`).
- **Functional Requirements specified:** Yes (Detailed in `docs/project-report.md`).
- **Non-functional Requirements specified:** Yes (Performance, Reliability, Precision, Usability in `docs/project-report.md`).
- **Functional Modules:** FinTrack implements 8 major modules (Portfolio, Transactions, Analytics, Risk, Reports, Simulation, Goals, Data Management).

## 2. Design & Documentation
**Evidence of Compliance:**
- **System Architecture Diagram:** Yes (`docs/diagrams/architecture-diagram.md`).
- **Use Case Diagram:** Yes (`docs/diagrams/use-case-diagram.md`).
- **Workflow/Process Flow Diagram:** Yes (`docs/diagrams/workflow-diagram.md`).
- **Sequence Diagram:** Yes (`docs/diagrams/sequence-diagram.md`).
- **Class/Component Diagram:** Yes (`docs/diagrams/class-diagram.md`).
- **Storage/Data Schema Design:** Yes (`docs/diagrams/storage-design-diagram.md`).
- **Design Decisions & Rationale:** Yes (Detailed in `docs/project-report.md`).

## 3. Implementation Quality
**Evidence of Compliance:**
- **Modular Implementation:** Yes. Code is strictly separated into `cli`, `model`, `service`, `repository`, `enums`, and `exception` packages.
- **Correct Java/OOP Concepts:** Yes. Abstract `Asset` class with specific subclasses (Polymorphism/Inheritance). Interfaces for Repositories. Encapsulation of balances.
- **Testing:** Yes. JUnit 5 test suite containing 14 comprehensive tests verifying business logic and exceptions.
- **Validation & Error Handling:** Yes. Custom exceptions (`InsufficientFundsException`, `InvalidAmountException`) and `InputValidator` class.
- **Minimum 5-10 meaningful modules/classes:** Yes. Over 15 active Java classes spanning Models, Services, and Repositories.
- **Code Comments:** Yes. Javadoc and inline comments explain calculation logic (e.g., compound interest formulas).

## 4. Innovation, Depth & Complexity
**Evidence of Compliance:**
- **Financial Mathematics:** Implements accurate compound growth iteration loops and Profit/Loss tracking.
- **Rule-based Risk Engine:** Evaluates diversification and equity exposure dynamically based on portfolio states.
- **Data Persistence:** Implements a custom CSV reader/writer to map POJOs to text files using `java.io` and `java.nio`.
- **Precision:** Uses `java.math.BigDecimal` exclusively for monetary calculations rather than simple `double` primitives.

## 5. GitHub Repository & Version Control
**Evidence of Compliance:**
- **README.md and statement.md:** Both present at the project root.
- **Source Code / Project Files:** Correctly structured Maven layout (`src/main/java`).
- **Organized Project Structure:** Separated into `docs/`, `data/`, and `src/`.
- **Testing Instructions:** Explicitly provided in `README.md` using the Maven wrapper.
- **Installation/Run Instructions:** Explicitly provided in `README.md` using the Maven wrapper.
- **GitHub Elements:** Repository contains a `.gitignore` and `.github/workflows/maven.yml` for CI/CD tracking.

## 6. Project Report
**Evidence of Compliance:**
- **Cover Page:** Included with correct registration details.
- **Required Sections Included:** All 16 required sections (Introduction, Functional/Non-functional reqs, Design, Implementation Details, Screenshots, Testing Approach, Challenges, Learnings, Future scope, etc.) are present and logically ordered in `docs/project-report.md`.
- **Screenshots:** Placeholders mapped and ready for capture.
