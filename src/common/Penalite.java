package common;

public class Penalite {
	
	private int numPeriode;
	private long tempsPeriodeDebutMs;
	private long tempsPeriodeFinMs;
	private String equipePen;
	private int tempsPenalite;
	
	public Penalite(String equipe, int periode, long tempsPeriodeMs, int tempsPenalite){
		equipePen = equipe;
		numPeriode = periode;
		tempsPeriodeDebutMs = tempsPeriodeMs;
		tempsPeriodeFinMs = tempsPeriodeMs + (tempsPenalite * 60000);
	}
	
	public String ToXml()
	{
		return SerializateurXML.objectToXML(this);
	}
	
	static public Penalite XmlToBut(String s)
	{
		return (Penalite)SerializateurXML.xmlToObject(s);
	}

}
