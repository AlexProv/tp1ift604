package serveur;

import java.io.*;

import common.ListeDesMatchs;
import common.ListeDesMatchsSimp;
import common.Match;
import serveur.Communication.Serveur;

public class GestionnaireServeur
{
	public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args)
	{
		System.out.println("Initialisation du serveur");
		//PreLaunchSequence();
		int port1 = 9876,
			port2 = 9877;
		try
		{
			Serveur serveur1 = new Serveur(port1);
			//Serveur serveur2 = new Serveur(port2);
			String donnee = "";
			while(true)
			{
				System.out.println("Message a envoyer: ");
				donnee = bufferedReader.readLine();
				serveur1.EnvoyeTousClients(donnee);
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();
		}		
	}
	
	
	
	private static void PreLaunchSequence()
	{
		File file = new File("");
		String path = file.getAbsolutePath();
		ListeDesMatchs listMatch = new ListeDesMatchs();
		
		try
		{
			String bdtext = lireFichier(path + "/bd.xml");
			if(bdtext != null){
				listMatch = ListeDesMatchs.XmlToListDesMatchs(bdtext);
				ListeDesMatchs.setListeDesMatchs(listMatch);
			}
			else{
				listMatch.ajouterPartie(new Match(1, "Montreal", "Boston"));
				listMatch.ajouterPartie(new Match(2, "Washington", "Ottawa"));
				ListeDesMatchs.setListeDesMatchs(listMatch);
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			listMatch = ListeDesMatchs.getListeDesMatchs();
		}
	}
	
	private static String lireFichier(String nomFichier) throws IOException
	{
	   String contenu = null;
	   File fichier = new File(nomFichier);
	   if(!fichier.exists())
		   return null;
	   FileReader reader = new FileReader(fichier);
       char[] chars = new char[(int) fichier.length()];
       reader.read(chars);
       contenu = new String(chars);
       reader.close();
	   return contenu;
	}
}
