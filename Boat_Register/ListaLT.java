import java.io.*;
import java.util.*; 

public class ListaLT {    

   private SolmuLT alku; // Linkitetyn listan alku    
   
   /*************************************************************************
    Konstruktorimetodi antaa alkuarvon null listan alkuosoitteelle.
   /*************************************************************************/
   
   public ListaLT()
   {
      alku = null;  // lista on aluksi tyhj�
   }// ListaLT    
   
   /************************************************************************
    Metodi lisaa_listaan lis�� solmun linkitettyyn listaan niin, ett� datat
    tulevat j�rjestykseen (esim. pienimm�st� suurimpaan).
   /************************************************************************/
   public void lisaa_listaan(SolmuLT uusin) throws IOException
   {
      BufferedReader stdin =
      new BufferedReader ( new InputStreamReader ( System.in ));

      String toppi;        // M��ritell��n osoittimet ptr ja previous.
      SolmuLT ptr = alku;
      SolmuLT previous;  /* viittaa siihen listan solmuun, jonka
                                 per��n uusi solmu laitetaan */       
      // Jos lista on tyhj�,
      if ( alku == null )
      {
         // uusin solmu astetaan listan ensimm�iseksi.
         alku = uusin;
      }
      // ellei lista ollut tyhj�,
      else  // Nro 1
      {
         // haetaan listasta sijoitettavalle arvolle oma paikka
         // (kun alkiot ovat  suuruusj�rjestyksess�).
         // Jos paikka on ennen listan alkua,
         if ( uusin.anna_venenumero() </*KYTKIN_1*/ ptr.anna_venenumero() )
         {
           // lis�t��n uusin ennen alkua ja
           // p�ivitet��n listan alku.
           uusin.lisaa( alku);
           alku = uusin;
         }
         // Muuten haetaan paikkaa silmukalla.
         else  // Nro 2
         {
           do
           {
              previous = ptr;       // previous tulee ptr:n per�ss�
              ptr = ptr.seuraava(); // ptr etenee
           }  while ( ptr != null &&
               ptr.anna_venenumero() <=/*KYTKIN_2*/uusin.anna_venenumero() );
              // Jatketaan niin kauan kuin lista ei ole loppunut
              // ja listan solmujen arvot m��r��v�t (ovat pienempi� tai
              // yht�suuria kuin sijoitettava pienimm�st� suurimpaan
              // j�rjestyksess�).            
	     // Mik�li tilinumero ei ole k�yt�ss�,
           // solmu uusin tulee previous-solmun j�lkeen
           // ja ennen ptr-solmua (jos se on olemassa).
           
           if ( previous.anna_venenumero() != uusin.anna_venenumero())
           {
              previous.lisaa ( uusin );
              if (ptr != null) uusin.lisaa(ptr);
           }
           else  // Nro 3
           {
             System.out.println("\nVenenumero oli jo k�yt�ss�!");
             System.out.print("Venetietuetta ei perusteta! Paina ENTER> ");
             toppi = stdin.readLine();
           }    // else Nro 3
         } // else Nro 2
      }//else Nro 1
    }//lisaa_listaan    
    
    /****************************************************************************
    Metodi poista_listasta poistaa k�ytt�j�n antaman arvon (parametrina)
    solmulistasta, mik�li se l�ytyy.
   /****************************************************************************/
   public void poista_listasta(int poistettava)
   {
      SolmuLT ptr = alku; // ptr asetetaan listan alkuun
      SolmuLT previous = null; // previous kulkee ptr:n per�ss�

      // Jos lista ei ole tyhj�,
      if ( ptr != null )
      {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             // Jos poistettava l�ytyy, on kaksi tapausta:
             if ( ptr.anna_venenumero() == poistettava)	//?
             {
                // 1) poistettava (ptr) on listan alussa,
                //    jolloin hoidetaan lista alku-osoitin,
                if (ptr == alku)
                {
                   alku = alku.seuraava();
                   ptr.aseta_nextnull(); // POISTETUN LINKKI NOLLATAAN
                   return;
                }
                // 2) poistettava (ptr) on listan v�lisolmu tai
                //    viimeinen solmu.
                else
                {
                   previous.lisaa( ptr.seuraava() );
                   ptr.aseta_nextnull(); // POISTETUN LINKKI NOLLATAAN
                   return;
                } // else
             } // if while-silmukan sis�ll�

             // Molemmat osoittimet etenev�t (per�kk�in).
             // Osoitin previous saa ptr:n vanhan arvon
             // ts. seuraa ptr-osoitinta.
             previous = ptr;
             ptr = ptr.seuraava();
         } // while

         System.out.println("\nPoistettavaa venett� ei l�ydy listasta");
      } // if       // Tapaus: lista on tyhj�.
      else
      {
          System.out.println ("Veneiden lista on tyhj�!");
      } // else     
      }// poista_listasta   
      
   /****************************************************************************
    Metodi tulosta_lista tulostaa listan sis�ll�n.
   /****************************************************************************/
	public void tulosta_lista() throws IOException
    {
      BufferedReader stdin =
      new BufferedReader ( new InputStreamReader ( System.in ));

      SolmuLT ptr = alku; // aletaan listan alusta
      final int riveja = 16; // n�yt�lle mahtuvien rivien m��r�
      int k=1; // Laskuri pys�ytykselle
      String toppi;       // K�yd��n koko lista l�pi ja tulostetaan jokaisen
      // solmun sis�lt�.
      System.out.println("\n***** VENEIDEN LISTA:");
      // Tulostussilmukka
      while ( ptr != null )
      {
         if (k % riveja != 0)
         {
           ptr.tulosta(); // solmun tulostaminen
         }
         else
         {
           System.out.println("\nPaina ENTER, niin jatketaan ");
           toppi = stdin.readLine();
           ptr.tulosta(); // solmun tulostaminen
         }
         k++;
         ptr = ptr.seuraava(); //seuraavaan solmuun
      }//while       
      //System.out.println(ptr); // Tulostetaan lopuksi null (sana)
   }// tulosta_lista

   /***************************************************************************
    Metodi tuhoa_solmut on listan t�ystuho (terminaattori). Se tuhoaa listan
    solmu kerrallaan alkaen listan alusta.
   ***************************************************************************/
   public void tuhoa_solmut() throws IOException
   {
      // Tuhoamien tapahtuu laittamalla lista osoittamaan
      // listan seuraavaa alkiota, jolloin alkuper�inen
      // listan solmu j�� vaille "omistajaa" ja Java systeemi
      // siivoaa sen pois sitten kun se sille sopii.       while ( alku != null)
      {
        alku = alku.seuraava();
      }

      System.out.println("\nVenelista on tyhj�");
      // tulosta_lista();
   }//tuhoa_solmut   
   
   /****************************************************************************
    Metodi onko_arvoa etsii alkiota solmulistasta. Se palauttaa totuusarvon
    l�ytymisest�.
  /****************************************************************************/
   public boolean onko_arvoa(int etsittava)
   {
      SolmuLT ptr = alku; // ptr viittaa listan alkuun

      // Jos lista ei ole tyhj�,
      if ( ptr != null )
      {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             if ( ptr.anna_venenumero() == etsittava)	
             {
                return true;
             } // if while-silmukan sis�ll�
             ptr = ptr.seuraava();
         } // while          
         // System.out.println("\nEtsitt�v�� tietoa ei l�ydy listasta");
         return false;
      } // if
      // Tapaus: lista on tyhj�.
      else
      {
          System.out.println ("Venelista on tyhj�!");
          return false;
      }
   }// onko_arvoa  
   
   /****************************************************************************
    Metodi hae_solmu etsii solmua solmulistasta. Se palauttaa l�ydetyn solmun
    (osoittimen l�ydettyyn solmuuun).
    Metodit onko_arvoa ja hae_solmu ovat melkein toistensa kopioita!
    Niiden pohjalta on tehty metodi poista_listasta (huomaa samankaltaisuus).
 /****************************************************************************/
   public SolmuLT hae_solmu(int etsittava)
   {
      SolmuLT ptr = alku; // ptr viittaa listan alkuun       // Jos lista ei ole tyhj�,
      if ( ptr != null )       {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             //if ( ptr.anna_tilinumero() == etsittava)
             if ( ptr.anna_venenumero() == etsittava)	//
             {
                // System.out.println("\nEtsitt�v� tieto l�ytyi listasta");
                return ptr;
             } // if while-silmukan sis�ll�
             ptr = ptr.seuraava();
         } // while          // System.out.println("\nEtsitt�v�� tietoa ei l�ydy listasta");
         return null;
      } // if       // Tapaus: lista on tyhj�.
      else
      {
          System.out.println ("Tililista on tyhj�!");
          return null;
      }
   }// hae_solmu    
   
   /****************************************************************************
    Metodi kirjoita_tiedostoon tallettaa listan alkiot tiedostoon.
   /****************************************************************************/
   public void kirjoita_tiedostoon(String tnimi) throws IOException
   {
     BufferedWriter out =
       new BufferedWriter( new FileWriter (tnimi/*, true*/ ) );
       // true aiheuttaa sen, ett� kirjoitetaan (lis�t��n)
       // tiedostossa olevien datojen per��n !!

     SolmuLT ptr = alku; // aloitetaan listan alusta
     String erotin = "#"; // datakenttien erotinmerkki tiedoston rivill�      
     String sijoitukset = "";
     while ( ptr != null )
     {
	   sijoitukset = "";
       for(int i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++){
      	  sijoitukset += Integer.toString( ptr.anna_osakilpailu_sijoitus(i) ) + erotin;
       }

        out.write( Integer.toString( ptr.anna_venenumero() ) + erotin 
	     + ptr.anna_nimi() + erotin
	     + sijoitukset + erotin + ptr.anna_kommentti());

        out.newLine(); // vaihdetaan rivi� tiedostossa
        ptr = ptr.seuraava(); // listassa seuraavaan solmuun
     }
     out.close();
   } // kirjoita_tiedostoon

   /****************************************************************************
    Metodi lue_tiedostosta lukee tiedostosta tietoja ja lis�� ne listaan.
   ****************************************************************************/
   public void lue_tiedostosta(String tnimi) throws IOException
   {
     int numero;   // apumuuttuja tilinumero-datalle
     String temp;  // apumuuttuja nimi-datalla

     StringTokenizer rivi; // t�h�n tulee tiedoston rivi
     String erotin = "#"; // datakenttien erotinmerkki tiedoston rivill�

     BufferedReader in; // luetaan tiedostosta in-puskuriin
     try
     {
        // onnistuuko avaus
        in = new BufferedReader( new FileReader(tnimi) );
     }
     catch ( IOException e )
     {
       // ellei onnistunut, tulostetaan ilmoitus
       System.out.println("\nVIRHE TIEDOSTON AVAAMISESSA!");
       return;
     }

     // suoritetaan tiedostorivin luku while-silmukan ehdossa
     while ( ( temp = in.readLine() ) != null )
     {
       // luettu rivi sijoitetaan rivi-olioon
       rivi = new StringTokenizer(temp, erotin);

       // nextToken-metodilla
       // otetaan esiin venenumero, luodaan uusin solmu
       // ja sijoitetaan venenumero siihen.
       numero = Integer.valueOf(rivi.nextToken()).intValue();
       SolmuLT uusin = new SolmuLT(numero);

       //sijoitus
       //numero = Integer.valueOf(rivi.nextToken()).intValue();
       //uusin.aseta_sijoitus(numero);
       
       // otetaan nimi esille ja sijoitetaan uusin solmuun
       temp = rivi.nextToken();
       uusin.aseta_nimi(temp);        
	 //osakilpailut
       for(int i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++){
	       numero = Integer.valueOf(rivi.nextToken()).intValue();
     		 uusin.aseta_osakilpailu_sijoitus(i, numero);
       }
	 // kommentti
	 temp = rivi.nextToken();
	 uusin.aseta_kommentti(temp);       

       // Lis�t��n uusin solmu listaan.
       lisaa_listaan(uusin);
     } // while

     in.close();      // Tiedosto suljetaan.
   } // lue_tiedostosta

  /****************************************************************************
     Metodi jarjesta_hyvaksytyt k�y l�pi kaikki linkitetyn listan SolmuLT-oliot
     ja laskee summan halutusta m��r�st� kilpailusijoituksia. Metodi kutsuu 
     my�s listaTaulukkoon-metodia, joka muuntaa SolmuLT-oliot AlkioLT-olioiksi 
     ja sijoittaa ne taulukkoon, jossa varsinainen j�rjest�minen tapahtuu.
  ****************************************************************************/
    public void jarjesta_hyvaksytyt(int lkm) throws Exception
    {
		SolmuLT ptr = alku;
		while(ptr != null){
			ptr.laske_arvo_ja_hylkays(lkm);	
			ptr = ptr.seuraava();
		}
		listaTaulukkoon();
	}

  /****************************************************************************
     Metodi listaTaulukkoon sijoittaa linkitetyn listan SolmuLT-oliot 
     taulukkoon AlkioLT-olioina ja j�rjest�� oliot suuruusj�rjestykseen
     AlkioLT-olion sijoitus-muuttujan perusteella.
  ****************************************************************************/
  
    public void listaTaulukkoon() throws IOException
    {
      BufferedReader stdin =
      new BufferedReader ( new InputStreamReader ( System.in ));

      int i=0,lkm=0, koko=50;
      AlkioLT [] taulu =  new AlkioLT[koko];
      SolmuLT ptr = alku; // aletaan listan alusta

      // Tulostussilmukka
      while ( ptr != null && i<koko-1)
      {
         taulu[i++] = new AlkioLT(ptr);
         lkm++;
         ptr = ptr.seuraava(); //seuraavaan solmuun
      }//while         
      
      // Lajitellaan taulukko pienimm�st� suurimpaan (ps)
      lajittele_ps(taulu,0,lkm-1);

		ptr = alku;
		int hylatyt = 0;
		System.out.println("\nHyv�ksytyt veneet paremmuusj�rjestyksess�");

		for ( i = 0; i < lkm; i++){
			//K�yd��n l�pi ne taulukon alkiot, 
			//joihin on tallennettu kilpaveneen tiedot
			if ( taulu[i].anna_sijoitus() != 0 ){
				if(taulu[i].anna_hylkays_tulos() == false){
					taulu[i].tulosta();	
				} else {	//hylatty
					hylatyt++;
				}
			}
	    }
	    System.out.println("\nYhteens� " + hylatyt + " kilpavenett� hyl�tty.");

   } // listaTaulukkoon
  
/****************************************************************************
 Metodin lajittele_ps quick_sort algoritmi on k�yt�nn�ss� hyv�, pahimmillaan
 neli�llist� kompleksisuusluokkaa oleva lajittelualgoritmi. Se ei palauta
 mit��n arvoa.
 Parametreina ovat taulukko ja lajiteltavien alkioiden ensimm�inen ja viimeinen
 indeksi. Metodi lajittelee taulukon alkiot pienimm�st� suurimpaan.
*****************************************************************************/
     public static void lajittele_ps ( AlkioLT [] list, int left, int right )
     {
        double  pivot;
        int i, j;
        AlkioLT temp /* = new AlkioLT() */;  // apumuuttuja alkioiden vaihtoon

        if ( left < right ) // rekursiivisessa ohjelmassa on usein
        {                   // if-k�sky (eik� silmukkak�sky)

          pivot = list[left].anna_sijoitus(); // hajottaja		//
          i = left;           // i on alustap�in indeksi
          j = right + 1;      // j on lopustap�in indeksi

          // pikalajittelun (quick_sort)
          // p��silmukka ( do .. while(i < j) )
          do
          {
             // etsit��n alustap�in do .. while-silmukalla
             do
             {
                i++;
             } while( list[i].anna_sijoitus()</* KYTKIN_1 */ pivot && i<right );

             // etsit��n lopustap�in do .. while-silmukalla
             do
             {
               j--;
             } while( list[j].anna_sijoitus()>/* KYTKIN_2 */ pivot && j>left );

             // jos indeksit i ja j eiv�t ole kohdanneet toisiaan,
             if ( i < j )
             {
               // vaihdetaan list[i] ja list[j].
              temp = list[i];
              list[i] = list[j];
              list[j] = temp;
               //lisaaLaskuria();
             } // if (i < j)

          } while ( i < j ); // Jatketaan p��silmukassa niin kauan
                             // kuin indeksit eiv�t kohtaa toisiaan.

         // Kun indeksit i ja j ovat kohdanneet toisensa,
         // vaihdetaan hajottaja list[left] ja list[j].
         // Hajoittaja tulee oikealle paikalleen ts. paikkaan j.
         temp = list[left];
         list[left] = list[j];
         list[j] = temp;
         // lisaaLaskuria();

         // Sen j�lkeen tulevat rekursiiviset kutsut pikalajittelulle
         lajittele_ps( list, left, j-1 ); // paikan j etupuolelle,
         lajittele_ps( list, j+1, right ); // paikan j j�lkipuolelle.

        }  // if (left < right)

        // System.out.println("\nVaihtoja oli "+laskuri +" kpl");
     } // metodi lajittele_ps

 /***************************************************************************
 Metodin lajittele_sp quick_sort algoritmi on k�yt�nn�ss� hyv�, pahimmillaan
 neli�llist� kompleksisuusluokkaa oleva lajittelualgoritmi. Se ei palauta
 mit��n arvoa.
 Parametreina ovat taulukko ja lajiteltavien alkioiden ensimm�inen ja viimeinen
 indeksi. Metodi lajittelee taulukon alkiot suurimmasta pienimp��n.
 ***************************************************************************/
 public static void lajittele_sp ( int [] list, int left, int right )
 {
        int  pivot;
        int i, j;
        int temp;  // apumuuttuja alkioiden vaihtoon

        if ( left < right ) // rekursiivisessa ohjelmassa on usein
        {                   // if-k�sky (eik� silmukkak�sky)
          pivot = list[left]; // hajottaja
          i = left;           // i on alustap�in indeksi
          j = right + 1;      // j on lopustap�in indeksi

          // pikalajittelun (quick_sort)
          // p��silmukka ( do .. while(i < j) )
          do
          {
             // etsit��n alustap�in do .. while-silmukalla
             do
             {
                i++;
             } while ( list[i] >/* KYTKIN_1 */ pivot  &&  i<right );

             // etsit��n lopustap�in do .. while-silmukalla
             do
             {
               j--;
             } while ( list[j] </* KYTKIN_2 */ pivot  &&  j>left );

             // jos indeksit i ja j eiv�t ole kohdanneet toisiaan,
             if ( i < j )
             {
               // vaihdetaan list[i] ja list[j].
               temp = list[i];
               list[i] = list[j];
               list[j] = temp;
               // lisaaLaskuria();
             } // if (i < j)

          } while ( i < j ); // Jatketaan p��silmukassa niin kauan
                             // kuin indeksit eiv�t kohtaa toisiaan.


         // Kun indeksit i ja j ovat kohdanneet toisensa,
         // vaihdetaan hajottaja list[left] ja list[j].
         // Hajoittaja tulee oikealle paikalleen ts. paikkaan j.
         temp = list[left];
         list[left] = list[j];
         list[j] = temp;

         // Sen j�lkeen tulevat rekursiiviset kutsut pikalajittelulle
         lajittele_sp( list, left, j-1 ); // paikan j etupuolelle,
         lajittele_sp( list, j+1, right ); // paikan j j�lkipuolelle.

        }  // if (left < right)

        // System.out.println("\nVaihtoja oli "+laskuri +" kpl");
 } // metodi lajittele_sp
 
} // class ListaLT 