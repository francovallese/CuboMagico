package mypackage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

public class Controllore_campi_classi implements PropertyChangeListener 
{
	Messaggio uc;
	LinkedList<DatiControllo> dati = null;
	public Controllore_campi_classi(Messaggio uc) 
	{
		this.uc = uc;
		dati = new LinkedList<DatiControllo>();
	}
	public void propertyChange(PropertyChangeEvent evt) 
    {
		String nome_campo = evt.getPropertyName();
		Object val = evt.getNewValue();
		//System.out.println("CARICO DATI "+ nome_campo + " " + val);
    	dati.add(new DatiControllo("get_"+nome_campo,val));
    	/*
    	System.out.println("Classe = "+ evt.getSource().getClass().getName());
    	
        System.out.println("Name = " + evt.getPropertyName());
 
        System.out.println("Old Value = " + evt.getOldValue());
 
        System.out.println("New Value = " + evt.getNewValue());
 
        System.out.println("**********************************");
        */
    }

	public void gestione_valori_controllati() 
	{
		//System.out.println("gestione_valori_controllati");
		DatiControllo dc;
		while( (dc = dati.poll()) != null)
		{
			//System.out.println("gestione_valori_controllati" + dc.valore);
			uc.mostra_valori_controllati(dc);
		}
		
	}
}
