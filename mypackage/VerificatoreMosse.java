package mypackage;

import java.awt.event.KeyEvent;

public class VerificatoreMosse 
{
	private int FRONTE;
	private int DESTRA;
	private int ALTO;
	@SuppressWarnings("unused")
	private int BASSO;
	@SuppressWarnings("unused")
	private int RETRO;
	@SuppressWarnings("unused")
	private int SINISTRA;
	public VerificatoreMosse() 
	{
		
	}
	public void carica_parametri(int FRONTE, int DESTRA, int ALTO, int BASSO, int RETRO, int SINISTRA)
	{
		this.FRONTE   = FRONTE;
		this.DESTRA   = DESTRA;
		this.ALTO     = ALTO;
		this.BASSO    = BASSO; 
		this.RETRO    = RETRO; 
		this.SINISTRA = SINISTRA;
	}
	public char trova_mossa_centrale(int cub,boolean SHIFT) 
	{
		char c = '?';
		if(cub == FRONTE)
		{
			c = 'f';
			if(SHIFT)
				c='F';
		}
		else
		if(cub == DESTRA)
		{			
			c = 'd';			
			if(SHIFT)
				c='D';				
		}
		else
		if(cub == ALTO)
		{
			c = 'a';
			if(SHIFT)
				c='A';
		}		
		return c;
	}
	public char trova_contro_mossa(char una_mossa)
	{
		char contro_mossa = '?';
		switch(una_mossa)
		{
			case 'a':
				contro_mossa = 'A';
				break;
			case 'A':
				contro_mossa = 'a';
				break;
			case 'b':
				contro_mossa = 'B';
				break;
			case 'B':
				contro_mossa = 'b';
				break;
			case 'f':
				contro_mossa = 'F';
				break;
			case 'F':
				contro_mossa = 'f';
				break;
			case 'r':
				contro_mossa = 'R';
				break;
			case 'R':
				contro_mossa = 'r';
				break;
			case 'd':
				contro_mossa = 'D';
				break;
			case 'D':
				contro_mossa = 'd';
				break;
			case 's':
				contro_mossa = 'S';
				break;
			case 'S':
				contro_mossa = 's';
				break;
		}
		return contro_mossa;
	}
	public int trova_contro_movimento(int una_freccia) 
	{
		
	    int contro_movimento = 0;
		switch(una_freccia)
		{
			case KeyEvent.VK_LEFT:
				contro_movimento = KeyEvent.VK_RIGHT;
				break;
			case KeyEvent.VK_RIGHT:
				contro_movimento = KeyEvent.VK_LEFT;
				break;
			case KeyEvent.VK_UP:
				contro_movimento = KeyEvent.VK_DOWN;
				break;
			case KeyEvent.VK_DOWN:
				contro_movimento = KeyEvent.VK_UP;
				break;
		}
		return contro_movimento;
	}
	public char trova_mossa_laterale(int nc, int nf, boolean SHIFT) 
	{
		char c = '?';
		//System.out.println("trova_mossa_laterale cubetto " + nc +" faccia " + nf+ " SHIFT "+ SHIFT);
		if(ALTO == 14)
		{
			if(FRONTE == 22)
			{
				if(nc == 5)
					c='r';
				else
				if(nc == 11)
					c='s';
				else
				if(nc == 15 || nc == 21 )
					c='b';				
			}
			else
			if(FRONTE == 16)
			{
				if(nc == 11)
					c='r';
				else
				if(nc == 23)
					c='s';
				else
				if(nc == 21 || nc == 3 )
					c='b';				
			}
			else
			if(FRONTE == 4)
			{
				if(nc == 23)
					c='r';
				else
				if(nc == 17)
					c='s';
				else
				if(nc == 3 || nc == 9 )
					c='b';				
			}
			else
			if(FRONTE == 10)
			{
				if(nc == 17)
					c='r';
				else
				if(nc == 5)
					c='s';				
				else
				if(nc == 9 || nc == 21 )
					c='b';				
			}
		
		}
		else
		if(ALTO == 12)
		{
			if(FRONTE == 10)
			{
				if(nc == 15)
					c='r';
				else
				if(nc == 21)
					c='s';
				else
				if(nc == 5 || nc == 11 )
					c='b';				
			}
			else
			if(FRONTE == 22)
			{
				if(nc == 3)
					c='r';
				else
				if(nc == 15)
					c='s';
				else
				if(nc == 11 || nc == 23 )
					c='b';				
			}
			else
			if(FRONTE == 16)
			{
				if(nc == 9)
					c='r';
				else
				if(nc == 3)
					c='s';
				else
				if(nc == 23 || nc == 17 )
					c='b';
			}
			else
			if(FRONTE == 4)
			{
				if(nc == 21)
					c='r';
				else
				if(nc == 9)
					c='s';
				else
				if(nc == 17 || nc == 5 )
					c='b';				
			}
		}
		if(SHIFT)
			c = trova_contro_mossa(c);
		return c;
	}
}
