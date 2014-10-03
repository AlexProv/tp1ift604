package common;

import com.google.gson.annotations.Expose;

public class Penalite {
	
	@Expose private int numPeriode;
	@Expose private long tempsPeriodeDebutMs;
	@Expose private long tempsPeriodeFinMs;
	@Expose private String equipePen;
	@Expose private int tempsPenalite;
	
	public Penalite(String equipe, int periode, long tempsPeriodeMs, int tempsPenalite){
		equipePen = equipe;
		numPeriode = periode;
		tempsPeriodeDebutMs = tempsPeriodeMs;
		tempsPeriodeFinMs = tempsPeriodeMs + (tempsPenalite * 60000);
	}
	
	public String ToJson()
	{
		return SerializateurJson.objectToJson(this);
	}
	
	static public Penalite JsonToBut(String s)
	{
		return (Penalite)SerializateurJson.jsonToObject(s, Penalite.class);
	}

}
