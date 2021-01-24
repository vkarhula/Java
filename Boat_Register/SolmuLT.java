import java.lang.*;
import java.util.*;

public class SolmuLT {
	
   private int venenro;			//Veneen kilpailunumero
   private int sijoitus;		//Luku, jonka perusteella veneet j‰rjestet‰‰n
   private String nimi;			//Veneen nimi
   private int[] osakilpailut;	//Osakilpailutulokset sijoitetaan taulukkoon
   private String kommentti;	//Kommenttikentt‰
   private boolean hylatty;		//Jos vene ei ole osallitunut k‰ytt‰j‰n 
   								//antamaan m‰‰r‰‰n osakilpailuja, merkit‰‰n
   								//vene hyl‰tyksi
   private SolmuLT next;		/* linkkikentt‰ viittaa toiseen SolmuLT-
                              	tyyppiseen olioon, jos next on null,
                              	ei ole solmua mihin viitata */    
   
   /**************************************************************************
    Konstruktorimetodi alustaa uuden solmun antamalla parametrin arvon
    venenumerolle ja alkuarvot nimelle (ENTER), osakilpailutuloksille ja kommentille.
   **************************************************************************/
   public SolmuLT (int uusi_nro)
   {
      venenro = uusi_nro;
      sijoitus = 0;
      nimi = new String("");
      osakilpailut = new int[OhjelmaLT.OSAKILPAILU_LKM];
      kommentti = new String("");
      hylatty = true;	//false;
      
      next = null;
   } // konstruktori    
   
   /**************************************************************************
    Toisenlainen konstruktorimetodi alustaa uuden solmun antamalla parametrina
    olevan solmun data-arvot uudelle solmulle.
   /**************************************************************************/
   public SolmuLT(SolmuLT vanha)
   {
      venenro = vanha.venenro;
      sijoitus = vanha.anna_sijoitus();
      nimi = new String(vanha.nimi);
      for (int i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++){
      	osakilpailut[i] = vanha.osakilpailut[i];
      }
      kommentti = new String(vanha.kommentti);
      hylatty = vanha.anna_hylkays_tulos();
      next = null;

   } // konstruktori    
   
   /**************************************************************************
    Metodi aseta_venenumero sijoittaa venenumeron (parametrista numero) solmun
    venenumero-kentt‰‰n.
   /**************************************************************************/
   public void aseta_venenumero(int numero)
   {
     venenro = numero;
   } 

   /**************************************************************************
    Metodi aseta_nimi sijoittaa nimen (parametrista teksti) solmun
    nimi-kentt‰‰n.
   **************************************************************************/
   public void aseta_nimi(String teksti)
   {
     nimi = teksti;
   } // aseta_nimi


   /**************************************************************************
    Metodi aseta_osakilpailu_sijoitus sijoittaa solmun osakilpailut[i]-kentt‰‰n 
    parametrin arvon.
   **************************************************************************/
   public void aseta_osakilpailu_sijoitus(int kisanro, int sija)
   {
      osakilpailut[kisanro] = sija;
   } 

   /**************************************************************************
    Metodi aseta_sijoitus sijoittaa solmun sijoitus-kentt‰‰n parametrin arvon.
   **************************************************************************/
   public void aseta_sijoitus(int sija)
   {
      sijoitus = sija;
   } 

   /**************************************************************************
    Metodi aseta_kommentti sijoittaa kommentin (parametrista teksti) solmun
    kommentti-kentt‰‰n.
   **************************************************************************/
   public void aseta_kommentti(String teksti)
   {
     kommentti = teksti;
   } // aseta_kommentti

   /**************************************************************************
    Metodi aseta_hylkay_tulos asettaa muuttujan hylatty arvon hylkays
    -parametrin mukaiseksi.    
   **************************************************************************/
	public void aseta_hylkays_tulos(boolean hylkays)
	{
		hylatty = hylkays;	
	}
	
   /**************************************************************************
    Metodi aseta_nextnull sijoittaa solmun next-kentt‰‰n arvon null.
   **************************************************************************/
   public void aseta_nextnull()
   {
      next = null;
   } // aseta_nextnull

   /*************************************************************************
    Metodi seuraava palauttaa viittauksen listan seuraavaan solmuun eli
    linkkikent‰n next arvon, jos ei ole seuraavaa solmua listassa palautetaan
    null.
   *************************************************************************/
   public SolmuLT seuraava()
   {
      return ( next );
   } // seuraava    
   
   /**************************************************************************
    Metodi lisaa liitt‰‰ parametrina annetun uusi-linkin osoittaman solmun
    (null-arvon, jos solmua ei ole) solmun per‰‰n.
   /**************************************************************************/
   public void lisaa (SolmuLT uusi)
   {
      next = uusi;
   } // lisaa    
   
   /**************************************************************************
    Metodi tulosta tulostaa solmun data-kent‰n sis‰llˆn.
   /**************************************************************************/
   public void tulosta()
   {
      System.out.print("\nVenenro: " + venenro + " " + nimi + " ");
      System.out.print("\nSijoitukset: ");
      for(int i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++){
      	System.out.print(osakilpailut[i] + " ");
      }
      System.out.println("\nKommentti: " + kommentti);

   } // tulosta

   /**************************************************************************
    Metodi anna_venenumero palauttaa solmun venenumero-kent‰n sis‰llˆn.
   **************************************************************************/
   public int anna_venenumero()
   {
      return ( venenro );
   } 
   
   /**************************************************************************
    Metodi anna_nimi palauttaa solmun nimi-kent‰n sis‰llˆn.
   **************************************************************************/
   public String anna_nimi()
   {
      return ( nimi );
   } // anna_nimi


   /**************************************************************************
    Metodi anna_osakilpailu_sijoitus palauttaa solmun osakilpailu[i]-kent‰n 
    sis‰llˆn.
   **************************************************************************/
   public int anna_osakilpailu_sijoitus(int kisanro)
   {
      return ( osakilpailut[kisanro] );
   } 


   /**************************************************************************
    Metodi anna_sijoitus palauttaa solmun sijoitus-kent‰n sis‰llˆn.
   **************************************************************************/
   public int anna_sijoitus()
   {
      return (sijoitus);
   }

   /**************************************************************************
    Metodi anna_kommentti palauttaa solmun kommentti-kent‰n sis‰llˆn.
   **************************************************************************/
   public String anna_kommentti()
   {
      return ( kommentti );
   }

   /**************************************************************************
    Metodi anna_hylkays_tulos() palauttaa hylatty-muuttujan arvon, 
    joka on laskettu laske_arvo_ja_hylkays()-metodissa.
   **************************************************************************/
	public boolean anna_hylkays_tulos()
	{
		return hylatty;	
	}

   /**************************************************************************
    Metodi laske_arvo_ja_hylkays() laskee halutun kilpailulukum‰‰r‰n
    kilpailun sijoitukset yhteen, sijoittaa ne sijoitus-muuttujaan sek‰
    sijoittaa myˆs totuusarvon hylatty-muuttujaan.
    Sijoitus-muuttujan arvoa k‰ytet‰‰n hyv‰ksi asetettaessa 
    kilpaveneit‰ paremmuusj‰rjestykseen ListaLT-luokan metodissa lajittele_ps().
   **************************************************************************/
   public void laske_arvo_ja_hylkays(int lkm)
   {
   		int osallistutut = 0;
   		int osa;
   		int summa = 0;
   		int j = 0;
   		int i = 0;
   		boolean asetettu = false;
   		Vector v = new Vector();
		
		for(i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++) {
			asetettu = false;

   			if((osa = osakilpailut[i]) != 0){
   				osallistutut++;
   				if(v.size() == 0){
   					v.add(new Integer(osa));
   					asetettu = true;	
   				}
   				if (asetettu == false){
   					for (j = 0; (j < (osallistutut - 1) && asetettu == false); j++){
   						if( ((Integer)v.get(j)).intValue() > osa){
   							v.add(j, new Integer(osa));
   							asetettu = true;	
   						}
   					}
   				}
   				if (asetettu == false){
   					v.add(new Integer(osa));	
   					asetettu = true;
   				}
   			}
      	}

   		// Sijoitetaan parhaiden tulosten summa muuttujaan sijoitus
   		// ( ei siis suoraan sijoitus vaan siihen verrannollinen arvo)
   		for (i = 0; i < lkm && i < v.size(); i++){
			summa += ((Integer)v.get(i)).intValue();
        }
   		sijoitus = summa;

      	if(osallistutut < lkm){
      		hylatty = true;	
      	} else {
      		hylatty = false;
      	}
      	
   }

}// class SolmuLT

 