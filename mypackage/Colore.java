package mypackage;


import render.Material;

public class Colore 
{
	
	int [] VIA = {2,5,8,1,4,7,0,3,6};
	int [] via = {6,3,0,7,4,1,8,5,2};
	int ALTO;
	int BASSO;
	int DESTRA;
	int SINISTRA;
	int FRONTE;
	int RETRO;
	private boolean colora_13 = false;
	public Colore(int ALTO,int BASSO,int DESTRA,int SINISTRA,int FRONTE,int RETRO) 
	{
		this.ALTO     = ALTO;
		this.BASSO    = BASSO;
		this.DESTRA   = DESTRA;
		this.SINISTRA = SINISTRA;
		this.FRONTE   = FRONTE;
		this.RETRO    = RETRO;
			
	}
	private char trova_vera_mossa(char una_mossa) 
	{
		char ret = una_mossa;
		if(ret == 'f' )
		{
			switch(FRONTE)
			{
				case 16:
					ret = 'd';
					break;
				case 10:
					ret = 'S';
					break;
				case 4:
					ret = 'R';
					break;
				
			}
		}
		else
		if( ret == 'r')
		{
			switch(FRONTE)
			{
				case 16:
					ret = 's';
					break;
				case 4:
					ret = 'F';
					break;
				case 10:
					ret = 'D';
					break;
			}
		}
		else
		if(ret == 'F' )
		{
			switch(FRONTE)
			{
				case 16:
					ret = 'D';
					break;
				case 10:
					ret = 's';
					break;
				case 4:
					ret = 'r';
					break;
				
			}
		}
		else
		if(ret == 'R')
		{
			switch(FRONTE)
			{
				case 16:
					ret = 'S';
					break;
				case 4:
					ret = 'f';
					break;
				case 10:
					ret = 'd';
					break;					
			
			}
		}
		else
		if(ret == 'a' )
		{
			switch(ALTO)
			{
				case 12:
					ret = 'A';
					break;
			
			}
		}
		else
		if( ret == 'b')
		{
			switch(ALTO)
			{
				case 12:
					ret = 'B';
					break;
				
			}
		}
		else
		if(ret == 'A' )
		{
			switch(ALTO)
			{
				case 12:
					ret = 'a';
					break;
				
			}
		}
		else
		if(ret == 'B')
		{
			switch(ALTO)
			{
				case 12:
					ret = 'b';
					break;
				
			}
		}
		else
		if(ret == 'd' )
		{
			switch(DESTRA)
			{
				case 4:
					ret = 'R';
					break;
				case 10:
					ret = 'S';
					break;
				case 22:
					ret = 'f';
					break;
			}
			
		}
		else
		if( ret == 's')
		{
			switch(DESTRA)
			{
				case 4:
					ret = 'F';
					break;
				case 10:
					ret = 'D';
					break;
				case 22:
					ret = 'r';
					break;
			}
		}
		else
		if(ret == 'D' )
		{
			switch(DESTRA)
			{
				case 4:
					ret = 'r';
					break;
				case 10:
					ret = 's';
					break;
				case 22:
					ret = 'F';
					break;
			}
		}
		else
		if(ret == 'S')
		{
			switch(DESTRA)
			{
				case 4:
					ret = 'f';
					break;
				case 10:
					ret = 'd';
					break;
				case 22:
					ret = 'R';
					break;
			}
		}
		return ret;
	}
	public Material trova_nuovo_colore_faccetta(int cubetto_mosso,int faccetta, char una_mossa,Material [][] old_material) 
	{
		//System.out.println("cubetto_mosso:"+cubetto_mosso + " faccetta:"+faccetta + " mossa:"+mossa);
		Material mat = Cubino.colore_cubetto;
		char mossa = trova_vera_mossa(una_mossa);
		//System.out.println("entra " + una_mossa + " esce "+mossa);
		if(mossa == 'f' )
		{			
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0: case 2:
					mat = old_material[via[cubetto_mosso]][faccetta];
					break;
				case 1:
					//  2 5 8
					//  1 4 7
					//  0 3 6
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][4];
							break;
						case 3:
							mat = old_material[7][4];
							break;
						case 6:
							mat = old_material[8][4];
							break;
					}					
					break;				
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[8][3];
							break;						
						case 7:
							mat = old_material[5][3];
							break;						
						case 8:
							mat =  old_material[2][3];
							break;
					}					
					break;				
				case 3:
					switch(cubetto_mosso)
					{						
						case 2:
							mat = old_material[0][5];
							break;
						case 5:
							mat = old_material[1][5];
							break;
						case 8:
							mat = old_material[2][5];
							break;						
					}
					break;				
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][1];
							break;
						case 1:
							mat = old_material[3][1];
							break;
						case 2:
							mat = old_material[0][1];
							break;							
					}
					break;
			}
		}
		else
		if(mossa == 'r')
		{			
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0: case 2:
					mat = old_material[via[cubetto_mosso]][faccetta];
					break;
				case 1:
					//  2 5 8
					//  1 4 7
					//  0 3 6
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][4];
							break;
						case 3:
							mat = old_material[7][4];
							break;
						case 6:
							mat = old_material[8][4];
							break;
					}					
					break;				
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[8][3];
							break;						
						case 7:
							mat = old_material[5][3];
							break;						
						case 8:
							mat =  old_material[2][3];
							break;
					}					
					break;				
				case 3:
					switch(cubetto_mosso)
					{						
						case 2:
							mat = old_material[0][5];
							break;
						case 5:
							mat = old_material[1][5];
							break;
						case 8:
							mat = old_material[2][5];
							break;						
					}
					break;				
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][1];
							break;
						case 1:
							mat = old_material[3][1];
							break;
						case 2:
							mat = old_material[0][1];
							break;							
					}
					break;
			}
		}
		else
		if(mossa == 'F' )
		{			
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0: case 2:
					mat = old_material[VIA[cubetto_mosso]][faccetta];
					break;
					
				case 1:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[0][5];
							break;
						case 3:
							mat = old_material[1][5];
							break;
						case 0:
							mat = old_material[2][5];
							break;
						
					}
					
					break;
				
				case 4:
					switch(cubetto_mosso)
					{
						case 8:
							mat =  old_material[6][1];
							break;
						
						case 7:
							mat = old_material[3][1];
							break;						
							
						case 6:
							mat = old_material[0][1];
							break;
						
						
					}
					//mat = old_material[cubetto_mosso][0];
					break;
				
				case 3:
					switch(cubetto_mosso)
					{
						
						case 2:
							mat = old_material[8][4];
							break;
						case 5:
							mat = old_material[7][4];
							break;
						case 8:
							mat = old_material[6][4];
							break;
						
					}
					break;
				
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[2][3];
							break;
						case 1:
							mat = old_material[5][3];
							break;
						case 2:
							mat = old_material[8][3];
							break;
							
					}
					break;
			
				
			}
		}
		else
		if(mossa == 'R')
		{			
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0: case 2:
					mat = old_material[VIA[cubetto_mosso]][faccetta];
					break;
					
				case 1:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[0][5];
							break;
						case 3:
							mat = old_material[1][5];
							break;
						case 0:
							mat = old_material[2][5];
							break;
						
					}
					
					break;
				
				case 4:
					switch(cubetto_mosso)
					{
						case 8:
							mat =  old_material[6][1];
							break;
						
						case 7:
							mat = old_material[3][1];
							break;						
							
						case 6:
							mat = old_material[0][1];
							break;
						
						
					}
					//mat = old_material[cubetto_mosso][0];
					break;
				
				case 3:
					switch(cubetto_mosso)
					{
						
						case 2:
							mat = old_material[8][4];
							break;
						case 5:
							mat = old_material[7][4];
							break;
						case 8:
							mat = old_material[6][4];
							break;
						
					}
					break;
				
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[2][3];
							break;
						case 1:
							mat = old_material[5][3];
							break;
						case 2:
							mat = old_material[8][3];
							break;
							
					}
					break;
			
				
			}
		}
		else
		if(mossa == 'D' || mossa == 'S')
		{
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0:
					switch(cubetto_mosso)
					{
						//  2 5 8
						//  1 4 7
						//  0 3 6
						case 0:
							mat = old_material[2][3];
							break;
						case 1:
							mat = old_material[5][3];
							break;
						case 2:
							mat = old_material[8][3];
							break;
						
					}					
					break;
				case 1:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[0][0];
							break;
						case 3:
							mat = old_material[1][0];
							break;
						case 0:
							mat = old_material[2][0];
							break;
						
					}
					break;
				case 4: case 5:
					mat = old_material[VIA[cubetto_mosso]][faccetta];
					break;
				
				case 3: 
					switch(cubetto_mosso)
					{
						
						case 2:
							mat = old_material[8][2];
							break;
						case 5:
							mat = old_material[7][2];
							break;
						case 8:
							mat = old_material[6][2];
							break;
						
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 8:
							mat = old_material[6][1];
							break;
						case 7:
							mat = old_material[3][1];
							break;
						case 6:
							mat = old_material[0][1];
							break;
							
					}
					break;				
				
				
			}
		}
		else
		if(mossa == 'd' || mossa == 's')
		{
			switch(faccetta)
			{
				//   3
				// 5 0 4 2
				//   1
				case 0:
					switch(cubetto_mosso)
					{
						//  2 5 8
						//  1 4 7
						//  0 3 6
						case 0:
							mat = old_material[6][1];
							break;
						case 1:
							mat = old_material[3][1];
							break;
						case 2:
							mat = old_material[0][1];
							break;
						
					}					
					break;
				case 1:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[8][2];
							break;
						case 3:
							mat = old_material[7][2];
							break;
						case 0:
							mat = old_material[6][2];
							break;
						
					}
					break;
				case 4: case 5:
					mat = old_material[via[cubetto_mosso]][faccetta];
					break;
					
				case 3: 
					switch(cubetto_mosso)
					{
						
						case 2:
							mat = old_material[0][0];
							break;
						case 5:
							mat = old_material[1][0];
							break;
						case 8:
							mat = old_material[2][0];
							break;
						
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 8:
							mat = old_material[2][3];
							break;
						case 7:
							mat = old_material[5][3];
							break;
						case 6:
							mat = old_material[8][3];
							break;
							
					}
					break;				
				
				
			}
		}
		else
		if(mossa == 'A' )
		{
			switch(faccetta)
			{
				
				case 0:
					switch(cubetto_mosso)
					{
						
						case 0:
							mat = old_material[2][5];
							break;
						case 3:
							mat = old_material[1][5];
							break;
						case 6:
							mat = old_material[0][5];
							break;
						
					}
					break;
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[0][0];
							break;
						case 7:
							mat = old_material[3][0];
							break;
						case 8:
							mat = old_material[6][0];
							break;
							
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 2:
							mat = old_material[8][4];
							break;
						case 5:
							mat = old_material[7][4];
							break;
						case 8:
							mat = old_material[6][4];
							break;
						
					}
					
					break;
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[2][2];
							break;
						case 1:
							mat = old_material[5][2];
							break;
						case 2:
							mat = old_material[8][2];
							break;
						
					}
					break;
				case 3: case 1:
					mat = old_material[VIA[cubetto_mosso]][faccetta];
					break;
				
				
			}
		}
		else
		if( mossa == 'B')
		{
			switch(faccetta)
			{
				
				case 0:
					switch(cubetto_mosso)
					{
						
						case 0:
							mat = old_material[2][5];
							break;
						case 3:
							mat = old_material[1][5];
							break;
						case 6:
							mat = old_material[0][5];
							break;
						
					}
					break;
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[0][0];
							break;
						case 7:
							mat = old_material[3][0];
							break;
						case 8:
							mat = old_material[6][0];
							break;
							
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 2:
							mat = old_material[8][4];
							break;
						case 5:
							mat = old_material[7][4];
							break;
						case 8:
							mat = old_material[6][4];
							break;
						
					}
					
					break;
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[2][2];
							break;
						case 1:
							mat = old_material[5][2];
							break;
						case 2:
							mat = old_material[8][2];
							break;
						
					}
					break;
				case 3: case 1:
					mat = old_material[VIA[cubetto_mosso]][faccetta];
					break;
				
				
			}
		}
		else
		if(mossa == 'a' )
		{
			switch(faccetta)
			{
				
				case 0:
					switch(cubetto_mosso)
					{
						
						case 0:
							mat = old_material[6][4];
							break;
						case 3:
							mat = old_material[7][4];
							break;
						case 6:
							mat = old_material[8][4];
							break;
						
					}
					break;
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[8][2];
							break;
						case 7:
							mat = old_material[5][2];
							break;
						case 8:
							mat = old_material[2][2];
							break;
							
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 2:
							mat = old_material[0][5];
							break;
						case 5:
							mat = old_material[1][5];
							break;
						case 8:
							mat = old_material[2][5];
							break;
						
					}
					
					break;
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][0];
							break;
						case 1:
							mat = old_material[3][0];
							break;
						case 2:
							mat = old_material[0][0];
							break;
						
					}
					break;
				case 3: case 1:
					mat = old_material[via[cubetto_mosso]][faccetta];
					break;
					
				
				
			}
		}
		else
		if(mossa == 'b')
		{
			switch(faccetta)
			{
				
				case 0:
					switch(cubetto_mosso)
					{
						
						case 0:
							mat = old_material[6][4];
							break;
						case 3:
							mat = old_material[7][4];
							break;
						case 6:
							mat = old_material[8][4];
							break;
						
					}
					break;
				case 4:
					switch(cubetto_mosso)
					{
						case 6:
							mat = old_material[8][2];
							break;
						case 7:
							mat = old_material[5][2];
							break;
						case 8:
							mat = old_material[2][2];
							break;
							
					}
					break;
				case 2:
					switch(cubetto_mosso)
					{
						case 2:
							mat = old_material[0][5];
							break;
						case 5:
							mat = old_material[1][5];
							break;
						case 8:
							mat = old_material[2][5];
							break;
						
					}
					
					break;
				case 5:
					switch(cubetto_mosso)
					{
						case 0:
							mat = old_material[6][0];
							break;
						case 1:
							mat = old_material[3][0];
							break;
						case 2:
							mat = old_material[0][0];
							break;
						
					}
					break;
				case 3: case 1:
					mat = old_material[via[cubetto_mosso]][faccetta];
					break;
					
				
				
			}
		}
		
		return mat;
	}
	

	private static void set_colore(Faccetta f,Material m )
	{
		f.setMaterial(m);
		
	}
	private  void set_colore_default(Faccetta f,Material m )
	{
		f.setMaterial(m);
		
	}
	public  Material colora_faccina(int numf,Faccetta f,int indice) 
	{
		Material ret = Cubino.colore_cubetto;
		Material sfondo = Cubino.colore_cubetto;
		Material colore = Cubino.materiali[numf];
		switch(indice)
		{
			case 0:
				switch(numf)
				{
					case 1: case 2: case 5:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
						//f.setMaterial(colore);
						//((Faccetta)f).colore = colori[numf];
					break;
					default:
						set_colore_default( f,sfondo);
						//f.setMaterial(sfondo);
						//((Faccetta)f).colore = "NERO";
						break;
				}
				break;
			
			case 1:
				switch(numf)
				{
					case 5: case 2:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore); //
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 2:
				switch(numf)
				{
					case 2: case 3: case 5: 
						ret = colore;
						set_colore( f,colore); //
					break;
					default:
						f.setMaterial(sfondo);
						break;
				}
				break;
			case 3:
				switch(numf)
				{
					case 1: case 2:    // 0 = blue 3 = giallo 4= bianco  5= verde  ????????
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 4:
				switch(numf)
				{
					case 2:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 5:
				switch(numf)
				{
					case 2:  case 3:   
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			
			case 6:
				switch(numf)
				{
					case 1: case 4: case 2:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 7:
				switch(numf)
				{
					case 4: case 2:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 8:
				switch(numf)
				{
					case 3: case 2: case 4:  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
		//--------------------------------
			case 9:
				switch(numf)
				{
					case 1: case 5:  // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 10:
				switch(numf)
				{
					case 5:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 11:
				switch(numf)
				{
					case 3: case 5:  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 12:
				switch(numf)
				{
					 case 1:    // 0 = blue 3 = giallo 4= bianco  5= verde  ????????
						 ret = colore;
						 set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
					
				}
				break;
			case 13:
				if(colora_13 )
				{
					ret = colore;
					set_colore( f,colore); //f.setMaterial(colore); //f.material = sfondo;
				}
				break;
			case 14:
				switch(numf)
				{
					case 3: 
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
				
			
			case 15:
				switch(numf)
				{
					case 1: case 4:   // 0 = blue 3 = giallo 4= bianco  5= verde 
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 16:
				switch(numf)
				{
					case 4:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 17:
				switch(numf)
				{
					case 3: case 4:   
						ret = colore;
						set_colore( f,colore);
						break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
		//------------------------------------------------------------
				
			case 18:
				switch(numf)
				{
					case 0: case 1: case 5:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore); //f.setMaterial(colore);
					break;
					default:
						set_colore_default( f,sfondo); //f.setMaterial(sfondo);
						break;
				}
				break;
			
			case 19:
				switch(numf)
				{
					case 0: case 5:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 20:
				switch(numf)
				{
					case 0: case 3: case 5:  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 21:
				switch(numf)
				{
					case 0: case 1:    // 0 = blue 3 = giallo 4= bianco  5= verde  ????????
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 22:
				switch(numf)
				{
					case 0:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 23:
				switch(numf)
				{
					case 0:  case 3:   
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			
			case 24:
				switch(numf)
				{
					case 0: case 1: case 4:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 25:
				switch(numf)
				{
					case 0: case 4:   // 0 = blue 3 = giallo 4= bianco  5= verde  
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
			case 26:
				switch(numf)
				{
					case 0: case 3: case 4:   
						ret = colore;
						set_colore( f,colore);
					break;
					default:
						set_colore_default( f,sfondo);
						break;
				}
				break;
				
			default:
				set_colore_default( f,sfondo);
				break;	
		}
		return ret;
	}
}
