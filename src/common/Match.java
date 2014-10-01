package common;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Match extends Observable implements Runnable {
	
	private String equipeV;
	private String equipeD;
	private int butV;
	private int butD;
	private int numPeriode;
	private Timer timerPeriode;
	private Timer timerAlert;
	
	private long tempsPeriodeMillSeconde; 
	private long stopTime;
	
	private ArrayList<But> listeBut;
	private ArrayList<Penalite> listePenalite;
	private int id;
	
	
	public long getTempsPeriodeMillSeconde() {
		return tempsPeriodeMillSeconde;
	}

	public void setTempsPeriodeMillSeconde(long tempsPeriodeMillSeconde) {
		this.tempsPeriodeMillSeconde = tempsPeriodeMillSeconde;
	}

	public Match(int numeroMatch, String equipeVis, String equipeDom){
		equipeV = equipeVis;
		equipeD = equipeDom;
		id = numeroMatch;
		numPeriode = 1;
		butV = 0;
		butD = 0;
		timerPeriode = new Timer();
		listeBut = new ArrayList<But>();
		listePenalite = new ArrayList<Penalite>();
		
	}
	
	public Match(MatchSimp matchSimp, int id){
		equipeV = matchSimp.getAwayTeam();
		equipeD = matchSimp.getHomeTeam();
		this.id = id;
		numPeriode = 1;
		listeBut = new ArrayList<But>();
		listePenalite = new ArrayList<Penalite>();
		
		tempsPeriodeMillSeconde = 0;
		stopTime =  System.currentTimeMillis();
		
		timerPeriode = new Timer();
		PeriodeTimer periodeTimer = new PeriodeTimer(this);
		timerPeriode.scheduleAtFixedRate(periodeTimer, 20*60*1000,20*60*1000);
		
		timerAlert = new Timer();
		AlertTimer alertTimer = new AlertTimer(this);
		timerPeriode.scheduleAtFixedRate(periodeTimer, 2*60*1000,2*60*1000);
				
		
		new Thread(this).start();
	}
	
	public String getEquipeV() {
		return equipeV;
	}

	public void setEquipeV(String equipeV) {
		this.equipeV = equipeV;
	}

	public String getEquipeD() {
		return equipeD;
	}

	public void setEquipeD(String equipeD) {
		this.equipeD = equipeD;
	}

	public int getButV() {
		return butV;
	}

	public void setButV(int butV) {
		this.butV = butV;
	}

	public int getButD() {
		return butD;
	}

	public void setButD(int butD) {
		this.butD = butD;
	}

	public int getNumPeriode() {
		return numPeriode;
	}

	public void setNumPeriode(int numPeriode) {
		this.numPeriode = numPeriode;
	}


	public ArrayList<But> getListeBut() {
		return listeBut;
	}

	public void setListeBut(ArrayList<But> listeBut) {
		this.listeBut = listeBut;
	}

	public ArrayList<Penalite> getListePenalite() {
		return listePenalite;
	}

	public void setListePenalite(ArrayList<Penalite> listePenalite) {
		this.listePenalite = listePenalite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void alert()
	{
		//todo Alert Client trough observator ?
		setChanged();
        notifyObservers();
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
	
	public String ToXml()
	{
		return SerializateurXML.objectToXML(this);
	}
	
	static public Match XmlToMatch(String s)
	{
		return (Match)SerializateurXML.xmlToObject(s);
	}
	
	public String PenaliteToXml()
	{
		return SerializateurXML.objectToXML(listePenalite);
	}
	
	@Override
	public void run() {
		tempsPeriodeMillSeconde += System.currentTimeMillis() - stopTime;
		stopTime = System.currentTimeMillis();
	}
	

	private class PeriodeTimer extends TimerTask{
		
		public int nbPeriode;
		Match match;
		public PeriodeTimer(Match m)
		{
			match = m;
		}
		
		@Override
		public void run() {
			nbPeriode +=1;
			match.setNumPeriode(nbPeriode);
			if(nbPeriode >= 3)
				this.cancel();
		}
	
	}
	
	private class AlertTimer extends TimerTask{
		
		public int nbAlert;
		Match match;
		public AlertTimer(Match m)
		{
			match = m;
		}
		
		@Override
		public void run() {
			nbAlert +=1;
			match.alert();
			if(nbAlert >=10)
				this.cancel();
		}
	
	} 
}



