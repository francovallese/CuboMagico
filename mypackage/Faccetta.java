package mypackage;

import render.Geometry;
import render.Material;

public class Faccetta extends Geometry
{
	double PI = Math.PI;
	double _45 =PI/4.0;
	double _90 =PI/2.0;
	public int numf;
	public int indice;
	int TX;  //TRIANGOLO PER FACCIA IN VISTA
	int TY;
	int TZ;
	private transient  boolean visibile;
	Material color = Materials.METALLIC_BLACK;
	
	
	public Faccetta(int numf,int indice,double len)
	{
		super();
		this.numf = numf;
		this.indice = indice;
		
		for(int i = 0; i< 4;i++)
		{
			Geometry g = new Geometry();
			g.name = "et";			
			double[] X= {0,1,
					1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,         
					0.9970753362380551,
					 0.9854226065180615,
					  0.9664026096753472,				 
					   0.913606797749979,
					  0.8501141009169892,
					 0.7835961998958187,
					0.750,0.700,0.650,0.600,0.550,0.500,0.450,0.400,0.350,0.300,0.250,0.200,0.150,0.100,0.050,
					0};
			double[] Y= {0,0,
					0.050,0.100,0.150,0.200,0.250,0.300,0.350,0.400,0.450,0.500,0.550,0.600,0.650,0.700,0.750,
					0.7835961998958187,	
					 0.8501141009169892,
					   0.913606797749979,
					 0.9664026096753472,
					0.9854226065180615,
					0.9970753362380551,
					1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,1.000,  
					1};
			for(int c = 0; c <X.length;c++)
				X[c] = X[c]*len;
			for(int c = 0; c <Y.length;c++)
				Y[c] = Y[c]*len;
			g.polygon(X, Y);
			//double scala = len;//+0.0075;
			//g.matrix.scale(scala,scala,scala);
			g.matrix.rotateZ((double)i*_90);			
			add(g); 
			
			
		}
		Colore c = new Colore(-1,-1,-1,-1,-1,-1);		
		c.colora_faccina(numf, this, indice);
			
	}
	
	


	
	
	



	public synchronized void set_TX(int  x) 
	{
		TX = x;
		
	}
	public synchronized int  get_TX() 
	{
		return TX;
		
	}
	public synchronized void set_TY(int  y) 
	{
		TY = y;
		
	}
	public synchronized int  get_TY() 
	{
		return TY;
		
	}
	public synchronized void set_TZ(int  z) 
	{
		TZ = z;
		
	}
	public synchronized int  get_TZ() 
	{
		return TZ;
		
	}
	public synchronized void set_visible(boolean b) 
	{
		visibile = b;
		
	}
	public synchronized boolean get_visible() 
	{
		return visibile;
		
	}
}