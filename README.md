# Online Voting System

A Java-based online voting system implemented using Object-Oriented Programming (OOP) concepts. This system allows voters to cast their votes for candidates and maintains vote counts in a SQL Server database.

## Features

- Voter registration and authentication
- Vote casting with duplicate prevention
- Real-time vote counting
- Candidate management
- Winner declaration based on vote count
- Database integration with Microsoft SQL Server

## Project Structure

```
├── OnlineVotingSystem.java    # Main application class
├── Admin.java                  # Admin functionality (start/stop voting, declare winner)
├── Voter.java                  # Voter class with vote casting logic
├── Candidate.java              # Candidate class with vote tracking
└── Database queries            # SQL scripts for database setup
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Microsoft SQL Server
- SQL Server JDBC Driver

## Database Setup

1. Create the database and tables by running the SQL commands from the `Database queries` file:

```sql
Create database onlinevotingsystem
Use onlinevotingsystem

CREATE TABLE Candidates (
  CandidateId INT IDENTITY(1,1) PRIMARY KEY,
  Name VARCHAR(100) NOT NULL,
  Post VARCHAR(100) NOT NULL,
  Party VARCHAR(100) NOT NULL,
  Votes INT DEFAULT 0
)

CREATE TABLE Voters (
  VoterId INT PRIMARY KEY,
  Name VARCHAR(100) NOT NULL,
  CNIC VARCHAR(15) NOT NULL,
  Gender VARCHAR(10) NOT NULL,
  Address VARCHAR(255) NOT NULL,
  HasVoted BIT DEFAULT 0
)

INSERT INTO Candidates (Name, Post, Party)
VALUES ('Imran Khan', 'President', 'PTI'),
       ('Asif Ali Zardari', 'Vice President', 'PPP'),
       ('Pervaiz Elahi', 'President', 'PTI'),
       ('Bilaval jani', 'Vice President', 'PPP');
```

2. Download the SQL Server JDBC driver from [Microsoft's official website](https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server)

## Configuration

Before running the application, update the database connection string in `OnlineVotingSystem.java` (line 10):

```java
String url = "jdbc:sqlserver://YOUR_SERVER\\INSTANCE;databaseName=onlinevotingsystem;user=YOUR_USERNAME;password=YOUR_PASSWORD;encrypt=false;";
```

Replace:
- `YOUR_SERVER\\INSTANCE` with your SQL Server instance name
- `YOUR_USERNAME` with your database username
- `YOUR_PASSWORD` with your database password

## How to Run

1. Compile all Java files:
```bash
javac *.java
```

2. Run the application (include the JDBC driver in the classpath):
```bash
java -cp .;sqljdbc4.jar OnlineVotingSystem
```

*Note: On Linux/Mac, use `:` instead of `;` in the classpath*

## Usage

1. The system will start by connecting to the database and retrieving candidate information
2. Enter voter details when prompted:
   - Voter ID
   - Name
   - CNIC (National ID)
   - Gender
   - Address
3. Select a candidate by entering their name and post
4. Type 'end' to finish voting and see results

## Classes Overview

### OnlineVotingSystem
Main class that handles:
- Database connection
- User interaction via console
- Vote processing and storage

### Admin
Manages voting session:
- Start/stop voting
- Declare winner based on vote count

### Voter
Represents a voter with:
- Personal information (ID, name, CNIC, gender, address)
- Vote casting functionality
- Duplicate vote prevention

### Candidate
Represents a candidate with:
- Name, post, and party information
- Vote count tracking

## Database Schema

### Candidates Table
| Column     | Type         | Description           |
|------------|--------------|-----------------------|
| CandidateId| INT          | Primary key (auto-inc)|
| Name       | VARCHAR(100) | Candidate name        |
| Post       | VARCHAR(100) | Position contested    |
| Party      | VARCHAR(100) | Political party       |
| Votes      | INT          | Vote count            |

### Voters Table
| Column   | Type         | Description        |
|----------|--------------|--------------------|
| VoterId  | INT          | Primary key        |
| Name     | VARCHAR(100) | Voter name         |
| CNIC     | VARCHAR(15)  | National ID        |
| Gender   | VARCHAR(10)  | Gender             |
| Address  | VARCHAR(255) | Voter address      |
| HasVoted | BIT          | Vote status flag   |

## Disclaimer

⚠️ **This project is for educational purposes only.** Use this code only for studying and learning OOP concepts and database integration. Do not use in production environments without proper security measures and enhancements.

## License

This project is created for educational purposes.

