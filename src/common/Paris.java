package common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Paris implements Runnable{
	private List<ParisPersonne> listParisMatch;
	private BlockingQueue<ParisPersonne> queueWaitParis;
	
	public Paris(){
		listParisMatch = new ArrayList<ParisPersonne>();
		queueWaitParis = new LinkedBlockingQueue<ParisPersonne>();
		new Thread(this).start();
	}
	
	public void setParis(ParisPersonne parisPersonne){
		listParisMatch.add(parisPersonne);
	}
	
	public List<ParisPersonne> getParis(){
		return listParisMatch;
	}
	
	public void addParisQueue(ParisPersonne parisPersonne) throws InterruptedException{
		queueWaitParis.put(parisPersonne);
	}
	
	public void calculerGain(){
		
	}

	@Override
	public void run() {
		while(true){
			while(queueWaitParis.isEmpty());
			listParisMatch.add(queueWaitParis.poll());
		}
	}
}
