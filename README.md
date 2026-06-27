# Appointment Scheduling Application

- **Author:** Volderbeek
- **Contact:** Volderbeek@gmail.com
- **Original Application Version:** 1.0
- **Original Date:** 06/08/2022
- **Current Application Version:** 2.0
- **Current Date:** 06/27/2026

## Environment & Dependencies
- **IDE:** 
  - IntelliJ IDEA 2021.3.1 (Ultimate Edition) Build #IU-213.6461.79, built on December 28, 2021
    - **JRE:** 11.0.13+7-b1751.21 amd64
    - **JVM:** 11.0.13+7-b1751.21 (OpenJDK 64-Bit Server VM)
  - Antigravity IDE
    - **JRE/JVM:** 21.0.11+9-LTS-211 (Java HotSpot(TM) 64-Bit Server VM)
- **OS:** Windows 10
- **JDK:** Oracle JDK 21.0.11 (Java 21)
- **JavaFX:** JavaFX-SDK-17.0.2
- **Database Backend:** SQLite (stored locally as `client_schedule.db`)
- **Database Driver:** sqlite-jdbc-3.46.0.0 (with slf4j-api-1.7.36 and slf4j-nop-1.7.36)

## Running the Application
- Most of the UI is relatively straightforward.
- No external MySQL server is required. On first launch, the application automatically creates the `client_schedule.db` SQLite database file in the project root and initializes it using the script in `resources/init_sqlite.sql`.
- Appointments times are restricted to 15, 30, 45, or 60 minutes long.
- Times are always shown in a 24-hour format for a global userbase.
- Appointment times can overlap if and only if both the user and customer are unique.
- Throughout the application, **Back** buttons in the top left corner will take the user to the previous screen.
- **Refresh** buttons in the top right corner (with one exception on the main appointments screen) will reload from the database in the event of an error or desired update.
- Refreshing the pie chart screen will change the color and have a reloading animation.
- Popups are used frequently for both errors and to convey information.

## Additional Report
- The custom report consists of a pie chart with a visual breakdown of the percentages of each appointment length.
- This could potentially be used to schedule appointments more efficiently.
