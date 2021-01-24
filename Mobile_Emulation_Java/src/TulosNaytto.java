/**
 * Parhaiden tulosten n�ytt�
 *
 * TulosNaytto.java
 *
 * Mobiiliohjelmointi, Haluatko miljon��riksi? -peli
 * (c) Virpi Karhula 2003
 */
 
package mil;

import java.lang.*;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

/**
 * TulosNaytto-luokka sis�lt�� sek� RecordStoren k�sittelyyn
 * liittyv�t metodit ett� tuloslistan n�ytt�miseen liittyv�t metodit.
 *
 * Tuloslistalle sijoitetaan jo konstruktorissa kymmenen alkiota,
 * joiden sis�lt�j� muokataan korvaamalla edellinen String uudella.
 * RecordStoressa s�ilytet��n vain minimim��r� tietoa, vain listan 
 * aktiiviset alkiot (max 10).
 * Kun uuden henkil�n tiedot halutaan sijoittaa parhaiden tulosten 
 * listalle, sijoitetaan Voittaja-olio (muuttujina henkil�n nimi
 * ja voittosumma) vektoriin oikealle paikalle ja vektorin sis�lt� 
 * kirjoitetaan ensin Stringiksi ja sen j�lkeen byte[]-taulukkona
 * RecordStoreen. 
 * Tulokset p�ivittyv�t n�yt�lle, kun RecordStoren sis�lt� 
 * muunnetaan String-muuttujiksi, jotka sijoitetaan TulosNaytto-
 * oliossa n�ytett�v��n listaan.
 *
 * Jos RecordStoren sis�lt� halutaan tyhjent��, on konstruktorissa
 * kommenttina oleva tuhoaRecordStore()-metodikutsu poistettava 
 * k��nn�ksen ajaksi kommenteista ja k��nnett�v� ohjelma uudelleen.
 * Jos tulosten halutaan s�ilyv�n, siirret��n ko. metodikutsu
 * taas kommenteiksi, jonka j�lkeen ohjelma vaatii viel� uuden 
 * k��nn�ksen.
 */
public class TulosNaytto extends List implements CommandListener {
	private Command backCommand;
	private PeliControl peli;
	private RecordStore rs = null;
	final int PITUUS = 20;			//Recordin max pituus
	final int LISTA_MAX = 10;		//RecordStoren max lkm
	final String DBNAME = "TopTen"; //RecordStoren nimi
	//maksimilkm, mit� RecordStoresta luetaan alkioita
	final int RS_MAX_LKM = 10;	
		

	public TulosNaytto(PeliControl peli){
		super("Parhaat tulokset", Choice.IMPLICIT);
	
		// N�ytt�lista luodaan aluksi, jonka j�lkeen 
		// sis�lt�� vain muokataan: set(,,)
		append("1.", null);
		append("2.", null);
		append("3.", null);
		append("4.", null);
		append("5.", null);
		append("6.", null);
		append("7.", null);
		append("8.", null);
		append("9.", null);
		append("10.", null);

		this.peli = peli;
		
		backCommand = new Command("Jatka", Command.EXIT, 1); //SCREEN?
		addCommand(backCommand);
		setCommandListener(this);
		
		// Jos RecordStore tuhotaan, tulokset nollautuvat
		//tuhoaRecordStore();
		
		avaaRecordStore();
		sijoitaRecordStoreListaan();
	}

	////// MUIDEN LUOKKIEN K�YTT�M�T METODIT //////
	
	/**
	 * Metodi tarkistaa, mahtuuko k�ytt�j�n nimi voittosumman
	 * perusteella kymmenen parhaan tuloksen listalle.
	 * Palautusarvona boolean-tyyppinen vastaus.
	 */
	public boolean kysytaankoHenkilonNimi(int voitto){
		// Lue RecordStoren viimeisen indeksin voitto
		int rsKoko = 0;
		if ((rsKoko = lueRecordStorenKoko()) == 0){ return true;}
		else {
			Voittaja vo = muutaStringVoittajaOlioksi(lueRecord(lueRecordStorenKoko()));
			int pieninVoitto = vo.annaVoitto();
			if ((voitto > 0) && ((voitto > pieninVoitto) || (rsKoko < 10))) {
				return true; 
			} else { 
				return false; 
			}
		}
	}

	/**
	 * Metodi, jota varsinaisesti k�ytet��n uuden henkil�n
	 * pelitietojen lis��miseksi.
	 * Ennen t�m�n metodin kutsua tarkistetaan Viesti-luokan
	 * kysyNimi()-metodilla, joka kutsuu t�m�n luokan 
	 * kysytaankoHenkilonNimi()-metodia, mahtuuko nimi voiton 
	 * perusteella listalle.
	 * K�ytt�j�lt� kysyt��n nime� vain, jos voitto p��see listalle.
	 * Tietojen p�ivitys voi olla hidasta, koska ensin sijoitetaan 
	 * tiedot RecordStoreen, josta ne sitten vasta luetaan n�yt�lle.
	 */
	public void lisaaUusiNimi(String nimi, int voitto){
		Vector v = new Vector();
		// Lis�tt�v�n henkil�n tiedot
		Voittaja vo = new Voittaja(nimi, voitto);		
		// Lue koko RS vektoriin, vektorissa Voittaja-oliot(nimi, voitto)
		v = lueRecordStoreVektoriin();
		v = lisaaUusiVoittajaVektoriin(v, vo);	//sama v: input ja output?
		// Uusi tulosvektori sijoitetaan ensin RecordStoreen
		// josta se sitten luetaan n�yt�n listaan
		sijoitaVektoriRecordStoreen(v);
		sijoitaRecordStoreListaan();
	}

	////// N�YT�N METODIT //////

	/**
	 * N�yt�n metodi.
	 * Luetaan koko RecordStore ja sijoitetaan n�yt�n listaan.
	 */
	public void sijoitaRecordStoreListaan(){
		// Tyhjenn� lista
		for (int i = 0; i < LISTA_MAX; i++){
			set(i, "", null);
		}
		// Lue RecordStoren sis�lt� ja sijoita listaan
		int koko = lueRecordStorenKoko();
		int nro = 0;
		String nroS = "";
		// Jos RecordStore on tyhj�
		if (koko == 0){
			// Jos RS on tyhj�, ei lueta RS:aa
			for (int i = 0; i < LISTA_MAX; i++){
				nro = i + 1;
				nroS = Integer.toString(nro);
				set(i, (nroS + "."), null);
			}
		// RecordStoreen on tallennettu jotain
		} else {
			if (koko > RS_MAX_LKM) koko = RS_MAX_LKM;
			for (int i = 0; i < koko; i++){
				nro = i + 1;
				nroS = Integer.toString(nro);
				set(i, (nroS + ". " + lueRecord(i+1)), null);
			}
		}
	}

	////// RECORDSTOREN K�SITTELYYN LIITTYV�T METODIT //////

	/**
	 * Metodi lis�� uuden voittajan nimen ja voittosumman vektoriin. 
	 * Lis�ys tehd��n voittosummien mukaisessa j�rjestyksess� eik�
	 * erillist� j�rjest�mist� tarvita.
	 * Palautusarvona on max 10 alkioinen vektori, joka sis�lt��
	 * Voittaja-olioita.
	 */
	public Vector lisaaUusiVoittajaVektoriin(Vector v, Voittaja vo){
		try {
			// Lis�t��n suoraan oikeaan paikkaan
			boolean lisays = false;
			int koko = v.size();
			// Jos vektorissa on olioita
			if (koko > 0){
				int rsVoitto = 0;
				int voVoitto = vo.annaVoitto();
				for (int i = 0; (i < koko && (lisays == false)); i++){
					// Luetaan RecordStoressa olevat voitot
					rsVoitto = ((Voittaja)v.elementAt(i)).annaVoitto();
					if (voVoitto > rsVoitto){
						v.insertElementAt(vo, i);
						lisays = true;
					}
				}
				// Lis�t��n olio vektorin viimeiseksi
				if(!lisays){
					v.addElement(vo);
				}
			// Kun vektori on tyhj�
			} else {	
				v.addElement(vo);		
			}
			// Poista vektorista ylim��r�iset oliot siten,
			// ett� koko on max 10
			int vKoko = v.size();
			if (vKoko > 10){
				for (int i = 10; i < vKoko; i++){
					v.removeElementAt(i);
					//System.out.println("#####Poistin vektorista yhden olion");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){ System.out.println(e);
		} catch (NoSuchElementException e){ System.out.println(e);
		}
		//System.out.println("vektorin koko on " + v.size());
		return v;
	}

	/**
	 * Sijoittaa vektorin RecordStoreen. Vanhojen alkioiden
	 * p��lle kirjoitetaan ensin ja uusia alkioita lis�t��n
	 * tarpeen mukaan.
	 */
	public void sijoitaVektoriRecordStoreen(Vector v){
		String s;
		Voittaja vo;
		byte[] data;
		int rsKoko = lueRecordStorenKoko();
		int vKoko = v.size();
		int kierrokset = 10;
		//System.out.println("vektorin koko on " + vKoko);
		//System.out.println("rs:n koko on " + rsKoko);
		if (vKoko > 10) kierrokset = 10;
		else kierrokset = vKoko;
		for (int i = 0; i < kierrokset; i++){
			// Voittaja-> string 
			vo = (Voittaja)v.elementAt(i);
			s = muutaVoittajaStringiksi(vo);
			// string -> byte[]
			data = s.getBytes();
			
			// Korvataan tai lis�t��n sen mukaan
			// onko RS:ssa jo ko.indeksill� sijoitettu dataa
			if (rsKoko == 0){
				lisaaRecord(data);
			} else {
				if (i < rsKoko) {
					korvaaRecord(i+1, data);
				} else {
					lisaaRecord(data);
				}
			}
		}
	}

	/**
	 * Luetaan koko RecordStore vektoriin Voittaja-olioina.
	 * Metodia tarvitaan, kun aiotaan lis�t� uusi voittaja 
	 * RecordStoreen.
	 */
	public Vector lueRecordStoreVektoriin(){
		Vector v = new Vector();
		Voittaja vo = new Voittaja();
		int koko = lueRecordStorenKoko();
		for (int i = 0; i < koko; i++){
			vo = muutaStringVoittajaOlioksi(lueRecord(i+1));
			v.addElement(vo);
			//System.out.println("Vektorin " + i + ". alkio on " 
			//	+ vo.annaNimi() + ", " + vo.annaVoittoString());
		}
		return v;
	}
	
	////// RECORDSTOREN K�SITTELYYN LIITTYV�T METODIT //////
	
	/**
	 * RecordStore-metodi.
	 * Avaa ja tarvittaessa luo RecordStoren.
	 */
	public void avaaRecordStore(){
		// Avataan vain kerran:
		if (rs == null){
			try {
				rs = RecordStore.openRecordStore(DBNAME, true);
				//System.out.println("avasin RecordStoren");	
			} catch (RecordStoreException e){ 
				System.out.println(e);	
			} 
		}
	}
	
	/**
	 * RecordStore-metodi.
	 * Huom! T�t� metodia kutsuu vain PeliMIDlet lopettaessaan.
	 */
	public void suljeRecordStore(){
		try {
			rs.closeRecordStore();	
			//System.out.println("suljin RecordStoren");	
		} catch (RecordStoreNotOpenException e){ 
			System.out.println(e);
		} catch (RecordStoreException e){ 
			System.out.println(e);
		}
	}

	/**
	 * RecordStore-metodi.
	 * Metodia k�ytet��n, kun halutaan tyhjent�� RecordStore.
	 */
	public void tuhoaRecordStore(){
		try {
			RecordStore.deleteRecordStore(DBNAME);	
			rs = null;
			//System.out.println("tuhosin RecordStoren");
		} catch	(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * RecordStore-metodi.
	 * Lis�� uuden Recordin RecordStoreen.
	 */
	public int lisaaRecord(byte[] data){
		int id = 0;	//virheellinen arvo, jos t�m� palautuu
		try {
		    id = rs.addRecord( data, 0, data.length );
		} catch( RecordStoreFullException e ){ 
			System.out.println(e);
		    // no room left for more data
		} catch( RecordStoreNotOpenException e ){ 
			System.out.println(e);
		    // store has been closed
		} catch( RecordStoreException e ){ 
			System.out.println(e);
		    // general error
		}
		// id:t alkavat 1:sta!
		return id;
	}
	
	/**
	 * RecordStore-metodi.
	 * Korvaa RecordStoressa olemassa olevan Recordin.
	 */
	public void korvaaRecord(int id, byte[] data){
		try {
		    rs.setRecord( id, data, 0, data.length );
		} catch( RecordStoreFullException e ){ 
			System.out.println(e);
			// no room left for more data
		} catch( InvalidRecordIDException e ){ 
			System.out.println(e);
		    // record doesn't exist
		} catch( RecordStoreNotOpenException e ){ 
			System.out.println(e);
		    // store has been closed
		} catch( RecordStoreException e ){ 
			System.out.println(e);
		    // general error;
		}
	}

	/**
	 * RecordStore-metodi. 
	 * Metodi lukee RecordStoren alkion id:n perusteella
	 * ja palauttaa sen String-tyyppisen� kutsuvalle ohjelmalle.
	 */
	public String lueRecord(int id){
		int koko = lueRecordKoko(id);
		// Tehd��n kullekin Recordille juuri sopivan mittainen byte[]-taulukko
		byte[] data = new byte[koko];
		try {
		    int numBytes = rs.getRecord( id, data, 0 );
		} catch( ArrayIndexOutOfBoundsException e ){ 
			System.out.println(e);
		    // record too big for the array
		} catch( InvalidRecordIDException e ){ 
			System.out.println(e);
		    // record doesn't exist
		} catch( RecordStoreNotOpenException e ){ 
			System.out.println(e);
		    // store has been closed
		} catch( RecordStoreException e ){ 
			System.out.println(e);
		    // general error
		}
		//tyyppimuunnos byte[] -> StringBuffer -> String
		StringBuffer b = new StringBuffer(koko);
		for (int i = 0; i < data.length; i++){
			b.append( (char) data[i]);
		}
		String s = b.toString();
		//System.out.println("byte[] -> string s = XX" + s + "XXX");
		return s;
	}

	/**
	 * Metodi palauttaa RecordStoreen sijoitettujen Recordien
	 * lukum��r�n.
	 */
	public int lueRecordStorenKoko(){
		int koko = 0;
		try {
			koko = rs.getNumRecords();
		} catch (RecordStoreNotOpenException e){	
			System.out.println(e);
		}
		//System.out.println("RecordStoren koko on " + koko);
		return koko;
	}
	
	/**
	 * Metodi palauttaa RecordStoren yksitt�isen Recordin
	 * koon tavuina. Parametrin� vied��n kysytyn Recordin
	 * indeksi. (Indeksit alkavat ykk�sest�).
	 */
	public int lueRecordKoko(int id){
		int koko = 0;
		try {
			koko = rs.getRecordSize(id);
		} catch (RecordStoreNotOpenException e){ 
			System.out.println(e);
		} catch (InvalidRecordIDException e){ 
			System.out.println(e);
		} catch (RecordStoreException e){ 
			System.out.println(e);
		}
		return koko;		
	}
	
	
	////// VOITTAJA-LUOKKA //////
	
	/**
	 * Tuloslistan nimi- ja voittotietoja k�sitell��n
	 * muunnostilanteissa Voittaja-olioina, jotka
	 * sijoitetaan vektoriin, jotta mm. uuden voittajan 
	 * oikean sijaintipaikan l�yt�minen olisi yksinkertainen
	 * toteuttaa.
	 */
	private class Voittaja{
		private String nimi; // = null; // = "";
		private int voitto = 0;
		
		public Voittaja(){
			//nimi = new String();	//kun m��ritelty yll�: = null;
		}
		public Voittaja(String nimi, int voitto){
			this.nimi = nimi;
			this.voitto = voitto;	
		}
		
		public String annaNimi(){
			return nimi;	
		}
		public int annaVoitto(){
			return voitto;	
		}
		public String annaVoittoString(){
			return Integer.toString(voitto);
		}
		public void asetaNimi(String nimi){
			this.nimi = nimi;	
		}
		public void asetaVoitto(int voitto){
			this.voitto = voitto;	
		}
	}

	public String muutaVoittajaStringiksi(Voittaja vo){
		String s = vo.annaNimi() + " " + vo.annaVoittoString();
		return s;
	}

	public Voittaja muutaStringVoittajaOlioksi(String s){
		int pituus = s.length();
		int vali = 0;
		String nimi = "";
		int voitto = 0;
		Voittaja vo = new Voittaja();

		// Nimi ja voitto l�ytyv�t
		if (s.indexOf(32) >= 0){	//v�li l�ytyy, space = 32
			// tai: if (s.indexOf(" ") >= 0){
			vali = s.indexOf(32);
			nimi = s.substring(0, vali);	
			//System.out.println("nimi = " + nimi);
			//System.out.println("s.substring(vali + 1) = XX" 
			//	+ s.substring(vali + 1) + "XX");
			voitto = Integer.parseInt(s.substring(vali + 1));
			vo.asetaNimi(nimi);
			vo.asetaVoitto(voitto);

		// Vain voitto l�ytyy
		} else {
			voitto = Integer.parseInt(s);
			vo.asetaNimi("");
			vo.asetaVoitto(voitto);
		}
		return vo;
	}


	////// KOMENNOT //////
	public void commandAction(Command c, Displayable s){
		if (c == backCommand) {
			// Paluu takaisin p��valikkoon			
			peli.naytaNaytto("paa");
		} else {
		}
	}

	/*
	public void koeSijoitaRecordStoreen(){
		int rsKoko = lueRecordStorenKoko();
		byte[] data = "Ekanimi 1000".getBytes();
		if (rsKoko < 1) lisaaRecord(data); else korvaaRecord(1, data);
		data = "Tokanimi 800".getBytes();
		if (rsKoko < 2) lisaaRecord(data); else korvaaRecord(2, data);
		data = "Kolmasnimi 700".getBytes();
		if (rsKoko < 3) lisaaRecord(data); else korvaaRecord(3, data);
		data = "nelj�snimi 600".getBytes();
		if (rsKoko < 4) lisaaRecord(data); else korvaaRecord(4, data);
		data = "viides 500".getBytes();
		if (rsKoko < 5) lisaaRecord(data); else korvaaRecord(5, data);
		data = "kuudes 400".getBytes();
		if (rsKoko < 6) lisaaRecord(data); else korvaaRecord(6, data);
		data = "seitsem�s 300".getBytes();
		if (rsKoko < 7) lisaaRecord(data); else korvaaRecord(7, data);
		data = "kahdeksas 200".getBytes();
		if (rsKoko < 8) lisaaRecord(data); else korvaaRecord(8, data);
		data = "yhdeks�s 100".getBytes();
		if (rsKoko < 9) lisaaRecord(data); else korvaaRecord(9, data);
		data = "kymmenes 0".getBytes();
		if (rsKoko < 10) lisaaRecord(data); else korvaaRecord(10, data);
	}
	*/

}