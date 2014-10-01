package common;

import java.util.ArrayList;

public class ListeDesMatchsSimp {
	private ArrayList<MatchSimp> listeDuel;
	private final int nbMatchsMaximum = 10;
	
	public ListeDesMatchsSimp(){
		listeDuel = new ArrayList<MatchSimp>();
	}
	
	public void ajouterPartie(MatchSimp match){
		if(listeDuel.size() < nbMatchsMaximum)
			listeDuel.add(match);
		else
			System.out.print("Match maximum atteint");
	}
	
	public ArrayList<MatchSimp> obtenirListeMatch(){
		return listeDuel;
	}
	
	public String ToXml()
	{
		return SerializateurXML.objectToXML(this);
	}
	
	static public ListeDesMatchsSimp XmlToBut(String s)
	{
		return (ListeDesMatchsSimp)SerializateurXML.xmlToObject(s);
	}
}
