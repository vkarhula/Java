/**
 * Pelin kontrollilohko.
 *
 * PeliControl.java 
 *
 * Mobiiliohjelmointi, Haluatko miljonääriksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import java.util.*;
import javax.microedition.lcdui.*;

/*
 * PeliControl-luokasta luodaan vain yksi ilmentymä 
 * PeliMIDlet:n käynnistyessä.
 * Kaikki näyttöoliot luodaan keskitetysti tässä ja 
 * kustakin luokasta luodaan vain yksi ilmentymä.
 * PeliControl-luokka pitää kirjaa pelitilanteesta ja 
 * ohjaa pelin kulkua.
 * PeliControl-luokka huolehtii eri näyttöjen näyttämisestä.
 */
public class PeliControl {
 	private int kysymysNro;
 	private int oikeinVastatut;
 	private boolean vaaraVastaus;
 	private boolean olkiAKaytetty;
 	private boolean olkiBKaytetty;
 	private boolean olkiCKaytetty;
 	private Vector kysymykset;
 	private int olkiAKaytettyKysymysNro;
	private PeliMIDlet midlet;

	private PaaNaytto paa;
	private KysymysNaytto kysy;
	private OlkiNaytto olki;
	private Viesti viesti;
	private TulosNaytto tulos;
	private Ajastin aika = null;
 	
 	// KONSTRUKTORI
 	public PeliControl(PeliMIDlet midlet){
 		kysymysNro = 0;
 		oikeinVastatut = 0;
 		vaaraVastaus = false;
		olkiAKaytetty = false;
	 	olkiBKaytetty = false;
	 	olkiCKaytetty = false;
	 	this.midlet = midlet;
 		
 		// Luodaan pelin kysymykset vektoriin
 		kysymykset = luoKysymykset();
 		
 		// Luodaan kaikki näyttöoliot
		tulos = new TulosNaytto(this);
 		paa = new PaaNaytto(midlet, this, tulos);
 		olki = new OlkiNaytto(midlet, this);
 		kysy = new KysymysNaytto(midlet, this, olki);
		viesti = new Viesti(this, kysy, olki, tulos);

		midlet.merkitseViiteTulosNaytostaPeliMidletille(tulos);

		// Näytä päävalikko
 		naytaNaytto("paa");
 	}
 	
 	// METODIT
 	
 	/**
 	 * Metodi valitsee aktiivisen näytöolion näytölle.
 	 */
 	public void naytaNaytto(String naytto){
 		if (naytto.equals("paa")){
			Display.getDisplay(midlet).setCurrent(paa);
 		} else if (naytto.equals("kysy")) {
 			Display.getDisplay(midlet).setCurrent(kysy);
 		} else if (naytto.equals("olki")) {
 			Display.getDisplay(midlet).setCurrent(olki);
 		} else if (naytto.equals("viesti")) {
 			Display.getDisplay(midlet).setCurrent(viesti);
 		} else if (naytto.equals("tulos")) {
 			Display.getDisplay(midlet).setCurrent(tulos);
 		}
 	}
 	
 	public void aloitaUusiPeli() {
 		kysymysNro = 0;
 		oikeinVastatut = 0;
 		vaaraVastaus = false;
		olkiAKaytetty = false;
	 	olkiBKaytetty = false;
	 	olkiCKaytetty = false;
 		// Näytetään kysymysvalikko
 		kysy.asetaKysymysListaan(annaSeuraavaKysymys());
 		naytaNaytto("kysy");
  	}
  	  	
  	public void keskeytaPeli() {
  		// Tarkista tikittääkö ajastin, jos niin peruuta se
		if (aika != null){	//ajastin on luotu
			aika.keskeytaAjastin();
			aika = null;
		}
  		//ilmoita voittosumma viestinaytolla
  		viesti.ilmoitaLopputulos();
  		naytaNaytto("viesti");
  	}

	/** 
	 * Metodi palauttaa kysymykset-vektorista private kysymysNro:n osoittaman
	 * kysymyksen.
	 */
	public Kysymys annaSeuraavaKysymys(){
		Kysymys k = (Kysymys)kysymykset.elementAt(kysymysNro);
		return k;
	}
  	
  	public void kayttajaVastasiOikein() {
  		oikeinVastatut++;
		kysymysNro++;
		// Tarkista tikittääkö ajastin, jos niin peruuta se
		if (aika != null){	//ajastin on luotu
			aika.keskeytaAjastin();
			aika = null;
		}
		// Ilmoita käyttäjälle
		if (oikeinVastatut == 8){
			viesti.ilmoitaLopputulos();
		} else {
			viesti.ilmoitaOikeaVastaus();
		}
		naytaNaytto("viesti");
  	}

  	public void kayttajaVastasiVaarin() {
  		vaaraVastaus = true;
		// Tarkista tikittääkö ajastin, jos niin peruuta se
		if (aika != null){	//ajastin on luotu
			aika.keskeytaAjastin();
			aika = null;
		}
  		// Ilmoita käyttäjälle väärä vastaus ja voittosumma
  		viesti.ilmoitaVaaraVastaus();
		naytaNaytto("viesti");
  	}
  	
  	public void olkiCAjastinLaukesi(){
  		vaaraVastaus = true;  		
  		// Ilmoita käyttäjälle pelin loppuminen ja voittosumma
  		viesti.ilmoitaLopputulos();
		naytaNaytto("viesti");
  	}
  	
	public void kaynnistaAjastin(){
		aika = new Ajastin(this, 30);	//private
	}
	
  	public int annaOikeinVastatutLkm(){
  		return oikeinVastatut;	
  	}
  	
  	public void merkitseOlkiAKysymysNro(){ 
		olkiAKaytettyKysymysNro = kysymysNro;
	}
	
	public int annaOlkiAKysymysNro(){
		if (onkoOlkiAKaytettavissa()) return (-1);	//olkiA:a ei ole käytetty
		else return olkiAKaytettyKysymysNro;
	}
	public boolean onkoOlkiAKaytettyTassaKysymyksessa(){
		// ohitetaan annaOlkiAKysymysNro():n -1 palautuksen 
		// tarkistaminen testaamalla onko olkiAKaytetty == true
		if (olkiAKaytetty && (olkiAKaytettyKysymysNro == kysymysNro)) {
			return true;
		} else {
			return false;
		}
	}
	
  	public void merkitseOlkiAKaytetyksi() {
  		olkiAKaytetty = true;
  	}
  	public void merkitseOlkiBKaytetyksi() {
  		olkiBKaytetty = true;
  	}
  	public void merkitseOlkiCKaytetyksi() {
  		olkiCKaytetty = true;
  	}
  	public boolean onkoOlkiAKaytettavissa(){
  		// Molemmat toteutukset toimivat:
  		// if(olkiAKaytetty == false) return true;
  		// else return false;
  		return (olkiAKaytetty == false);
  	}
  	public boolean onkoOlkiBKaytettavissa(){
  		return (olkiBKaytetty == false);  		
  	}
  	public boolean onkoOlkiCKaytettavissa(){
  		return (olkiCKaytetty == false);
  	}

	public boolean onkoOlkijaJaljella(){  	
		if (onkoOlkiAKaytettavissa() || 
		onkoOlkiBKaytettavissa() || 
		onkoOlkiCKaytettavissa()) {
			return true;
		} else {
			return false;
		}
	}

  	public int palautaVoittoSumma() {
  		int voitto;
  		if (vaaraVastaus){
  			// Käyttäjä vastaa väärin
	  		switch (oikeinVastatut) {
	  			case 0:
	  			case 1: 
	  				voitto = 0; 
	  				break;
	  			case 2: 
	  			case 3: 
	  			case 4:
	  				voitto = 1000;
	  				break;
	  			case 5:
	  			case 6:
	  			case 7:
	  				voitto = 10000;
	  				break;
	  			//case 8:
	  			default:
	  				voitto = 0;
	  				break;
	  		}
	  	} else {
	  		// Käyttäjä lopetti pelin itse
	  		switch (oikeinVastatut) {
	  			case 0:
	  				voitto = 0;
	  				break;
	  			case 1: 
	  				voitto = 100; 
	  				break;
	  			case 2: 
	  				voitto = 1000; 
	  				break;
	  			case 3: 
	  				voitto = 5000; 
	  				break;
	  			case 4:
	  				voitto = 10000;
	  				break;
	  			case 5:
	  				voitto = 30000; 
	  				break;
	  			case 6:
	  				voitto = 70000; 
	  				break;
	  			case 7:
	  				voitto = 100000;
	  				break;
	  			case 8:
	  				voitto = 200000;
	  				break;
	  			default:
	  				voitto = 0;
	  				break;
	  		}
	  	}
	  	return voitto;
  	}
 
	/**
	 * Luo ja palauttaa Kysymys-olioista koostuvan vektorin.
	 */
 	public Vector luoKysymykset() {
 		Vector kysymykset = new Vector();

 		Kysymys k1 = new Kysymys(
 			"Mikä on öylätti?",
 			"Yritys",
 			"Öljykanisteri",
 			"Ehtoollisleipä",
 			"Sukunimi",
 			2, 	// oikean vastauksen indeksi (0-3)
 			1);	// vaikeusaste

		Kysymys k2 = new Kysymys(
			"Kuka on pisin Dalton?",
			"Jack",
			"Averell",
			"Joe",
			"Aaron", 1, 1);
		
		Kysymys k3 = new Kysymys(
			"Mikä on cortex?",
			"Aivokuori",
			"Autovaha",
			"Ihovoide",
			"Särkylääke", 0, 2);

		Kysymys k4 = new Kysymys(
			"Paljonko verta on norsussa?",
			"150",
			"750",
			"450",
			"50", 2, 2);
 		
 		Kysymys k5 = new Kysymys(
		 	"Missä maassa on Lesotho?",
		 	"Kanadassa",
		 	"Italiassa",
		 	"Etelä-Afrikassa",
		 	"Vietnamissa", 2, 2);
 	
 		Kysymys k6 = new Kysymys(
		 	"Kuka laulaa Muuttohaukan?",
		 	"Kirka",
		 	"Topi Sorsakoski",
		 	"Jari Sillänpää",
		 	"Riki Sorsa", 3, 3);
 	
 		Kysymys k7 = new Kysymys(
			"Mikä oli New York ennen?",
			"York Town",
			"Nieuw Amsterdam",
			"New Holland",
			"Big Apple", 1, 3);

		Kysymys k8 = new Kysymys(
		 	"Missä sijaitsee painijapatsas?",
		 	"Kuortaneella",
		 	"Seinäjoella",
		 	"Nurmossa",
		 	"Ilmajoella", 2, 3);

 		kysymykset.addElement(k1);
 		kysymykset.addElement(k2);
 		kysymykset.addElement(k3);
 		kysymykset.addElement(k4);
 		kysymykset.addElement(k5);
 		kysymykset.addElement(k6);
 		kysymykset.addElement(k7);
 		kysymykset.addElement(k8);
		
		return kysymykset;
 	}
 		
}