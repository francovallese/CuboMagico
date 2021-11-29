package mypackage;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kociemba.Search;
import perlinK.Geometry;
import perlinK.Material;
import perlinK.Matrix;
import perlinK.RenderJPanel;


public class Forma extends RenderJPanel implements ChangeListener,RicevitoreComandi
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean DRAG = false;
	LinkedList<Accodabile> memo = new LinkedList<Accodabile>();
	private JSlider velocity;
	private JSlider zoom;	
	private boolean SHIFT = false;
	public boolean CAPS_LOCK = false;
	Cubo27 cubo27;
	//int UD = 0;
	String cmd;	
	private int ZOOM_MIN = 0;
	private int ZOOM_MAX = 100;
	private int ZOOM_DEFAULT = 50;
	private boolean visibili_27 = true;
	private double OTTICA_CAMERA = Constants.LENTE; //13.5;
	private double PROSPETTIVA = Constants.PROSPETTIVA;
	boolean CopyPaste = false;
	MainApp mainApp;
	Timer timer_reset;	
	private Matrix posizione_iniziale;
	boolean MOSTRA_SPECCHIO = false; //DA CANCELLARE ? FRANCO
	String str_solve = "";	
	double _180 = Math.PI;
	double _90  = Math.PI/2;	
	double _30  = Math.PI/6.0;
	String str_mosse = "";
	public boolean INIZIO_NUOVA_PARTITA = false;
	public Partita partita = null;
	public boolean CUBO_RISOLTO = false;
	
	public static String [] registrazione = 
	{
		"AFSDRsdAFSDRsd",                     //mescola         0
		"SDBARFSD",                           //punti           1
		"adBRddffdssrrBrddBBddbSSffARRbff",   //3cubi           2
		"afArraFAffaffArraffAff",             //ciliegina       3
		"daffbdsfrbFDffdAAfddFDAFAAfd",       //frecce          4
		"sddFsraRSfdAdSdsfrabds",             //6 u             5
		"dsffDSbb",                           //lift            6
		"aabbddssffrr",                       //facile          7
		"SRbrBsFBfb",                         //last            8  SRbrBsFBfb  bSRbrBsFBf
		"DSRDSaDSffdsadsRdsBB",               //2 edge          9
		"BdbDBdbsBDbdBDbS",                   //2 corner       10  
		"ffaaFdsaaDSFaaff",                   //3 edge         11
		"BdbsBDbS",                           //3 corner       12 
		"",
		"DaadaaDfdaDADFddA",                  // swap          14
		"fdADAdaDFdaDADfdF",                  // swap 1        15
		"DAdAdadADadaddADaa",                 // swap 2        16
		"ddardsffDSbbRAdd",                   // lift1         17
	};
		
		//AFSDRsdAFSDRsd nuova partita (mescola)
		//DSrdsfaDSrdsfa per risolvere con funzione List (contro mescola)
	
	public Forma(MainApp mainApp  )
	{	
		
		super.init();
		this.mainApp =  mainApp;
		
		aggiungi_keyListener();
		
		crea_slider();		
    	
    	MainApp.lc.set_Slider(velocity,zoom);    	
    	
		crea_cubo();
		
		start();		
		
		requestFocusInWindow();
		
	}
	private void crea_slider() 
	{
		Font font = new Font("Monospaced",Font.PLAIN,10);		
		velocity = new JSlider(SwingConstants.HORIZONTAL,5,200,20);
		velocity.setFocusable(false);
    	velocity.addChangeListener(this);
    	TitledBorder tb = new TitledBorder("Time");
    	tb.setTitleFont(font);
    	velocity.setBorder(tb);    	
    	
    	zoom = new JSlider(SwingConstants.HORIZONTAL,ZOOM_MIN,ZOOM_MAX,ZOOM_DEFAULT);
    	zoom.setFocusable(false);
    	zoom.setValue(ZOOM_DEFAULT);
    	zoom.addChangeListener(this);
    	TitledBorder tb1 = new TitledBorder("Zoom");
    	tb1.setTitleFont(font);
    	zoom.setBorder(tb1);    	
		
	}
	private void aggiungi_keyListener() 
	{
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == KeyEvent.VK_SHIFT)
					SHIFT  = true;	
			}
			public void keyReleased(KeyEvent ke) 
			{
				if (ke.getKeyCode() == KeyEvent.VK_SHIFT)
					SHIFT = false;
				if (ke.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
				{
					CAPS_LOCK = !CAPS_LOCK;
					cubo27.messaggio.mostra_caps_lock();
				}
					
			}			
		});
		
	}
	public void posizione_standard()
    {	
		//System.out.println("posizione_standard con posizione_iniziale");
		Matrix tmp = new Matrix();
		Matrix cam = renderer.getCamera();
		tmp.copy(cam);
		tmp.rotateX(_90/2.0);
		tmp.rotateY(-_90/2.0);
		posizione_iniziale = tmp;
		
		renderer.setCamera(tmp);		
    }
	
	@Override
	public void initialize()
    {
	   //setBgColor(.4,.4,0.8);
	   setBgColor(Constants.RGB_SFONDO[0],Constants.RGB_SFONDO[1],Constants.RGB_SFONDO[2]);
	   //SCALA
	   setFL(OTTICA_CAMERA);
	   setFOV(PROSPETTIVA);
	   addLight(-0.5,  1, 1       ,.5,.5,.5);
	   addLight(0.5,  -1, -1       ,.5,.5,.5);
	   /*
       addLight(1,  1, 1       ,.5,.5,.5); //  ,.50,.50,.46);
       addLight(1,  1, -1         ,.5,.5,.5); 
       addLight(-1, -1, 1       ,.5,.5,.5); //  ,.50,.50,.46);
       addLight(-1,  -1, -1         ,.5,.5,.5); 
       */
       posizione_standard();       
    }
	
	
	
	
	
	@Override	
	public boolean processCommand(KeyEvent ke) 
	{
		//System.out.println("processCommand "+ ke.getKeyCode()+ " "+ke.getKeyChar());
		
		
		String cmd = MainApp.lc.is_operativo();
		if(cmd.length() > 0 )
		{
			if(!cmd.equals("COLONNA") && ke.getKeyCode() == KeyEvent.VK_ENTER)
				MainApp.lc.lancio_comando(cmd);
			return true;
		}
		
		super.processCommand(ke);
		int keycode = ke.getKeyCode();
		if(keycode == KeyEvent.VK_DELETE)
		{
			delete();
		}		
		
		
		
		
		char c = ke.getKeyChar();
		if(
				c == 'a' || c =='A' ||
				c == 'b' || c =='B' ||
				c == 'f' || c =='F' ||
				c == 'r' || c =='R' ||
				c == 'd' || c =='D' ||
				c == 's' || c =='S'
				
				)
		{	
			
			cubo27.gestione_faccia_selezionata_da_tastiera(c);
		}
		
		if
		( 
			keycode == KeyEvent.VK_LEFT  || 
			keycode == KeyEvent.VK_RIGHT || 
			keycode == KeyEvent.VK_UP    || 
			keycode == KeyEvent.VK_DOWN 
		)
		{	
			int freccia = keycode;
			
			if(freccia ==  KeyEvent.VK_LEFT || freccia ==  KeyEvent.VK_RIGHT)
			{
				boolean bianco = cubo27.cubetti[14].faccette[3].get_visible(); //faccia cubetto centrale ALTO
				if(!bianco) // vedo giallo
				{
					if(freccia ==  KeyEvent.VK_LEFT)
						freccia = KeyEvent.VK_RIGHT;
					else
					if(freccia ==  KeyEvent.VK_RIGHT)
						freccia = KeyEvent.VK_LEFT;
				}					
			}
			
			cubo27.accoda_movimento_cubo(freccia);
			
		}
		return true;
	}
	
	
	
	@Override
	public synchronized void paintComponent(Graphics g) 
	{
		//System.out.println("paintComponent su Forma");
		
		super.paintComponent(g);
		if (showFPS) 
		{
            g.setColor(Color.white);
            g.fillRect(0, H - 14, 60, 14);
            g.setColor(Color.black);
            String s_fps = ""+(int) frameRate + "." + ((int) (frameRate * 10) % 10) + " fps";
            g.drawString(s_fps, 2, H - 2);      
            
        }
		if(partita != null )
		{
			String time = partita.get_time();
			g.setColor(Constants.SFONDO);
            g.fillRect(0, 0, 100, 14);
            g.setColor(Color.YELLOW);            
            g.drawString(time, 2, 14-2);     
		}
		
		//g.drawString("X:"+round((float)X,3)+" Y:"+round((float)Y,3)+" Z:"+round((float)Z,3), W/2, 10);
		//g.drawString("BL | GI | VE | BI | RO | AR", W/2, 20);
		//g.drawString("alfax: "+alfax + " alfay: "+ alfay, W/2, 20);
		
		
		if(cubo27 != null && visibili_27 )
		{
			cubo27.mostra_facce_visibili(g,H,W,false);
		}
		//g.drawString("alfax"+ Math.toDegrees(alfax)+" alfay"+ Math.toDegrees(alfay), W/2, 50);
		//messaggio.repaint();
		
	}
	@SuppressWarnings("unused")
	private float round(float number, int scale) {
	    int pow = 10;
	    for (int i = 1; i < scale; i++)
	        pow *= 10;
	    float tmp = number * pow;
	    return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
	}
	
	
	public void animate(double time) 
	{
		//System.out.println("animate su Forma");
		if(CopyPaste)return;
		
		if(cubo27 != null)
		{
			cubo27.gestione_coda_generica();
		} 
		
    	requestFocusInWindow();	
	}
	
	public synchronized void salva_cubo() 
	{
		//System.out.println("salva cubo");
		Matrix c0 = renderer.getCamera();
		
		ObjectOutputStream oss;
		try
		{
			FileOutputStream out = new FileOutputStream("salvato");
			oss = new ObjectOutputStream(out);
			oss.writeObject(c0);
			
			
			
			for(int i=0; i<27;i++)
			{
				for(int f=0; f<6;f++)
				{
					Material m = cubo27.cubetti[i].faccette[f].material;
					oss.writeObject(m);
					
					//if(m != null)
					//{
					//	oss.writeObject(m);
					//}
					
					
				}
			}
			oss.flush();
			oss.close();
			
			
		}
		catch(Exception e)
		{
			System.out.println("errore scrittura:"+ e.getMessage());
		}
		
	}

	
	public void self(int num)
	{
		
		if(str_mosse.length() > 0)	
		{
			for(int i = 0; i<str_mosse.length(); i++)
			{
				char mossa = str_mosse.charAt(i);				
				cubo27.gestione_faccia_selezionata_da_lista_mosse(mossa,false);				
			}
			str_mosse = "";
		}
		else
		{
			for(int i = 0; i<registrazione[num].length(); i++)
			{
				char mossa = registrazione[num].charAt(i);
				cubo27.gestione_faccia_selezionata_da_lista_mosse(mossa,true);
			}
			/*
			if(num == 0) // "Shuffle"
			{
				cubo27.set_num_mosse(0);
				cubo27.set_num_movimenti(0);
				cubo27.set_size_coda_mosse(0);
			}
			*/
		}
	}
	
	
	
	
	public void solve()
	{
		Matrix ctmp = new Matrix();
		Matrix cam = renderer.getCamera();
		ctmp.copy(cam);
		
		int val_alto = cubo27.ALTO;
		//System.out.println(" cam " + val_alto);
		if(val_alto == 12)
		{
			
			ctmp.rotateX(_180);
			int SX_DX = cubo27.solo_positivi();
			if(SX_DX == 0 || SX_DX == 2)
			{					
				ctmp.rotateY(-_90);
			}
			else
			if(SX_DX == 1 || SX_DX == 3)
			{
				ctmp.rotateY(_90);
			}
			
				
		}
		//ctmp.rotateX(_30);
		//ctmp.rotateY(-_30);
		renderer.setCamera(ctmp);	
		
		JOptionPane.showMessageDialog(null, " START SOLVE!!!");
		long start = System.currentTimeMillis();
		
		//memo = new ArrayList<>();	
		memo.clear();
		str_solve = "";
			
		char [] M = {'U','U','U','U','U','U','U','U','U','R','R','R','R','R','R','R','R','R','F','F','F','F','F','F','F','F','F','D','D','D','D','D','D','D','D','D','L','L','L','L','L','L','L','L','L','B','B','B','B','B','B','B','B','B'};
		//boolean ok = false;
		Cubino[] lista = cubo27.cubetti;
		for(int c = 0; c< lista.length;c++)
		{
			if(c==13)
				continue;
			//System.out.println("------------------");
			Cubino cubino = lista[c];
			//System.out.println("");
			Geometry [] fac = cubino.faccette;
			for(int f = 0; f< fac.length;f++)
			{
				
				char car = fac[f].material.getChar();
				if(car != ' ')
				{
					int indice = trova_indice(c,f); 
					M[indice] = car;
					//ok = true;
					//System.out.print(car+"["+c+"]"+"["+f+"]");
				}
				
			}			
		}
		//System.out.println("");
		//System.out.println(M);
		//if(ok)
		//	return;
		str_solve = new String(M);
		if(str_solve.equals("UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"))
		{
			JOptionPane.showMessageDialog(null, " IS OK!!!");
			return;
		}
		//System.out.println(str_solve+" "+str_solve.length());
		
		String result = Search.solution(str_solve, 24, 5, false);
	
		// +++++++++++++++++++ Replace the error messages with more meaningful ones in your language ++++++++++++++++++++++
		if (result.contains("Error"))
		{
			switch (result.charAt(result.length() - 1)) {
			case '1':
				result = "There are not exactly nine facelets of each color!";
				break;
			case '2':
				result = "Not all 12 edges exist exactly once!";
				break;
			case '3':
				result = "Flip error: One edge has to be flipped!";
				break;
			case '4':
				result = "Not all 8 corners exist exactly once!";
				break;
			case '5':
				result = "Twist error: One corner has to be twisted!";
				break;
			case '6':
				result = "Parity error: Two corners or two edges have to be exchanged!";
				break;
			case '7':
				result = "No solution exists for the given maximum move number!";
				break;
			case '8':
				result = "Timeout, no solution found within given maximum time!";
				break;
			}
			JOptionPane.showMessageDialog(null, result);
		}
		else
		{
			long end_res = System.currentTimeMillis();
			long tm = end_res - start;
			JOptionPane.showMessageDialog(null, " TIME: " + tm);
			//System.out.println("NESSUN ERRORE ");
			//JOptionPane.showMessageDialog(null, result);
			str_mosse = "";
			for(int i = 0; i<result.length();i++)
			{
				char c = result.charAt(i);
				char mo = ' ';
				if(c == 'U')
					mo = 'a';
				else
				if(c == 'F')
					mo = 'f';
				else
				if(c == 'R')
					mo = 'd';
				else
				if(c == 'L')
					mo = 's';
				else
				if(c == 'D')
					mo = 'b';
				else
				if(c == 'B')
					mo = 'r';
					
				if(mo != ' ')
				{
					if(i< (result.length()-1))
					{
						char tipo = result.charAt(i+1);
						if(tipo == '2')
						{
							str_mosse = str_mosse.concat(""+mo);
							str_mosse = str_mosse.concat(""+mo);
						}
						else
						if(tipo == '\'')
						{
							String minu = ""+mo;
							str_mosse = str_mosse.concat(""+minu.toUpperCase());
						}
						else
						{
							
								str_mosse = str_mosse.concat(""+mo);
						}
					}
					else
						str_mosse = str_mosse.concat(""+mo);
				}
			}
			//JOptionPane.showMessageDialog(null, str_mosse);
			MainApp.ta.setText(str_mosse);
			
			char [] tmp = str_mosse.toCharArray();
			for(int i = 0; i<tmp.length;i++)
			{
				if(tmp[i] == 's')
					tmp[i] = 'S';
				else 
				if(tmp[i] == 'S')
					tmp[i] = 's';
				
				if(tmp[i] == 'b')
					tmp[i] = 'B';
				else 
				if(tmp[i] == 'B')
					tmp[i] = 'b';
				
				if(tmp[i] == 'R')
					tmp[i] = 'r';
				else 
				if(tmp[i] == 'r')
					tmp[i] = 'R';
					
			}
			str_mosse = "" + new String(tmp);
			
			
			//System.out.println("SOLUZIONE "+ str_mosse);
			self(13);
		}
		
	}
	public int trova_indice(int cubetto, int f) 
	{
		int I = -1;
		switch(cubetto)
		{
			case 0:
				switch(f)
				{
					case 1:
						I = 33;
						break;
					case 2:
						I = 53;
						break;
					case 5:
						I = 42;
						break;
				}	
				break;
			case 1:
				switch(f)
				{
					case 2:
						I = 50;
						break;
					case 5:
						I = 39;
						break;
				}	
				break;
			case 2:
				switch(f)
				{
					case 2:
						I = 47;
						break;
					case 3:
						I = 0;
						break;
					case 5:
						I = 36;
						break;
				}	
				break;
			case 3:
				switch(f)
				{
					case 1:
						I = 34;
						break;
					case 2:
						I = 52;
						break;					
				}	
				break;
			case 4:
				switch(f)
				{
					case 2:
						I = 49;
						break;					
				}	
				break;
			case 5:
				switch(f)
				{
					case 2: 
						I = 46;
						break;
					case 3:
						I = 1;
						break;
				}	
				break;
			case 6:
				switch(f)
				{
					case 1:
						I = 35;
						break;
					case 2:
						I = 51;
						break;
					case 4:
						I = 17;
						break;
				}	
				break;
			case 7:
				switch(f)
				{
					case 2:
						I = 48;
						break;
					case 4:
						I = 14;
						break;					
				}	
				break;
			case 8:
				switch(f)
				{
					case 2:
						I = 45;
						break;
					case 3:
						I = 2;
						break;
					case 4:
						I = 11;
						break;
				}	
				break;
			case 9:
				switch(f)
				{
					case 1:
						I = 30;
						break;
					case 5:
						I = 43;
						break;
				}	
				break;
			case 10:
				switch(f)
				{
					case 5:
						I = 40;
						break;
				}	
				break;
			case 11:
				switch(f)
				{
					case 3:
						I = 3;
						break;
					case 5:
						I = 37;
						break;					
				}	
				break;
			case 12:
				switch(f)
				{
					case 1:
						I = 31;
						break;					
				}	
				break;
			// 13 centrale non serve
			case 14:
				switch(f)
				{
					case 3:
						I = 4;
						break;				
				}	
				break;
			case 15:
				switch(f)
				{
					case 1:
						I = 32;
						break;
					case 4:
						I = 16;
						break;					
				}	
				break;
			case 16:
				switch(f)
				{
					case 4:
						I = 13;
						break;
				}	
				break;
			case 17:
				switch(f)
				{
					case 3:
						I = 5;
						break;
					case 4:
						I = 10;
						break;					
				}	
				break;
			case 18:
				switch(f)
				{
					case 0:
						I = 24;
						break;
					case 1:
						I = 27;
						break;
					case 5:
						I = 44;
						break;
				}	
				break;
			case 19:
				switch(f)
				{
					case 0:
						I = 21;
						break;
					case 5:
						I = 41;
						break;
				}	
				break;
			case 20:
				switch(f)
				{
					case 0:
						I = 18;
						break;
					case 3:
						I = 6;
						break;
					case 5:
						I = 38;
						break;
				}	
				break;
			case 21:
				switch(f)
				{
					case 0:
						I = 25;
						break;
					case 1:
						I = 28;
						break;
				}	
				break;
			case 22:
				switch(f)
				{
					case 0:
						I = 22;
						break;
				}	
				break;
			case 23:
				switch(f)
				{
					case 0:
						I = 19;
						break;
					case 3:
						I = 7;
						break;
				}	
				break;
			case 24:
				switch(f)
				{
					case 0:
						I = 26;
						break;
					case 1:
						I = 29;
						break;
					case 4:
						I = 15;
						break;
				}	
				break;
			case 25:
				switch(f)
				{
					case 0:
						I = 23;
						break;
					case 4:
						I = 12;
						break;
				}	
				break;
			case 26:
				switch(f)
				{
					case 0:
						I = 20;
						break;
					case 3:
						I = 8;
						break;
					case 4:
						I = 9;
						break;
				}	
				break;			
		}
		return I;
	}
	
	
	

	
	
	
	
	
	
	
	private void crea_cubo() 
	{	
		//System.out.println("crea_cubo ");
		
		world.name = "M:1";		
		cubo27 = new Cubo27(this);		
		world.add(cubo27);	
		
		/*
		Geometry specchietto = new Geometry();
		specchietto.add().globe(30, 30 , 0.5,1.0,0.0,1.00);
		specchietto.setMaterial(Materials.VETRO);
		specchietto.matrix.scale(40, 40, 40);
		specchietto.matrix.translate(0,-0.5, 0.1);
		world.add(specchietto);
		*/
		/*
		
		double[] x = {-0.5,0.5,0.5,-0.5};
		double[] y = {-0.5,-0.5,0.5,0.5};
		
		Geometry g = new Geometry().polygon(x,y); //OK davanti
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(0,0, 0.6);
		world.add(g);
		
		g = new Geometry().polygon(x,y); //OK destra
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(0.6,0, 0);
		g.matrix.rotateY(_90);
		world.add(g);
		
		g = new Geometry().polygon(x,y); //OK alto
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(0,0.6, 0);
		g.matrix.rotateX(_90*3);
		world.add(g);
		
		g = new Geometry().polygon(x,y); // ok dietro
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(0,0, -0.6);
		g.matrix.rotateX(_90*2);
		world.add(g);
		
		
		
		
		g = new Geometry().polygon(x,y); //OK sinistra
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(-0.6,0, 0);
		g.matrix.rotateY(_90*3);
		world.add(g);
		
		g = new Geometry().polygon(x,y); //OK basso
		g.setMaterial(Materials.METALLIC_BLACK);
		g.matrix.translate(0,-0.6, 0);
		g.matrix.rotateX(_90);
		world.add(g);
		*/
		
	}
	public void sposta_cubo(int un_UD)
	{
		if(un_UD == 1)
		{
			cubo27.matrix.translate(0, -0.6, 0);					
		}
		else
		{
			cubo27.matrix.translate(0, 0.6, 0);	
		}
	}
	
	private void crea_timer_reset() 
	{
		timer_reset.scheduleAtFixedRate(new TimerTask() 
		{
			int n = 0;
			@Override
			public void run() 
			{
				if(n == 0)
				{
					synchronized(this)
					{
						//cubo27.coda_mosse.clear();
						//cubo27.coda_movimenti.clear();
						cubo27.coda_generica.clear();
					}
				}
				else
				if(n == 10)
				{
					//System.out.println("un UD "+UD + " muovi " + MUOVI_CUBO+ " mossa " + MOSSA_CUBO);
					esegui_reset_colori();
				}
				else
				if(n == 11)
					completa_reset();
				else
				if(n == 12)
				{
					//renderer.setCamera(posizione_iniziale);
					mainApp.mostra_specchio();
					
					timer_reset.cancel();
				}
				
				n++;
					
			}
		}, 100,100); // 0.1 secondi a partire dopo 0.1 secondi
		
	}
	public void reset()
	{
		//SHUFFLE = true;
		timer_reset = new Timer();
		crea_timer_reset();
	}
	public void completa_reset()
	{
		//memo = new ArrayList<>();
		azzera_memo();
		cubo27.azzera_contatori();
		zoom.setValue(ZOOM_DEFAULT );
		setFL(OTTICA_CAMERA);	
		INIZIO_NUOVA_PARTITA = false;
		partita = null;
		CUBO_RISOLTO = false;
	}
	
	public void esegui_reset_colori()
	{	
			
		Colore c = new Colore(-1,-1,-1,-1,-1,-1);			
		for(int i=0; i<27;i++)
		{
			for(int f=0; f<6;f++)
			{
				Faccetta fac = cubo27.cubetti[i].faccette[f];
				c.colora_faccina(f, fac , i);
				
			}
		}
		renderer.setCamera(posizione_iniziale);
	}
	
	public void mostra_memo_mosse() 
	{
		String s = "";
		for(int m = 0;m< memo.size();m++)
		{
			Accodabile accodabile = memo.get(m);
			if(accodabile instanceof Movimento)
			{
				Movimento un_movimento = (Movimento)accodabile;
				char c = 0;
				switch(un_movimento.movimento)
				{
					case KeyEvent.VK_LEFT:
						c = '\u2190';
						break;
					case KeyEvent.VK_UP:
						c = '\u2191';
						break;
					case KeyEvent.VK_RIGHT:
						c = '\u2192';
						break;
					case KeyEvent.VK_DOWN:
						c = '\u2193';
						break;
				}
				s = s.concat(""+c);
			}
			else
			{
				Mossa una_mossa = (Mossa)accodabile;
				s = s.concat(""+una_mossa.mossa);
			}
			//s = s.concat(""+memo.get(m).mossa);
			//s = s.concat(""+'\u2190'+'\u2191'+'\u2193'+'\u2193');	
		}
		
		MainApp.ta.setText(s);		
		if(partita != null)
		{
			cubo27.eureka();
		}
	}
	public void delete() 
	{		
		cubo27.accoda_mossa('*',true);		
	}
	
	@Override
	public boolean mouseUp(MouseEvent event) // MouseEvent event 
	{
		   int x = event.getX();
		   int y = event.getY();
		
		Geometry C = getGeometry(x, y);
		
		if(C != null)
	    {
			if(cubo27 != null)
				cubo27.gestione_faccia_selezionata_dal_mouse(C,SHIFT);	    	
	    }
		
	    //super.mouseUp(event, x, y);		    
		return true; 
	}
	
	@Override
	public boolean mouseDrag(MouseEvent event) 
	{
		
		if(DRAG)
			return super.mouseDrag(event); 
		else
			return false;
	}
	
	
	
	public boolean mouseMove(MouseEvent event) 
	{
		requestFocusInWindow();
		int x = event.getX();
		int y = event.getY();
		
		Geometry C = getGeometry(x, y);
		//if (tmp != null)
		//   mat = tmp.material;
		boolean ok = false;
		boolean cursore_in_sfondo = false;
		if(C != null )
		{
			Geometry p = C.getParent();
			
			if( p != null && p instanceof Faccetta)
			{
				Faccetta fac = (Faccetta)p;
				int nc = fac.indice;
				int nf = fac.numf;
				if(cubo27.is_centrale(nc))
					ok= true;
				else
				{					
					char car = cubo27.trova_mossa_laterale(nc,nf,false);
	    			if(car != '?')
	    				ok= true;		
				}
				
			}
			else
				ok= false;	
		}
		else
		{
			ok = false;
			cursore_in_sfondo = true;
		}
		
		if(ok)
			modifica_cursore("./images/PawVerde.png",cursore_in_sfondo);
		else
			modifica_cursore(null,cursore_in_sfondo);
		return true;
	}
	private void modifica_cursore(String file_cursore,boolean cursore_nello_sfondo) 
	{
		if(file_cursore != null)
		{
			setCursor(file_cursore); 
			cubo27.messaggio.set_messaggio("Click: Rotazione oraria Shift(maiuscolo)+Click: Rotazione antioraria");
		}
		else
		{
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if(cursore_nello_sfondo)				
				cubo27.messaggio.set_messaggio("Usa il mouse o la tastiera. Alt+m per accedere al menù.",Color.YELLOW);
			else
				cubo27.messaggio.set_messaggio("a,b,d,s,f,r: Rotazione oraria A,B,D,S,F,R: Rotazione antioraria");
				
		}
		
	}
	public void setCursor(String pathimg)
	{
		File f = new File(pathimg);
		if(f.exists())
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage(pathimg);
			Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
			super.setCursor(c);
		}
	}
	
	
	public void info() 
	{
		JOptionPane.showMessageDialog(null, "DA FARE");
	}
	
	@Override
	public void set_cmd(String un_cmd) 
	{
		
		this.cmd = un_cmd;
		if(cmd.equals("Info"))
		{
			info();
		}
		else
    	if(cmd.equals("Ranking"))
		{
    		Partita.classifica();	   		
		}
		else
    	if(cmd.equals("Reset"))
		{    		
			reset();			
		}
    	else
    	if(cmd.equals("Save"))
		{
			save();
		}
    	else
    	if(cmd.equals("Load"))
		{
    		azzera_memo();    		
    		cubo27.azzera_contatori();
			load();
			
		}
		else
		if(cmd.equals("Points"))
		{
			self(1);
		}
		else
		if(cmd.equals("3 Cubes"))
		{
			self(2);
		}
		else
		if(cmd.equals("Cherry"))
		{
			self(3);
		}
		else
		if(cmd.equals("Arrows"))
		{
			self(4);
		}
		else
		if(cmd.equals("6 U"))
		{
			self(5);
		}		
		else
		if(cmd.equals("Easy"))
		{
			self(7);
		}			
		else
		if(cmd.equals("Shuffle"))
		{
			INIZIO_NUOVA_PARTITA = true;
			self(0);			
		}		
		else
		if(cmd.equals("Aliasing"))
		{
			
			aliasing();
			
		}
		else
		if(cmd.equals("Delete"))
		{
			delete();
		}
		else
		if(cmd.equals("List"))
		{
			lista();
		}
		else
		if(cmd.equals("Last"))
		{
			self(8);
		}
		else
		if(cmd.equals("Lift"))
		{
			self(6);
		}
		else
		if(cmd.equals("Lift1"))
		{
			self(17);
		}
		else
		if(cmd.equals("2 edges"))
		{
			self(9);
		}
		else
		if(cmd.equals("2 corners"))
		{
			self(10);
		}
		else
		if(cmd.equals("3 edges"))
		{
			self(11);
		}
		else
		if(cmd.equals("3 corners"))
		{
			self(12);
		}
		else
		if(cmd.equals("swap"))
		{
			self(14);
		}
		else
		if(cmd.equals("swap 1"))
		{
			self(15);
		}	
		else
		if(cmd.equals("swap 2"))
		{
			self(16);
		}	
		else
		if(cmd.equals("Exit"))
		{			
			System.exit(0);
		}
		else
		if(cmd.equals("Solve"))
		{
			MainApp.lc.ta.setText(MainApp.ta.getText());
			MainApp.ta.setText("");					
			solve();			
		}
		
		
	}
	
	public void save()
	{
		Matrix c0 = renderer.getCamera();
		
		ObjectOutputStream oss;
		try
		{
			oss = new ObjectOutputStream(new FileOutputStream("salvato1"));
			oss.writeObject(c0);
			
			
			
			for(int i=0; i<27;i++)
			{
				for(int f=0; f<6;f++)
				{
					Material m = cubo27.cubetti[i].faccette[f].material;
					oss.writeObject(m);
					/*
					if(m != null)
					{
						oss.writeObject(m);
					}
					*/
					
				}
			}
			oss.flush();
			oss.close();
		}
		catch(Exception e)
		{
			System.out.println("errore:"+ e.getMessage());
		}
	}
	public void load()
	{
		ObjectInputStream ois;
		try{
		ois = new ObjectInputStream(new FileInputStream("salvato1"));
		Matrix c0 = (Matrix) ois.readObject();
		renderer.setCamera(c0);
		
		for(int i=0; i<27;i++)
		{
			for(int f=0; f<6;f++)
			{
				Material m = (Material) ois.readObject();
				if(m != null)
				{
					cubo27.cubetti[i].faccette[f].setMaterial(m);
					
				}
			}
		}
		
		ois.close();
		
		}
		catch(Exception e)
		{

			System.out.println(e.getMessage());
		}
		mainApp.aggiorna_specchio();
		
	}
	private void aliasing() 
	{		
		super.set_aliasing();		
	}
	private void lista() 
	{
		new ElencoMosseDigitato(cubo27);		
	}
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		Object xx = e.getSource();
		if(xx.equals(velocity))
		{
			super.velocita = (long)velocity.getValue();
			if(cubo27 != null)
				cubo27.velocita = (long)velocity.getValue();
		}
		else
		if(xx.equals(zoom))
		{
			double z = OTTICA_CAMERA * (double)(100 - zoom.getValue())/ 50.0;
			setFL(z);
			
		}
	}
	public void nuova_partita() 
	{
		azzera_memo();
		cubo27.azzera_contatori();	
		String giocatore = richiesta_nome_giocatore();
		if(giocatore != null)
		{
			if(giocatore.length() > 0)
			{
				partita = new Partita(giocatore);
				partita.mostra_vecchi_punti(giocatore);	
				partita.start();
				CUBO_RISOLTO = false;
				//INIZIO_NUOVA_PARTITA = false;	
			}
			else
			{
				partita = null;
				JOptionPane.showMessageDialog(this, "INDICARE NOME VALIDO");
				//INIZIO_NUOVA_PARTITA = false;
				
			}
		}
		else
			partita = null;
			
	}
	
	private String richiesta_nome_giocatore() 
	{
		
		String giocatore      = null;
        
        String risposta = JOptionPane.showInputDialog(this, "Nome giocatore:", "NUOVA PARTITA", JOptionPane.YES_NO_OPTION);
        
        if (risposta != null)
        {
            giocatore = risposta.trim();
        }
       
		return giocatore;
	}
	private void azzera_memo() 
	{
		memo.clear();
		MainApp.ta.setText("");
		MainApp.lc.ta.setText("");	
		
	}
	
}
