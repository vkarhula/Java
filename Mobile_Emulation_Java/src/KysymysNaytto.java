/**
 * KysymysNaytto.java
 * 
 * Mobiiliohjelmointi, Haluatko miljon��riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import javax.microedition.lcdui.*;

public class KysymysNaytto extends List implements CommandListener {

	private Command cancelCommand;
	private Command selectCommand;
	private PeliMIDlet midlet;
	private PeliControl peli;
	private OlkiNaytto olki;
	private Kysymys k;	
	private int listanAktiiviset = 0;
	
	// Konstruktori
	public KysymysNaytto(PeliMIDlet midlet, PeliControl peli, OlkiNaytto olki){
		super("", Choice.IMPLICIT);
		this.midlet = midlet;
		this.peli = peli;
		this.olki = olki;
		
		append("", null);
		append("", null);
		append("", null);
		append("", null);
		append("", null);
		
		cancelCommand = new Command("Keskeyt�", Command.EXIT, 1);	//->SCREEN?
		selectCommand = new Command("Valitse", Command.SCREEN, 1);
		addCommand(cancelCommand);
		addCommand(selectCommand);
		setCommandListener(this);
	}


	public void commandAction(Command c, Displayable s){
		if (c == cancelCommand) {
			peli.keskeytaPeli(); 
		} else { 	//if (c == selectCommand){
			// Lue valinnan indeksin paikasta vastaava String
			List down = (List)Display.getDisplay(midlet).getCurrent();
			String valittu = down.getString(down.getSelectedIndex());
			switch(down.getSelectedIndex()){
				case 0: 
					analysoiVastaus(valittu); break;
				case 1: 
					analysoiVastaus(valittu); break;
				case 2: 
					if (listanAktiiviset == 2){
						// OlkiA on k�ytetty t�ss� kysymyksess�
						// eik� muita olkia ole k�ytett�v�n�
						//->�l� tee mit��n	
					} else if(listanAktiiviset == 3){	
						// (olkiA on k�ytetty t�ss� kysymyksess�
						// ja oljenkorsia on j�ljell�)
						// -> naytaOlkiNaytto()
						olki.sijoitaOljetListaan(this, k);
						peli.naytaNaytto("olki");
					} else {
						// Normaalivastaus
						analysoiVastaus(valittu);
					}
					break;
				case 3: 
					// Kun OlkiA ei ole n�yt�ss�, normaalivastaus
					if (listanAktiiviset > 3){
						System.out.println("Analysoin vastauksen: " + valittu);
						analysoiVastaus(valittu);
					}
					break;
				case 4:
					// Kun OlkiA ei ole n�yt�ss�, n�yt� oljenkorsin�ytt�
					if (listanAktiiviset == 5){
						olki.sijoitaOljetListaan(this, k);
						peli.naytaNaytto("olki");
					}
					break;
				default:
					break;
			} 
		}
	}

	// METODIT

	public void asetaKysymysListaan(Kysymys k){
		this.k = k;
		setTitle(k.annaKysymysTeksti());
		set(0, k.annaVastaus(0), null);
		set(1, k.annaVastaus(1), null);
		set(2, k.annaVastaus(2), null);
		set(3, k.annaVastaus(3), null);
		set(4, "", null);
		listanAktiiviset = 4;
		asetaOljenkorsiListaan(4);
	}

	public void asetaOljenkorsiListaan(int elementNum){
		if (peli.onkoOlkijaJaljella()){
			set(elementNum, "Oljenkorsi", null);
			listanAktiiviset++;
			//append("oljenkorsi", null);
			//append("5. oljenkorsi", null);
		}
	}
	
	// Jotta olkiA:n toteutus toimisi viimeisen p��lle fiksusti
	// pit�isi kaikki listan alkiot ensin poistaa ja sitten lis�t�
	// niin monta uutta kuin tarvitsee
	// Nykyisell� toteutuksella listan alkiot 3 ja 4 ovat olemassa, 
	// vaikka niit� valitessa ei tapahdukaan mit��n. Mik��n listan
	// alkioista ei n�ytt�isi olevan aktiivinen kun alkiot 3 tai 4
	// ovat valittuja (ei mustaa korostusta).
	/**
	 * OljenkorsiA:n lopullinen toteutus.
	 */
	public void asetaOlkiAListaan(Kysymys k, int oikea, int vaara){
		this.k = k;
		if(oikea < vaara){
			set(0, k.annaVastaus(oikea), null);
			set(1, k.annaVastaus(vaara), null);
			set(2, "", null);
			set(3, "", null);
			set(4, "", null);
			//append("1. " + k.annaVastaus(oikea), null);
			//append("2. " + k.annaVastaus(vaara), null);
		} else {
			set(0, k.annaVastaus(vaara), null);
			set(1, k.annaVastaus(oikea), null);
			set(2, "", null);
			set(3, "", null);
			set(4, "", null);
			//append("1. " + k.annaVastaus(vaara), null);
			//append("2. " + k.annaVastaus(oikea), null);
		}
		listanAktiiviset = 2;
		asetaOljenkorsiListaan(2);
	}
	
	
	// Metodia voisi muuttaa siten, ett� jos olkiA k�ytetty t�ss� 
	// kysymyksess� niin n�ytt�isi vain ne vaihtoehdot!
	/**
	 * OljenkorsiB:n lopullinen toteutus.
	 */
	public void asetaOlkiBListaan(Kysymys k, int oikeaPro, 
			int pro1, int pro2, int pro3){
		String oikeaTeksti;
		String vaaraTeksti;
		int i = 0;
		int[] pro = {pro1, pro2, pro3};
		this.k = k;
		setTitle(k.annaKysymysTeksti());

		oikeaTeksti = k.annaOikeaVastausTxt() + " " + oikeaPro + "%";
		int oikeaInd = k.annaOikeaVastausNro();
		set (oikeaInd, oikeaTeksti, null);

		if (oikeaInd != 0){
			vaaraTeksti = k.annaVastaus(0) + " " + pro[i++] + "%";
			set(0, vaaraTeksti, null);
		}
		if (oikeaInd != 1){
			vaaraTeksti = k.annaVastaus(1) + " " + pro[i++] + "%";
			set(1, vaaraTeksti, null);
		}
		if (oikeaInd != 2){
			vaaraTeksti = k.annaVastaus(2) + " " + pro[i++] + "%";
			set(2, vaaraTeksti, null);
		}
		if (oikeaInd != 3){
			vaaraTeksti = k.annaVastaus(3) + " " + pro[i] + "%";
			set(3, vaaraTeksti, null);
		}
		listanAktiiviset = 4;
		asetaOljenkorsiListaan(4);
	}	
	
	/**
	 * Analysoi k�ytt�j�n antaman vastauksen vertaamalla
	 * listasta valittua String-muuttujaa oikeaan vastaukseen
	 * (String). Siirt�� toiminnan PeliControl-luokalle.
	 */
	public void analysoiVastaus(String valittu){
		if(vertaaStringeja(valittu, k.annaOikeaVastausTxt())) {
			peli.kayttajaVastasiOikein();
		} else {
			peli.kayttajaVastasiVaarin();
		}
	}
	
	/**
	 * Metodi vertailee kahta merkkijonoa ja palauttaa true, 
	 * jos 0.indeksist� alkaen pitempi merkkijono (searchMe)
	 * on sama kuin lyhyempi findMe.
	 * T�t� metodia k�ytet��n, kun prosentit sijoitetaan
	 * vastausten j�lkeen.
	 */
	public boolean vertaaStringeja(String searchMe, String findMe){
        int len = findMe.length();
        boolean loytyi = false;
        if(searchMe.regionMatches(false, 0, findMe, 0, len)){
        	loytyi = true;
        } 
       	return loytyi;
	}

	/* ei k�ytet�, vaan set (t�m� ei toiminut oikein??)
	public void tyhjennaLista(){
		setTitle("");
		System.out.println("Listan size() = " + size());
		int i;
		for (i = 0; i < size(); i++){ 	//listan koko size()
			System.out.println(i + ". Tuhoan " + getString(i));
			delete(i);
		}
	}
	*/
	
}
