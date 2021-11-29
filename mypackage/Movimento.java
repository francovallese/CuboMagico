package mypackage;

public class Movimento implements Accodabile
{
	public int movimento; //freccia da tastiera su giu destra sinistra
	boolean cancellazione;
	
	public Movimento(int movimento,boolean cancellazione) 
	{
		this.movimento     = movimento;
		this.cancellazione = cancellazione;		
	}
}
