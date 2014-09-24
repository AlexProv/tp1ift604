package serveur.Communication;

import java.io.IOException;
import java.net.ServerSocket;

public class Serveur
{
	private ServerSocket socketServeur;
	
	public Serveur(int port) throws IOException
	{
		ecoute(port);
	}
	
	private void ecoute(int port) throws IOException
	{
		socketServeur = new ServerSocket(port);
		
		while(true)
		{
			Socket sock = socketServeur.accept();
			
		}
	}
	
}