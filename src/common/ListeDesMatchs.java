package common;

import java.util.ArrayList;

public class ListeDesMatchs {
	private ArrayList<Match> liste;
	private final int nbMatchsMaximum = 10;
	
	public ListeDesMatchs(){
		liste = new ArrayList<Match>();
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
	
	public Match obtenirMatch(int noMatch){
		for (Match match : liste) {
			if(match.getId() == noMatch)
				return match;
		}
		return null;
	}
}
