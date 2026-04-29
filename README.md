# 🚀 Professional Web Automation Suite - D'Paragon

Welcome! This repository showcases a comprehensive end-to-end automation testing suite for the **D'Paragon** web application. As a QA Engineer, I developed these scripts to handle complex UI behaviors, including nested navigation, dynamic pop-ups, and multi-business unit validation.

## 🎯 Key Scenarios Tested
- **Dynamic Pop-up Interception:** Smart logic to detect and close promotional pop-ups at any stage of the user journey.
- **Extended Navigation Audit:** Validating all 9 main and sub-menus (Home, Partnership, Corporate Sales, Cross Branding, Trip & Tour, About, Promo, Gallery, and Contact).
- **Sub-Menu Interaction:** Handling hidden elements and nested spans within the responsive navigation drawer.
- **Automated Search Flow:** Simulating full user booking behavior (Date selection, duration, city filtering, and result validation).
- **Cross-Environment Stability:** Implemented Incognito sessions and explicit window sizing to ensure consistent test results.

## 🛠️ Tech Stack & Tools
- **IDE:** IntelliJ IDEA
- **Language:** Java (JDK 21+)
- **Automation:** Selenium WebDriver
- **Test Framework:** TestNG
- **Build Tool:** Maven (Project Object Model)

## 📋 Project Highlights
- **Robust Locators:** Utilizes specialized XPath `descendant` and `contains` strategies to ensure 100% element discovery.
- **JavaScript Execution:** Fallback click mechanisms using `JavascriptExecutor` to bypass UI obstructions.
- **Clean Code Standards:** Optimized using modern Java standards and centralized helper methods for reusable logic.
- **Detailed Reporting:** Professional console logging for step-by-step execution tracking.

## ⚙️ How to Run
1. Clone this repository.
2. Open the project in **IntelliJ IDEA**.
3. Ensure **Maven** dependencies are loaded via `pom.xml`.
4. Run `DParagonTest.java` for full navigation audit or `DParagonBookingTest.java` for search functionality.

---
*Developed for professional portfolio purposes by a dedicated QA Specialist.*
