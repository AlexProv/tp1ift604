package common;


public class But {
	
	private String pointeur;
	private String equipe;
	private int numPeriode;
	private double tempsPeriode;
	
	public But(String pointeur, String equipe, int numPeriode, double tempsPeriode){
		this.pointeur = pointeur;
		this.equipe = equipe;
		this.numPeriode = numPeriode;
		this.tempsPeriode = tempsPeriode;
	}
	
	public String ToXml()
	{
		return SerializateurXML.objectToXML(this);
	}
	
	static public But XmlToBut(String s)
	{
		return (But)SerializateurXML.xmlToObject(s);
	}
	
	
	
	
}
