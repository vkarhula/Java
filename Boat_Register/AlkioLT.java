

public class AlkioLT {    

   private int venenro;
   private int sijoitus;
   private String nimi;
   private int osakilpailut[];
   private String kommentti;
   private boolean hylatty;

   /**************************************************************************
    Konstruktorimetodi alustaa uuden solmun antamatta data-arvoja.
   **************************************************************************/
   public AlkioLT()
   {
   } // konstruktori

   /**************************************************************************
    Toisenlainen konstruktorimetodi alustaa uuden solmun antamalla parametrina
    olevan solmun data-arvot uudelle solmulle.
   **************************************************************************/
   public AlkioLT (SolmuLT vanha)
   {
   	  venenro = vanha.anna_venenumero();
   	  sijoitus = vanha.anna_sijoitus();
   	  nimi = vanha.anna_nimi();
      osakilpailut = new int[OhjelmaLT.OSAKILPAILU_LKM];
   	  for (int i = 0; i < OhjelmaLT.OSAKILPAILU_LKM; i++){
   	  	osakilpailut[i] = vanha.anna_osakilpailu_sijoitus(i);
   	  }
   	  kommentti = vanha.anna_kommentti();
      hylatty = vanha.anna_hylkays_tulos();
   } // konstruktori    
   
   /**************************************************************************
    Metodi tulosta tulostaa AlkioLT-olion oleellisen sisällön.
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
    Metodi anna_sijoitus palauttaa solmun sijoitus-kentän sisällön.
   **************************************************************************/
   public int anna_sijoitus()
   {
      return (sijoitus);
   }

   /**************************************************************************
    Metodi anna_hylkays_tulos() palauttaa hylatty-totuusmuuttujan arvon.
   **************************************************************************/
	public boolean anna_hylkays_tulos()
	{
		return hylatty;	
	}

   /**************************************************************************
    Metodi anna_venenumero palauttaa venenumero-kentän sisällön.
   **************************************************************************/
   public int anna_venenumero()
   {
      return ( venenro );
   } 
   
   }// class AlkioLT 