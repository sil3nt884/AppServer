package Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SessionManagement implements Runnable {

	Thread t;
	boolean running = true;
	
	public SessionManagement (){
		t = new Thread(this);
		t.start();
		
	}
	
	
	
	public void checkSessionExpireFileTime(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		File[]  files = getAllSessions();
		for(int i=0; i<files.length; i++){
			long last =files[i].lastModified();
			long now = cal.getTime().getTime();
			long diff = (now - last);
			if(diff >= 86400000){
				files[i].delete();
			}
		}
		
		
	}
	
	public File [] getAllSessions(){
		File dir  = new File("/web/cms/session/");
		File[] files = dir.listFiles();
		
		return files;
	}
	
	
	@Override
	public void run() {
		System.err.println("Session Service started");
		while(running){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			checkSessionExpireFileTime();
		}
		
	}

	
}
