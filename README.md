# PayDay Board Game (Digital Edition)

A fully functional, 2-player digital implementation of the classic **PayDay** board game. Players navigate a month-long calendar, managing their finances, dealing with bills, and making investments to see who ends the month with the highest net worth.

## 🚀 Features

- **Month-long Simulation**: Navigate a 31-day calendar with randomized tile placement each game.
- **MVC Architecture**: Clean separation of concerns using Model-View-Controller design patterns.
- **Financial Mechanics**:
  - **Loans & Interest**: Take out $1,000 loans with a 10% interest rate payable at month-end.
  - **Deal & Mail Cards**: Buy properties/items and manage various mail events (bills, advertisements, etc.).
  - **Dynamic Tiles**: Includes Special tiles like Lottery, Radio Contests, Yard Sales, and Casino.
- **Mini-Games**: 
  - **Sunday Football**: Gamble on match results (Home Win, Draw, or Guest Win).
  - **Thursday Crypto**: High-risk, high-reward investment simulation.
- **Jackpot**: Accumulated funds that can be won by rolling a 6.

## 🛠️ Technical Stack

- **Language**: Java
- **GUI Framework**: Java Swing / AWT
- **IDE Support**: IntelliJ IDEA

## 📂 Project Structure

- `src/project/model`: Contains core logic, game entities (Player, Board, Cards), and tile behaviors.
- `src/project/view`: Handles the graphical user interface and visual updates using Swing.
- `src/project/controller`: Manages event listeners and coordinates communication between Model and View.

## 🎮 How to Play

1. **Roll the Dice**: Start your turn by rolling the die to move your pawn.
2. **Handle Events**: Depending on the tile you land on, you may need to pay bills, collect money, or make a choice (like buying a Deal card).
3. **Manage Loans**: If you run out of cash, use the "Get Loan" button to stay in the game.
4. **End Turn**: Pass the turn to the next player.
5. **Winning**: Once both players reach Day 31 (Pay Day), loans and bills are deducted from the total cash. The player with the highest remaining balance wins!

## ⚙️ Setup & Installation

1. Clone the repository
2. Open the project in **IntelliJ IDEA**.
3. Ensure the project JDK is set to **Java 8 or higher**.
4. Locate `PayDayController.java` in `src/project/controller/`.
5. Run the `main` method to launch the game.

---
*Developed as part of the HY252 (Object-Oriented Programming) course at CSD.*
