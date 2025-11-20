# Banking System Simulator – Spring Boot + MongoDB

A monolithic backend application that simulates core banking operations such as creating accounts, deposit, withdrawal, transfer of funds, and transaction history management.

## 🚀 Technologies Used
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data MongoDB
- JUnit + Mockito
- SLF4J + Logback
- Jacoco (70%+ Test Coverage)

## 📂 Project Structure
controller/ → REST API Endpoints
service/ → Business Logic
repository/ → MongoDB Repositories
document/ → MongoDB Document Models
exception/ → Custom Exceptions + Global Handler
util/ → Helper Classes (API Response, ID Generator)

markdown
Copy code

## 🗄 Database
MongoDB (Local or Atlas)

Collections:
- accounts
- transactions

## 🔗 Account–Transaction Linking
Transactions store:
sourceAccount: <accountNumber>
destinationAccount: <accountNumber>

bash
Copy code
This creates a *manual reference* (One-to-Many).

## 📌 API Endpoints

### Create Account
`POST /api/accounts?holderName=John Doe`

### Get Account
`GET /api/accounts/{accountNumber}`

### Deposit
`PUT /api/accounts/{accountNumber}/deposit?amount=500`

### Withdraw
`PUT /api/accounts/{accountNumber}/withdraw?amount=200`

### Transfer
`POST /api/accounts/transfer`  
Body:
{
"sourceAccount": "JOH1234",
"destinationAccount": "ANN5678",
"amount": 100
}

bash
Copy code

### Get Transactions
`GET /api/accounts/{accountNumber}/transactions`

## 🧪 Testing
Run:
mvn clean test

makefile
Copy code

Coverage:
Open:
target/site/jacoco/index.html

markdown
Copy code

More than **70% line coverage** achieved.