package common;

import java.net.Socket;

public class ParisPersonne {
	private String equipe;
	private int mise;
	private Socket socket;
	
	public ParisPersonne(String equipe, int mise, Socket socket){
		this.setEquipe(equipe);
		this.setMise(mise);
		this.setSocket(socket);
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
	public int getMise() {
		return mise;
	}

	/**
	 * @param mise the mise to set
	 */
	public void setMise(int mise) {
		this.mise = mise;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
