import java.io.*;
import java.util.*; 

public class ListaLT {    

   private SolmuLT alku; // Linkitetyn listan alku    
   
   /*************************************************************************
    Konstruktorimetodi antaa alkuarvon null listan alkuosoitteelle.
   /*************************************************************************/
   
   public ListaLT()
   {
      alku = null;  // lista on aluksi tyhjä
   }// ListaLT    
   
   /************************************************************************
    Metodi lisaa_listaan lisää solmun linkitettyyn listaan niin, että datat
    tulevat järjestykseen (esim. pienimmästä suurimpaan).
   /************************************************************************/
   public void lisaa_listaan(SolmuLT uusin) throws IOException
   {
      BufferedReader stdin =
      new BufferedReader ( new InputStreamReader ( System.in ));

      String toppi;        // Määritellään osoittimet ptr ja previous.
      SolmuLT ptr = alku;
      SolmuLT previous;  /* viittaa siihen listan solmuun, jonka
                                 perään uusi solmu laitetaan */       
      // Jos lista on tyhjä,
      if ( alku == null )
      {
         // uusin solmu astetaan listan ensimmäiseksi.
         alku = uusin;
      }
      // ellei lista ollut tyhjä,
      else  // Nro 1
      {
         // haetaan listasta sijoitettavalle arvolle oma paikka
         // (kun alkiot ovat  suuruusjärjestyksessä).
         // Jos paikka on ennen listan alkua,
         if ( uusin.anna_venenumero() </*KYTKIN_1*/ ptr.anna_venenumero() )
         {
           // lisätään uusin ennen alkua ja
           // päivitetään listan alku.
           uusin.lisaa( alku);
           alku = uusin;
         }
         // Muuten haetaan paikkaa silmukalla.
         else  // Nro 2
         {
           do
           {
              previous = ptr;       // previous tulee ptr:n perässä
              ptr = ptr.seuraava(); // ptr etenee
           }  while ( ptr != null &&
               ptr.anna_venenumero() <=/*KYTKIN_2*/uusin.anna_venenumero() );
              // Jatketaan niin kauan kuin lista ei ole loppunut
              // ja listan solmujen arvot määräävät (ovat pienempiä tai
              // yhtäsuuria kuin sijoitettava pienimmästä suurimpaan
              // järjestyksessä).            
	     // Mikäli tilinumero ei ole käytössä,
           // solmu uusin tulee previous-solmun jälkeen
           // ja ennen ptr-solmua (jos se on olemassa).
           
           if ( previous.anna_venenumero() != uusin.anna_venenumero())
           {
              previous.lisaa ( uusin );
              if (ptr != null) uusin.lisaa(ptr);
           }
           else  // Nro 3
           {
             System.out.println("\nVenenumero oli jo käytössä!");
             System.out.print("Venetietuetta ei perusteta! Paina ENTER> ");
             toppi = stdin.readLine();
           }    // else Nro 3
         } // else Nro 2
      }//else Nro 1
    }//lisaa_listaan    
    
    /****************************************************************************
    Metodi poista_listasta poistaa käyttäjän antaman arvon (parametrina)
    solmulistasta, mikäli se löytyy.
   /****************************************************************************/
   public void poista_listasta(int poistettava)
   {
      SolmuLT ptr = alku; // ptr asetetaan listan alkuun
      SolmuLT previous = null; // previous kulkee ptr:n perässä

      // Jos lista ei ole tyhjä,
      if ( ptr != null )
      {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             // Jos poistettava löytyy, on kaksi tapausta:
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
                // 2) poistettava (ptr) on listan välisolmu tai
                //    viimeinen solmu.
                else
                {
                   previous.lisaa( ptr.seuraava() );
                   ptr.aseta_nextnull(); // POISTETUN LINKKI NOLLATAAN
                   return;
                } // else
             } // if while-silmukan sisällä

             // Molemmat osoittimet etenevät (peräkkäin).
             // Osoitin previous saa ptr:n vanhan arvon
             // ts. seuraa ptr-osoitinta.
             previous = ptr;
             ptr = ptr.seuraava();
         } // while

         System.out.println("\nPoistettavaa venettä ei löydy listasta");
      } // if       // Tapaus: lista on tyhjä.
      else
      {
          System.out.println ("Veneiden lista on tyhjä!");
      } // else     
      }// poista_listasta   
      
   /****************************************************************************
    Metodi tulosta_lista tulostaa listan sisällön.
   /****************************************************************************/
	public void tulosta_lista() throws IOException
    {
      BufferedReader stdin =
      new BufferedReader ( new InputStreamReader ( System.in ));

      SolmuLT ptr = alku; // aletaan listan alusta
      final int riveja = 16; // näytölle mahtuvien rivien määrä
      int k=1; // Laskuri pysäytykselle
      String toppi;       // Käydään koko lista läpi ja tulostetaan jokaisen
      // solmun sisältö.
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
    Metodi tuhoa_solmut on listan täystuho (terminaattori). Se tuhoaa listan
    solmu kerrallaan alkaen listan alusta.
   ***************************************************************************/
   public void tuhoa_solmut() throws IOException
   {
      // Tuhoamien tapahtuu laittamalla lista osoittamaan
      // listan seuraavaa alkiota, jolloin alkuperäinen
      // listan solmu jää vaille "omistajaa" ja Java systeemi
      // siivoaa sen pois sitten kun se sille sopii.       while ( alku != null)
      {
        alku = alku.seuraava();
      }

      System.out.println("\nVenelista on tyhjä");
      // tulosta_lista();
   }//tuhoa_solmut   
   
   /****************************************************************************
    Metodi onko_arvoa etsii alkiota solmulistasta. Se palauttaa totuusarvon
    löytymisestä.
  /****************************************************************************/
   public boolean onko_arvoa(int etsittava)
   {
      SolmuLT ptr = alku; // ptr viittaa listan alkuun

      // Jos lista ei ole tyhjä,
      if ( ptr != null )
      {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             if ( ptr.anna_venenumero() == etsittava)	
             {
                return true;
             } // if while-silmukan sisällä
             ptr = ptr.seuraava();
         } // while          
         // System.out.println("\nEtsittävää tietoa ei löydy listasta");
         return false;
      } // if
      // Tapaus: lista on tyhjä.
      else
      {
          System.out.println ("Venelista on tyhjä!");
          return false;
      }
   }// onko_arvoa  
   
   /****************************************************************************
    Metodi hae_solmu etsii solmua solmulistasta. Se palauttaa löydetyn solmun
    (osoittimen löydettyyn solmuuun).
    Metodit onko_arvoa ja hae_solmu ovat melkein toistensa kopioita!
    Niiden pohjalta on tehty metodi poista_listasta (huomaa samankaltaisuus).
 /****************************************************************************/
   public SolmuLT hae_solmu(int etsittava)
   {
      SolmuLT ptr = alku; // ptr viittaa listan alkuun       // Jos lista ei ole tyhjä,
      if ( ptr != null )       {
         // haetaan silmukan avulla.
         while ( ptr!= null )
         {
             //if ( ptr.anna_tilinumero() == etsittava)
             if ( ptr.anna_venenumero() == etsittava)	//
             {
                // System.out.println("\nEtsittävä tieto löytyi listasta");
                return ptr;
             } // if while-silmukan sisällä
             ptr = ptr.seuraava();
         } // while          // System.out.println("\nEtsittävää tietoa ei löydy listasta");
         return null;
      } // if       // Tapaus: lista on tyhjä.
      else
      {
          System.out.println ("Tililista on tyhjä!");
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
       // true aiheuttaa sen, että kirjoitetaan (lisätään)
       // tiedostossa olevien datojen perään !!

     SolmuLT ptr = alku; // aloitetaan listan alusta
     String erotin = "#"; // datakenttien erotinmerkki tiedoston rivillä      
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

        out.newLine(); // vaihdetaan riviä tiedostossa
        ptr = ptr.seuraava(); // listassa seuraavaan solmuun
     }
     out.close();
   } // kirjoita_tiedostoon

   /****************************************************************************
    Metodi lue_tiedostosta lukee tiedostosta tietoja ja lisää ne listaan.
   ****************************************************************************/
   public void lue_tiedostosta(String tnimi) throws IOException
   {
     int numero;   // apumuuttuja tilinumero-datalle
     String temp;  // apumuuttuja nimi-datalla

     StringTokenizer rivi; // tähän tulee tiedoston rivi
     String erotin = "#"; // datakenttien erotinmerkki tiedoston rivillä

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

       // Lisätään uusin solmu listaan.
       lisaa_listaan(uusin);
     } // while

     in.close();      // Tiedosto suljetaan.
   } // lue_tiedostosta

  /****************************************************************************
     Metodi jarjesta_hyvaksytyt käy läpi kaikki linkitetyn listan SolmuLT-oliot
     ja laskee summan halutusta määrästä kilpailusijoituksia. Metodi kutsuu 
     myös listaTaulukkoon-metodia, joka muuntaa SolmuLT-oliot AlkioLT-olioiksi 
     ja sijoittaa ne taulukkoon, jossa varsinainen järjestäminen tapahtuu.
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
     taulukkoon AlkioLT-olioina ja järjestää oliot suuruusjärjestykseen
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
      
      // Lajitellaan taulukko pienimmästä suurimpaan (ps)
      lajittele_ps(taulu,0,lkm-1);

		ptr = alku;
		int hylatyt = 0;
		System.out.println("\nHyväksytyt veneet paremmuusjärjestyksessä");

		for ( i = 0; i < lkm; i++){
			//Käydään läpi ne taulukon alkiot, 
			//joihin on tallennettu kilpaveneen tiedot
			if ( taulu[i].anna_sijoitus() != 0 ){
				if(taulu[i].anna_hylkays_tulos() == false){
					taulu[i].tulosta();	
				} else {	//hylatty
					hylatyt++;
				}
			}
	    }
	    System.out.println("\nYhteensä " + hylatyt + " kilpavenettä hylätty.");

   } // listaTaulukkoon
  
/****************************************************************************
 Metodin lajittele_ps quick_sort algoritmi on käytännössä hyvä, pahimmillaan
 neliöllistä kompleksisuusluokkaa oleva lajittelualgoritmi. Se ei palauta
 mitään arvoa.
 Parametreina ovat taulukko ja lajiteltavien alkioiden ensimmäinen ja viimeinen
 indeksi. Metodi lajittelee taulukon alkiot pienimmästä suurimpaan.
*****************************************************************************/
     public static void lajittele_ps ( AlkioLT [] list, int left, int right )
     {
        double  pivot;
        int i, j;
        AlkioLT temp /* = new AlkioLT() */;  // apumuuttuja alkioiden vaihtoon

        if ( left < right ) // rekursiivisessa ohjelmassa on usein
        {                   // if-käsky (eikä silmukkakäsky)

          pivot = list[left].anna_sijoitus(); // hajottaja		//
          i = left;           // i on alustapäin indeksi
          j = right + 1;      // j on lopustapäin indeksi

          // pikalajittelun (quick_sort)
          // pääsilmukka ( do .. while(i < j) )
          do
          {
             // etsitään alustapäin do .. while-silmukalla
             do
             {
                i++;
             } while( list[i].anna_sijoitus()</* KYTKIN_1 */ pivot && i<right );

             // etsitään lopustapäin do .. while-silmukalla
             do
             {
               j--;
             } while( list[j].anna_sijoitus()>/* KYTKIN_2 */ pivot && j>left );

             // jos indeksit i ja j eivät ole kohdanneet toisiaan,
             if ( i < j )
             {
               // vaihdetaan list[i] ja list[j].
              temp = list[i];
              list[i] = list[j];
              list[j] = temp;
               //lisaaLaskuria();
             } // if (i < j)

          } while ( i < j ); // Jatketaan pääsilmukassa niin kauan
                             // kuin indeksit eivät kohtaa toisiaan.

         // Kun indeksit i ja j ovat kohdanneet toisensa,
         // vaihdetaan hajottaja list[left] ja list[j].
         // Hajoittaja tulee oikealle paikalleen ts. paikkaan j.
         temp = list[left];
         list[left] = list[j];
         list[j] = temp;
         // lisaaLaskuria();

         // Sen jälkeen tulevat rekursiiviset kutsut pikalajittelulle
         lajittele_ps( list, left, j-1 ); // paikan j etupuolelle,
         lajittele_ps( list, j+1, right ); // paikan j jälkipuolelle.

        }  // if (left < right)

        // System.out.println("\nVaihtoja oli "+laskuri +" kpl");
     } // metodi lajittele_ps

 /***************************************************************************
 Metodin lajittele_sp quick_sort algoritmi on käytännössä hyvä, pahimmillaan
 neliöllistä kompleksisuusluokkaa oleva lajittelualgoritmi. Se ei palauta
 mitään arvoa.
 Parametreina ovat taulukko ja lajiteltavien alkioiden ensimmäinen ja viimeinen
 indeksi. Metodi lajittelee taulukon alkiot suurimmasta pienimpään.
 ***************************************************************************/
 public static void lajittele_sp ( int [] list, int left, int right )
 {
        int  pivot;
        int i, j;
        int temp;  // apumuuttuja alkioiden vaihtoon

        if ( left < right ) // rekursiivisessa ohjelmassa on usein
        {                   // if-käsky (eikä silmukkakäsky)
          pivot = list[left]; // hajottaja
          i = left;           // i on alustapäin indeksi
          j = right + 1;      // j on lopustapäin indeksi

          // pikalajittelun (quick_sort)
          // pääsilmukka ( do .. while(i < j) )
          do
          {
             // etsitään alustapäin do .. while-silmukalla
             do
             {
                i++;
             } while ( list[i] >/* KYTKIN_1 */ pivot  &&  i<right );

             // etsitään lopustapäin do .. while-silmukalla
             do
             {
               j--;
             } while ( list[j] </* KYTKIN_2 */ pivot  &&  j>left );

             // jos indeksit i ja j eivät ole kohdanneet toisiaan,
             if ( i < j )
             {
               // vaihdetaan list[i] ja list[j].
               temp = list[i];
               list[i] = list[j];
               list[j] = temp;
               // lisaaLaskuria();
             } // if (i < j)

          } while ( i < j ); // Jatketaan pääsilmukassa niin kauan
                             // kuin indeksit eivät kohtaa toisiaan.


         // Kun indeksit i ja j ovat kohdanneet toisensa,
         // vaihdetaan hajottaja list[left] ja list[j].
         // Hajoittaja tulee oikealle paikalleen ts. paikkaan j.
         temp = list[left];
         list[left] = list[j];
         list[j] = temp;

         // Sen jälkeen tulevat rekursiiviset kutsut pikalajittelulle
         lajittele_sp( list, left, j-1 ); // paikan j etupuolelle,
         lajittele_sp( list, j+1, right ); // paikan j jälkipuolelle.

        }  // if (left < right)

        // System.out.println("\nVaihtoja oli "+laskuri +" kpl");
 } // metodi lajittele_sp
 
} // class ListaLT 