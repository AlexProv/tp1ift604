package serveur.Communication;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;


public class Serveur
{
	private ServerSocket socketServeur;
	
	private Hashtable<Socket, DataOutputStream> outputStreams = new Hashtable<Socket, DataOutputStream>();
	
	public Serveur(int port) throws IOException
	{
		socketServeur = new ServerSocket(port);
		ThreadPool pool = new ThreadPool(42);//TODO: peut-etre pas 42... ou dequoi de dynamique
		
		Message message;
		while(true)
		{
			Socket socket = socketServeur.accept();
			System.out.print("Connection de " + socket);
			outputStreams.put(socket, new DataOutputStream(socket.getOutputStream()));
			//TODO: manage thread... new ServerThread( this, s );
			message = new Message(this, socket);
			pool.addTask(message);
		}
		
		
	}

	
	Enumeration<DataOutputStream> getOutputStreamsEle()
	{
		return outputStreams.elements();
	}
	
	void EnvoyeAClients(String message)
	{
		synchronized(outputStreams)
		{
			for (Enumeration e = getOutputStreamsEle(); e.hasMoreElements();)
			{
				DataOutputStream outStream = (DataOutputStream)e.nextElement();
				try
				{
					outStream.writeUTF(message);
				}
				catch(IOException ioe)
				{
					System.out.println(ioe);
				}
			}
		}
	}
	
	void FermerConnection(Socket socket)
	{
		synchronized(outputStreams)
		{
			System.out.println("This is the end..." + socket);
			outputStreams.remove(socket);
			try
			{
				socket.close();
			}
			catch(IOException ioe)
			{
				System.out.println("Erreur de fermeture" + socket);
				ioe.printStackTrace();
			}
		}
	}
}