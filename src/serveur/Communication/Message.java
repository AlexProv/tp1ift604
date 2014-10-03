package serveur.Communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import serveur.InterpreteurDeRequete;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;

	private InterpreteurDeRequete interpreteurRequete;


	public Message(Serveur serveur, Socket socket) 
	{
		this.serveur = serveur;
		this.socket = socket;
		interpreteurRequete = new InterpreteurDeRequete();
	}

	
	public void run()
	{
		try
		{
			BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = inStream.readLine();
			System.out.println( "reception du message -- " + message + " -- du socket: " + socket);
			String answer = interpreteurRequete.ParseCommand(message, socket);
			serveur.EnvoyeAClient(socket, answer);
		}
		catch(EOFException eofe)
		{
			System.out.println("EOF Execption just happen..");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
