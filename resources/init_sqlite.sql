PRAGMA foreign_keys = ON;

-- Table: countries
CREATE TABLE IF NOT EXISTS countries (
  Country_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Country TEXT,
  Create_Date TEXT,
  Created_By TEXT,
  Last_Update TEXT,
  Last_Updated_By TEXT
);

-- Table: first_level_divisions
CREATE TABLE IF NOT EXISTS first_level_divisions (
  Division_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Division TEXT,
  Create_Date TEXT,
  Created_By TEXT,
  Last_Update TEXT,
  Last_Updated_By TEXT,
  Country_ID INTEGER NOT NULL,
  FOREIGN KEY (Country_ID) REFERENCES countries (Country_ID)
);

-- Table: contacts
CREATE TABLE IF NOT EXISTS contacts (
  Contact_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Contact_Name TEXT,
  Email TEXT
);

-- Table: users
CREATE TABLE IF NOT EXISTS users (
  User_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  User_Name TEXT UNIQUE,
  Password TEXT,
  Create_Date TEXT,
  Created_By TEXT,
  Last_Update TEXT,
  Last_Updated_By TEXT
);

-- Table: customers
CREATE TABLE IF NOT EXISTS customers (
  Customer_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Customer_Name TEXT,
  Address TEXT,
  Postal_Code TEXT,
  Phone TEXT,
  Create_Date TEXT,
  Created_By TEXT,
  Last_Update TEXT,
  Last_Updated_By TEXT,
  Division_ID INTEGER NOT NULL,
  FOREIGN KEY (Division_ID) REFERENCES first_level_divisions (Division_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Table: appointments
CREATE TABLE IF NOT EXISTS appointments (
  Appointment_ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Title TEXT,
  Description TEXT,
  Location TEXT,
  Type TEXT,
  Start TEXT,
  End TEXT,
  Create_Date TEXT,
  Created_By TEXT,
  Last_Update TEXT,
  Last_Updated_By TEXT,
  Customer_ID INTEGER NOT NULL,
  User_ID INTEGER NOT NULL,
  Contact_ID INTEGER NOT NULL,
  FOREIGN KEY (Contact_ID) REFERENCES contacts (Contact_ID) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (Customer_ID) REFERENCES customers (Customer_ID) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (User_ID) REFERENCES users (User_ID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Data for: countries
INSERT INTO countries (Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (1, 'U.S', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script');
INSERT INTO countries (Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (2, 'UK', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script');
INSERT INTO countries (Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (3, 'Canada', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script');

-- Data for: first_level_divisions
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (1, 'Alabama', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (2, 'Arizona', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (3, 'Arkansas', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (4, 'California', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (5, 'Colorado', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (6, 'Connecticut', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (7, 'Delaware', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (8, 'District of Columbia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (9, 'Florida', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (10, 'Georgia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (11, 'Idaho', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (12, 'Illinois', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (13, 'Indiana', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (14, 'Iowa', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (15, 'Kansas', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (16, 'Kentucky', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (17, 'Louisiana', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (18, 'Maine', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (19, 'Maryland', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (20, 'Massachusetts', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (21, 'Michigan', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (22, 'Minnesota', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (23, 'Mississippi', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (24, 'Missouri', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (25, 'Montana', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (26, 'Nebraska', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (27, 'Nevada', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (28, 'New Hampshire', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (29, 'New Jersey', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (30, 'New Mexico', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (31, 'New York', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (32, 'North Carolina', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (33, 'North Dakota', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (34, 'Ohio', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (35, 'Oklahoma', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (36, 'Oregon', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (37, 'Pennsylvania', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (38, 'Rhode Island', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (39, 'South Carolina', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (40, 'South Dakota', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (41, 'Tennessee', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (42, 'Texas', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (43, 'Utah', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (44, 'Vermont', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (45, 'Virginia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (46, 'Washington', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (47, 'West Virginia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (48, 'Wisconsin', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (49, 'Wyoming', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (52, 'Hawaii', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (54, 'Alaska', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (60, 'Northwest Territories', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (61, 'Alberta', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (62, 'British Columbia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (63, 'Manitoba', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (64, 'New Brunswick', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (65, 'Nova Scotia', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (66, 'Prince Edward Island', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (67, 'Ontario', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (68, 'Québec', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (69, 'Saskatchewan', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (70, 'Nunavut', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (71, 'Yukon', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (72, 'Newfoundland and Labrador', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 3);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (101, 'England', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 2);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (102, 'Wales', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 2);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (103, 'Scotland', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 2);
INSERT INTO first_level_divisions (Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES (104, 'Northern Ireland', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 2);

-- Data for: contacts
INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (1, 'Anika Costa', 'acoasta@company.com');
INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (2, 'Daniel Garcia', 'dgarcia@company.com');
INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (3, 'Li Lee', 'llee@company.com');

-- Data for: users
INSERT INTO users (User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (1, 'test', 'test', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script');
INSERT INTO users (User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (2, 'admin', 'admin', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script');

-- Data for: customers
INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (1, 'Daddy Warbucks', '1919 Boardwalk', '01291', '869-908-1875', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 29);
INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (2, 'Lady McAnderson', '2 Wonder Way', 'AF19B', '11-445-910-2134', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 103);
INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (3, 'Dudley Do-Right', '48 Horse Manor ', '28198', '874-916-2672', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 60);
INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (11, 'mr. 11', '11', '111', '11', NULL, NULL, NULL, NULL, 67);

-- Data for: appointments
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (1, 'title', 'description', 'location', 'Planning Session', '2020-05-28T12:00', '2020-05-28T13:00', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 1, 1, 3);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (2, 'title', 'description', 'location', 'De-Briefing', '2020-05-29T12:00', '2020-05-29T13:00', '2022-03-27T14:05:34', 'script', '2022-03-27 14:05:34.0', 'script', 2, 2, 2);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (6, 'a', 'a', 'a', 'a', '2022-06-04T17:00', '2022-06-04T17:15', NULL, NULL, NULL, NULL, 2, 1, 3);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (10, 'a', 'a', 'a', 'a', '2022-06-05T21:00', '2022-06-05T22:00', NULL, NULL, NULL, NULL, 3, 1, 2);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (24, 'aa', 'aa', 'aa', 'aa', '2022-06-07T23:00', '2022-06-07T23:15', NULL, NULL, NULL, NULL, 2, 1, 2);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (26, 'a', 'a', 'a', 'a', '2022-06-08T01:00', '2022-06-08T01:45', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (32, 'a', 'a', 'a', 'a', '2022-12-01T13:45', '2022-12-01T14:00', NULL, NULL, NULL, NULL, 11, 1, 2);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (33, 'aa', 'aa', 'aa', 'aa', '2022-06-09T19:00', '2022-06-09T19:15', NULL, NULL, NULL, NULL, 1, 1, 1);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (36, 'a', 'a', 'aa', 'a', '2022-06-09T19:00', '2022-06-09T19:30', NULL, NULL, NULL, NULL, 2, 2, 2);
INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (37, 'Try this', 'testiiiiing', 'Space', 'Meeting', '2026-06-26T01:00', '2026-06-26T02:00', NULL, NULL, NULL, NULL, 1, 1, 3);

