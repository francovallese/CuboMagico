package mypackage;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class Lanciatore_comandi_Menu extends JMenuBar implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5666137149672648834L;
	ArrayList<RicevitoreComandi>  rc = null;
	private JMenuItem solve;
	JMenu menu3;
	JTextField ta;
	
	public Lanciatore_comandi_Menu( ) 
	{
		//super();
		
		crea_menu();
		setBorderPainted(true);
		
		
	}
	
	public String is_operativo()
	{
		String ret = "";
		Component[] xx = getComponents();
		//System.out.println("COLONNE: "+ xx.length);
		for(int c = 0; c < xx.length; c++)
		{
			if(!(xx[c] instanceof JMenu))
				continue;
			JMenu yy = (JMenu)xx[c];
			
			boolean attivo = yy.isSelected();
			if(attivo)
			{
				ret = "COLONNA";
				int nr = yy.getItemCount();				
				//System.out.println("COLONNA MENU: "+ yy.getActionCommand() + " CON ELEM:" + nr);
				for(int r=0;r<nr;r++)
				{
					JMenuItem zz = yy.getItem(r);
					if(zz != null && zz.getActionCommand().length() > 0 && zz.isArmed())
						ret = zz.getActionCommand();
						//System.out.println("COMANDO: "+ zz.getActionCommand());
				}
				
			}
			
			/*
			int nr = yy.getItemCount();
			System.out.println("RIGHE: "+ nr);
			
			for(int r=0;r<nr;r++)
			{
				JMenuItem zz = yy.getItem(r);
				if(zz != null)
					System.out.println("RIGHE: "+ zz.getActionCommand());
			}
			*/
			
			
		}
		return ret;
	}
	private void crea_menu() 
	{	
		@SuppressWarnings("unused")
		BufferedImage info_img = null;
		BufferedImage last_img= null;
		BufferedImage lift_img= null;
		BufferedImage edges2_img= null;
		BufferedImage corners2_img= null;
		BufferedImage edges3_img= null;
		BufferedImage corners3_img= null;
		BufferedImage swap_img= null;
		BufferedImage swap_1_img= null;
		BufferedImage swap_2_img= null;
		BufferedImage lift1_img= null;
		try
		{
			info_img     = ImageIO.read(new File("./images/cube3d.jpg")); 
			last_img = ImageIO.read(new File("./images/last.jpg")); 
			lift_img = ImageIO.read(new File("./images/lift.jpg"));
			lift1_img = ImageIO.read(new File("./images/lift1.jpg"));
			edges2_img   = ImageIO.read(new File("./images/edges2.jpg"));
			corners2_img = ImageIO.read(new File("./images/corners2.jpg"));
			edges3_img   = ImageIO.read(new File("./images/edges3.jpg"));
			corners3_img = ImageIO.read(new File("./images/corners3.jpg"));
			
			swap_img= ImageIO.read(new File("./images/swap.jpg"));
			swap_1_img= ImageIO.read(new File("./images/swap_1.jpg"));
			swap_2_img= ImageIO.read(new File("./images/swap_2.jpg"));
		}
		catch(IOException ie)
		{
			info_img = null;
		}
		JMenu menu = new JMenu("FIGURE");
		menu.setMnemonic(KeyEvent.VK_M);
		//menu.setFocusTraversalKeysEnabled(false);
		//menu.setFocusable(false);
		menu.add(crea_riga("Points","Points[3]",null));
		menu.add(new JSeparator());
		menu.add(crea_riga("3 Cubes","3 Cubes[3]",null));
		menu.add(new JSeparator());
		menu.add(crea_riga("Cherry","Cherry[3]",null));
		menu.add(new JSeparator());
		menu.add(crea_riga("Arrows","Arrows[3]",null));
		menu.add(new JSeparator());
		menu.add(crea_riga("6 U","6 U[3]",null));
		menu.add(new JSeparator());
		menu.add(crea_riga("Easy","Easy[2]",null));
		
		
		JMenu menu1 = new JMenu("MOSSE");
		
		menu1.add(crea_riga("List","List",null));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("Last","Last",last_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("Lift","Lift",lift_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("Lift1","Lift1",lift1_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("2 edges","2 edges",edges2_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("2 corners","2 corners",corners2_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("3 corners","3 corners",corners3_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("3 edges","3 edges",edges3_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("swap","swap",swap_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("swap 1","swap 1",swap_1_img));
		menu1.add(new JSeparator());
		menu1.add(crea_riga("swap 2","swap 2",swap_2_img));
		menu1.add(new JSeparator());
		
		JMenu menu2 = new JMenu("Utility");
		
		menu2.add(crea_riga("Delete","Delete",null));
		menu2.add(new JSeparator());
		menu2.add(crea_riga("Shuffle","Nuova partita",null));
		menu2.add(new JSeparator());
		menu2.add(crea_riga("Reset","Reset",null));
		menu2.add(new JSeparator());
		menu2.add(crea_riga("Save","Save",null));
		menu2.add(new JSeparator());
		menu2.add(crea_riga("Load","Load",null));
		menu2.add(new JSeparator());
		solve = crea_riga("Solve","Solve",null);
		solve.setEnabled(false);
		menu2.add(solve);
		menu2.add(new JSeparator());
		menu2.add(crea_riga("Aliasing","Aliasing",null));
		
		
		menu3 = new JMenu("INFO");
		
		menu3.add(crea_riga("Info","Info",null));
		menu3.add(new JSeparator());
		menu3.add(crea_riga("Ranking","Ranking",null));
		menu3.add(new JSeparator());
		menu3.add(crea_riga("Exit","Exit",null));
		//menu3.add(crea_riga("~Aliasing",null));
		
		
		
		
		
		
		
		add(menu);
		add(menu1);
		add(menu2);
		add(menu3);
		
		ta = new JTextField(""); 
		ta.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		ta.setEditable(false);
		ta.setEnabled(true);
		add(ta);
		ta.addFocusListener(new FocusListener() 
		{

			@Override
			public void focusGained(FocusEvent e) 
			{
				if(ta.getText().length() == 0)
				try 
				{ 
					// These coordinates are screen coordinates 
					int xCoord = 350; int yCoord = 250; 
					// Move the cursor 
					Robot robot = new Robot(); 
					robot.mouseMove(xCoord, yCoord); 
					return;
				} 
				catch (AWTException ecc) 
				{ 
					
				} 
				
				MainApp.forma.CopyPaste = true;
				
			}

			@Override
			public void focusLost(FocusEvent e) 
			{
				
				MainApp.forma.requestFocusInWindow();
				MainApp.forma.CopyPaste = false;			
				
			}
			
		});
	}
	public void set_Slider(JSlider velocity,JSlider zoom)
	{
		if(menu3.getItemCount() == 5)
		{		
			
		}
		else
		{
			menu3.remove(5);
			menu3.remove(5);
			
		}
		menu3.add(new JSeparator());
		menu3.add(velocity);		
		menu3.add(zoom);
	}
	private JMenuItem crea_riga(String azione,String stringa_menu,BufferedImage lift_img)
	{
		JMenuItem m = new JMenuItem(stringa_menu);
		if(lift_img != null)
			m.setIcon(new ImageIcon(lift_img));
		m.setActionCommand(azione);
		m.addActionListener(this);
		if(azione.equals("Aliasing"))
			//m.setForeground(Color.RED);
		m.setBackground(Color.RED);
		
		//if(azione.equals("Reset"))
		//m.setAccelerator(KeyStroke.getKeyStroke('T', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMaskEx()));
		//getMenuShortcutKeyMaskEx   per getMenuShortcutKeyMask
		return m;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		int mod = e.getModifiers();
		if(mod == 16) //InputEvent.BUTTON1_MASK)
			lancio_comando(cmd);
		else
		if(mod == 4) // InputEvent.META_MASK)
			scrivi_comando(cmd);
		
	}
	public void lancio_comando(String cmd) 
	{
		//System.out.println("lancio_comando " + cmd);
		if(rc != null)
			for(int r = 0;r<rc.size();r++)
				rc.get(r).set_cmd(cmd);
		
	}
	public void scrivi_comando(String cmd) 
	{
		/*
		if(rc != null)
			for(int r = 0;r<rc.size();r++)
				rc.get(r).scrivi_cmd(cmd);
		*/
		scrivi_cmd(cmd); 
	}
	public void scrivi_cmd(String cmd) 
	{
		if(cmd.equals("Points"))
		{
			ta.setText("Points: " +Forma.registrazione[1]);
		}
    	else
		if(cmd.equals("3 Cubes"))
		{
			ta.setText("3 Cubes: " +Forma.registrazione[2]);
		}
    	else
		if(cmd.equals("Cherry"))
		{
			ta.setText("Cherry: " +Forma.registrazione[3]);
		}
    	else
		if(cmd.equals("Arrows"))
		{
			ta.setText("Arrows: " +Forma.registrazione[4]);
		}
    	else
		if(cmd.equals("6 U"))
		{
			ta.setText("6 U: " +Forma.registrazione[5]);
		}
    	else
		if(cmd.equals("Easy"))
		{
			ta.setText("Easy: " +Forma.registrazione[7]);
		}
		else
    	if(cmd.equals("List"))
		{
			//ta.setText("Last: " +Forma.registrazione[8]);
		}
    	else
    	if(cmd.equals("Last"))
		{
			ta.setText("Last: " +Forma.registrazione[8]);
		}
		else
		if(cmd.equals("Lift"))
		{
			ta.setText("Lift: " +Forma.registrazione[6]);
		}
		else
		if(cmd.equals("Lift1"))
		{
			ta.setText("Lift1: " +Forma.registrazione[17]);
		}
		else
		if(cmd.equals("2 edges"))
		{
			ta.setText("2 edges: " +Forma.registrazione[9]);
		}
		else
		if(cmd.equals("2 corners"))
		{
			ta.setText("2 corners: " +Forma.registrazione[10]);
		}
		else
		if(cmd.equals("3 edges"))
		{                            
			ta.setText("3 edges: " +Forma.registrazione[11]);
		}
		else
		if(cmd.equals("3 corners"))
		{
			ta.setText("3 corners: " +Forma.registrazione[12]);
		}
		else
		if(cmd.equals("swap"))
		{
			ta.setText("swap: " +Forma.registrazione[14]);
		}
		else
		if(cmd.equals("swap 1"))
		{
			ta.setText("swap 1: " +Forma.registrazione[15]);
		}
		else
		if(cmd.equals("swap 2"))
		{
			ta.setText("swap 2: " +Forma.registrazione[16]); 
		}
		
	}
	public void set_ricevitore(RicevitoreComandi un_ricevitore_comandi) 
	{
		if(rc == null)
			rc = new ArrayList<RicevitoreComandi>();
		rc.add(un_ricevitore_comandi);
		
	}
	public void attiva_solve()
	{
		solve.setEnabled(true);
	}	
}
