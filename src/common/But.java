package common;


public class But {
	
	private String pointeur;
	private String equipe;
	private int numPeriode;
	private long tempsPeriodeMs;
	
	public But(String pointeur, String equipe, int numPeriode, long tempsPeriodeMs){
		this.pointeur = pointeur;
		this.equipe = equipe;
		this.numPeriode = numPeriode;
		this.tempsPeriodeMs = tempsPeriodeMs;
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
