import java.io.*;
import java.util.*; 

public class OhjelmaLT {    

   final static int OSAKILPAILU_LKM = 10;    

   public static void main(String [] argv ) throws Exception {	//IOException {      
     BufferedReader stdin =
     new BufferedReader( new InputStreamReader ( System.in ));

     int valinta;       // Vaihtoehdon valintamuuttuja
     ListaLT  Jupit  = new ListaLT(); // Luodaan tyhj‰ lista.      
     
     // Kerrotaan k‰ytt‰j‰lle mit‰ toimintoja on mahdollista suorittaa.
     do
     {
           System.out.println( "\nMit‰ tehd‰‰n seuraavaksi?\n" );
           System.out.println( "1 = Veneen lis‰ys listaan");
           System.out.println( "2 = Veneen poisto listasta");
           System.out.println( "3 = Venetietojen muuttaminen");
           System.out.println( "4 = Veneen haku numeron perusteella");
           System.out.println( "5 = Venetietojen tulostus");
           System.out.println( "6 = Tiedostoon kirjoittaminen " );
           System.out.println( "7 = Tiedostosta lukeminen " );
           System.out.println( "8 = Osakilpailujen valinta");
           System.out.print( "\n0 = Lopetus\n\nValintasi: ");

           valinta = lue_int();
           System.out.println();            // Tutkitaan mik‰ toiminta valittiin.
           switch ( valinta )
           {
             case 1:  lisaaVeneita(Jupit);     break;
             case 2:  poistaVeneita(Jupit);    break;
             case 3:  muutaVeneita(Jupit);     break;
             case 4:  onkoVene(Jupit);        break;
             case 5:  Jupit.tulosta_lista();  break;
             case 6:  listaTiedostoon(Jupit); break;
             case 7:  tiedostoListaan(Jupit); break;
             case 8: valitseOsakilpailut(Jupit); break;
             case 0:  System.out.println("HEI! HEI!");
             break;
           }// switch
     } while ( valinta != 0 );
   }// main    
   
   /**************************************************************************
    Metodi lisaaVeneita lis‰‰ venetietoja Venesolmulistaan. Avaintiedon (nyt
    venenumeron) lis‰‰minen vaatii tarkistuksen, onko muutos mahdollinen.
   /**************************************************************************/
   public static void lisaaVeneita(ListaLT omatJupit) throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));         
        int valinta;       // vaihtoehdon valintamuuttuja
        int arvo = 0;      // muuttuja kysytt‰v‰lle tilinumerolle
        double tuohi;      // muuttuja kysytt‰v‰lle saldolle
        String kommentti;
        int venenro = 0;
        int sija = 0;
        String nimi;       // muuttuja kysytt‰v‰lle nimelle
        boolean loytyi;    // totuusarvomuuttuja tilin lˆytymiselle

        // Lis‰t‰‰n veneit‰ do ... while silmukassa
        do {
          System.out.print
           ("\nAnna lis‰tt‰v‰n veneen numero (0 = p‰‰valikkoon): ");
          arvo = lue_int();

          // katsotaan, onko tili jo olemassa
          loytyi = omatJupit.onko_arvoa( arvo );

          // jos ei ole ennest‰‰n kyseist‰ tili‰ ja tilinumero on k‰yp‰
          if (!loytyi && arvo > 0)
          {
            // luodaan uusi solmu, johon tilinumero laitetaan
            SolmuLT uusi = new SolmuLT(arvo);

            // kysyt‰‰n nimi ja pistet‰‰n se uusi-solmuun
            System.out.print("\nNimess‰ ei saa olla # merkki‰! ");
            System.out.print("\nAnna lis‰tt‰v‰n veneen nimi: ");
            nimi = stdin.readLine();
            uusi.aseta_nimi(nimi);

            System.out.println("\nAnna osakilpailutulokset: ");
      		for(int i = 0; i < OSAKILPAILU_LKM; i++){
      			System.out.print("Veneen sijoitus " + (i+1) + ". kilpailussa: ");
      			sija = lue_int();
      			uusi.aseta_osakilpailu_sijoitus(i, sija);
      		}

			System.out.print("\nAnna kommentti: ");
 			kommentti = stdin.readLine();
			uusi.aseta_kommentti(kommentti);

            // lis‰t‰‰n uusi solmu listaan
            omatJupit.lisaa_listaan(uusi);
            omatJupit.tulosta_lista(); // tulostetaan lista
          }
          // jos tilinumero oli ep‰asiallinen tai tili oli jo olemassa,
          // tulostetaan ilmoitukset
          else if (arvo < 0)
             {
               System.out.println("Venenumeron pit‰‰ olla positiivinen luku!");
             }
             else if (arvo != 0)
             {
               System.out.println("Venenumero on jo listassa!");
             }
        } while (arvo != 0);    } // lisaaVeneita
        
   /***************************************************************************
    Metodi poistaVeneita poistaa annetun numeroisen veneen, mik‰li se on olemassa.
   /***************************************************************************/
   public static void poistaVeneita(ListaLT omatJupit) throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));

        int arvo = 0;      // kysytt‰v‰n tilinumeron muuttuja         
        // HUOM! Poistotoimintokin voidaan laittaa silmukkaan,
        // jos siihen on tarvetta.

        System.out.print("\nAnna poistettavan veneen numero: ");
        arvo = lue_int();         // Jos tilinumero on asiallinen,
        // yritet‰‰n poistoa.
        if (arvo > 0)
        {
          omatJupit.poista_listasta( arvo );
          omatJupit.tulosta_lista();
        }
        else
        {
          System.out.println("\nVenenumeron pit‰‰ olla positiivinen luku!");
        }    } // poistaTili    
        
    /**************************************************************************
    Metodi muutaVeneita muuttaa venetietoja VenesolmuListaan. Avaintiedon (nyt
    venenumeron) muuttaminen vaatii tarkistuksen, onko muutos mahdollinen.
    Jos se on, solmu t‰ytyy poistaa listasta ja lis‰t‰ sitten uutena solmuna
    listaan takaisin.
   /**************************************************************************/
   public static void muutaVeneita(ListaLT omatJupit) throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));         

        int valinta;       // vaihtoehdon valintamuuttuja
        int uusiarvo, arvo = 0; // muuttujia kysytt‰v‰lle tilinumerolle
	  int numero;
	  int kisa;
	  int sija;
	  String kommentti;
        String nimi;   // apumuuttuja nimi-datalle
        boolean loytyi;      // totuusarvomuuttuja tilin lˆytymiselle
        SolmuLT osoitin;  // osoitin lˆydetylle solmulle

        // HUOM! TƒMƒ ON VAIN YKSI MAHDOLLINEN RATKAISU!
        do {
          System.out.print
            ("\nAnna muutettavan veneen numero(0 = p‰‰valikkoon): ");
          arvo = lue_int();
          // haetaan muutettava solmu ja liitet‰‰n siihen osoitin
          osoitin = omatJupit.hae_solmu( arvo );
          // Jos lˆytyi,
          if ( osoitin != null ) {
             // tulostetaan sen sis‰ltˆ n‰ytˆlle.
             System.out.println("\nMuutettavan kilpailuveneen sis‰ltˆ: ");
             System.out.println("\n---------------------------------------- ");
             osoitin.tulosta();
             System.out.println("\n---------------------------------------- ");              
             // Vaihtoehdot muuttamiselle:
             System.out.println( "  Mit‰ haluat muuttaa?\n" );
             System.out.println( "  1 = Venenumeroa");
             System.out.println( "  2 = Veneen nime‰");
             System.out.println( "  3 = Osakilpailutuloksia");
             System.out.println( "  4 = Kommenttia");
             // Huom! Vaihtoehto 5 ei ole v‰ltt‰m‰tt‰ tarpeellinen!
             System.out.print( "  0 = Ei mit‰‰n\n\nValintasi: ");              
             // Kysyt‰‰n k‰ytt‰j‰lt‰ h‰nen valintansa.
             valinta = lue_int();
             System.out.println();

             // Haaraudutaan valinnan mukaiseen toimintoon.
             switch(valinta)
             {
               // tilinumeron muuttaminen
               case 1:
                 System.out.print("\nAnna uusi venenumero: ");
                 uusiarvo = lue_int();
                 // onko uudella numerolla jo tili olemassa
                 loytyi = omatJupit.onko_arvoa( uusiarvo );                  
                 // jos on, ei voida tehd‰ tilin numeron muuttamista,
                 if (loytyi)
                 {
                    System.out.println("\nNumero on jo k‰ytˆss‰!");
                    System.out.println("\nVenenumeroa ei voi muuttaa!");
                 }
                 // ellei, niin tehd‰‰n numeron muuttaminen
                 else if (arvo > 0)
                      {
                        System.out.println("\nMuutos onnistuu!");
                         // solmu pois listasta,
                        omatJupit.poista_listasta( arvo );
                         // sen (osoitin osoittaa siihen)
                         // tilinumero muutetaan
                        osoitin.aseta_venenumero(uusiarvo);
                         // ja laitetaan takaisin listaan
                        omatJupit.lisaa_listaan(osoitin);
                      }
                 omatJupit.tulosta_lista(); // tulostetaan lista
               break;                
   		   //nimen muuttaminen
               case 2:
                 System.out.print("\nTekstiss‰ ei saa olla # merkki‰! ");
                 System.out.print("\nAnna uusi veneen nimi: ");
                 nimi = stdin.readLine();
                 osoitin.aseta_nimi(nimi);
                 omatJupit.tulosta_lista(); // tulostetaan lista
               break;

               // tulosten muuttaminen
               case 3:
                 System.out.print("\nMink‰ osakilpailun tulosta haluat muuttaa?");
		     System.out.print("\nValitse 1 - " + OSAKILPAILU_LKM + ": ");
		     kisa = lue_int();
		     System.out.print("\nAnna veneen sijoitus " + kisa  + ". osakilpailussa: ");
		     sija = lue_int();
                 osoitin.aseta_osakilpailu_sijoitus(kisa-1,sija);
                 omatJupit.tulosta_lista(); // tulostetaan lista
               break;

               // saldon muuttaminen
               case 4:
                 System.out.print("\nAnna uusi kommentti: ");
		     kommentti = stdin.readLine();
                 osoitin.aseta_kommentti(kommentti);
                 omatJupit.tulosta_lista(); // tulostetaan lista
               break;                
               default:
                 omatJupit.tulosta_lista(); // tulostetaan lista
             } // switch
          }           
          // ilmoitukset ep‰asiallisesta tilinumerosta ja
          // tilin puuttumisesta ( ei ole olemassa )
          else if (arvo < 0)
             {
               System.out.println("Tilinumeron pit‰‰ olla positiivinen luku!");
             }
             else if (arvo > 0)
             {
               System.out.println("Tili ei ole listassa!");
             }
        } while (arvo != 0);
   } // muutaVeneita
   
   
   /**************************************************************************
    Metodi onkoVene hakee annetun numeroisen veneen, mik‰li se on olemassa.
   /**************************************************************************/
   public static void onkoVene(ListaLT omatJupit) throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));         
        int arvo = 0;      // etsitt‰v‰n tilinumeron muuttuja
        boolean loytyi;   // totuusarvomuuttuja tilin lˆytymiselle

        System.out.print("\nAnna etsitt‰v‰n veneen numero: ");
        arvo = lue_int();         
        // Katsotaan, onko tilinumero k‰ytˆss‰,
        // ja tulostetaan asianmukaiset ilmoitukset
        loytyi = omatJupit.onko_arvoa( arvo );

        if (loytyi)
          System.out.println("\nVene lˆytyi!");
        else
          System.out.println("\nVene ei ole olemassa!");         
          omatJupit.tulosta_lista(); // tulostetaan lista
   } // onkoTili

  /**************************************************************************
    Metodi listaTiedostoon lis‰‰ listan tiedot tiedostoon.
   **************************************************************************/
   public static void listaTiedostoon(ListaLT omatJupit)
   throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));         
        // TIEDOSTON NIMI VOIDAAN KYSELLƒ KƒYTTƒJƒLTƒ.
        // TEE SIITƒ KOKEILUJA!
        String nimi /*= new String("a:\\piilo.dat")*/;
        System.out.print("\nAnna tiedoston nimi ( esim. a:\\piilo.dat ) ");
        nimi = stdin.readLine();

        // Listan datat talletetaan tiedostoon.
        omatJupit.kirjoita_tiedostoon(nimi);

        omatJupit.tulosta_lista();  // tulostetaan lista
   } // listaTiedostoon

  /**************************************************************************
    Metodi tiedostoListaan hakee tilitiedot tiedostosta ja lis‰‰ ne listaan.
   **************************************************************************/
   public static void tiedostoListaan(ListaLT omatJupit)
   throws IOException
   {
        BufferedReader stdin =
        new BufferedReader ( new InputStreamReader ( System.in ));
        // int valinta;    // vaihtoehdon valintamuuttuja         
        // TIEDOSTON NIMI VOIDAAN KYSELLƒ KƒYTTƒJƒLTƒ.
        // TEE SIITƒ KOKEILUJA!
        String nimi /*= new String("a:\\markat.dat")*/;
        System.out.print("\nAnna tiedoston nimi ( esim. a:\\piilo.dat ) ");
        nimi = stdin.readLine();         // Datat tiedostosta lis‰t‰‰n listaan.
        omatJupit.lue_tiedostosta(nimi);

        omatJupit.tulosta_lista(); // tulostetaan lista
   } // tiedostoListaan

    /******************************************************************
        M‰‰ritell‰‰n p‰‰ohjelmassa k‰ytetty metodi nimelt‰‰n
        "lue_int". Se suorittaa yhden kokonaisluvun lukemisen
        k‰ytt‰m‰ll‰ poikkeusk‰sittely‰ virheellisille syˆtˆille.
    *****************************************************************/
     public static int lue_int() {

        BufferedReader stdin = new BufferedReader
         ( new InputStreamReader ( System.in));

       int luku=0;
       boolean virhe;

       do {
         virhe=false;
         try {
           luku = Integer.valueOf(stdin.readLine()).intValue();
           System.out.println();
         } // try
         catch( Exception e)
         {
           virhe = true;
           System.err.println( "\n *** " +e +"\n");
           System.out.print (" Yrit‰ antaa kokonaisluku uudelleen > ");
         } // catch

       }while (virhe); // do .. while

       return luku;
     }// lue_int

     /******************************************************************
        M‰‰ritell‰‰n p‰‰ohjelmassa k‰ytetty metodi nimelt‰‰n
        "lue_double". Se suorittaa yhden reaaliluvun lukemisen
        k‰ytt‰m‰ll‰ poikkeusk‰sittely‰ virheellisille syˆtˆille.
    *****************************************************************/
     public static double lue_double() {

        BufferedReader stdin = new BufferedReader
         ( new InputStreamReader ( System.in));

       double luku=0.0;
       boolean virhe;

       do {
         virhe=false;
         try {
           luku = Double.valueOf(stdin.readLine()).doubleValue();
           System.out.println();
         } // try
         catch( Exception e)
         {
           virhe = true;
           System.err.println( "\n *** " +e +"\n");
           System.out.print (" Yrit‰ antaa reaaliluku uudelleen > ");
         } // catch
       }while (virhe); // do .. while

       return luku;
     }// lue_double


     /******************************************************************
      Metodi valitseOsakilpailut kysyy k‰ytt‰j‰lt‰, montako osakilpailua
      huomioidaan tuloksia laskettaessa. Kilpaveneet asetetaan sen j‰lkeen
      j‰rjestykseen.
    *****************************************************************/
	public static void valitseOsakilpailut(ListaLT omatJupit) throws Exception
	{
		int lkm = 0;
		while(lkm <= 0 || lkm > 10){
			System.out.println("Montako osakilpailua otetaan huomioon (max 10)?");
			lkm = lue_int();	
		}
		omatJupit.jarjesta_hyvaksytyt(lkm);
	}

}
