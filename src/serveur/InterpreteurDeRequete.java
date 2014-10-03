package serveur;

import java.util.Observable;
import java.util.Observer;

import common.Commands;
import common.ListeDesMatchs;
import common.Match;

public class InterpreteurDeRequete implements Observer
{
	

	public InterpreteurDeRequete()
	{
	
	}
	
	public String ParseCommand(String s)
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
				//ex: request|num|num 
				String[] requestParams = s.split("|");
				int matchID = Integer.parseInt(requestParams[1]); 
				int previousmatchID = Integer.parseInt(requestParams[2]);
				
				ListeDesMatchs.getListeDesMatchs().getMatch(previousmatchID).deleteObserver(this);
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
				
				m.addObserver(this);
				answer = "EquipeMatch|" + m.ToJson();
			}
			catch(Exception e )
			{
				e.printStackTrace();
			}
		}
		else if (s.startsWith(Commands.GET_CHRONO.toString()))
		{
			//ex: request|num 
			String[] requestParams = s.split("|");
			int matchID = Integer.parseInt(requestParams[1]); 
			m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
			
			answer = "Chrono|" + m.getTempsPeriodeMillSeconde();
			
		}
		else if (s.startsWith(Commands.GET_POINTAGE.toString()))
		{
			//ex: request num 
			String[] requestParams = s.split("|");
			int matchID = Integer.parseInt(requestParams[1]); 
			m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
			
			answer = "Pointage|" + m.getButD() + "|" + m.getButV();
		}
		else if (s.startsWith(Commands.GET_PENALITE.toString()))
		{
			//mathieu je ne sais pas comment les penalite fonctione mais il les a tt quand tu fait un get etuqipe match
			//String[] requestParams = s.split("\\s");
			//int matchID = Integer.parseInt(requestParams[1]); 
			//m = ListeDesMatchs.getListeDesMatchs().getMatch(matchID);
			
			answer = "Penalite|";
		}
		else if (s.startsWith(Commands.SET_BET.toString()))
		{
			s = s.substring(Commands.SET_BET.toString().length());
			String VouD = s.substring(1);
			s = s.substring(1);
			int mise = Integer.parseInt(s); 
		}
		return answer;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//call client update 2 min
	}
}
