package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.kociemba.twophase.Search;



public class MainApp extends JFrame 
{
	static final long serialVersionUID = 1L;
	
	Image info_img;
	Image last_img;
	Image lift_img;
	Image edges2_img;
	Image corners2_img;
	Image edges3_img;
	Image corners3_img;
	Thread t = null; 
	public static Lanciatore_comandi_Menu lc;
	public static JTextField ta;
	public static Forma forma;
	Specchio specchio = null;
	
	public MainApp()
	{
	
		setTitle("Cubo Magico");
	    Toolkit tk = getToolkit();
	    Image img = tk.createImage("./texture/leo.gif");
	    setIconImage(img);
	    
	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    setUndecorated(true);
	    
	    //setSize(Constants.W_TOT,Constants.H_TOT);
	  
	    setLayout(new BorderLayout());
	    
	    ta = new JTextField(""); 
	    ta.setBackground(new Color(102,102,204));
	    ta.setForeground(Color.WHITE);
		ta.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		ta.setEditable(false);
		ta.setEnabled(true);
		
		add(ta,BorderLayout.NORTH);
		
		
		lc = new Lanciatore_comandi_Menu();
		
		setJMenuBar(lc);
		
		forma = new Forma(this);
		add(forma,BorderLayout.CENTER);
		
								
		
		
		setVisible(true);
		
		specchio = new Specchio();
		add(specchio,BorderLayout.EAST);
		mostra_specchio();
		
		
		lc.set_ricevitore((RicevitoreComandi)forma);
		
		ta.addFocusListener(new FocusListener() 
		{

			@Override
			public void focusGained(FocusEvent e) 
			{				
				forma.CopyPaste = true;
			}

			@Override
			public void focusLost(FocusEvent e) 
			{				
				forma.requestFocusInWindow();
				forma.CopyPaste = false;
			}
			
		});
	   
	    addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
            	if(t != null && t.isAlive())
            		t.interrupt();
            	System.exit(0);
            }
        });
	   
	    setVisible(true);
	   
	    boolean cl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		if(cl)
		{
			JOptionPane.showMessageDialog(null, "IL BLOCCO MAIUSCOLE(TASTO CAPS LOCK) DELLA TUA TASTIERA RISULTA ATTIVATO. PREMI OK E POI IL TASTO CAPS LOCK");
			forma.CAPS_LOCK = true;
		}
		forma.cubo27.messaggio.mostra_caps_lock();	
	    
	    t = new Thread(new Runnable() 
	    {
	    	
	    	@Override 
	    	public void run() 
	    	{	
	    		//f d da tastiera
	    		String str_solve ="UUFUUFLLFUUURRRRRRFFRFFDFFDRRBDDBDDBLLDLLDLLDLBBUBBUBB";
	    		//String str_solve ="UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB";
	    		String ret =  Search.solution(str_solve, 21, 5, false);	
	    		if(!ret.startsWith("Error"))
	    			lc.attiva_solve();
	    		
	    		
	    	}
	    });
	    t.start();
	   
	    //forma.requestFocusInWindow();
	    
	    
	    
	    //System.out.println("MAIN: "+this.getBounds().getHeight()+" "+Constants.H_TOT);
	    
	   
	    
	    
	}
	
	public void mostra_specchio() 
	{
		
		if(specchio != null)
		{
			
			specchio.set_cub_centrale_difronte(forma.cubo27.FRONTE);
			specchio.set_cub_centrale_alto(forma.cubo27.ALTO);			
			forma.salva_cubo();			
			specchio.aggiorna("salvato");
			
		}
		
		if(forma != null)
		{
			forma.requestFocusInWindow();	
		}
	}
	
	public void aggiorna_specchio() 
	{
		if(specchio != null)
		{
			specchio.aggiorna("salvato1");
		}
		
	}
	
	public static void main(String[] args)
	{
		/*
		System.out.println("0 << 2 "+ (0<<2));
		System.out.println("1 << 2 "+ (1<<2));
		System.out.println("2 << 2 "+ (2<<2));
		System.out.println("3 << 2 "+ (3<<2));
		*/
		//System.out.println("a "+ (int)'a' + " A "+(int)'A');
		//System.out.println('a' < 'A');
		//new MainApp();
		System.out.println("pippo");
	}

	
}