package common;

import java.util.ArrayList;

public class Match {
	private String equipeV;
	private String equipeD;
	private int butV;
	private int butD;
	private int numPeriode;
	private double tempsRestantPeriode;
	private ArrayList<But> listeBut;
	private ArrayList<Penalite> listePenalite;
	private int numero;
	
	
	public Match(int numeroMatch, String equipeVis, String equipeDom){
		equipeV = equipeVis;
		equipeD = equipeDom;
		numero = numeroMatch;
		numPeriode = 1;
		butV = 0;
		butD = 0;
		tempsRestantPeriode = 20;
		listeBut = new ArrayList<But>();
		listePenalite = new ArrayList<Penalite>();
	}
	
	public Match(MatchSimp matchSimp, int numero){
		equipeV = matchSimp.getAwayTeam();
		equipeD = matchSimp.getHomeTeam();
		this.numero = numero;
		numPeriode = 1;
		tempsRestantPeriode = 20;
		listeBut = new ArrayList<But>();
		listePenalite = new ArrayList<Penalite>();
	}
	
	public void ajouterPenalite(String equipe, int periode, double tempsPeriode, int tempsPenalite){
		listePenalite.add(new Penalite(equipe, periode, tempsPeriode, tempsPenalite));
	}
	
	public void ajouterBut(String compteur, String equipe, int periode, double tempsPeriode){
		listeBut.add(new But(compteur, equipe, periode, tempsPeriode));
		if(equipe.equals(equipeD))
			++butD;
		else
			++butV;
	}
	
	public int getId(){
		return numero;
	}
}
