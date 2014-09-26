package common;

public class Penalite {
	
	private int numPeriode;
	private double tempsPeriodeDebut;
	private double tempsPeriodeFin;
	private String equipePen;
	private int tempsPenalite;
	
	public Penalite(String equipe, int periode, double tempsPeriode, int tempsPenalite){
		equipePen = equipe;
		numPeriode = periode;
		tempsPeriodeDebut = tempsPeriode;
		tempsPeriodeFin = tempsPeriode + tempsPenalite;
	}

}
