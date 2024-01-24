Project Instructions:

You have been tasked with creating a computer based version of the game blackjack for a casino looking to expand their customer base. The owner has asked you to create a prototype. The goal of this project is to develop a Java-based blackjack game that incorporates the specified criteria. The game must provide the player the ability to play against a computer dealer with all the standard blackjack rules. In addition to standard blackjack rules, the game should also handle the shuffling and sorting of cards, player betting, and keeping a record of all games played at the casino.

Before a player starts a game, they should enter in their name. As the game progresses, copy everything that is input or output to the console into a log file. Make sure to use directories to keep your log files neatly organized and use a logical naming convention for each log file including the player name. If the user has played before, keep appending to an existing log file instead of creating a new one (be careful not to overwriting it). Add a timestamp recording the date and time. 

Separate from the log files, there should be an accounting file that keeps track of money. Each time a player wins or loses money, the casino should keep a summary of their cumulative winnings/losings in a file with all previous and current players' names and cumulative winnings/losings. This file will have to be read when the program starts up, and overwritten when the program ends. (Bonus: Keep the file contents sorted with the largest winners at the top.) Make sure the file is human-readable so it can be viewed outside of when the program is running.

Choose the appropriate data structures you've learned about so far and the appropriate FileIO operations and file types. 

Technical Requirements: 

Application is written in Java 8
Must be playable through the console or a GUI
Must use a standard 52 card deck with no duplicates
Dealing a card to the Player or the House removes it from the Deck
Program the game to adhere to the standard rules of blackjackLinks to an external site.
Program the game to allow betting such as doubling downLinks to an external site. or splitting
Must include classes for example:

Player
Name
Earnings
Hand of cards
Hit method
Split method
Stay method
etc.

House (the Computer)
Hand of cards
Earnings
Hit method
Stay method
etc.

Deck
All cards currently in the Deck
Shuffle method
Deal method
Card
Suit
Value

Hand
Cards in hand

Game
Main method

At the start of the program, read in the names and winnings of all previous players
At the start of the program, prompt the user for their name
During the game, keep a record of the actions taken (create a separate file for each player and save it in a directory folder)
At the end of each round,
Prompt the user if they would like to continue playing
Shuffle the deck between each round
Upon completion of a game, write to file the player's updated earnings (Optional: sort the data being saved based on winnings)
Code should compile and run
Code should conform to standard naming conventions and coding best practices
All necessary files should be present on a public GitHub repository by the due date.
GitHub commit history should show best practices including the use of branches and git commits early, often, consistently from both team members
 

Bonus Challenge Ideas: 

Build the game so that it can be played via a GUI using JavaFX
Allow the user to pick whether they want to play using the GUI or CLI upon program start
Create a game walk-through guide using MarkdownLinks to an external site. and have it present on GitHub
To keep track of the winnings, instead of FileIO use a database (jdbc and MySQL recommended) 
Some Casinos allow players to place an insurance bet. Add insurance betting to your casino's blackjack game.
Write other games for the casino such as slots, roulette, or poker.
Since we are keeping track of the winners at the casino, it would be useful to differentiate between new or returning players with the same name
Use a unique identifier in the player class to distinguish between new and returning players (ex: email, phone number, or username) 
Implement a user account feature, so when the program starts the user must login or create an account to play. (Store user login info in a file or database making sure to hash the password)
If storing user emails, along with saving to file everything that happened in a game, also email the player the results when they finish playing
At the end of a game, if any player on the top 10 list is booted out of the top 10, send them an email encouraging them to come play to regain their top 10 status