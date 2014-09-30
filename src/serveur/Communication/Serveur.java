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
		//socketServeur.setSoTimeout(3000);//Debloquer le serveur quand personne ne se connecte
		new Thread(this).start();
	}
	
	public void run ()
	{
		Message message;
		Socket socket;

			while(true)
			{
				try{

					socket = socketServeur.accept();
					System.out.println("Connection de " + socket);
					
					//outputStreams.put(socket, new DataOutputStream(socket.getOutputStream()));
					message = new Message(this, socket);
					pool.addTask(message);
				//} catch (SocketTimeoutException s){
					//System.out.println("Timeout waiting for client...");}
				} catch (IOException e) {
					e.printStackTrace();
					//break;
				}
			}
	}
	
	public void EnvoyeTousClients(String message)
	{
		synchronized(outputStreams)
		{
			for (Enumeration<DataOutputStream> e = outputStreams.elements(); e.hasMoreElements();)
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
	
	public void EnvoyeAClient(Socket socket, String reponse)
	{
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println(reponse);
		} catch (IOException ioe) {
			System.out.println(ioe);
			ioe.printStackTrace();
		}
		//DataOutputStream outStream;
		/*synchronized(outputStreams)
		{
			outStream = outputStreams.get(socket);
			try
			{
				outStream.writeUTF(reponse);
			}
			catch(IOException ioe)
			{
				System.out.println(ioe);
				ioe.printStackTrace();
			}
		}*/
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