package client.Communication;

import java.io.*;
import java.net.Socket;

import serveur.InterpreteurDeRequete;

import com.sun.corba.se.impl.legacy.connection.SocketFactoryContactInfoImpl;

import common.ListeDesMatchs;
import common.Match;
import client.GestionnaireClient;

public class Client implements Runnable
{
	private Socket socket;
	private DataOutputStream outStream;
	private DataInputStream inStream;
	private InterpreteurDeRequete interpreteurRequete;
	
	
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

			envoyerDonnee("GetListMatch");
			while(true)
			{
				if(GestionnaireClient.bufferedReader.ready()){
					donnee = GestionnaireClient.bufferedReader.readLine();
					envoyerDonnee(donnee);
				}
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
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
	
	
	public void run()
	{
		try
		{
			while (true)
			{
				String message = inStream.readUTF();
				message = message.substring(9);
				System.out.println("Reception de: " + message + "\n");
				ListeDesMatchs ldm = ListeDesMatchs.XmlToListDesMatchs(message);
				System.out.println("Partie disponible : ");
				for(Match match : ldm.getAllMatchs()){
					System.out.println(match.getEquipeV() + " " + match.getEquipeD());
				}
				
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
}
