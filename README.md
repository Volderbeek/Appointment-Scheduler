# Appointment Scheduling Application

- **Author:** Volderbeek
- **Contact:** Volderbeek@gmail.com
- **Application Version:** 1.1
- **Date:** 06/08/2022

## Environment & Dependencies
- **IDE:** IntelliJ IDEA 2021.3.1 (Ultimate Edition) Build #IU-213.6461.79, built on December 28, 2021
  - **JRE:** 11.0.13+7-b1751.21 amd64
  - **JVM:** 11.0.13+7-b1751.21 (OpenJDK 64-Bit Server VM)
- **OS:** Windows 10
- **JDK:** OpenJDK 17.0.2
- **JavaFX:** JavaFX-SDK-17.0.2
- **Database Driver:** mysql-connector-java-8.0.28

## Running the Application
- Most of the UI is relatively straightforward.
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
