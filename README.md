# Library Management System

This Java application implements a Library Management System with features for managing general book data, personal book data for users, user authentication, and user roles.

## Features
- **General Database Management**: Allows administrators to add, update, and delete books in the general database.
- **Personal Database Management**: Allows users to add books from the general database to their personal database, and manage their personal reading data.
- **User Authentication**: Includes user registration, login, and logout functionalities.
- **User Roles**: Supports different roles such as admin and regular user with role-based access control.

## Installation
1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Configure the project settings as needed.
4. Build and run the project.

## Development Process
- **LoginPageGUI**: Implements the login page user interface.
- **UserFileManager for login page**: Checks if user exists in the users.csv file.
- **SignUpPageGUI**: Allows users to create new accounts.
- **UserFileManager for signup page**: Add new user to users.csv file and creates personaldatabase for each registered user.
- **Dashboard**: Creates a user dashboard with tabs for different functionalities based on the user's role.
- **HomePagePanel**: Creates a home page panel with a library image.
- **GeneralDatabasePanel**: Manages the general database of books in the library system. It allows administrators to add, update, and delete books, and users to add selected books to their personal databases.
- **PersonalDatabasePanel**: Manages the personal database of books for each user. It allows users to view, add, delete, and refresh their personal book records.
- **UsersPanel**: Manages user accounts and roles. It allows administrators to view, add, and delete user accounts.

## Usage
1. **Login / Register**
   - Use the provided login page to log in with your username and password.
   - If you don't have an account, you can register using the sign-up page.

2. **Dashboard**
   - Upon successful login, you'll be directed to the dashboard.
   - The dashboard displays different tabs based on your role (admin or user).
     - Admins can manage general book data and user accounts.
     - Users can manage their personal book data and view general book data.

3. **General Database**
   - Admins can add, update, and delete books in the general database.
   - Users can view general book data and add books to their personal database.

4. **Personal Database**
   - Users can manage their personal book data, including:
     - Starting entries
     - Ending entries
     - Deleting entries

5. **Users Management (Admin)**
   - Admins can view and delete user accounts from the system.

## LanguageSelectionGUI
The `LanguageSelectionGUI` class provides a graphical user interface for selecting a language from a dropdown list.

Note: Our program starts to run from `ProgramStart.java` class.

## Technologies Used
- Java
- Swing (GUI toolkit)
- CSV file handling

## Contributors
- Farid Karimli: https://github.com/ferid-kerimli
- Ali Hasanli: https://github.com/AliHasanli736
- Ismayil Panahli: https://github.com/Ismayil-Panahli
- Kanan Abilov: https://github.com/Kananabilov






