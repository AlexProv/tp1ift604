package serveur;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import common.Commands;
import common.ListeDesMatchs;
import common.Match;
import common.ParisPersonne;
import common.Penalite;

public class InterpreteurDeRequete// implements Observer
{
	

	public InterpreteurDeRequete()
	{
	
	}
	
	public String ParseCommand(String s, Socket socket) throws InterruptedException
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
				if(requestParams.length == 3){
					int previousmatchID = Integer.parseInt(requestParams[2]);
					//ListeDesMatchs.getListeDesMatchs().getMatch(previousmatchID).deleteObserver(this);
				}
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
				
				//m.addObserver(this);
				
				answer = "EquipeMatch|" + m.ToJson();
			}
			catch(Exception e )
			{
				e.printStackTrace();
			}
		}
		else if (s.startsWith(Commands.SET_BET.toString()))
		{
			String[] requestParams = s.split("/");
			//Id match
			int idMatch = Integer.parseInt(requestParams[1]);
			//V ou R
			String equipe = requestParams[2];
			int mise = Integer.parseInt(requestParams[3]);
			ListeDesMatchs.getListeDesMatchs().getMatch(idMatch).getParis().addParisQueue(new ParisPersonne(equipe, mise, socket));
		}
		return answer;
	}
	
	//@Override
	//public void update(Observable o, Object arg) {
		//call client update 2 min
	//}
}
