package client.Communication;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import serveur.InterpreteurDeRequete;

public class Client //implements Runnable
{
	private DataInputStream inStream;
	private DataOutputStream outStream;
	private InetSocketAddress inetSocketAddress;
	private Socket socket;
	private String reponseServeur;
	private final long REPONSE_TIMEOUT = 2000;
	private InterpreteurDeRequete interpreteurRequete;
	
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
			socket = new Socket(host, port);
			//socket.connect(inetSocketAddress);
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());

			outStream.writeUTF(cmd);
			Thread t = new Thread() {
			    public void run() {
					try {
						reponseServeur = inStream.readUTF();
					} catch (IOException ioe) {
						System.out.println(ioe + " ... en attendant la reponse du serveur.");
						ioe.printStackTrace();
					}
			    }
			};
			t.start();
			t.join(REPONSE_TIMEOUT);
			
			System.out.println("Reception de: " + reponse + "\n");
			socket.close();
		}
		catch (InterruptedException ie)
		{
			reponseServeur = "ie";
			System.out.println("Timeout en attendant la reponse du serveur.");
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
				message = message.substring(9);
				System.out.println("Reception de: " + message + "\n");
				ListeDesMatchs ldm = ListeDesMatchs.JsonToListDesMatchs(message);
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
	}//*/
}
