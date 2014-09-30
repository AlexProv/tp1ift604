package serveur;

import java.io.*;
import serveur.Communication.Serveur;

public class GestionnaireServeur
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Initialisation du serveur");
		
		//int port = Integer.parseInt(args[0]);
		int port1 = 9876,
			port2 = 9877;
		try
		{
			Serveur serveur1 = new Serveur(port1);
			//Serveur serveur2 = new Serveur(port2);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();
		}		
	}
	
	public String lireFichier(String nomFichier)
	{
	   String contenu = null;
	   File fichier = new File(nomFichier);
	   try {
	       FileReader reader = new FileReader(fichier);
	       char[] chars = new char[(int) fichier.length()];
	       reader.read(chars);
	       contenu = new String(chars);
	       reader.close();
	   } catch (IOException ioe) {
		   System.out.println(ioe);
	       ioe.printStackTrace();
	   }
	   return contenu;
	}
}
