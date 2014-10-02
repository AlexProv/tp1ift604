package common;

import java.util.ArrayList;

public class ListeDesMatchs {
	private ArrayList<Match> liste;
	private final int nbMatchsMaximum = 10;
	
	static ListeDesMatchs lm;
	

	public static void setListeDesMatchs(ListeDesMatchs l)
	{
		lm = l;
	}
	public static ListeDesMatchs getListeDesMatchs()
	{
		if(lm == null)
		{
			lm = new ListeDesMatchs(1);
			return lm;
		}
		return lm;
	}
	
	private ListeDesMatchs(int i)
	{
		liste = new ArrayList<Match>();
		lm = this;
	}
	
	public ListeDesMatchs(){
		liste = new ArrayList<Match>();
		lm = this;
	}
	
	public int ajouterPartie(Match match){
		if(liste.size() < nbMatchsMaximum)
			liste.add(match);
		else
			System.out.print("Match maximum atteint");
		return match.getId() + 1;
	}
	
	public int ajouterPartie(MatchSimp match, int numero){
		if(liste.size() < nbMatchsMaximum)
			liste.add(new Match(match, numero));
		else
			System.out.print("Match maximum atteint");
		return ++numero;
	}
	
	public Match getMatch(int noMatch){
		for (Match match : liste) {
			if(match.getId() == noMatch)
				return match;
		}
		return null;
	}
	
	
	public String ToXml()
	{
		return SerializateurXML.objectToXML(this);
	}
	
	static public ListeDesMatchs XmlToListDesMatchs(String s)
	{
		return (ListeDesMatchs)SerializateurXML.xmlToObject(s);
	}
}
