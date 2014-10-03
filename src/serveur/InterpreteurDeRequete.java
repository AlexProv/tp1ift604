package serveur;

import java.util.Observable;
import java.util.Observer;

import common.Commands;
import common.ListeDesMatchs;
import common.Match;

public class InterpreteurDeRequete implements Observer
{
	private int focusMatch;

	public InterpreteurDeRequete()
	{
		focusMatch = -1;
	}

	public int getFocusMatch() {
		return focusMatch;
	}
	
	private void setFocusMatch(int focusMatch) {
		if(focusMatch > -1)
			this.focusMatch = focusMatch;
	}
	
	public String ParseCommand(String s)
	{
		String answer = "";
		
		Match m = ListeDesMatchs.getListeDesMatchs().getMatch(focusMatch);
		
		if(s.startsWith(Commands.GET_LIST_MATCH.toString()))
		{
			s = s.substring(Commands.GET_LIST_MATCH.toString().length());
			answer = "ListMatch" + ListeDesMatchs.getListeDesMatchs().ToXml();
		}
		else if (s.startsWith(Commands.GET_EQUIPES_MATCH.toString()))
		{
			try{
				m.deleteObserver(this);
				s = s.substring(Commands.GET_EQUIPES_MATCH.toString().length());
				int i = Integer.parseInt(s);
				setFocusMatch(i);
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(focusMatch);
				m.addObserver(this);
				answer = "EquipesMatch" + m.ToXml();
			}
			catch(Exception e )
			{
				e.printStackTrace();
			}
			
		}
		else if (s.startsWith(Commands.GET_CHRONO.toString()))
		{
			s = s.substring(Commands.GET_CHRONO.toString().length());
			answer = "Chrono" + m.getTempsPeriodeMillSeconde();
			
		}
		else if (s.startsWith(Commands.GET_POINTAGE.toString()))
		{
			s = s.substring(Commands.GET_POINTAGE.toString().length());
			answer = "Pointage" + m.getButD() + " " + m.getButV();
		}
		else if (s.startsWith(Commands.GET_PENALITE.toString()))
		{
			s = s.substring(Commands.GET_PENALITE.toString().length());
			answer = "Penalite" + m.PenaliteToXml();
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
