package serveur;

import java.io.IOException;
import serveur.Communication.Serveur;

public class GestionnaireServeur
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("allo! du serveur");
		
		//int port = Integer.parseInt(args[0]);
		int port1 = 9876,
			port2 = 9877;
		try
		{
			Serveur serveur1 = new Serveur(port1);
			Serveur serveur2 = new Serveur(port2);	
		}
		catch(IOException ie)
		{
			
		}
		
	}	
}
