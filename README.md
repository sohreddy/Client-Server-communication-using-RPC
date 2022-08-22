In the Project folder, all files and directories needed are present. 
 
This directory holds following files- 
FileServer.java – contains java source code for server. 
SocketClient.java– contains java source code for client. 
Client and Server folders that contain files on which operations (upload, download, rename, delete) can be performed. 
 
STEPS ON HOW TO COMPILE AND RUN THE PROGRAM-  
In order to get desired results for assignment 1, kindly follow the below written steps – 
1.	Open 2 terminals-  
Terminal1 – to compile and run FileServer.java 
Terminal2 – to compile and run SocketClient.java 
2.	In each of the above terminals, enter into the present working directories. 
3.	To compile and run JavaServer.java program in Terminal1 use the following commands: 
To compile: javac FileServer.java 
To run:  java FileServer <port number>  
     Eg: java FileServer localhost 8080 
 
 The system them returns “opening the port” indicating that the server is now ready to accept requests from the client.  
4.	Now in Terminal2, compile and run SocketClient program by giving the following command: 
To compile: javac SocketClient.java 
To run: Java SocketClient <host name> <port number>                     
    Eg: java SocketClient localhost 8080 
The system then returns the following prompt: 
 “Please enter the command to perform” 
 
 
The commands supported are:  
upload, download, rename, delete 
 
In order to precisely understand how the files are manipulated using these operations, let me give examples for each of them 
 
For UPLOAD: 
Under “Please enter the command to perform:”  
Type upload (not case sensitive) 
Then it asks us the local file name to upload
Enter any file name to get uploaded on the server
The file gets uploaded on the server
 
 
 
For DOWNLOAD: 

Under “Please enter the command to perform” 
Type download (not case sensitive) 
Then it asks us to provide the file name to download 
Enter the file name to download 
The file then gets downloaded successfully on the client
 
 
 
For RENAME: 
Under “Please enter the command to perform” 
Type rename (not case sensitive) 
Then it asks us to enter the file name on the server to rename
Type already existing file name to rename (Eg: sf4) Then it asks us to enter the new file name. Type the new name for that file (Eg: serverfile4) 
The file name gets renamed successfully 
 
 
 
For DELETE: 	 
Under “Please enter the command to perform” 
Type delete (not case sensitive) 
Then it asks to enter the file name on the server to delete
Eg: serverfile4 
The file gets deleted successfully

Finally, enter the command quit. 
