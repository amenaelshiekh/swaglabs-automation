# SauceDemo (Swag Labs) UI Test Automation Framework

[![CI](https://github.com/amenaelshiekh/swaglabs-automation/actions/workflows/ci.yml/badge.svg)](https://github.com/amenaelshiekh/swaglabs-automation/actions/workflows/ci.yml)

A UI test automation framework built from scratch for the [SauceDemo](https://www.saucedemo.com/) e-commerce web application, using Selenium 4, Java 21, and TestNG with the Page Object Model. Includes data-driven testing, Allure reporting, documented bug findings, end-to-end flows, and a GitHub Actions CI/CD pipeline that runs headless on every push and publishes the report.

**Live Allure report:** https://amenaelshiekh.github.io/swaglabs-automation/

---

## Overview

This project automates the core user journeys of an e-commerce site — login, browsing, cart, checkout, and account/session behavior — as a maintainable, professional test framework rather than a collection of scripts.

- **31 automated tests** covering positive, negative, and edge scenarios across every screen of the application.
- **8 page objects** (plus a shared `BasePage`) following the Page Object Model.
- **Data-driven testing** with external JSON test data, so scenarios are added by editing data files, not code.
- **3 real application defects** discovered, documented, and pinned by dedicated tests.
- **End-to-end flows** that verify the complete purchase journey across multiple pages.
- **Continuous integration** on GitHub Actions: the suite runs headless on every push, and the Allure report is published automatically.

> Note: 3 of the 31 tests intentionally fail to document confirmed application defects (see [Bug Findings](#bug-findings)). They are isolated in a `known-bugs` group and excluded from the CI build gate, so the published report and pipeline stay green while the findings remain on record.

---

## Tech Stack

| Layer | Tool |
|---|---|
| Language | Java 21 |
| Browser automation | Selenium 4.44 (Selenium Manager — no WebDriverManager) |
| Test runner | TestNG 7.12 |
| Assertions | AssertJ 3.27 |
| Test data | Gson 2.14 (JSON parsing) |
| Reporting | Allure 2.33 |
| Build | Maven |
| CI/CD | GitHub Actions (headless, GitHub Pages) |

---

## Architecture

The framework separates concerns into clear layers so that locators, browser handling, configuration, and test data each live in one place.

```
swaglabs-automation/
├── src/
│   ├── main/java/com/swaglabs/
│   │   ├── pages/        Page Objects (one class per screen) + BasePage
│   │   └── utils/        ConfigReader, JsonReader, data POJOs, Allure/Screenshot helpers
│   └── test/java/com/swaglabs/
│       ├── base/         BaseTest (browser open/close, listeners)
│       ├── listeners/    TestNG listener (failure screenshots, results cleanup, environment)
│       └── tests/        Test classes, including an e2e/ package
├── src/test/resources/
│   ├── config.properties           Settings (URL, browser, waits, credentials)
│   └── testdata/                    JSON test-data files
├── bugs.md                         Documented application defects
└── .github/workflows/ci.yml        CI/CD pipeline
```

**Design highlights**

- **Page Object Model** — every screen is a class exposing actions and getters; tests contain no raw locators, so a UI change is fixed in one place.
- **Inheritance-based setup** — `BaseTest` manages the browser lifecycle (`@BeforeMethod`/`@AfterMethod`); `BasePage` provides shared, wait-backed helpers (`type`, `click`, `getText`).
- **Explicit waits** — all interactions wait for element readiness, eliminating timing flakiness.
- **Externalized configuration** — no hardcoded URLs or credentials; values come from `config.properties` and can be overridden from the command line (e.g. `-Dheadless=true`).
- **Test isolation** — each test runs in a fresh browser session, so tests are independent and order-agnostic.

---

## Test Coverage

Tests are grouped by feature in the Allure report (Epic → Feature → Story).

- **Authentication** — valid login, locked-out user, wrong/empty/whitespace/case-sensitive credentials, and session-security checks (direct-URL access while logged out, post-logout back button, cart persistence across re-login).
- **Inventory** — product listing, add/remove to cart, cart-badge behavior, sorting by price and name.
- **Product detail** — opening a product and returning to the listing.
- **Cart** — multi-item contents, removal, persistence across checkout cancel.
- **Checkout** — required-field validation, the full happy-path purchase, and empty-cart handling.
- **Menu** — logout and reset-app-state.
- **End-to-End** — two full purchase journeys (full checkout, and sort-then-buy-cheapest) across multiple pages.

**Data-driven testing** — the login and checkout validation scenarios are driven from `src/test/resources/testdata/*.json`, parsed via Gson and supplied through TestNG `@DataProvider`. One test method runs across many data rows; new cases are added by editing JSON.

---

## Reporting

The framework uses **Allure** for rich, browsable reports:

- Behavior-based grouping (Epic / Feature / Story) and severity levels.
- Step-by-step breakdowns of actions via `@Step`, most useful in the E2E journeys.
- Automatic screenshots captured and attached on any failure.
- An environment summary panel (application, URL, browser, Java version).

The latest report from CI is published live: **https://amenaelshiekh.github.io/swaglabs-automation/**

---

## How to Run

### Prerequisites
- Java 21
- Maven
- Google Chrome (the matching driver is resolved automatically by Selenium Manager)

### Clone
```bash
git clone https://github.com/amenaelshiekh/swaglabs-automation.git
cd swaglabs-automation
```

### Run the full suite (visible browser, includes the documented-bug tests)
```bash
mvn test
```

### Run as CI does (headless, excluding the known-bug tests)
```bash
mvn test -Dheadless=true -Dexcluded.groups=known-bugs
```
On Windows PowerShell, quote each property: `mvn test "-Dheadless=true" "-Dexcluded.groups=known-bugs"`

### View the Allure report locally
```bash
allure serve target/allure-results
```

---

## CI/CD

The GitHub Actions pipeline (`.github/workflows/ci.yml`) runs on every push and pull request to `main`:

1. Checks out the code and sets up Java 21.
2. Runs the suite **headless**, excluding the `known-bugs` group so the build gate reflects expected behavior.
3. Generates the Allure HTML report.
4. Publishes the report to GitHub Pages.

Pipeline status: [![CI](https://github.com/amenaelshiekh/swaglabs-automation/actions/workflows/ci.yml/badge.svg)](https://github.com/amenaelshiekh/swaglabs-automation/actions/workflows/ci.yml)

---

## Bug Findings

Beyond verifying expected behavior, this project surfaced **3 real defects** in the application under test. Each is documented in [`bugs.md`](bugs.md) with reproduction steps, severity, and expected-vs-actual results, and is pinned by a dedicated test linked through Allure's `@Issue`.

| ID | Summary | Severity |
|---|---|---|
| BUG-001 | "Reset App State" does not restore inventory "Add to cart" buttons | Minor |
| BUG-002 | Checkout accepts invalid field content (non-numeric postal code, whitespace-only fields) | Minor |
| BUG-003 | Checkout can proceed with an empty cart | Normal |

These tests are kept in the suite (in the `known-bugs` group) to document the defects, and are excluded from the CI gate so the pipeline reflects the application's *intended* behavior.

---

## Author

**Amena Elshiekh** — Individual Graduation Project
