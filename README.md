# Connect_4_Java_RMI
Java RMI version of connect 4 (4 in a row)

# Files Description:-
- BoardInterface.java = Interface for the application
- Client.java         = Client side code 
- Server.java         = Server side code

# How to use:-
We need 4 cmd terminals to run this game.
Type the following commands in those terminals.

## 1st terminal (Compile and RMI Registry) :-
- javac *.java
- rmic Server
- rmic Client
- rmiregistry

## 2nd terminal (Server) :-
- java Server

## 3rd and 4th terminals (Clients) :-
- java Client

2 players can begin playing the game in the 3rd and 4th terminals. If we open another terminal with java Client while these 2 are connected then it wont be allowed to join.
