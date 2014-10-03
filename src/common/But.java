package common;

import com.google.gson.annotations.Expose;


public class But {
	
	@Expose private String pointeur;
	@Expose private String equipe;
	@Expose private int numPeriode;
	@Expose private long tempsPeriodeMs;
	
	public But(String pointeur, String equipe, int numPeriode, long tempsPeriodeMs){
		this.pointeur = pointeur;
		this.equipe = equipe;
		this.numPeriode = numPeriode;
		this.tempsPeriodeMs = tempsPeriodeMs;
	}
	
	public String ToXml()
	{
		return  SerializateurJson.objectToJson(this);
	}
	
	static public But XmlToBut(String s)
	{
		return (But)SerializateurJson.jsonToObject(s, But.class);
	}
	
	
	
	
}
