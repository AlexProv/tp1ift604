package client.Communication;

import java.io.*;
import java.net.Socket;

import client.GestionnaireClient;

public class Client implements Runnable
{
	private Socket socket;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	
	
	public Client(String host, int port) throws IOException
	{
		try
		{
			socket = new Socket(host, port);
			System.out.println("Connection a: " + socket);
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
			String donnee = "";
			new Thread(this).start();

			while(true)
			{
				donnee = GestionnaireClient.bufferedReader.readLine();
				envoyerDonnee(donnee);
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	
	private void envoyerDonnee(String donnee)
	{
		try
		{
			outStream.writeUTF(donnee);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	
	public void run()
	{
		try
		{
			while (true)
			{
				String message = inStream.readUTF();
				System.out.println("envoye de: " + message + "\n");
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
}