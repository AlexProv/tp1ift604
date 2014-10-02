package client.Communication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client //implements Runnable
{
	private DataInputStream inStream;
	private DataOutputStream outStream;
	private InetSocketAddress inetSocketAddress;
	private Socket socket;
	private String reponseServeur;
	private final long ANSWER_TIMEOUT = 2000; //TODO: en fr
	
	public Client(String host, int port) throws IOException
	{
		try
		{
			inetSocketAddress = new InetSocketAddress(host, port);
			socket = new Socket(host, port);
			System.out.println("Connection a: " + socket);
			socket.close();
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	public String envoyerRequete(String cmd)
	{
		try
		{
			String reponse = "";
			socket.connect(inetSocketAddress);
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());

			outStream.writeUTF(cmd);
			Thread t = new Thread() {
			    public void run() {
					try {
						reponseServeur = inStream.readUTF();
					} catch (IOException ioe) {
						//TODO: mettre en fr. ++
						System.out.println(ioe + " ...while waiting for an answer from the server.");
						ioe.printStackTrace();
					}
			    }
			};
			t.start();
			t.join(ANSWER_TIMEOUT);
			
			System.out.println("Reception de: " + reponse + "\n");
			socket.close();
		}
		catch (InterruptedException ie)
		{
			reponseServeur = "ie";
			System.out.println("Timeout while waiting for an answer from the server.");
		}
		catch(IOException ioe)
		{
			reponseServeur = "ioe";
			System.out.println(ioe);
		}
		
		return reponseServeur;
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
	
	
	/*public void run()
	{
		try
		{
			while (true)
			{
				String message = inStream.readUTF();
				System.out.println("Reception de: " + message + "\n");
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}//*/
}