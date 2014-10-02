package serveur.Communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
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
		while(true){
			try
			{
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				String message = inStream.readUTF();
				System.out.println( "reception du message -- " + message + " -- du socket: " + socket);
				String answer = interpreteurRequete.ParseCommand(message);
				serveur.EnvoyeAClient(socket, answer);
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
			//finally
			//{
				//serveur.FermerConnection(socket);
			//}
		}
	}
}
