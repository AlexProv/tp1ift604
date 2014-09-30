package client;

import java.io.*;

import client.Communication.Client;
import serveur.Communication.Serveur;

public class GestionnaireClient {

	/**
	 * @param args
	 */
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		System.out.print("IP (127.0.0.1 by default)? ");
		String ip ="";
		int port1 = 9876,
			port2 = 9877;

		try
		{
			ip = br.readLine();
			if(null ==ip || "" == ip)
			{
				ip = "127.0.0.1";
			}
			
			Client client = new Client(ip, port1);	
		}
		catch(IOException ioe)
		{
			System.out.println("allo! du client");			
		}
		
	}

}
