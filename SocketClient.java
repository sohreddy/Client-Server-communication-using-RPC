import java.net.*;
import java.io.*;

public class SocketClient
{
	Socket server = null;
	InputStream siStream = null;
	OutputStream soStream = null;
	private String workingFolder = "/home/sreddy/Documents/assignment1/Client/";
	public static void main(String args[])
	{
		try
		{
			String hostname = args[0];
			int portNumber = Integer.parseInt(args[1]);
			SocketClient sc = new SocketClient();
			sc.connectToFileServer(hostname, portNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void connectToFileServer(String hostname, int port)
	{
	    try
	    {
			server = new Socket(hostname,port);
			siStream = server.getInputStream();
			soStream = server.getOutputStream();

			String userInput;
			BufferedReader stdIn =  new BufferedReader(new InputStreamReader(System.in));
			
			while (true)
			{
				System.out.println("Please enter the command to perform:");
				userInput = stdIn.readLine();

				if (userInput != null && !userInput.equals(""))
				{
					if (userInput.equalsIgnoreCase("RENAME"))
					{
						System.out.println("Please enter the file name on the Server to rename:");
						String fileNameFrom = stdIn.readLine();
						
						System.out.println("Please enter the new file name:");
						String fileNameTo = stdIn.readLine();

						System.out.println("Renaming file from " + fileNameFrom + " to " + fileNameTo);
						executeRenameFile(fileNameFrom, fileNameTo);
					
					}
					else if (userInput.equalsIgnoreCase("DELETE"))
					{
						System.out.println("Please enter the file name on the Server to delete:");
						String filename = stdIn.readLine();

						executeDeleteFile(filename);

					}
					else if (userInput.equalsIgnoreCase("UPLOAD"))
					{
						System.out.println("Please enter the local file name to upload:");
						String filename = stdIn.readLine();
						//System.out.println("Please enter the local folder name for the file: " + userInput);
						//String foldername = stdIn.readLine();
						//executeUploadFile(server, "/Users/nageshg/Tinker/clientfolder/", "Test1.txt");
						executeUploadFile(filename);
					}
					else if (userInput.equalsIgnoreCase("DOWNLOAD"))
					{
						System.out.println("Please enter the file name to download:");
						String filename = stdIn.readLine();
						//System.out.println("Downloading file " + fileName);
						executeDownloadFile(filename);
					}
					else if (userInput.equalsIgnoreCase("QUIT"))
					{
						sendQuitToServer();
						break;
					}
					else
					{
						System.out.println("Unkown COMMAND!! <" + userInput + ">");
						System.out.println("Enter Quit to exit.");
					}
					
				}
				else
				{
					System.out.println("Empty COMMAND!!");
					System.out.println("Enter Quit to exit.");
				}
			}
			
	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
		}
		finally
		{
			//close resources...
		}
	}


	private void sendQuitToServer()
	{
		try
		{
			PrintWriter writeOut = new PrintWriter(soStream, true);
			writeOut.println("Quit");
			server.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void executeRenameFile(String fromName, String toName)
	{
	    try
	    {
			PrintWriter writeOut = new PrintWriter(soStream, true);
			BufferedReader readIn = new BufferedReader(new InputStreamReader(siStream));
			
			System.out.println("##############################");
			String command = "Rename";
			System.out.println("### Sending " + command + " to fileserver ###");
			writeOut.println(command);
			
			System.out.println("### Sending " + fromName + " to fileserver ###");
			writeOut.println(fromName);
			System.out.println("### Sending " + toName + " to fileserver ###");
			writeOut.println(toName);
			
			System.out.println("### Reading message from fileserver ###");
			String serverMsg = readIn.readLine();;
			System.out.println("Message from Server = " + serverMsg);
			System.out.println("##############################");

	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
	    }
	}

	private void executeDeleteFile(String fileName)
	{
	    try
	    {
			PrintWriter writeOut = new PrintWriter(soStream, true);
			BufferedReader readIn = new BufferedReader(new InputStreamReader(siStream));
			
			System.out.println("##############################");
			String command = "Delete";
			System.out.println("### Sending " + command + " to fileserver ###");
			writeOut.println(command);
			
			System.out.println("### Sending " + fileName + " to fileserver ###");
			writeOut.println(fileName);
			
			System.out.println("");
			System.out.println("### Reading message from fileserver ###");
			String serverMsg = readIn.readLine();;
			System.out.println("Message from Server = " + serverMsg);
			System.out.println("##############################");

	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
	    }
	}

	private void executeUploadFile(String fileName)
	{
		FileInputStream fileToSendIS = null;
		BufferedInputStream fileToSendBIS = null;

	    try
	    {
			PrintWriter writeOut = new PrintWriter(soStream, true);
			BufferedReader readIn = new BufferedReader(new InputStreamReader(siStream));
			
			System.out.println("##############################");
			String command = "Upload";
			System.out.println("### Sending " + command + " to fileserver ###");
			writeOut.println(command);

			System.out.println("### Sending File Name:" + fileName + " to fileserver ###");
			writeOut.println(fileName);

			File fileToSend = new File(workingFolder, fileName);
			int fileSize = (int)fileToSend.length();
			System.out.println("### Sending File size:" + fileSize + " to fileserver ###");
			writeOut.println(fileSize + "");

			
			byte[] filebytes = new byte[(int)fileSize];
			fileToSendIS = new FileInputStream(fileToSend);
			fileToSendBIS = new BufferedInputStream(fileToSendIS);
			fileToSendBIS.read(filebytes, 0, filebytes.length);

			soStream.write(filebytes, 0, filebytes.length);
			soStream.flush();

			System.out.println("### DONE uploading file to fileserver ###");

			
			System.out.println("### Reading message from fileserver ###");
			String serverMsg = readIn.readLine();;
			System.out.println("Message from Server = " + serverMsg);
			System.out.println("##############################");
			

	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
		}
		finally
		{
		
		}

	}

	private void executeDownloadFile(String fileName)
	{
		FileOutputStream rcvdFileOS = null;
		BufferedOutputStream rcvdFileBOS = null;

	    try
	    {
			PrintWriter writeOut = new PrintWriter(soStream, true);
			BufferedReader readIn = new BufferedReader(new InputStreamReader(siStream));
			
			System.out.println("##############################");
			String command = "Download";
			System.out.println("### Sending " + command + " to fileserver ###");
			writeOut.println(command);

			System.out.println("### Sending File Name:" + fileName + " to fileserver ###");
			writeOut.println(fileName);

			String fileSizeString = readIn.readLine();
			System.out.println("Receiving file size =" + fileSizeString);
			int fileSize = Integer.parseInt(fileSizeString);
			
			byte [] rcvdBytes  = new byte [fileSize];
			rcvdFileOS = new FileOutputStream(workingFolder + fileName);
			rcvdFileBOS = new BufferedOutputStream(rcvdFileOS);

			int bytesRead = siStream.read(rcvdBytes, 0, rcvdBytes.length);
			rcvdFileBOS.write(rcvdBytes, 0, bytesRead);
			System.out.println("Received file size=" + bytesRead);

			rcvdFileBOS.close();

			System.out.println("### DONE downloading file from fileserver ###");

			/*
			System.out.println("### Reading message from fileserver ###");
			String serverMsg = readIn.readLine();;
			System.out.println("Message from Server = " + serverMsg);
			System.out.println("##############################");
			*/

	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
		}
		finally
		{
		
		}

	}

}

