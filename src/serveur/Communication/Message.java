package serveur.Communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;
	
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
			
			while(true)
			{
				String message = inStream.readUTF();
				System.out.println( "reception du message: " + message );
				//TODO: la gestion des messages a faire ici..
			}
		}
		catch(EOFException ie)
		{
			System.out.println("EOF Execption just happen..");
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
		finally
		{
			serveur.FermerConnection(socket);
		}
	}
}
