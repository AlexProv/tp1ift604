package client.Communication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.TimerTask;

import common.Match;

import serveur.InterpreteurDeRequete;

public class Client //implements Runnable
{
	private DataInputStream inStream;
	private DataOutputStream outStream;
	private InetSocketAddress inetSocketAddress;
	private Socket socket;

	private InterpreteurDeRequete interpreteurRequete;
	private String host;
	private int port;
	
	
	public Client(String host, int port) throws IOException
	{
		this.host = host;
		this.port = port;
	}

	
	public String envoyerRequete(String cmd)
	{
		String reponse = "";
		try
		{
			socket = new Socket(host, port);
			//TODO: peutertre mettre un timeout sur ce socket la
			PrintWriter  printWriter = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			try
			{
				printWriter.println(cmd);
				reponse = inStream.readLine();
			}
			catch (SocketTimeoutException ste)
			{
				reponse = "ste";
				System.out.println("Timeout en attendant la reponse du serveur.");
			}
			
			//System.out.println("Reception de: " + reponse + "\n");
			socket.close();
		}
		catch(IOException ioe)
		{
			reponse = "ioe";
			System.out.println(ioe);
		}
		
		return reponse;
	}
	
	public void envoyerDonnee(String donnee)
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
	 
	
}
