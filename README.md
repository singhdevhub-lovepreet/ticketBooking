# ticketBooking

A simple ticket-booking application built with Java (Gradle) — allows users to book tickets for events/shows, manage bookings, and view available seats.

## 📌 Table of Contents

- [About](#about)  
- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Getting Started](#getting-started)  
  - [Prerequisites](#prerequisites)  
  - [Setup & Run](#setup--run)  
- [Usage](#usage)  
- [Project Structure](#project-structure)  
- [Contributing](#contributing)  
- [License](#license)  

## 🧐 About

ticketBooking is a Java-based application using Gradle that implements a basic ticket booking system. It’s designed for educational purposes — to demonstrate backend logic, booking workflows, and a command-line or simple UI interface for booking, cancellation, and seat management.

## ✅ Features

- View list of available events/shows  
- Book tickets for an event  
- Cancel bookings  
- Show available seats / seat-inventory tracking  
- (Optionally) User registration / login — based on how you implement it  
- Booking history for users  

## 🛠 Tech Stack

- Java  
- Gradle (build tool)  
- (Optional) Any UI framework / console interface (if implemented)  
- (If database used) H2 / SQLite / MySQL / Java-supported database  

## 🚀 Getting Started

### Prerequisites

- JDK 8 or above installed  
- Gradle installed (or use the Gradle wrapper included)  
- (If database used) MySQL or any supported DB configured  

### Setup & Run

```bash
# Clone the repo
git clone https://github.com/D-Saketh/ticketBooking.git
cd ticketBooking

# If you are using Gradle wrapper
./gradlew build
./gradlew run

# Or if using Gradle locally
gradle build
gradle run
