/*
 * Viestinäyttö.
 *
 * Viesti.java
 *
 * Mobiiliohjelmointi, Haluatko miljonääriksi? -peli
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
 		// Käytettävissä
 		if (selectCommand == null) {
 			selectCommand = new Command("Jatka", Command.SCREEN, 1);
 			addCommand(selectCommand);
 		}
 		if (cancelCommand == null){
 			cancelCommand = new Command("Keskeytä", Command.SCREEN, 1);
			addCommand(cancelCommand);
		}

		// Ei käytössä
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
 		// Ei käytössä
 		if (selectCommand != null) {
			removeCommand(selectCommand);
			selectCommand = null;
 		}
 		if (cancelCommand != null){
			removeCommand(cancelCommand);
			cancelCommand = null;
		}
		// Käytettävissä, päävalikkoon
		if (exitCommand == null){
			exitCommand = new Command("Lopeta", Command.SCREEN, 1);
			addCommand(exitCommand);
		}
 		setTitle("Väärä vastaus!");
 		// Voittosumman ilmoittaminen
 		String teksti;
 		if (peli.palautaVoittoSumma() == 0) {
 			teksti = "Peli päättyi. Ei voittoa.";
 		} else {
 			teksti = "Peli päättyi. Voitit " 
 				+ peli.palautaVoittoSumma() + " euroa.";
 		}
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}
 	
 	/**
 	 * Käytetään kun käyttäjä on itse päättänyt lopettaa.
 	 */
 	public void ilmoitaLopputulos(){
 		poistaKaikkiKomennot();
 		// Ei käytössä
 		if (selectCommand != null) {
			removeCommand(selectCommand);
			selectCommand = null;
 		}
 		if (cancelCommand != null){
			removeCommand(cancelCommand);
			cancelCommand = null;
		}

		// Käytettävissä, päävalikkoon
		if (exitCommand == null){
			exitCommand = new Command("Lopeta", Command.SCREEN, 1);
			addCommand(exitCommand);
		}
 		setTitle("Pelin lopetus");
 		// Voittosumman ilmoittaminen
 		String teksti;
 		if (peli.palautaVoittoSumma() == 0) {
 			teksti = "Peli päättyi. Ei voittoa.";
 		} else {
 			teksti = "Peli päättyi. Voitit " 
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
 		// Käytettävissä
 		if (selectCommand == null) {
 			selectCommand = new Command("Jatka", Command.SCREEN, 1);
 			addCommand(selectCommand);
 		}
 		if (cancelCommand == null){
 			cancelCommand = new Command("Keskeytä", Command.SCREEN, 1);
			addCommand(cancelCommand);
		}
		// Ei käytössä
		if (exitCommand != null){
			removeCommand(exitCommand);
			exitCommand = null;
		}
		setTitle("Kaveri vastaa");
 		StringItem item = new StringItem("", teksti);
 		set(0, item);
	}	

 	// Poistetaan kaikki komennot, jotka formissa tällä hetkellä on
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
		// Tarkistetaan ensin, pääseekö käyttäjä RecordStoreen 
		// ennen kuin kysytään nimeä
		int voitto = peli.palautaVoittoSumma();
		if(tulos.kysytaankoHenkilonNimi(voitto)){
			poistaKaikkiKomennot();
	 		// Käytettävissä
	 		if (selectCommand == null) {
	 			selectCommand = new Command("Talleta", Command.SCREEN, 1);
	 			addCommand(selectCommand);
	 		}
	 		// Ei käytössä
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
			// Huom! Käyttäjän antama nimi luetaan commandAction()
			// selectCommand-komennon jälkeen
			peli.naytaNaytto("viesti");
		}
	}
 	
	public void commandAction(Command c, Displayable s){
		String nimi = "";
		if (c == exitCommand){
			//Jos käyttäjä pääsee RecordStoreen, kysytään nimi
			if (tulos.kysytaankoHenkilonNimi(peli.palautaVoittoSumma())){
				kysyNimi();
			} else {
				peli.naytaNaytto("paa");
			}
		} else if (c == cancelCommand) {
			peli.keskeytaPeli();
		} else if (c == selectCommand) {
			// Luetaan henkilön nimi
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