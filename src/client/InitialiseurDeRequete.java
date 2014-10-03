package client;

import serveur.Communication.Serveur;
import common.Commands;
import common.ListeDesMatchs;
import common.Match;

public class InitialiseurDeRequete
{
	private Serveur serveur;
	private Match matchCourrant;
	private ListeDesMatchs lm;
	
	public InitialiseurDeRequete(Serveur serveur)
	{
		this.serveur = serveur;
	}
	
	public String getListeEquipeRequest()
	{
		return Commands.GET_LIST_MATCH.toString();
	}
	
	public String getMatchReqeust(int newMatch, int oldMatch)
	{
		return Commands.GET_EQUIPES_MATCH.toString() + "|" + newMatch + "|" + oldMatch;
	}
	
	public void ParseAnswer(String s)
	{
		String[] answers = s.split("|");
		
		if(answers[0] == Commands.LIST_MATCH.toString())
		{
			lm = ListeDesMatchs.JsonToListDesMatchs(answers[1]);
		}
		
		if(answers[0] == Commands.EQUIPE_MATCH.toString())
		{
			matchCourrant = Match.JsonToMatch(answers[1]);
		}
	}
	
}
