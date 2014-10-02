package client;

import java.io.*;
import client.Communication.Client;

public class GestionnaireClient {

	public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		System.out.println("Initialisation du client");
		System.out.println("Specifiez l'adresse IP (127.0.0.1 par defaut): ");
		String ip ="";
		int port1 = 9876,
			port2 = 9877;

		try
		{
			ip = bufferedReader.readLine();
			if(ip == null || ip == "")
			{
				ip = "127.0.0.1";
			}
			
			Client client = new Client(ip, port1);
			String reponse = "";
			
			while(reponse == "" || reponse == "ioe" || reponse == "ie")
			{
				System.out.println("Envoye de la requete: " + "GetListMatch" + " ...");
				reponse = client.envoyerRequete("GetListMatch");
			}
			
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();			
		}
		
	}

}
