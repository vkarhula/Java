/*
 * Kysymysluokka
 *
 * Kysymys.java
 *
 * Mobiiliohjelmointi, Haluatko miljonääriksi? -peli
 * (c) Virpi Karhula 2003
 */

package mil;

import java.lang.*;
import java.util.*;

public class Kysymys {
 	
 	private String kysymys; 
 	private String vastaus[];
	private int oikeaVastausNro;	//indeksi vastaus-taulukossa (0-3)
	private int vaikeusAste;

	// KONSTRUKTORIT
	public Kysymys(){}

	public Kysymys(String kysymys, 
				   String vastausA, 
				   String vastausB, 
				   String vastausC,
				   String vastausD, 
				   int oikeaVastausNro,
				   int vaikeus) {
		vastaus = new String[4];
		this.kysymys = kysymys;
		vastaus[0] = vastausA;
		vastaus[1] = vastausB;
		vastaus[2] = vastausC;
		vastaus[3] = vastausD;
		vaikeusAste = vaikeus;
		this.oikeaVastausNro = oikeaVastausNro;
	} 	

  
  	// METODIT
	public String annaKysymysTeksti(){
		return kysymys;
	}
	
	public String annaVastaus(int indeksi){
		return vastaus[indeksi];
	}

	/**
	 * Funktio palauttaa oikean vastauksen indeksin
	 * vastaus-taulusta.
	 */
  	public int annaOikeaVastausNro() {
  		return oikeaVastausNro;	
  	}
  	
  	public String annaOikeaVastausTxt(){
  		return annaVastaus(annaOikeaVastausNro());	
  	}
  	
  	/**
  	 * Palautetaan satunnaisesti indeksi yhteen vääristä vastauksista.
  	 * Jos palautettu vastaus onkin oikea vastaus, kutsutaan
  	 * funktiota uudelleen rekursiivisesti.
  	 */
	public int annaVaaraVastausNro() {
		int luku = annaSatunnaisluku(4);
		if (luku == annaOikeaVastausNro()) {
			luku++;
			if (luku == 4) luku = 0;
		}
		return luku;
  	}

	/*
	 * Metodi palauttaa satunnaisluvun 0 ... (jakaja-1), 
	 * attribuutti jakaja määrittää monestako luvusta
	 * luku valitaan.
	 */
	public int annaSatunnaisluku(int jakaja){
		Date d = new Date();
		Random r = new Random(d.getTime());
		int jako = r.nextInt()%jakaja;
		// Muutetaan luku positiiviseksi
		if (jako < 0) jako = jako - 2*jako;
		return jako;	
	} 
	
}