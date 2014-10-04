package common;

import java.net.Socket;

import serveur.Communication.Serveur;

public class ParisPersonne {
	private String equipe;
	private double mise;
	private Socket socket;
	private Serveur serveur;
	
	public ParisPersonne(String equipe, double mise, Socket socket, Serveur serveur){
		this.setEquipe(equipe);
		this.setMise(mise);
		this.setSocket(socket);
		this.setServeur(serveur);
	}

	/**
	 * @return the equipe
	 */
	public String getEquipe() {
		return equipe;
	}

	/**
	 * @param equipe the equipe to set
	 */
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	/**
	 * @return the mise
	 */
	public double getMise() {
		return mise;
	}

	/**
	 * @param mise the mise to set
	 */
	public void setMise(double mise) {
		this.mise = mise;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Serveur getServeur() {
		return serveur;
	}

	public void setServeur(Serveur serveur) {
		this.serveur = serveur;
	}
}
