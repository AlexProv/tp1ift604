package serveur;

import java.net.Socket;

import serveur.Communication.Serveur;
import common.Commands;
import common.ListeDesMatchs;
import common.Match;
import common.ParisPersonne;

public class InterpreteurDeRequete// implements Observer
{
	

	public InterpreteurDeRequete()
	{
	
	}
	
	public String ParseCommand(String s) throws InterruptedException
	{
		String answer = "";
		
		Match m;
		
		if(s.startsWith(Commands.GET_LIST_MATCH.toString()))
		{
			s = s.substring(Commands.GET_LIST_MATCH.toString().length());
			answer = "ListMatch|" + ListeDesMatchs.getListeDesMatchs().ToJson();
		}
		else if (s.startsWith(Commands.GET_EQUIPES_MATCH.toString()))
		{
			try{
				//ex: request/num/num ou request/num si premier partie
				String[] requestParams = s.split("/");
				int matchID = Integer.parseInt(requestParams[1]);
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
				
				answer = "EquipeMatch|" + m.ToJson();
			}
			catch(Exception e )
			{
				e.printStackTrace();
			}
		}
		return answer;
	}
	
	public boolean ParseCommandPari(String s, Socket socket, Serveur serveur){
		String[] requestParams = s.split("/");
		//Id match
		int idMatch = Integer.parseInt(requestParams[1]);
		//V ou R
		String equipe = requestParams[2];
		int mise = Integer.parseInt(requestParams[3]);
		synchronized (ListeDesMatchs.getListeDesMatchs().getMatch(idMatch).getParis()) {
			if(ListeDesMatchs.getListeDesMatchs().getMatch(idMatch).getNumPeriode() != 3){
				ListeDesMatchs.getListeDesMatchs().getMatch(idMatch).getParis().setParis(new ParisPersonne(equipe, mise, socket, serveur));
				return true;
			}
			else{
				return false;
			}
		}
	}
}
