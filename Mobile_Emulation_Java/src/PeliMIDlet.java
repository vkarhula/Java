/**
 * Miljonaaripeli MIDlet.
 *
 * PeliMIDlet.java 
 *
 * Mobiiliohjelmointi, Haluatko miljon‰‰riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class PeliMIDlet extends MIDlet {
	
	private TulosNaytto tulos;
	
    public PeliMIDlet() {
    }

    public void startApp() {
   		PeliControl peli = new PeliControl(this);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

	// A convenience method for exiting
	void exitRequested() {
		tulos.suljeRecordStore();
        destroyApp(false);
        notifyDestroyed();
	}
	
	public void merkitseViiteTulosNaytostaPeliMidletille(TulosNaytto tulos){
		this.tulos = tulos;	
	}

}

