# PeerText
An simple application that helps users edit and share text

PeerText Implements the JavaFX library to present a simple easy to use GUI 

to create and share "textfiles" among a set of predetermined users 

All edits are automatically saved in real time and updated across of User accounts

User can allow other users the permission to view, edit, or not access thier files
in a clean manner

All communication to the Database (AWS RDS Postgres) are done with asynchrounus Http REsT calls
ensuring maximum concurrency. 
