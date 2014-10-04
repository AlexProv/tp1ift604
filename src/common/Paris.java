package common;

import java.util.ArrayList;
import java.util.List;

public class Paris{
	private List<ParisPersonne> listParisMatch;
	private double totalMise = 0;
	
	public Paris(){
		listParisMatch = new ArrayList<ParisPersonne>();
	}
	
	public void setParis(ParisPersonne parisPersonne){
		listParisMatch.add(parisPersonne);
		totalMise += parisPersonne.getMise();
	}
	
	public List<ParisPersonne> getParis(){
		return listParisMatch;
	}
	
	public void calculerGain(String gagnant){
		double totalMiseProrata = 0;
		double totalMiseGagnante = 0;
		if(gagnant == "N"){
			for (ParisPersonne parisPersonne : listParisMatch) {
				parisPersonne.getServeur().EnvoyeAClient(parisPersonne.getSocket(), "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:" + (parisPersonne.getMise() * 0.75) + "$");
			}
		}
		else{
			totalMiseProrata = (0.75 * totalMise);
			for (ParisPersonne parisPersonne : listParisMatch) {
				if(parisPersonne.getEquipe() == gagnant){
					totalMiseGagnante += parisPersonne.getMise();
				}
				else{
					parisPersonne.getServeur().EnvoyeAClient(parisPersonne.getSocket(), "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:0$");
				}
			}
			for (ParisPersonne parisPersonne : listParisMatch) {
				if(parisPersonne.getEquipe() == gagnant){
					double currentMise = parisPersonne.getMise();
					double pourcentageTotalGagnant = currentMise/totalMiseGagnante;
					parisPersonne.getServeur().EnvoyeAClient(parisPersonne.getSocket(), "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:" + (pourcentageTotalGagnant * totalMiseProrata) + "$");
				}
			}
		}
	}
}
