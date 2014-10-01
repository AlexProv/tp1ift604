package serveur.Communication;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import common.ListeDesMatchs;
import common.Match;

public class Message implements Runnable, Observer
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
			try{
				m.deleteObserver(this);
				s = s.substring(GetListMatch.length());
				int i = Integer.parseInt(s);
				setFocusMatch(i);
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(FocusMatch);
				m.addObserver(this);
				answer = "EquipeMatch" + m.ToXml();
			}
			catch(Exception e )
			{
				e.printStackTrace();
			}
			
		}
		else if (s.startsWith(GetChrono))
		{
			s = s.substring(GetChrono.length());
			answer = "Chrono" + m.getTempsPeriodeMillSeconde();
			
		}
		else if (s.startsWith(GetPointage))
		{
			s = s.substring(GetPointage.length());
			answer = "Pointage" + m.getButD() + " " + m.getButV();
		}
		else if (s.startsWith(GetPenalite))
		{
			s = s.substring(GetPenalite.length());
			answer = "Penalite" + m.PenaliteToXml();
		}
		else if (s.startsWith(setBet))
		{
			s = s.substring(setBet.length());
			String VouD = s.substring(1);
			s = s.substring(1);
			int mise = Integer.parseInt(s); 
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


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//call client update 2 min
	}
}
