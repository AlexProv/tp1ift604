package serveur.Communication;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;


public class Serveur implements Runnable
{
	private Hashtable<Socket, DataOutputStream> outputStreams = new Hashtable<Socket, DataOutputStream>();
	private ThreadPool pool = new ThreadPool(42);//TODO: peut-etre pas 42... ou dequoi de dynamique
	private ServerSocket socketServeur;

	
	public Serveur(int port) throws IOException
	{
		socketServeur = new ServerSocket(port);
		new Thread(this).start();
	}
	
	public void run ()
	{
		Message message;
		Socket socket;

		try
		{
			while(true)
			{
				synchronized (outputStreams)
				{
					socket = socketServeur.accept();
					System.out.println("Connection de " + socket);
					outputStreams.put(socket, new DataOutputStream(socket.getOutputStream()));
					message = new Message(this, socket);
					pool.addTask(message);
				}
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();
		}
	}
	
	public void EnvoyeTousClients(String message)
	{
		synchronized(outputStreams)
		{
			for (Enumeration e = outputStreams.elements(); e.hasMoreElements();)
			{
				DataOutputStream outStream = (DataOutputStream)e.nextElement();
				try
				{
					outStream.writeUTF(message);
				}
				catch(IOException ioe)
				{
					System.out.println(ioe);
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public void EnvoyeAClient(Socket socket, String message)
	{
		DataOutputStream outStream;
		synchronized(outputStreams)
		{
			outStream = outputStreams.get(socket);
			try
			{
				outStream.writeUTF(message);
			}
			catch(IOException ioe)
			{
				System.out.println(ioe);
				ioe.printStackTrace();
			}
		}
	}
	
	public void FermerConnection(Socket socket)
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