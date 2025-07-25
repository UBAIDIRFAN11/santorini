# Santorini Board Game (Java Implementation)

This project is a turn-based digital implementation of the board game **Santorini**, written in Java using the Swing GUI framework. 
It includes features such as standard movement, building, god powers, win/lose conditions, and turn management - along with a timer system and an original Nature Meter extension that introduces fast-paced, environmentally conscious decision-making.

**Note:** After cloning, mark the resources directory as “Resources Root” in IntelliJ (Right-click > Mark Directory As > Resources Root)

---

## Project Structure

- `santorini.model` – Core game logic (e.g., Board, Cell, Player, Worker, etc.)
- `santorini.model.actions` – Encapsulated game actions (e.g. MoveAction, BuildAction, etc.)
- `santorini.model.gods` - All GodCards and Special Powers (e.g. GaiaCard, GaiaPower, ZeusCard, ZeusPower, etc.)
- `santorini.model.loseconditions` Lose condition logic (e.g. StuckLoseCondition, NatureMeterDepletedLoseCondition) - implements LoseCondition interface
- `santorini.model.winconditions` Win condition logic (WinCondition interface, BasicWinCondition)
- `santorini.model.meters` Nature meter logic (e.g. ImpactLevel enum, NatureMeter, etc.)
- `santorini.model.timing` Timer functionality (e.g. DefaultPlayerTimer, GameTimerManager, TimeOutHandler, etc.)
- `santorini.view` – GUI components (e.g. BoardUI, ViewCell, TimerLabel, etc.)
- `santorini.controller` – Game Loop (e.g. TurnManager, GodCardAssigner, WorkerPlacementStrategy, etc.)
- `Application.java` – Entry point for the application
- Deliverables Documents - Can be found in `docs` -> `deliverables`
- Class Responsibility Collaborator (CRC) Cards - Can be found in `docs` -> `crc`
- Low-Fidelity Prototypes - Can be found in `docs` -> `lofi`
- Design Rationales (sprint 2) - Can be found in `docs` -> `design rationale` with draft iterations
- Domain Model (sprint 1) - Can be found in `docs` -> `model` with draft iterations
- UML Diagrams - Can be found in `docs` -> `uml` with draft iterations
- Sequence Diagrams - Can be found in `docs` -> `sq_diagram` with draft iterations
- Manual Testing Document (sprint 2) - Can be found in `docs` -> `Testing`
- Resources - Images used in the game can be found in `resources`

---

## How to create an executable:

-	In Intellij, click on File --> Project Structure
-	In Project Structure window, select Artefacts --> Plus Symbol (+) --> JAR -->  from modules with dependencies
-	Set main class in the artefact. This is the class that contains the psvm method --> OK
-	Set an output directory, e.g. output executable JAR to the desktop
-	Select Build --> Build Artefacts --> select artefact you want to build --> Build

OpenJDK version "22.0.2" (Corretto 22.0.2.9.1) and 
Ensure you are using Java 17 or above to run the project successfully.

---

## Author

**Personal/Complete Version Developed by:**
- Ubaid Irfan

Originally a group assignment for FIT3077, with contributions from:
- Ubaid Irfan,
- Allan Ho, 
- Aaron, 
- Uchralt Myagmarsuren 

**Original pixel-art and design aesthetic** by Ubaid Irfan.  

Monash University – FIT3077 Assignment Project Semester 1, 2025