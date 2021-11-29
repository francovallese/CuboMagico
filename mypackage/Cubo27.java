package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import perlinK.Geometry;
import perlinK.Material;
import perlinK.Matrix;

public class Cubo27 extends Geometry implements Controllabile
{
	Cubino [] cubetti = new Cubino[27];
	private int conta_Z = -1;
	private int conta_Y = -1;
	private int conta_X = -1;
	
	int step = 0;
	int step_movimento = 0;
	Timer timer = null;
	TimerTask task = null;
	int [] cubetti_da_muovere;
	Geometry sezione;
	double PI = Math.PI;
	volatile Cubino [] cubetti_mossi = new Cubino[9]; 
	public Forma forma;
	
	public long velocita = 20;
	public int FRONTE = 22;
	public int DESTRA;
	public int ALTO = 14;
	public int BASSO;
	public int RETRO;
	public int SINISTRA;
	int [] cubetti_centrali = {22,12,4,14,16,10};
	private boolean[] facce_visibili = {false,false,false,false,false,false};
	int[][] xyz_visibili = {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}};
	int FACCE_IN_VISTA;
	
	LinkedList<Accodabile> coda_generica = null;
	
	
	public int num_mosse = -1;          // il monitor "Controllore_campi_classi" sente la variazione allora scatta quando set...(0)
	public int num_movimenti = -1;          // il monitor "Controllore_campi_classi" sente la variazione allora scatta quando set...(0)
	public int size_coda_mosse = -1;    // il monitor "Controllore_campi_classi" sente la variazione allora scatta quando set...(0)
	
	Mossa mossa_in_movimento = null;
	Movimento movimento_in_movimento = null;	
	VerificatoreMosse vm = new VerificatoreMosse();
	private PropertyChangeSupport pcs = null;
	public Messaggio messaggio;
	double _90 = Math.PI/2;
	double _180 = Math.PI;
	double _30  = Math.PI/6.0;
	int incr_SU_GIU = 0;
	int incr_SX_DX  = 0;
	
	
	public Cubo27(Forma forma) 
	{
		super();
		name = "C:27";
		this.forma = forma;
		crea_27_cubetti();
		
		coda_generica = new LinkedList<Accodabile>();
		
		pcs = new PropertyChangeSupport(this);
		messaggio = new Messaggio();
		messaggio.set_messaggio("Puoi usare il mouse o la tastiera.");
		messaggio.set_da_controllare(this);
		messaggio.mostra_caps_lock(); 
		forma.mainApp.add(messaggio,BorderLayout.SOUTH);		
		azzera_contatori();
		
	}
	public void azzera_contatori() 
	{
		set_num_mosse(0);
		set_num_movimenti(0);
		set_size_coda_mosse(0); 		
	}
	public Cubo27(Retro forma) 
	{
		super();
		name = "C:27";		
		crea_27_cubetti();
	}
	
	
	private void crea_27_cubetti() 
	{	
		for(int i= 0;i < 27;i++)    // 0 27
		{
			Cubino c = new Cubino(i);
			//SCALA
			//c.matrix.scale(0.15, 0.15,0.15);
			cubetti[i] = c;
			int modz = i % 9;
			int mody = modz % 3;			
			if(modz == 0)
				conta_Z++;
			conta_Y = mody;
			conta_X = modz / 3;
			//System.out.println(""+i+ ": "+ conta_X+ " "+ conta_Y+ " "+ conta_Z);
			posiziona_cubetto(i);
			add(cubetti[i]);			
		}		
	}

	private void posiziona_cubetto( int i) 
	{			
		double spostamento = 14; //14;
		double tx = ((double)(conta_X-1))*spostamento;
		double ty = ((double)(conta_Y-1))*spostamento;
		double tz = ((double)(conta_Z-1))*spostamento;
		cubetti[i].matrix.translate(tx,ty,tz);			
	}


	
	public void esegui_mossa(Mossa mo) 	
	{
		//System.out.println("esegui_mossa " + mo.mossa + " step: "+ step + " canc="+mo.cancellazione);
		
		if (step < 2) 
    	{
			if(step == 0)
			{
				char una_mossa = mo.mossa;
				if(mo.cancellazione)
					una_mossa = vm.trova_contro_mossa(una_mossa);
				cubetti_da_muovere = trova_indici_cubetti_da_muovere(una_mossa);	
				crea_sezione();
				carica_vecchi_colori_sezione();
			}
            esegui_movimento(mo);
            step++;
        } 
    	else 
        {     		
    		termina_mossa(mo); 
    		mossa_in_movimento = null;
    		set_size_coda_mosse(coda_generica.size());    		
        }  		
	}
	private void termina_mossa(Mossa mo) 
	{
		delete(sezione);
		char una_mossa = mo.mossa;
		if(mo.cancellazione)
			una_mossa = vm.trova_contro_mossa(una_mossa);
		colora_mossi(una_mossa);
		rimetti_cubetti();
		
        step = 0;
        
        if(!mo.cancellazione)
        	forma.memo.add(mo);    
        
        forma.mostra_memo_mosse();
        
        forma.mainApp.mostra_specchio();		
		
	}
	

	private void crea_sezione() 
	{
		sezione = new Geometry();		
		//System.out.println(Arrays.toString(cubetti_da_muovere));
		for(int i = 0; i < cubetti_da_muovere.length;i++)
		{	
			int mosso = cubetti_da_muovere[i];
			Cubino c = cubetti[mosso];
			sezione.add(c);	
			cubetti_mossi[i] = c;			
		}	
		
		for(int i = 0; i < cubetti_da_muovere.length;i++)
		{	
			delete(cubetti[cubetti_da_muovere[i]]);
		}
		
		add(sezione);
	}



	private void rimetti_cubetti() 
	{	
		//cubetti_mossi[0].debug_colori("rimetti :");
		synchronized(this)
		{			
			for(int c = 0; c < 9;c++)
			{
				add(cubetti_mossi[c]);
				cubetti[cubetti_da_muovere[c]] = cubetti_mossi[c]; 				
			}
		}
		//cubetti[18].debug_colori("rimetti :" + cubetti_da_muovere[0] + " ");
	}
	
	Material [][] old_material;
	private void carica_vecchi_colori_sezione()
	{
		old_material = new Material[9][6];
		for(int c = 0; c < 9;c++)
		{
			for(int f = 0; f < 6;f++)
			{
				old_material[c][f] =  cubetti_mossi[c].faccette[f].material;
			}
		}
	}
	private void colora_mossi(char mossa) 
	{	
		
		//System.out.println("colora_mossi " + mossa);
		//cubetti_mossi[0].debug_colori("PRIMA :");
		for(int c = 0; c < 9;c++)
		{
			for(int f = 0; f < 6;f++)
			{
				
				Material mat = trova_nuovo_colore_faccetta(c,f,mossa); 				
				cubetti_mossi[c].faccette[f].setMaterial(mat);
				
				
			}
		}
		//cubetti_mossi[0].debug_colori("DOPO  :");
	}
	private Material trova_nuovo_colore_faccetta(int cubetto_mosso,int faccetta, char mossa) 
	{
		Colore colore = new Colore(ALTO,BASSO,DESTRA,SINISTRA,FRONTE,RETRO);
		Material mat = colore.trova_nuovo_colore_faccetta(cubetto_mosso, faccetta, mossa,old_material);
		return mat;		 
	}

	private void esegui_movimento(Mossa mo) 
	{		
		char mossa = mo.mossa;  
		if(mo.cancellazione)
			mossa = vm.trova_contro_mossa(mossa);
		double trenta = PI / 6.0; 
		
		if(mossa == 'f' )
			esegui_rotazione(FRONTE,-trenta);
		else
		if(mossa == 'r')
			esegui_rotazione(RETRO,trenta);
		else
		if(mossa == 'a' )
			esegui_rotazione(ALTO,-trenta);
		else
		if( mossa == 'b')
			esegui_rotazione(BASSO,trenta);
		else
		if(mossa == 'd' )
			esegui_rotazione(DESTRA,-trenta);
		else
		if( mossa == 's')
			esegui_rotazione(SINISTRA,trenta);
		else
		if(mossa == 'F' )
			esegui_rotazione(FRONTE,trenta);
		else
		if( mossa == 'R')
			esegui_rotazione(RETRO,-trenta);
		else
		if(mossa == 'A' )
			esegui_rotazione(ALTO,trenta);
		else
		if(mossa == 'B')
			esegui_rotazione(BASSO,-trenta);
		else
		if(mossa == 'D' )
			esegui_rotazione(DESTRA,trenta);
		else
		if(mossa == 'S')
			esegui_rotazione(SINISTRA,-trenta);
	}

	private void esegui_rotazione(int cubo_centrale,double angolo) 
	{		
		switch(cubo_centrale)
		{
			case 22: 
				sezione.matrix.rotateZ(angolo);
				break;
			case 4:
				sezione.matrix.rotateZ(-angolo);
				break;
			case 16:
				sezione.matrix.rotateX(angolo);
				break;
			case 10: 
				sezione.matrix.rotateX(-angolo);
				break;
			case 14:
				sezione.matrix.rotateY(angolo);
				break;
			case 12: 
				sezione.matrix.rotateY(-angolo); //getMatrix()
				break;
		}
	}



	private int [] trova_indici_cubetti_da_muovere(char mossa) 
	{
		int [] ret = null ;
		if(mossa == 'f' || mossa == 'F') 
		{	
			ret  = trova_cubetti(FRONTE);			
		}
		else
		if(mossa == 'r' || mossa == 'R')
		{
			ret = trova_cubetti(RETRO);
		}
		else
		if(mossa == 'd' || mossa == 'D')
		{	
			ret = trova_cubetti(DESTRA);			
		}
		else
		if(mossa == 's' || mossa == 'S')
		{
			ret = trova_cubetti(SINISTRA);			
		}
		else
		if(mossa == 'a' || mossa == 'A')
		{	
			ret = trova_cubetti(ALTO);			
		}
		else
		if(mossa == 'b' || mossa == 'B')
		{
			ret = trova_cubetti(BASSO);			
		}
		return ret;		
	}



	private int[] trova_cubetti(int cubo_centrale) 
	{
		int [] cubetti_da_muovere = new int[9];
		switch(cubo_centrale)
		{
			case 22:
				cubetti_da_muovere[0] = 18;
				cubetti_da_muovere[1] = 19;
				cubetti_da_muovere[2] = 20;
				cubetti_da_muovere[3] = 21;
				cubetti_da_muovere[4] = 22;
				cubetti_da_muovere[5] = 23;
				cubetti_da_muovere[6] = 24;
				cubetti_da_muovere[7] = 25;
				cubetti_da_muovere[8] = 26;
				break;
			case 4:
				cubetti_da_muovere[0] = 0;
				cubetti_da_muovere[1] = 1;
				cubetti_da_muovere[2] = 2;
				cubetti_da_muovere[3] = 3;
				cubetti_da_muovere[4] = 4;
				cubetti_da_muovere[5] = 5;
				cubetti_da_muovere[6] = 6;
				cubetti_da_muovere[7] = 7;
				cubetti_da_muovere[8] = 8;
				break;
			case 16:
				cubetti_da_muovere[0] = 24;
				cubetti_da_muovere[1] = 25;
				cubetti_da_muovere[2] = 26;
				cubetti_da_muovere[3] = 15;
				cubetti_da_muovere[4] = 16;
				cubetti_da_muovere[5] = 17;
				cubetti_da_muovere[6] = 6;
				cubetti_da_muovere[7] = 7;
				cubetti_da_muovere[8] = 8;
				break;
			case 10://errore
				cubetti_da_muovere[0] = 18;
				cubetti_da_muovere[1] = 19;
				cubetti_da_muovere[2] = 20;
				cubetti_da_muovere[3] = 9;
				cubetti_da_muovere[4] = 10;
				cubetti_da_muovere[5] = 11;
				cubetti_da_muovere[6] = 0;
				cubetti_da_muovere[7] = 1;
				cubetti_da_muovere[8] = 2;
				break;
			case 14:
				cubetti_da_muovere[0] = 20;
				cubetti_da_muovere[1] = 11;
				cubetti_da_muovere[2] = 2;
				cubetti_da_muovere[3] = 23;
				cubetti_da_muovere[4] = 14;
				cubetti_da_muovere[5] = 5;
				cubetti_da_muovere[6] = 26;
				cubetti_da_muovere[7] = 17;
				cubetti_da_muovere[8] = 8;
				break;
			case 12:
				cubetti_da_muovere[0] = 18;
				cubetti_da_muovere[1] = 9;
				cubetti_da_muovere[2] = 0;
				cubetti_da_muovere[3] = 21;
				cubetti_da_muovere[4] = 12;
				cubetti_da_muovere[5] = 3;
				cubetti_da_muovere[6] = 24;
				cubetti_da_muovere[7] = 15;
				cubetti_da_muovere[8] = 6;
				break;
		}
		return cubetti_da_muovere;
	}



	public void trova_retro_basso_sinistra() 
	{
		switch(ALTO)
		{
			case 22:
				BASSO = 4;
				break;
			case 12:
				BASSO = 14;
				break;
			case 4:
				BASSO = 22;
				break;
			case 14:
				BASSO = 12;
				break;
			case 16:
				BASSO = 10;
				break;
			case 10:
				BASSO = 16;
				break;
		}
		switch(FRONTE)
		{
			case 22:
				RETRO = 4;
				break;
			case 12:
				RETRO = 14;
				break;
			case 4:
				RETRO = 22;
				break;
			case 14:
				RETRO = 12;
				break;
			case 16:
				RETRO = 10;
				break;
			case 10:
				RETRO = 16;
				break;
		}
		switch(DESTRA)
		{
			case 22:
				SINISTRA = 4;
				break;
			case 12:
				SINISTRA = 14;
				break;
			case 4:
				SINISTRA = 22;
				break;
			case 14:
				SINISTRA = 12;
				break;
			case 16:
				SINISTRA = 10;
				break;
			case 10:
				SINISTRA = 16;
				break;
		}		
	}
	
	public char trova_mossa_centrale(int cub,boolean SHIFT) 
	{
		vm.carica_parametri( FRONTE,  DESTRA,  ALTO,  BASSO,  RETRO,  SINISTRA);
		return vm.trova_mossa_centrale(cub, SHIFT);		
	}	
	
	public void mostra_facce_visibili(Graphics g, int H, int W, boolean paint) 
	{	
		Color [] colori = {Color.BLUE,Color.YELLOW,Color.GREEN,Color.WHITE,Color.RED,Color.ORANGE};
		
		int dim = 27;
		int Y = H - 50;
		for(int f = 0;f <6;f++)
		{
			boolean vis = cubetti[cubetti_centrali[f]].faccette[f].get_visible();
			
			facce_visibili[f] = vis;
			
			if(vis)
			{
				int tx = cubetti[cubetti_centrali[f]].faccette[f].get_TX();
				int ty = cubetti[cubetti_centrali[f]].faccette[f].get_TY();
				int tz = cubetti[cubetti_centrali[f]].faccette[f].get_TZ();
				xyz_visibili[f][0] = tx; //x
				xyz_visibili[f][1] = ty; //y
				xyz_visibili[f][2] = tz; //z
			}
			else
			{
				xyz_visibili[f][0] = 0; //x
				xyz_visibili[f][1] = 0; //y
				xyz_visibili[f][2] = 0; //z
			}			
		}
		
		trova_facce_visibili();
		if(FACCE_IN_VISTA == 3)
		{	
			int alto_centrale = trova_alto(H);
			int destra_centrale = trova_destra(alto_centrale);
			int fronte_centrale = trova_fronte(alto_centrale,destra_centrale);
			int inizio = W*3/4;
			for(int f = 0;f <6;f++)
			{
				if(facce_visibili[f])
				{
					//disegna_facsimile(g);
					if(paint)
					{
						g.setColor(colori[f]);
						g.fillRect(inizio+f*dim, Y, 25, 30);//(s, W/2, 30);
					}
					int trovato = -1;
					for(int t = 0;t<cubetti_centrali.length;t++)
					{
						if(cubetti_centrali[t] == alto_centrale)
						{
							ALTO = alto_centrale;
							trovato = t;
							break;
						}
					}
					if(trovato >=0)
					{
						if(paint)
						{
							g.setColor(Color.BLACK);
							g.drawString("a", inizio+trovato*dim+dim/3, Y+dim/2);//(s, W/2, 30);
							g.drawString(""+ALTO, inizio+trovato*dim+dim/3, Y+dim);
						}
					}
					trovato = -1;
					for(int t = 0;t<cubetti_centrali.length;t++)
					{
						if(cubetti_centrali[t] == destra_centrale)
						{
							DESTRA = destra_centrale;
							trovato = t;
							break;
						}
					}
					if(trovato >=0)
					{
						if(paint)
						{
							g.setColor(Color.BLACK);
							g.drawString("d", inizio+trovato*dim+dim/3, Y+dim/2);//(s, W/2, 30);
							g.drawString(""+DESTRA, inizio+trovato*dim+dim/3, Y+dim);
						}
					}
					trovato = -1;
					for(int t = 0;t<cubetti_centrali.length;t++)
					{
						if(cubetti_centrali[t] == fronte_centrale)
						{
							FRONTE = fronte_centrale;
							trovato = t;
							break;
						}
					}
					if(trovato >=0)
					{
						if(paint)
						{
							g.setColor(Color.BLACK);
							g.drawString("f", inizio+trovato*dim+dim/3, Y+dim/2);//(s, W/2, 30);
							g.drawString(""+FRONTE, inizio+trovato*dim+dim/3, Y+dim);
						}
					}
					//conferma_fronte_destra();
					trova_retro_basso_sinistra();
				}
				else
				{
					if(paint)
					{
						g.setColor(new Color(100,100,200));
						g.fillRect(inizio+f*dim, Y, 25, 30);
					}
				}
			}
		}		
	}
	

	private int[] trova_facce_visibili() 
	{
		
		int conta = 0;
		int [] n = {-1,-1,-1,-1,-1,-1};
		for(int i = 0; i <facce_visibili.length;i++)
			if(facce_visibili[i])
			{
				
				n[conta++]= i;
				FACCE_IN_VISTA =conta;
			}
		return n;
	}
	private int trova_fronte(int alto_centrale, int destra_centrale) 
	{
		int ret = -1;
		for(int f = 0;f<6;f++)
		{
			if(cubetti_centrali[f] == alto_centrale)
				continue;
			if(cubetti_centrali[f] == destra_centrale)
				continue;
			if(facce_visibili[f])
			{
				ret = cubetti_centrali[f];
				break;
			}
		}
		return ret;
	}
	private int trova_destra(int alto_centrale) 
	{
		int ret = -1;
		int xmax = 0;		
		for(int f = 0;f<6;f++)
		{
			if(cubetti_centrali[f] == alto_centrale)
				continue;
			if(facce_visibili[f])
			{	
				int destra = xyz_visibili[f][0];
				if( destra> xmax)
				{
					xmax =  destra; 
					ret = cubetti_centrali[f];
				}
			}
		}
		
		return ret;
	}
	private int trova_alto(int H) 
	{
		int ret = -1;
		int ymax = H;		;
		for(int f = 0;f<6;f++)
		{			
			if(facce_visibili[f])
			{	
				int alto = xyz_visibili[f][1];
				//System.out.println("faccia"+f+" alto " + alto);
				if( alto< ymax)
				{
					ymax =  alto; 
					ret = cubetti_centrali[f];
				}
			}
		}		
		return ret;
	}

	public void gestione_faccia_selezionata_dal_mouse(Geometry C, boolean SHIFT) 
	{
		Geometry p = C.getParent();
    	if(p != null && p instanceof Faccetta)
    	{
    		Faccetta fac = (Faccetta)p;
    		int nc = fac.indice;
    		int nf = fac.numf;
    		if(is_centrale(nc))
    		{
    			char car = trova_mossa_centrale(nc,SHIFT);
    			if(car != '?')
    			{
    				accoda_mossa(car,false);	
    				set_num_mosse(get_num_mosse()+1);
    			}
    		}
    		else
    		{
    			//System.out.println(nc + " "+nf);
    			char car = trova_mossa_laterale(nc,nf,SHIFT);
    			if(car != '?')
    			{
    				accoda_mossa(car,false);	
    				set_num_mosse(get_num_mosse()+1);
    			}
    			//else
    			//	System.out.println("no laterale");
    		}
    	}
		
	}
	



	public boolean is_centrale(int cub) 
	{
		boolean ret = false;
		
		if(cub % 6 == 4 || cub == 12 || cub == 14)
		{
			//System.out.println("is_centrale cubetto " + cub );
			ret = true;
		}
		return ret;
	}
	public char trova_mossa_laterale(int nc, int nf,boolean SHIFT) 
	{
		vm.carica_parametri( FRONTE,  DESTRA,  ALTO,  BASSO,  RETRO,  SINISTRA);
		return vm.trova_mossa_laterale(nc,nf,SHIFT);		
		
	}
	void accoda_mossa(char una_mossa, boolean cancellazione) 
	{
		if(FACCE_IN_VISTA < 3 && cancellazione)
		{
			JOptionPane.showMessageDialog(null, " MOSTRARE 3 FACCE!!!");
			return;
		}		
		Mossa m = new Mossa(una_mossa,cancellazione); 
		coda_generica.add(m);		
	}
	
	public void accoda_movimento_cubo(int una_freccia) 
	{			
		Movimento mov = new Movimento(una_freccia,false);
		coda_generica.add(mov);
		set_num_movimenti(get_num_movimenti()+1);
	}	
	
	public void gestione_faccia_selezionata_da_tastiera(char c) 
	{	
		//System.out.println("gestione_faccia_selezionata_da_tastiera " + c);
		accoda_mossa(c,false);
		set_num_mosse(get_num_mosse() + 1); 
	}

	public void gestione_faccia_selezionata_da_lista_mosse(char c, boolean da_registrazione) 
	{
		accoda_mossa(c,false);
		if(da_registrazione)
		set_num_mosse(get_num_mosse() + 1); 
	}



	public void gestione_cancellazione(LinkedList<Accodabile> memo) 
	{	
		//System.out.println("gestione_faccia_selezionata_cancellazione");
		

		Accodabile accodabile = memo.pollLast();
		
		if(accodabile instanceof Movimento)
		{
			movimento_in_movimento = (Movimento)accodabile;
			movimento_in_movimento.cancellazione = true;
			muovi_cubo(movimento_in_movimento);
			//forma.MOSTRA_SPECCHIO = true;
		}
		else
		{			
			mossa_in_movimento = (Mossa)accodabile;
			mossa_in_movimento.cancellazione = true;
			esegui_mossa(mossa_in_movimento);
			//forma.MOSTRA_SPECCHIO = true;
		}
		/*
		if(forma.MOSTRA_SPECCHIO)
		{
			forma.mainApp.mostra_specchio();
			forma.MOSTRA_SPECCHIO = false;
			
		}
		*/		
	}

	public void muovi_cubo(Movimento un_movimento) 
	{
		//System.out.println("muovi_cubo " + un_movimento.movimento + " step: "+ step_movimento + " canc:"+ un_movimento.cancellazione);
		int una_freccia       = un_movimento.movimento;
		boolean cancellazione = un_movimento.cancellazione;
		
		int freccia = una_freccia;
		
		if(cancellazione)
			freccia = vm.trova_contro_movimento(una_freccia);
		int num_movimenti = 6;
		if(freccia == KeyEvent.VK_LEFT || freccia == KeyEvent.VK_RIGHT)
			num_movimenti = 3;
		
		if(step_movimento < num_movimenti) 
    	{	
			muovi_cubo_per_step_con_matrici(freccia);			
            step_movimento++;
        } 
    	else 
        {       		
    		step_movimento = 0;
    		movimento_in_movimento = null;
    		if(!cancellazione)
    			forma.memo.add(un_movimento);  
    		forma.mostra_memo_mosse(); 
    		
    		set_size_coda_mosse(coda_generica.size());
    		
    		forma.mainApp.mostra_specchio();    		
        }   		
	}	
	
	private  void muovi_cubo_per_step_con_matrici(int freccia ) 
	{
		//System.out.println("muovi_cubo_per_step_con_matrici " + step_movimento + " " + freccia);		
		if(freccia == KeyEvent.VK_LEFT )
		{			
			Matrix tmp = new Matrix();			
			Matrix cam = forma.renderer.getCamera();
			tmp.copy(cam);
			tmp.rotateY(-_30); 
			forma.renderer.setCamera(tmp);			
		}
		else
		if(freccia == KeyEvent.VK_RIGHT )
		{			
			Matrix tmp = new Matrix();
			Matrix cam = forma.renderer.getCamera();
			tmp.copy(cam);
			tmp.rotateY(_30); 
			forma.renderer.setCamera(tmp);			
		}
		else
		if(freccia == KeyEvent.VK_UP )
		{			
			Matrix tmp = new Matrix();
			Matrix cam = forma.renderer.getCamera();
			tmp.copy(cam);
			tmp.rotate(-_30,cam.get()[0],cam.get()[1],cam.get()[2]);
			forma.renderer.setCamera(tmp);
		}
		else
		if(freccia == KeyEvent.VK_DOWN )
		{			
			Matrix tmp = new Matrix();
			Matrix cam = forma.renderer.getCamera();
			tmp.copy(cam);			
			tmp.rotate(_30,cam.get()[0],cam.get()[1],cam.get()[2]); 
			forma.renderer.setCamera(tmp);
		}		
	}
	
	
	
	public int solo_positivi() 
	{
		int ret = incr_SX_DX;
		if(incr_SU_GIU == 0)
		{
			switch(incr_SX_DX)
			{
				case 0:
					ret = 0;
					
				break;
				case -1:
					ret = 3;				
				break;
				case -2:
					ret = 2;
					
				break;
				case -3:
					ret = 1;				
				break;
			}
		}
		else
		{
			switch(incr_SX_DX)
			{
				case 0:
					ret = 0;
					
				break;
				case -1:
					ret = 3;				
				break;
				case -2:
					ret = 2;
					
				break;
				case -3:
					ret = 1;				
				break;
			}
		}
		
		return ret;
	}
	public int get_size_coda_mosse()
	{
		return size_coda_mosse;
	}
	public void set_size_coda_mosse(int una_size_coda_mosse)
	{
		pcs.firePropertyChange("size_coda_mosse", this.size_coda_mosse, una_size_coda_mosse);
		size_coda_mosse = una_size_coda_mosse;
    	messaggio.controllore.gestione_valori_controllati();
	}
	public int get_num_mosse() {
        return num_mosse;
    }
    public void set_num_mosse(int un_num_mosse) 
    {
    	//System.out.println("set_num_mosse fire");
    	pcs.firePropertyChange("num_mosse", this.num_mosse, un_num_mosse);
    	num_mosse = un_num_mosse;
    	messaggio.controllore.gestione_valori_controllati();	
    }
    
    public int get_num_movimenti() {
        return num_movimenti;
    }
    public void set_num_movimenti(int un_num_movimenti) 
    {
    	//System.out.println("set_num_movimenti fire");
    	pcs.firePropertyChange("num_movimenti", this.num_movimenti, un_num_movimenti);
    	num_movimenti = un_num_movimenti;
    	messaggio.controllore.gestione_valori_controllati();	
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) 
    {
    	//System.out.println("cubo27 sono controllabile");
    	
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) 
    {
    	pcs.removePropertyChangeListener(listener);
    }
	public void gestione_coda_generica() 
	{
		Accodabile accodabile = null;
		if(movimento_in_movimento == null && mossa_in_movimento == null)
		{
			accodabile = coda_generica.poll();
			if(accodabile != null)
			{
				if(accodabile instanceof Movimento)
				{
					movimento_in_movimento = (Movimento)accodabile;
					//forma.MOSTRA_SPECCHIO = true;
				}
				else
				{
					if(((Mossa)accodabile).mossa == '*')
					{
						esegui_cancellazione();
						return;
					}
					mossa_in_movimento = (Mossa)accodabile;
					//forma.MOSTRA_SPECCHIO = true;
				}
			}
			else
			{
				if(forma.INIZIO_NUOVA_PARTITA)
				{
					forma.nuova_partita();
					forma.INIZIO_NUOVA_PARTITA = false;
				}
				/*
				if(forma.MOSTRA_SPECCHIO)
				{
					forma.mainApp.mostra_specchio();					
					forma.MOSTRA_SPECCHIO = false;	
					
				}
				*/
				if(forma.CUBO_RISOLTO)
				{
					forma.CUBO_RISOLTO = false;
					boolean ok = forma.partita.chiudi(get_num_mosse(),get_num_movimenti());
					if(ok)
						forma.partita = null;
				}
				
			}
		}
		else
		{
			if(movimento_in_movimento != null)
				muovi_cubo(movimento_in_movimento);
			else
			if(mossa_in_movimento != null)
				esegui_mossa(mossa_in_movimento);
		}
	}
	public void eureka() 
	{
		String soluzione = "";		
		char [] M = {'U','U','U','U','U','U','U','U','U','R','R','R','R','R','R','R','R','R','F','F','F','F','F','F','F','F','F','D','D','D','D','D','D','D','D','D','L','L','L','L','L','L','L','L','L','B','B','B','B','B','B','B','B','B'};
		Cubino[] lista = cubetti;
		for(int c = 0; c< lista.length;c++)
		{
			if(c==13)
				continue;
			Cubino cubino = lista[c];
			Geometry [] fac = cubino.faccette;
			for(int f = 0; f< fac.length;f++)
			{				
				char car = fac[f].material.getChar();
				if(car != ' ')
				{
					int indice = forma.trova_indice(c,f); 
					M[indice] = car;
				}				
			}			
		}
		soluzione = new String(M);
		if(soluzione.equals("UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"))
		{			
			forma.CUBO_RISOLTO = true;
		}		
	}
	
	private void esegui_cancellazione() 
	{
		if(forma.memo.isEmpty())
		{	
			//set_size_coda_mosse(coda_generica.size());
			/*
			if(forma.partita != null)
			{
				eureka();
			}
			*/
			return;
		}
		gestione_cancellazione(forma.memo);
		
	}
}