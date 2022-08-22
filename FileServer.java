import java.net.*;
import java.io.*;
import java.util.*;


public class FileServer
{
	private String workingFolder = "/home/sreddy/Documents/assignment1/Server/";
	public static void main(String args[])
	{
		try
		{
			int portNumber = Integer.parseInt(args[0]);
			FileServer fs = new FileServer();
			fs.startFileServer(portNumber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void startFileServer(int port)
	{
	    try
	    {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Opening the port");
			Socket client = ss.accept();
			System.out.println("Opened the port");
			handleClient(client);	
			
	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
	    }
	}

	private void handleClient(Socket client)
	{
	    try
	    {
			PrintWriter writeOut = new PrintWriter(client.getOutputStream(), true);
			BufferedReader readIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String clientMsg = "";

			while(true)
			{
				clientMsg = readIn.readLine();
				System.out.println("Message from client = " + clientMsg);

				if (clientMsg != null && !clientMsg.equals(""))
				{
					if (clientMsg.equalsIgnoreCase("QUIT"))
					{
						break;
					}
					else if (clientMsg.equalsIgnoreCase("RENAME"))
					{
						try
						{
							String fileNameFrom = readIn.readLine();
							String fileNameTo = readIn.readLine();
							
							File fileToRename = new File(workingFolder, fileNameFrom);
							if (fileToRename.exists())
							{
								System.out.println("Renaming file from " + fileNameFrom + " to " + fileNameTo);
								fileToRename.renameTo(new File(workingFolder, fileNameTo));
								writeOut.println("File renamed!!!");
							}
							else
							{
								System.out.println("File " + fileNameFrom + " does not exist!!!");
								writeOut.println("File does not exist!!!");
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (clientMsg.equalsIgnoreCase("DELETE"))
					{
						try
						{
							String fileName= readIn.readLine();
							System.out.println("Deleting file " + fileName);

							// can add to ask for client confirmation....

							File fileToDelete = new File(workingFolder, fileName);
							if (fileToDelete.exists())
							{
								fileToDelete.delete();
								writeOut.println("File deleted!!!");
							}
							else
							{
								writeOut.println("File does not exist!!!");
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

					}
					else if (clientMsg.equalsIgnoreCase("UPLOAD"))
					{
						FileOutputStream rcvdFOS = null;
						BufferedOutputStream rcvdBOS = null;
						try
						{
							String fileName = readIn.readLine();
							System.out.println("Receiving file " + fileName);

							String fileSizeString = readIn.readLine();
							System.out.println("Receiving file size =" + fileSizeString);
							int fileSize = Integer.parseInt(fileSizeString);

							byte [] rcvdBytes  = new byte [fileSize];
							InputStream socketIS = client.getInputStream();
							rcvdFOS = new FileOutputStream(workingFolder + fileName);
							rcvdBOS = new BufferedOutputStream(rcvdFOS);

							int bytesRead = socketIS.read(rcvdBytes, 0, rcvdBytes.length);
							rcvdBOS.write(rcvdBytes, 0, bytesRead);
							System.out.println("Received file size=" + bytesRead);
							rcvdBOS.close();

							writeOut.println("File received!!!");
							
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{

						}
					}
					else if (clientMsg.equalsIgnoreCase("DOWNLOAD"))
					{
						FileInputStream fileToSendIS = null;
						BufferedInputStream fileToSendBIS = null;
						
						try
						{
							String fileName= readIn.readLine();
							System.out.println("Sending file " + fileName);

							File fileToSend = new File(workingFolder, fileName);
							int fileSize = (int)fileToSend.length();
							System.out.println("### Sending File size:" + fileSize + " to fileserver ###");
							writeOut.println(fileSize + "");

							byte[] filebytes = new byte[(int)fileSize];
							fileToSendIS = new FileInputStream(fileToSend);
							fileToSendBIS = new BufferedInputStream(fileToSendIS);
							fileToSendBIS.read(filebytes, 0, filebytes.length);

							client.getOutputStream().write(filebytes, 0, filebytes.length);
							client.getOutputStream().flush();

							System.out.println("### DONE sending file to client ###");

						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

					}
					else
					{
						System.out.println("Unkown COMMAND from Client:" + clientMsg);
					}
					
				}
				else
				{
					System.out.println("Empty COMMAND from Client:");
				}

				/*
				while ((clientMsg = readIn.readLine()) != null)
				{
					System.out.println("Message = " + clientMsg);
					clientMessages.add(clientMsg);
				}

				System.out.println("Size of messages== " + clientMessages.size());
				*/
			}
			
	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
	    }
	}

	private boolean renameFile(String fileNameFrom, String fileNameTo)
	{
	    boolean status = false;
	    
	    try
	    {
	    }
	    catch(Exception e)
	    {
			e.printStackTrace();
	    }

	    return status;
	}

}

