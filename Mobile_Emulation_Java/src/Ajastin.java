/**
 * Ajastin.java 
 *
 * Mobiiliohjelmointi, Haluatko miljon‰‰riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.util.*;

public class Ajastin {
    Timer timer;
    private PeliControl peli;

    public Ajastin(PeliControl peli, int seconds) {
    	this.peli = peli;
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            //System.out.println("Time's up!");
            // Jos timer laukeaa (k‰ytt‰j‰ ei ole vastannut), peli p‰‰ttyy
            peli.olkiCAjastinLaukesi();
            timer.cancel(); //Terminate the timer thread
        }
    }

	public void keskeytaAjastin(){
		timer.cancel();	
	}

    /*
    public static void main(String args[]) {
        System.out.println("About to schedule task.");
        new Reminder(5);
        System.out.println("Task scheduled.");
    }
    */
}

