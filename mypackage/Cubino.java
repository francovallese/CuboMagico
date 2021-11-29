package mypackage;

import perlinK.Geometry;
import perlinK.Material;

public class Cubino extends Geometry
{
	protected static Material colore_cubetto    = Materials.METALLIC_BLACK;
	protected static Material[] materiali;	
	double PI = Math.PI;
	double _45 =PI/4.0;
	double _90 =PI/2.0;
	//double lato = Cubo27.k;
	Faccetta [] faccette = new Faccetta[6];
	int indice_cubetto;
	int dettaglio = 8; 
	
	public Cubino(int indice) 
	{
		super();
		/*
		double[] spec = new double[4];
		colore_cubetto.getSpecular(spec);
		System.out.println(""+spec[0]+ " "+spec[1]+ " "+spec[2]+ " "+spec[3]);
		*/
		name = "C:"+indice;
		creo_materiali();
		this.indice_cubetto = indice;
		double len = 6.0; 
		double raggio = 1.0;
		//if(indice == 13)
		{
			struttura(len,raggio);
			etichette(len,raggio);
			angoli_etichette(len,raggio);
		}		
	}
	
	private void angoli_etichette(double len,double raggio) 
	{
		double [] roty= {0,_90,PI,-_90,0,0}; 
		double [] rotx= {0,0,0,0,_90,-_90}; 
		for(int f = 0;f<6;f++)
		{
			Geometry AngoloF = new Geometry();
			for(int i = 0; i< 4;i++)
			{
				Geometry Angolo = new Geometry();
				double[] XA= 
{1*len,0.750*len,0.7835961998958187*len,0.8501141009169892*len,0.913606797749979*len, 0.9664026096753472*len,0.9854226065180615*len,0.9970753362380551*len,1*len};
				double[] YA= 
{1*len,1*len,0.9970753362380551*len,0.9854226065180615*len,0.9664026096753472*len,0.913606797749979*len ,0.8501141009169892*len,0.7835961998958187*len,0.750*len};

				Angolo.polygon(XA, YA);
				//double Angoloscala = len;
				//Angolo.matrix.scale(Angoloscala,Angoloscala,0);
				Angolo.matrix.rotateZ((double)i*_90);	
				Angolo.setMaterial(Materials.METALLIC_BLACK);				
				AngoloF.add(Angolo);
			}
			AngoloF.matrix.rotateX(roty[f]);
			AngoloF.matrix.rotateY(rotx[f]);
			AngoloF.matrix.translate(0, 0, len+raggio);
			add(AngoloF);
		}
		
	}

	private void etichette(double len,double raggio) 
	{
		double [] roty= {0,_90,PI,-_90,0,0}; 
		double [] rotx= {0,0,0,0,_90,-_90}; 
		for(int f = 0;f<6;f++)
		{
			Faccetta fac = new Faccetta(f,indice_cubetto,len);
			//fac.matrix.scale(1+0.1,1+0.1,0);
			
			fac.matrix.rotateX(roty[f]);
			fac.matrix.rotateY(rotx[f]);
			fac.matrix.translate(0, 0, len+raggio); //*1.200); //1.199);
			
			//trucco(fac,f,indice_cubetto);
			
			
			add(fac);	
			
			faccette[f]=fac;	
		}
	}

	@SuppressWarnings("unused")
	private void trucco(Faccetta fac, int f, int indice_cubetto2) 
	{
		
		if(f== 0)
			fac.matrix.scale(1+0.0099,1+0.015,0); // BLU	
		/*
		if(f== 2)
			fac.matrix.scale(1+0.0099,1+0.0099,0); // VERDE		
		*/	
		if(f== 4) // && (indice_cubetto2 == 6 || indice_cubetto2 == 7 || indice_cubetto2 == 8 || indice_cubetto2 == 15 || indice_cubetto2 == 24))
			
			fac.matrix.scale(1+0.0099,1+0.015,0); // rosso
		/*
		if(f== 5)
			fac.matrix.scale(1+0.0099,1+0.0099,0); // ARANCIO	
			*/	
	}

	private void struttura(double len,double raggio) 
	{
		int risoluzione = 8; 
		coperchi_orizzontali(risoluzione,len);
		lati_verticali(risoluzione,len,raggio);
			
	}
	
	

	private void lati_verticali(int risoluzione, double len,double raggio) 
	{
		double [] rotaX = {_90,-_90,_90,-_90};
		double [] rotaY = {0,0,PI,PI};
		for(int n = 0;n < 4;n++)
		{
			Geometry g0 = new Geometry().tube4(risoluzione,raggio);
			g0.setMaterial(colore_cubetto);
			g0.matrix.scale(1, len, 1); // g0.matrix.scale(1, len*1.01, 1);
			g0.matrix.rotateX(rotaX[n]);
			g0.matrix.rotateY(rotaY[n]);
			g0.matrix.translate(len,len, 0);
			add(g0);
		}
	}

	private void coperchi_orizzontali(int risoluzione, double len) 
	{
		double [] traslay = {len,-len};
		for(int m = 0; m <2;m++)
		for(int n = 0;n < 4;n++)
		{
			
			Geometry g = new Geometry().pill4(risoluzione,len,1,1);//cylinder(50); //.gearDisk(15); //.bezeledCube(.2);	
			
			g.setMaterial(colore_cubetto);
			g.matrix.rotateY(_90*n);			
			g.matrix.translate(len, traslay[m], 0);
			g.matrix.rotateX(PI*m);
			add(g);			
		}		
	}
	protected static void creo_materiali() 
	{
		materiali = new Material[6];
		
		materiali[0] = Materials.METALLIC_BLUE;
		
		materiali[1] = Materials.METALLIC_YELLOW;
		
		materiali[2] = Materials.METALLIC_GREEN;
		
		materiali[3] = Materials.METALLIC_WHITE;
		
		materiali[4] = Materials.METALLIC_RED;
		
		materiali[5] = Materials.METALLIC_ORANGE;	
		
	}
}