/**
 * OlkiNaytto.java
 * 
 * Mobiiliohjelmointi, Haluatko miljon��riksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import java.util.*;
import javax.microedition.lcdui.*;

/**
 * OlkiNaytto toteuttaa oljenkorret k�ytt�j�n valinnan mukaan.
 * K�ytt�j� voi valita haluamansa oljenkorren OlkiNaytto-luokan
 * olion n�yt�lt�. Oljenkorret alustavasti toteutetaan t�ss�
 * luokassa, mutta lopullinen toteutus ja n�ytt� tapahtuu muiden 
 * luokkien avulla:
 * OlkiA toteutetaan varsinaisesti KysymysNaytto-luokan oliossa
 * ja k�ytt�j�n vastaus luetaan siell�, samoin OlkiB.
 * OlkiC:n lopputulos n�ytet��n Viesti-luokan oliossa (extends Form)
 * ja k�ytt�j�n vastaus luetaan KysymysNaytto-luokan olion avulla.
 */
public class OlkiNaytto extends List implements CommandListener {
	private Command backCommand;
	private Command selectCommand;
	private PeliMIDlet midlet;
	private PeliControl peli;
	private Viesti viesti;
	private KysymysNaytto kysy;
	private Kysymys k;	

	private int oikeaPro;
	private int vaaraPro1;
	private int vaaraPro2;
	private int vaaraPro3;

	public OlkiNaytto(PeliMIDlet midlet, PeliControl peli){
		super("", Choice.IMPLICIT);
		this.midlet = midlet;
		this.peli = peli;
		
		append("", null);
		append("", null);
		append("", null);
		
		backCommand = new Command("Peru", Command.EXIT, 1);	//->SCREEN?
		selectCommand = new Command("Valitse", Command.SCREEN, 1);
		addCommand(backCommand);
		addCommand(selectCommand);
		setCommandListener(this);
	}
	
	
	public void siirraViittausViestistaOlkiNaytolle(Viesti viesti){
		this.viesti = viesti;
	}
	
	public void sijoitaOljetListaan(KysymysNaytto kysy, Kysymys k){
		// Huom! KysymysNaytto- ja Kysymys-luokan olioiden tuonti
		this.kysy = kysy;
		this.k = k;
		int elementNum = 0;
		// Tyhjenn� kaikki oljet
		set(0,"",null);
		set(1,"",null);
		set(2,"",null);

		// Aseta k�ytett�viss� olevat oljenkorret listaan
		setTitle("Valitse oljenkorsi");		
		if (peli.onkoOlkiAKaytettavissa() == true){
 			set(elementNum, "Poista kaksi", null);
 			elementNum++;
		}
 		if (peli.onkoOlkiBKaytettavissa() == true){
 			set(elementNum, "Kysy yleis�lt�", null);
 			elementNum++;
		}
 		if (peli.onkoOlkiCKaytettavissa() == true){
 			set(elementNum, "Kilauta kaverille", null);
		}
	}
	
	/**
	 * Metodi toteuttaa oljenkorsi A:n, jossa kaksi v��r�� 
	 * vastausvaihtoehtoa poistetaan listasta. Metodi hakee
	 * oikean ja yhden v��r�n vastauksen ja vie ne parametrein� 
	 * Kysymyksen kera KysymysNaytto-luokan metodille 
	 * asetaOlkiAListaan(). T�m� metodi vasta varsinaisesti 
	 * toteuttaa OlkiA:n KysymysNaytto-luokan oliossa.
	 */
	public void toteutaOlkiA(){
		int oikea = k.annaOikeaVastausNro();
		int vaara = k.annaVaaraVastausNro();

		// Merkitse PeliControliin OlkiA k�ytetyiksi
		peli.merkitseOlkiAKaytetyksi();
		peli.merkitseOlkiAKysymysNro();

		// N�yt� olkiA:n toteutus KysymysNaytto-luokan oliossa
		kysy.asetaOlkiAListaan(k, oikea, vaara);
		peli.naytaNaytto("kysy");
	}

	/**
	 * Kysy yleis�lt� -oljenkorsi, olkiB
	 */
	public void toteutaOlkiB(){
		// P��tett�v�, huomioidaanko, jos OlkiA on k�ytetty 
		// samassa kysymyksess� -> vastausvaihtoehtojen rajaus 
		// kahteen vai n�ytet��nk� kaikki nelj�? 
		// V: Nyt n�ytet��n kaikki nelj� vaihtoehtoa.
		arvoProsentit();
		peli.merkitseOlkiBKaytetyksi();
		kysy.asetaOlkiBListaan(k, oikeaPro, vaaraPro1, vaaraPro2, vaaraPro3);
		peli.naytaNaytto("kysy");
	}

	/**
	 * Kilauta kaverille -oljenkorsi, olkiC
	 */
	public void toteutaOlkiC(){
		// P��tett�v�, huomioidaanko, jos OlkiA on k�ytetty 
		// samassa kysymyksess� -> vastausvaihtoehtojen rajaus 
		// kahteen vai n�ytet��nk� kaikki nelj�?
		// V: Nyt n�ytet��n kaikki nelj� vaihtoehtoa.
		arvoProsentit();

		// Etsi suurin
		int taulu[] = {oikeaPro, vaaraPro1, vaaraPro2, vaaraPro3};
		int lue;
		int suurin = taulu[1];
		int suurinInd = 0;
		for (int i = 0; i < 4; i++){
			lue = taulu[i];
			if (lue > suurin){
				suurin = lue;
				suurinInd = i;	
			}
		}

		// Tekstin muotoilu
		String varmuus;
		if (suurin > 50) {varmuus = "Olen varma, ett� oikea vastaus on ";}
		else if (suurin > 40) {varmuus = "Olen aika varma, ett� oikea vastaus on ";}
		else {varmuus = "Oikea vastaus on luultavasti ";}

		String vastaus;
		if (suurinInd == 0) {vastaus = k.annaOikeaVastausTxt();}
		else {vastaus = k.annaVastaus(k.annaVaaraVastausNro());}
		
		String teksti;
		teksti = varmuus + vastaus + ".";
		
		peli.merkitseOlkiCKaytetyksi();
		viesti.asetaOlkiCListaan(teksti);
		peli.naytaNaytto("viesti");
		peli.kaynnistaAjastin();
	}

	/**
	 * Metodi jakaa 100 % nelj�n muuttujan kesken 
	 * ja sijoittaa saadut arvot luokan private muuttujiin 
	 * oikeaPro, vaaraPro1, vaaraPro2 ja vaaraPro3.
	 */
	public void arvoProsentit(){
		int jaljella;
		oikeaPro = 35 + annaSatunnaisluku(30);
		jaljella = 80 - oikeaPro;
		vaaraPro1 = 5 + annaSatunnaisluku(jaljella/2);
		vaaraPro2 = 10 + annaSatunnaisluku(jaljella/3);
		vaaraPro3 = 100 - oikeaPro - vaaraPro1 - vaaraPro2;
		//rekursiivinen kutsu!
		if (vaaraPro3 < 0) arvoProsentit();
		//System.out.println("oikea = "+ oikeaPro);
		//System.out.println("vaara1 = "+ vaaraPro1);
		//System.out.println("vaara2 = "+ vaaraPro2);
		//System.out.println("vaara3 = "+ vaaraPro3);
	}

	/**
	 * Metodi palauttaa satunnaisluvun 0 ... (jakaja-1), 
	 * attribuutti jakaja m��ritt�� monestako luvusta
	 * luku valitaan.
	 */
	public int annaSatunnaisluku(int jakaja){
		Date d = new Date();
		Random r = new Random(d.getTime());
		int jako = r.nextInt() % jakaja;
		// Muutetaan luku positiiviseksi
		if (jako < 0) jako =jako - 2*jako;
		return jako;	
	} 


	public void commandAction(Command c, Displayable s){
		if (c == backCommand) {
			// Paluu takaisin kutsuneeseen KysymysNayttoon			
			peli.naytaNaytto("kysy");
		} else if (c == selectCommand){
			List down = (List)Display.getDisplay(midlet).getCurrent();
			String valittu = down.getString(down.getSelectedIndex());
			if (valittu.equals("Poista kaksi")) {
				toteutaOlkiA();
			} else if (valittu.equals("Kysy yleis�lt�")){
				toteutaOlkiB();
			} else if (valittu.equals("Kilauta kaverille")){
				toteutaOlkiC();
			}
		}
	}

}
