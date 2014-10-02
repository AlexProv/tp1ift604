package serveur.Communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.sun.corba.se.spi.activation.Server;

import serveur.GestionnaireRequete;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;
	private GestionnaireRequete gestionnaireRequete;
	private int FocusMatch;
	
	public int getFocusMatch() {
		return FocusMatch;
	}


	public Message(Serveur serveur, Socket socket) 
	{
		this.serveur = serveur;
		this.socket = socket;
		this.gestionnaireRequete = new GestionnaireRequete();
		this.FocusMatch = -1;
	}

	
	public void run()
	{
		while(true){
			try
			{
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				String message = inStream.readUTF();
				System.out.println( "reception du message -- " + message + " -- du socket: " + socket);
				String answer = gestionnaireRequete.ParseCommand(message);
				serveur.EnvoyeAClient(socket, answer);
				System.out.println(answer);
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
}
