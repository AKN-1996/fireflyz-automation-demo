# Fireflyz Travel Insurance Automation Demo

A lightweight, robust automated test suite built with **Java**, **Playwright**, and **TestNG** to automate the flight booking flow on the Fireflyz portal.

## 🚀 Features & Framework Highlights
* **Playwright Java API**: Fast, reliable execution leveraging modern web features without the overhead of Selenium.
* **Page Object Model (POM)**: Maintained clean separation between test cases (`TravelInsuranceTests`) and UI locators/actions (`BookingPage`).
* **Race-Condition Handling**: Implemented sequential typing simulations (`pressSequentially`) to effectively trigger dynamic frontend JavaScript autocomplete filters.
* **Attribute-Driven Date Selection**: Built a flexible calendar parser dealing with 0-indexed DOM months, allowing developers to declare clean readable dates (e.g., `"04 Jul 2026"`) directly in the tests.

## 🛠️ Prerequisites
* **Java Development Kit (JDK)** 17 or higher
* **Apache Maven** 3.8+
* An IDE (e.g., IntelliJ IDEA)

## 📦 Project Setup & Execution

1. Clone the repository:
   ```bash
   git clone <your-repository-url>