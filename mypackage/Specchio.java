package mypackage;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import render.Material;
import render.Matrix;
import render.RenderJPanel;

public class Specchio extends JPanel //JInternalFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 684392331493716734L;
	Retro retro;
	int LARGHEZZA_SPECCHIO = 560;
	int ALTEZZA_SPECCHIO   = 692;
	
	
	public Specchio()
	{
		
		super();
		
		crea_pannello();
				
		setVisible(true);
	}
	private void crea_pannello() 
	{
		setBackground(Constants.SFONDO); //new Color(102,102,204));
        setPreferredSize(new Dimension(LARGHEZZA_SPECCHIO,ALTEZZA_SPECCHIO));
        LineBorder bordo = new LineBorder(Constants.COLORE_BORDO);        
        TitledBorder title = BorderFactory.createTitledBorder(bordo," Specchio ");
        title.setTitleFont(new Font("Monospaced",Font.PLAIN,12));
        title.setTitleColor(Constants.COLORE_BORDO);
        setBorder(title);
        setLayout(new BorderLayout());
        retro = new Retro();
        add(retro,BorderLayout.CENTER);
        setCursor(Constants.NO_CURSOR);        
	}
	
	
	public void aggiorna(String nome_file) 
	{	
		retro.ANIMA = false;
			
		ObjectInputStream ois;
		try
		{
			ois = new ObjectInputStream(new FileInputStream(nome_file));
			Matrix c0 = (Matrix) ois.readObject();
			
			retro.posiziona_camera(c0);
			
			retro.aggiorna_colori(ois);
			
			ois.close();
		}
		catch(Exception e)
		{

			System.out.println("errore lettura matrice da Specchio:" +e.getMessage());
		}
		
		//System.out.println("passo");
		retro.ANIMA = true;
	}
	public void set_cub_centrale_difronte(int fronte) 
	{
		retro.set_cub_centrale_difronte(fronte); 
		
	}
	public void set_cub_centrale_alto(int alto) 
	{
		retro.set_cub_centrale_alto(alto); 		
	}
	
}
class Retro extends RenderJPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1891961529575176358L;
	Cubo27 cubo27;
	boolean ANIMA = false;
	private int FRONTE;
	private int ALTO;
	
	Retro()
	{
		super.init();
		cubo27 = new Cubo27(this);
		world.add(cubo27);
		start();
	}
	
	@Override
	public void initialize() 
    {
	   
	   //setBgColor(.4,.4,0.8);
		setBgColor(Constants.RGB_SFONDO[0],Constants.RGB_SFONDO[1],Constants.RGB_SFONDO[2]);
		setFL(Constants.LENTE*89.0/100.0); //10.8);
		setFOV(Constants.PROSPETTIVA*89.0/100.0);
		addLight(-0.5,  1, 1       ,.5,.5,.5);
		addLight(0.5,  -1, -1       ,.5,.5,.5);
	   

    }	
	@Override
	public void animate(double time) 
	{
		if(!ANIMA)
			return;
	}
	@Override
	public synchronized void paintComponent(Graphics g) 
	{
		if(!ANIMA)
			return;
		super.paintComponent(g);
	}
	
	public void set_cub_centrale_difronte(int fronte) 
	{
		this.FRONTE = fronte;		
	}
	public void set_cub_centrale_alto(int alto) 
	{
		this.ALTO = alto;		
	}
	public void posiziona_camera(Matrix c0) 
	{		
			Matrix tmp = new Matrix();
			Matrix cam = c0;		
			tmp.copy(cam);
			tmp.rotate(Math.PI, c0.get()[0], c0.get()[1], c0.get()[2]);			
			renderer.setCamera(tmp);
			renderer.rotateView(0,Math.PI/2);			
	}

	public void aggiorna_colori(ObjectInputStream ois) 
	{
		//System.out.println("aggiorna_colori ");
		try
		{
			for(int i=0; i<27;i++)
			{
				//System.out.println("cubo "+ i );
				for(int f=0; f<6;f++)
				{
					Material m = (Material) ois.readObject();
					if(m != null)
					{						
						int dest = f;
						Faccetta fac = cubo27.cubetti[i].faccette[dest];						
						fac.setMaterial(m);						
					}
				}
			}
		}
		catch(Exception e)
		{

			System.out.println("errore lettura materiale da Specchio:" +e.getMessage());
		}
		scambio_colori_effetto_specchio();
		
	}

	private void scambio_colori_effetto_specchio() 
	{
		
		if(ALTO == 14)
		{
			if(FRONTE == 22 || FRONTE == 4)
			{
				boolean flag = true;
				scambio(18,flag);
				scambio(19,flag);
				scambio(20,flag);
				
				scambio(21,9,flag);
				scambio(22,10,flag);
				scambio(23,11,flag);
				
				scambio(24,0,flag);
				scambio(25,1,flag);
				scambio(26,2,flag);		
				
				scambio(15,3,flag);
				scambio(16,4,flag);
				scambio(17,5,flag);		
				
				scambio(6,flag);
				scambio(7,flag);
				scambio(8,flag);
			}
			else //FRONTE == 10 || FRONTE == 16
			{
				boolean flag = false;
				scambio(24,flag);
				scambio(25,flag);
				scambio(26,flag);
				
				scambio(15,21,flag);
				scambio(16,22,flag);
				scambio(17,23,flag);
				
				scambio(6,18,flag);
				scambio(7,19,flag);
				scambio(8,20,flag);
				
				scambio(3,9,flag);
				scambio(4,10,flag);
				scambio(5,11,flag);
				
				scambio(0,flag);
				scambio(1,flag);
				scambio(2,flag);
			}
		}
		else // ALTO =12
		{
			//System.out.println("ALTO: "+ ALTO+" FRONTE: "+FRONTE);
			if(FRONTE == 10 || FRONTE == 16)
			{
				boolean flag = true;
				scambio(6,flag);
				scambio(7,flag);
				scambio(8,flag);
				
				scambio(3,15,flag);
				scambio(4,16,flag);
				scambio(5,17,flag);
				
				scambio(0,24,flag);
				scambio(1,25,flag);
				scambio(2,26,flag);
				
				scambio(9,21,flag);
				scambio(10,22,flag);
				scambio(11,23,flag);
				
				scambio(18,flag);
				scambio(19,flag);
				scambio(20,flag);
			}
			else //FRONTE == 10 || FRONTE == 16
			{
				boolean flag = false;
				scambio(24,flag);
				scambio(25,flag);
				scambio(26,flag);
				
				scambio(3,9,flag);
				scambio(4,10,flag);
				scambio(5,11,flag);
				
				scambio(6,18,flag);
				scambio(7,19,flag);
				scambio(8,20,flag);
				
				scambio(15,21,flag);
				scambio(16,22,flag);
				scambio(17,23,flag);
				
				
				scambio(0,flag);
				scambio(1,flag);
				scambio(2,flag);
			}
		}
	}
	private void scambio(int i,boolean flag) 
	{
		Material m0 = cubo27.cubetti[i].faccette[0].material;
		Material m1 = cubo27.cubetti[i].faccette[1].material;
		Material m2 = cubo27.cubetti[i].faccette[2].material;
		Material m3 = cubo27.cubetti[i].faccette[3].material;
		Material m4 = cubo27.cubetti[i].faccette[4].material;
		Material m5 = cubo27.cubetti[i].faccette[5].material;
		
		cubo27.cubetti[i].faccette[1].setMaterial(m1); 
		cubo27.cubetti[i].faccette[3].setMaterial(m3); 
		if(flag)
		{
			cubo27.cubetti[i].faccette[0].setMaterial(m5); 		
			cubo27.cubetti[i].faccette[2].setMaterial(m4);		
			cubo27.cubetti[i].faccette[4].setMaterial(m2); 
			cubo27.cubetti[i].faccette[5].setMaterial(m0);
		}
		else
		{
			cubo27.cubetti[i].faccette[0].setMaterial(m4); 		 
			cubo27.cubetti[i].faccette[2].setMaterial(m5);		
			cubo27.cubetti[i].faccette[4].setMaterial(m0); 
			cubo27.cubetti[i].faccette[5].setMaterial(m2);
		}
	}
	
	private void scambio(int i, int j,boolean flag) 
	{
		Material m0 = cubo27.cubetti[j].faccette[0].material;
		Material m1 = cubo27.cubetti[j].faccette[1].material;
		Material m2 = cubo27.cubetti[j].faccette[2].material;
		Material m3 = cubo27.cubetti[j].faccette[3].material;
		Material m4 = cubo27.cubetti[j].faccette[4].material;
		Material m5 = cubo27.cubetti[j].faccette[5].material;
				
		cubo27.cubetti[j].faccette[1].setMaterial(cubo27.cubetti[i].faccette[1].material); 
		cubo27.cubetti[j].faccette[3].setMaterial(cubo27.cubetti[i].faccette[3].material);
		if(flag)
		{
			cubo27.cubetti[j].faccette[0].setMaterial(cubo27.cubetti[i].faccette[5].material);
			cubo27.cubetti[j].faccette[2].setMaterial(cubo27.cubetti[i].faccette[4].material);
			cubo27.cubetti[j].faccette[4].setMaterial(cubo27.cubetti[i].faccette[2].material);
			cubo27.cubetti[j].faccette[5].setMaterial(cubo27.cubetti[i].faccette[0].material);		
		}
		else
		{
			cubo27.cubetti[j].faccette[0].setMaterial(cubo27.cubetti[i].faccette[4].material);
			cubo27.cubetti[j].faccette[2].setMaterial(cubo27.cubetti[i].faccette[5].material);
			cubo27.cubetti[j].faccette[4].setMaterial(cubo27.cubetti[i].faccette[0].material);
			cubo27.cubetti[j].faccette[5].setMaterial(cubo27.cubetti[i].faccette[2].material);	
		}
		cubo27.cubetti[i].faccette[1].setMaterial(m1);
		cubo27.cubetti[i].faccette[3].setMaterial(m3); 
		if(flag)
		{
			cubo27.cubetti[i].faccette[0].setMaterial(m5); 		 
			cubo27.cubetti[i].faccette[2].setMaterial(m4);		
			cubo27.cubetti[i].faccette[4].setMaterial(m2); 
			cubo27.cubetti[i].faccette[5].setMaterial(m0);
		}
		else
		{
			cubo27.cubetti[i].faccette[0].setMaterial(m4); 		 
			cubo27.cubetti[i].faccette[2].setMaterial(m5);		
			cubo27.cubetti[i].faccette[4].setMaterial(m0); 
			cubo27.cubetti[i].faccette[5].setMaterial(m2);
		}
	}	
	
}