package serveur.Communication;

import java.io.*;
import java.net.Socket;
import java.nio.*;

import common.ListeDesMatchs;
import common.Match;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;
	private int FocusMatch;
	
	public int getFocusMatch() {
		return FocusMatch;
	}


	private void setFocusMatch(int focusMatch) {
		if(focusMatch > -1)
			FocusMatch = focusMatch;
	}


	public Message(Serveur serveur, Socket socket) 
	{
		this.serveur = serveur;
		this.socket = socket;
		this.FocusMatch = -1;
	}
	
	
	void PreLaunchSequence()
	{
		String path = Paths.get(".").toAbsolutePath().normalize().toString();
		ListeDesMatchs listMatch;
		try
		{
			String bdtext = readFile(path + "bd.xml",Charset.forName("UTF-8"));
			listMatch = ListeDesMatchs.XmlToListDesMatchs(bdtext);
			ListeDesMatchs.setListeDesMatchs(listMatch);
		}
		catch(IOException e)
		{
			//e.printStackTrace();
			listMatch = ListeDesMatchs.getListeDesMatchs();
		}
	}
	
	String readFile(String path, Charset encoding) throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}

	void ParseCommand(String s)
	{
		String GetListMatch = "GetListMatch";
		String GetEquipesMatch = "GetEquipesMatch";
		String GetChrono = "GetChrono";
		String GetPointage = "GetPointage";
		String GetPenalite = "GetPenalite";
		String setBet = "setBet";
		
		String answer = "";
		
		Match m = ListeDesMatchs.getListeDesMatchs().getMatch(FocusMatch);
		
		if(s.startsWith(GetListMatch))
		{
			s = s.substring(GetListMatch.length());
			answer = "ListMatch" + ListeDesMatchs.getListeDesMatchs().ToXml();
		}
		else if (s.startsWith(GetEquipesMatch))
		{
			s = s.substring(GetListMatch.length());
			int i = Integer.parseInt(s);
			setFocusMatch(i);
			
			m = ListeDesMatchs.getListeDesMatchs().getMatch(FocusMatch);
			answer = "EquipeMatch" + m.ToXml();
		}
		else if (s.startsWith(GetChrono))
		{
			s = s.substring(GetChrono.length());
			
		}
		else if (s.startsWith(GetPointage))
		{
			s = s.substring(GetPointage.length());
		}
		else if (s.startsWith(GetPenalite))
		{
			s = s.substring(GetPenalite.length());
		}
		else if (s.startsWith(setBet))
		{
			s = s.substring(setBet.length());
		}
	}

	
	
	
	public void run()
	{
		try
		{
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			
			while(true)
			{
				String message = inStream.readUTF();
				System.out.println( "reception du message: " + message );
				//TODO: la gestion des messages a faire ici..
			}
		}
		catch(EOFException ie)
		{
			System.out.println("EOF Execption just happen..");
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
		finally
		{
			serveur.FermerConnection(socket);
		}
	}
}
