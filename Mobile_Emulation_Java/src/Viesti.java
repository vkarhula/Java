/*
 * Viestin�ytt�.
 *
 * Viesti.java
 *
 * Mobiiliohjelmointi, Haluatko miljon��riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import javax.microedition.lcdui.*;

public class Viesti extends Form implements CommandListener {

  	private PeliControl peli;
  	private KysymysNaytto kysy;
  	private OlkiNaytto olki;
  	private TulosNaytto tulos; 
  	private TextField field;
 	private Command selectCommand = null;
 	private Command cancelCommand = null;
 	private Command exitCommand = null;
 	private boolean nimenKysyminen = false;
 	
 	public Viesti(PeliControl peli, KysymysNaytto kysy, 
 			OlkiNaytto olki, TulosNaytto tulos) {
 		super("");
 		this.peli = peli; 
 		this.kysy = kysy;
 		this.olki = olki;
 		this.tulos = tulos;

 		append("");
        setCommandListener(this);
        
   		// viesti-olio ilmoittautuu OlkiNaytolle
		olki.siirraViittausViestistaOlkiNaytolle(this);

 	}
 	 
 	public void ilmoitaOikeaVastaus(){
 		poistaKaikkiKomennot();
 		// K�ytett�viss�
 		if (selectCommand == null) {
 			selectCommand = new Command("Jatka", Command.SCREEN, 1);
 			addCommand(selectCommand);
 		}
 		if (cancelCommand == null){
 			cancelCommand = new Command("Keskeyt�", Command.SCREEN, 1);
			addCommand(cancelCommand);
		}

		// Ei k�yt�ss�
		if (exitCommand != null){
			removeCommand(exitCommand);
			exitCommand = null;
		}
 		setTitle("Oikea vastaus!");
 		String teksti = "Olet vastannut oikein " 
 			+ peli.annaOikeinVastatutLkm() + " kertaa.";
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}
 	
 	public void ilmoitaVaaraVastaus(){
 		poistaKaikkiKomennot();
 		// Ei k�yt�ss�
 		if (selectCommand != null) {
			removeCommand(selectCommand);
			selectCommand = null;
 		}
 		if (cancelCommand != null){
			removeCommand(cancelCommand);
			cancelCommand = null;
		}
		// K�ytett�viss�, p��valikkoon
		if (exitCommand == null){
			exitCommand = new Command("Lopeta", Command.SCREEN, 1);
			addCommand(exitCommand);
		}
 		setTitle("V��r� vastaus!");
 		// Voittosumman ilmoittaminen
 		String teksti;
 		if (peli.palautaVoittoSumma() == 0) {
 			teksti = "Peli p��ttyi. Ei voittoa.";
 		} else {
 			teksti = "Peli p��ttyi. Voitit " 
 				+ peli.palautaVoittoSumma() + " euroa.";
 		}
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}
 	
 	/**
 	 * K�ytet��n kun k�ytt�j� on itse p��tt�nyt lopettaa.
 	 */
 	public void ilmoitaLopputulos(){
 		poistaKaikkiKomennot();
 		// Ei k�yt�ss�
 		if (selectCommand != null) {
			removeCommand(selectCommand);
			selectCommand = null;
 		}
 		if (cancelCommand != null){
			removeCommand(cancelCommand);
			cancelCommand = null;
		}

		// K�ytett�viss�, p��valikkoon
		if (exitCommand == null){
			exitCommand = new Command("Lopeta", Command.SCREEN, 1);
			addCommand(exitCommand);
		}
 		setTitle("Pelin lopetus");
 		// Voittosumman ilmoittaminen
 		String teksti;
 		if (peli.palautaVoittoSumma() == 0) {
 			teksti = "Peli p��ttyi. Ei voittoa.";
 		} else {
 			teksti = "Peli p��ttyi. Voitit " 
 				+ peli.palautaVoittoSumma() + " euroa.";
 		}
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}
	
	/**
	 * Kilauta kaverille -oljenkorren (olkiC) lopullinen toteutus.
	 */
	public void asetaOlkiCListaan(String teksti){
		poistaKaikkiKomennot();
 		// K�ytett�viss�
 		if (selectCommand == null) {
 			selectCommand = new Command("Jatka", Command.SCREEN, 1);
 			addCommand(selectCommand);
 		}
 		if (cancelCommand == null){
 			cancelCommand = new Command("Keskeyt�", Command.SCREEN, 1);
			addCommand(cancelCommand);
		}
		// Ei k�yt�ss�
		if (exitCommand != null){
			removeCommand(exitCommand);
			exitCommand = null;
		}
		setTitle("Kaveri vastaa");
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}	

 	// Poistetaan kaikki komennot, jotka formissa t�ll� hetkell� on
 	public void poistaKaikkiKomennot(){
 		if (cancelCommand != null){
 			removeCommand(cancelCommand);
 			cancelCommand = null;
		}
 		if (selectCommand != null){
 			removeCommand(selectCommand);
 			selectCommand = null;
 		}
 		if (exitCommand != null){
 			removeCommand(exitCommand);
 			exitCommand = null;
		}
 	}
 	
	public void kysyNimi(){
		// Tarkistetaan ensin, p��seek� k�ytt�j� RecordStoreen 
		// ennen kuin kysyt��n nime�
		int voitto = peli.palautaVoittoSumma();
		if(tulos.kysytaankoHenkilonNimi(voitto)){
			poistaKaikkiKomennot();
	 		// K�ytett�viss�
	 		if (selectCommand == null) {
	 			selectCommand = new Command("Talleta", Command.SCREEN, 1);
	 			addCommand(selectCommand);
	 		}
	 		// Ei k�yt�ss�
	 		if (cancelCommand != null){
				removeCommand(cancelCommand);
				cancelCommand = null;
			}
			if (exitCommand != null){
				removeCommand(exitCommand);
				exitCommand = null;
			}
			setTitle("Tulokset");
			TextField tf = new TextField("Anna nimesi:", "", 10, TextField.ANY);
			field = tf;	//viittaus privateen
			set(0, tf);	
			// Huom! K�ytt�j�n antama nimi luetaan commandAction()
			// selectCommand-komennon j�lkeen
			peli.naytaNaytto("viesti");
		}
	}
 	
	public void commandAction(Command c, Displayable s){
		String nimi = "";
		if (c == exitCommand){
			//Jos k�ytt�j� p��see RecordStoreen, kysyt��n nimi
			if (tulos.kysytaankoHenkilonNimi(peli.palautaVoittoSumma())){
				kysyNimi();
			} else {
				peli.naytaNaytto("paa");
			}
		} else if (c == cancelCommand) {
			peli.keskeytaPeli();
		} else if (c == selectCommand) {
			// Luetaan henkil�n nimi
			if (selectCommand.getLabel() == "Talleta"){
				nimi = field.getString();
				tulos.lisaaUusiNimi(nimi, peli.palautaVoittoSumma());
				peli.naytaNaytto("tulos");
			} else {
	 			kysy.asetaKysymysListaan(peli.annaSeuraavaKysymys());
				peli.naytaNaytto("kysy");
			}
		}
	}

}