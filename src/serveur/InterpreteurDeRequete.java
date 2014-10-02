package serveur;

import java.util.Observable;
import java.util.Observer;

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
		String GetListMatch = "GetListMatch";
		String GetEquipesMatch = "GetEquipesMatch";
		String GetChrono = "GetChrono";
		String GetPointage = "GetPointage";
		String GetPenalite = "GetPenalite";
		String setBet = "setBet";
		
		String answer = "";
		
		Match m = ListeDesMatchs.getListeDesMatchs().getMatch(focusMatch);
		
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
				
				m = ListeDesMatchs.getListeDesMatchs().getMatch(focusMatch);
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
		return answer;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//call client update 2 min
	}
}
