/**
 * P‰‰valikkon‰yttˆ.
 *
 * PaaNaytto.java
 *
 * Mobiiliohjelmointi, Haluatko miljon‰‰riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import javax.microedition.lcdui.*;

public class PaaNaytto extends List implements CommandListener {

  	private PeliMIDlet midlet;
  	private boolean peliControlLuotu = false;
  	private boolean tulosOlioLuotu = false;
  	private PeliControl peli;
  	private TulosNaytto tulos;
 	private Command exitCommand;
 	private Command selectCommand;
 	
 	public PaaNaytto(PeliMIDlet midlet, PeliControl peli, TulosNaytto tulos) {
 		super("Haluatko miljon‰‰riksi?", Choice.IMPLICIT);
 		this.midlet = midlet;
 		this.peli = peli;
 		this.tulos = tulos;
 		exitCommand = new Command("Lopeta", Command.EXIT, 1);
 		selectCommand = new Command("Valitse", Command.SCREEN, 1);
		append("1. Aloita peli", null);
		append("2. Tulokset", null);
		append("3. Lopetus", null);
		addCommand(exitCommand);
		addCommand(selectCommand);
        setCommandListener(this);
 	}
 	
	public void commandAction(Command c, Displayable s){
		if (c == exitCommand) {
			midlet.exitRequested();
		} else if(c == selectCommand) {
			List down = (List)Display.getDisplay(midlet).getCurrent();
			switch(down.getSelectedIndex()){
				case 0:	
					// Pelin aloitus
					peli.aloitaUusiPeli();
					break;
				case 1:	
					// Parhaat tulokset
					peli.naytaNaytto("tulos");
					break;
				case 2:	
					// Pelin lopetus
					midlet.exitRequested();
					break;
				default:
					break;
			}	
		}
	}

}