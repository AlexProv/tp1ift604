package client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import client.Communication.Client;
import common.But;
import common.Commands;
import common.ListeDesMatchs;
import common.Match;
import common.Penalite;

public class InitialiseurDeRequete
{
	private Match matchCourant;
	private ListeDesMatchs lm;
	
	private Client client;
	private Timer timerAlert;
	private AlertTimer alertTimer;
	
	public InitialiseurDeRequete(Client c)
	{
		client = c;
		
		timerAlert = new Timer();
	}
	
	public String getListeEquipeRequest()
	{
		return Commands.GET_LIST_MATCH.toString();
	}
	
	public String getMatchRequest(int newMatch, int oldMatch)
	{
		return Commands.GET_EQUIPES_MATCH.toString() + "|" + newMatch + "|" + oldMatch;
	}
	
	public void ParseAnswer(String s)
	{
		String[] answers = s.split("\\|");
		
		if(answers[0].equals(Commands.LIST_MATCH.toString()))
		{
			
			lm = ListeDesMatchs.JsonToListDesMatchs(answers[1]);
			for(Match match : lm.getAllMatchs()){
				System.out.println(match.getId() + " " + match.getEquipeD() + " vs. " + match.getEquipeV());
			}
			System.out.println("Pour plus de details sur la partie, entrez GetEquipesMatch/(id)!");
			
			if(alertTimer != null)
			{
				alertTimer.cancel();
			}
		}
		
		if(answers[0].equals(Commands.EQUIPE_MATCH.toString()))
		{	
			matchCourant = Match.JsonToMatch(answers[1]);
			System.out.println("Resume du match no." + matchCourant.getId() + " : " + matchCourant.getEquipeV() + " vs. " + matchCourant.getEquipeD());
			System.out.println(matchCourant.getButV() + "-" + matchCourant.getButD());
			long currentTime = matchCourant.getTempsPeriodeMillSeconde();
			System.out.println("Periode " + matchCourant.getNumPeriode() + ", " + setTime(currentTime));
			System.out.println("Sommaire des buts");
			int i = 1;
			for(But but: matchCourant.getListeBut()){
				System.out.println(i + ". " + but.getEquipe() + " Marque par : " + but.getPointeur() + ", Periode " + but.getNumPeriode() + " " + setTime(but.getTempsPeriodeMs()));
				++i;
			}
			
			System.out.println("Sommaire des penalites");
			i = 1;
			for(Penalite penalite: matchCourant.getListePenalite()){
				if(penalite.getTempsPeriodeFinMs() >= currentTime)
					System.out.println(i + ". " + penalite.getEquipePen() + " " + penalite.getTempsPenalite() +" min, Periode " + penalite.getNumPeriode() + " " + setTime(penalite.getTempsPeriodeDebutMs()) + " (En cours)");
				else
					System.out.println(i + ". " + penalite.getEquipePen() + " " + penalite.getTempsPenalite() +" min, Periode " + penalite.getNumPeriode() + " " + setTime(penalite.getTempsPeriodeDebutMs()));
				++i;
			}
			alertTimer = new AlertTimer(matchCourant);
			timerAlert.scheduleAtFixedRate(alertTimer, 30*1000,30*1000);

		}
		if(answers[0].equals(Commands.SET_BET.toString())){
			System.out.println(answers[1]);
		}
	}
	
	private String setTime(long ms){
		if(TimeUnit.MILLISECONDS.toMinutes(ms) >= 40)
			return String.format("%02d:%02d", 
			    (TimeUnit.MILLISECONDS.toMinutes(ms) - 40) ,
			    TimeUnit.MILLISECONDS.toSeconds(ms) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
		else if(TimeUnit.MILLISECONDS.toMinutes(ms) >= 20)
			return String.format("%02d:%02d", 
				    (TimeUnit.MILLISECONDS.toMinutes(ms) - 20) ,
				    TimeUnit.MILLISECONDS.toSeconds(ms) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
		else
			return String.format("%02d:%02d", 
				    TimeUnit.MILLISECONDS.toMinutes(ms) ,
				    TimeUnit.MILLISECONDS.toSeconds(ms) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
	}
	
	
	private class AlertTimer extends TimerTask{
		
		//public int nbAlert;
		Match match;
		public AlertTimer(Match m)
		{
			match = m;
		}
	
/*		public void setMatch(Match m)
		{
			match = m;
		}*/
		
		@Override
		public void run() {
			synchronized (match) {
				if(match.getTempsPeriodeMillSeconde() <= 60 * 1000 * 60)
				{
					String s = client.envoyerRequete(Commands.GET_EQUIPES_MATCH.toString() + "/" + match.getId());
					String[] answers = s.split("\\|");
					if(answers[0].equals(Commands.EQUIPE_MATCH.toString()))
					{
						match = Match.JsonToMatch(answers[1]);
						long currentTime = match.getTempsPeriodeMillSeconde();
						System.out.println("Nouveau chrono au match: Periode " + match.getNumPeriode() + ", " + setTime(currentTime));
						
					}
				}
			}
		}
	}
}
