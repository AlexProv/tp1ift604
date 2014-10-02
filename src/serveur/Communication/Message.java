package serveur.Communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;
	private int FocusMatch;


	public Message(Serveur serveur, Socket socket) 
	{
		this.serveur = serveur;
		this.socket = socket;
	}

	
	public void run()
	{
		try
		{
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			String message = inStream.readUTF();
			System.out.println( "reception du message -- " + message + " -- du socket: " + socket);
			//TODO: la gestion des messages a faire ici..
		}
		catch(EOFException eofe)
		{
			System.out.println("EOF Execption just happen..");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			serveur.FermerConnection(socket);
		}
	}
}
