package mypackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



public class Partita 
{
	private static int LARGO = 500;
	private static int ALTO  = 200;
	private static JDialog dialog;
	//private static JLabel riga_dati;
	
	long start = 0L;
	long stop = 0L;
	int num_mosse = 0;
	int num_movimenti = 0;
	String giocatore = "?";
	//long giorni  = 0L;
	//long ore     = 0L;
	//long minuti  = 0L;
	//long secondi = 0L;
	long tempo_trascorso;
	String Nome_file = Constants.FILE_PUNTI;
	
	public Partita(String giocatore) 
	{
		
		this.giocatore = giocatore;
	}
	public void start() 
	{
		start = System.currentTimeMillis();		
	}
	public boolean chiudi(int num_mosse, int num_movimenti) 
	{
		boolean ok = false;
		this.num_mosse     = num_mosse;
		this.num_movimenti = num_movimenti;
		stop = System.currentTimeMillis();
		tempo_trascorso    = stop - start;
		
		int num_lines      = countLineNumberReader(Nome_file);
		Punti_Giocatore [] righe = new Punti_Giocatore[num_lines];
		carica_records(Nome_file,righe);
		aggiorna_records(Nome_file,righe);
		ok = true;
		return ok;
	}
	
	private void aggiorna_records(String fileName, Punti_Giocatore[] righe) 
	{
		int riga_modificabile = -1;
		for(int i = 0; i < righe.length;i++)
		{
			if(righe[i].getNome().equals(giocatore))
			{
				riga_modificabile = i;
				break;
			}
		}
		if(riga_modificabile >= 0)	
		{
			Punti_Giocatore nuova_riga = null;
			if((nuova_riga = record_superato(righe[riga_modificabile])) != null)
			{
				righe[riga_modificabile] = nuova_riga;
				scrivo_file_record(righe,false,null);
				//JOptionPane.showMessageDialog(null, " EUREKA !!! G:"+this.giorni+" H:"+this.ore+ " M: "+this.minuti+" S:"+this.secondi);
				JOptionPane.showMessageDialog(null, " EUREKA !!! T:"+this.tempo_trascorso);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "RECORD NON SUPERATO");
			}
		}
		else
		{
			String sappend = new String(""+giocatore+ "|" +
					   this.num_mosse     + "|" +
					   this.num_movimenti + "|" +
					   this.tempo_trascorso);
			
					   //this.giorni        + "|" +
					   //this.ore           + "|" +
					   //this.minuti        + "|" +
					   //this.secondi);
			scrivo_file_record(righe,true,sappend);
			//JOptionPane.showMessageDialog(null, " (1)EUREKA !!! G:"+this.giorni+" H:"+this.ore+ " M: "+this.minuti+" S:"+this.secondi);	
			JOptionPane.showMessageDialog(null, " (1)EUREKA !!! T:"+this.tempo_trascorso);
			
		}
	}
	private void scrivo_file_record(Punti_Giocatore[] righe, boolean append,String da_appendere) 
	{
		try
	    {
			FileWriter fw = new FileWriter(Nome_file,append);
			BufferedWriter ou = new BufferedWriter(fw);
			if(append)
			{
				if(righe.length > 0)
					ou.newLine();
				ou.write(da_appendere);		        
		        ou.close();
			}
			else
			{
				for(int i = 0; i<righe.length;i++)
				{
					ou.write(righe[i].getNome()+ "|"+ righe[i].getMosse()+"|"+righe[i].getMovimenti()+ "|"+righe[i].getTime());
					if(i < (righe.length-1))
			        ou.newLine();
				}
				ou.close();
			}
	    }
		catch (IOException err)
		{
			JOptionPane.showMessageDialog(null, "ERRORE: " + err.getMessage(), "ERRORE(1)", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	private Punti_Giocatore record_superato(Punti_Giocatore riga_giocatore) 
	{
		Punti_Giocatore ret = null;
		
		long old_tempo_trascorso = riga_giocatore.getTime();
		
		if(tempo_trascorso < old_tempo_trascorso)
		{
			riga_giocatore.setTime(tempo_trascorso);
			riga_giocatore.setMosse(num_mosse);
			riga_giocatore.setMovimenti(num_movimenti);
			ret = riga_giocatore;
		}
		 
		return ret;
	}
	public static void carica_records(String fileName,Punti_Giocatore [] righe) 
	{
		
		BufferedReader in;
		int conta = 0;
		try 
		{
			in = new BufferedReader(new FileReader(fileName));
		
			String s = new String();
			while( (s = in.readLine())  != null)
			{
				righe[conta++] = new Punti_Giocatore(s.trim());
			}			
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	public static int countLineNumberReader(String fileName) 
	{

	      File file = new File(fileName);

	      int lines = 0;

	      try (LineNumberReader lnr = new LineNumberReader(new FileReader(file))) {

	          while (lnr.readLine() != null) ;

	          lines = lnr.getLineNumber();

	      } catch (IOException e) {
	          e.printStackTrace();
	      }

	      return lines;

	}
	public static void classifica() 
	{
		int num_lines = Partita.countLineNumberReader(Constants.FILE_PUNTI);
		Punti_Giocatore [] righe = new Punti_Giocatore[num_lines];
		Partita.carica_records(Constants.FILE_PUNTI,righe);
		
		Arrays.sort(righe);
		String msg = String.format("%-20.20s   %s      %s    %s      %s", "Nome Giocatrice/ore", "Giorni","Ore","Minuti","Secondi");
        JLabel testata = new JLabel(msg);
        testata.setFont(new Font("Monospaced",Font.PLAIN,14));
		
        JTextArea riga_dati = crea_textarea_classifica(righe);
		
		JScrollPane scroll = new JScrollPane(riga_dati);
		scroll.setPreferredSize(new Dimension(LARGO+30,ALTO));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		riga_dati.setCaretPosition(0);
		
		JPanel pOk = new JPanel();
        JButton bot_ok = new JButton("OK");
        bot_ok.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                esci_classifica();                 
            }
    
        });
        pOk.add(bot_ok);
        
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout()); 
        myPanel.add(testata , BorderLayout.NORTH);
        myPanel.add(scroll , BorderLayout.CENTER);
        myPanel.add(pOk, BorderLayout.SOUTH);
        
        Frame xx = (Frame)SwingUtilities.windowForComponent(myPanel);
		dialog = new JDialog(xx, " LE MIGLIORI PARTITE ",true); //(Frame)MainApp.forma.mainApp
		//dialog.setLocationRelativeTo(MainApp.forma);
		dialog.setLocation(Constants.W_TOT/2 - LARGO / 2, Constants.H_TOT/2 - ALTO / 2);
        dialog.setContentPane(myPanel);
        

        dialog.setUndecorated(true);
        dialog.pack(); 
        dialog.setVisible(true);
        
		
	}
	private static JTextArea crea_textarea_classifica(Punti_Giocatore[] righe) 
	{
		
        JTextArea riga_dati = new JTextArea(1,righe.length);
		riga_dati.setFont(new Font("Monospaced",Font.PLAIN,14));
		for(int i= 0; i< righe.length;i++)
		{
			Punti_Giocatore pg = righe[i];
			long time = pg.getTime();
			long secondInMillis = 1000;
			long minuteInMillis = secondInMillis * 60;
			long hourInMillis = minuteInMillis * 60;
			long dayInMillis = hourInMillis * 24;
			
			long giorni = time / dayInMillis;
			time = time % dayInMillis;
			long ore = time / hourInMillis;
			time = time % hourInMillis;
			long minuti = time / minuteInMillis;
			time = time % minuteInMillis;
			long secondi = time / secondInMillis; 
			String msg = String.format("%-20.20s      %3d       %2d        %2d           %2d", pg.getNome(), giorni,ore,minuti,secondi);
			riga_dati.append(msg);
			riga_dati.append("\n");
		}
		if(righe.length == 0)
			riga_dati.append("LISTA GIOCATORI VUOTA...");
		return riga_dati;
	}
	protected static void esci_classifica() 
	{
		if(dialog != null)
			dialog.dispose();
		
	}
	public void mostra_vecchi_punti(String giocatore) 
	{
		int num_lines = countLineNumberReader(Constants.FILE_PUNTI);
		Punti_Giocatore [] righe = new Punti_Giocatore[num_lines];
		carica_records(Constants.FILE_PUNTI,righe);
		int num_riga_giocatore = -1;
		for(int i= 0; i< righe.length;i++)
		{
			if(righe[i].getNome().equals(giocatore))
			{
				num_riga_giocatore = i;
				break;
				
			}
		}
		if(num_riga_giocatore >= 0)
		{
			
			
			long mosse      = righe[num_riga_giocatore].getMosse();
			long movimenti  = righe[num_riga_giocatore].getMovimenti();
			long old_tempo  = righe[num_riga_giocatore].getTime();
			
			
			
			//String giorni     = campi[3].trim();
			//String ore        = campi[4].trim();
			//String minuti     = campi[5].trim();
			//String secondi    = campi[6].trim();
			String titolo     = "VECCHIO RECORD DI "+giocatore;
			Object[] message = 
			{
					"Giocatore : "+giocatore,  
					"Mosse     : "+mosse,   
					"Movimenti : "+movimenti,  
					"Tempo     : "+old_tempo,
					/*
					"Giorni    : "+giorni,   
					"Ore       : "+ore,   
					"Minuti    : "+minuti,   
					"Secondi   : "+secondi, 
					*/  
			};
			
			JOptionPane.showMessageDialog(MainApp.forma, message, titolo, JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(MainApp.forma, "BENVENUTA/O "+giocatore);
		
	}
	
	public String get_time() 
	{		
		
		long time = System.currentTimeMillis() - start;
		long secondInMillis = 1000;
		long minuteInMillis = secondInMillis * 60;
		long hourInMillis = minuteInMillis * 60;
		long dayInMillis = hourInMillis * 24;
		
		@SuppressWarnings("unused")
		long giorni = time / dayInMillis;
		time = time % dayInMillis;
		long ore = time / hourInMillis;
		time = time % hourInMillis;
		long minuti = time / minuteInMillis;
		time = time % minuteInMillis;
		long secondi = time / secondInMillis; 
		return String.format("%02d:%02d:%02d", ore,minuti,secondi);
		
	}
}
class Punti_Giocatore implements Comparable<Punti_Giocatore>
{
	private String nome;
	private long mosse;
	private long movimenti;
	private long time;
	public Punti_Giocatore(String punti_pipe)
	{
		String campi[] = punti_pipe.split("[|]",0);
		nome       = campi[0].trim();
		mosse      = Long.parseLong(campi[1].trim());
		movimenti  = Long.parseLong(campi[2].trim());
		time       = Long.parseLong(campi[3].trim());
	}   

	@Override
	public int compareTo(Punti_Giocatore o) 
	{	
		return Long.compare(time, o.time);
	}
	
	public String getNome()
	{
		return nome;
	}
	public void setNome(String un_nome)
	{
		nome = un_nome;
	}
	
	public long getMosse()
	{
		return mosse;
	}
	public void setMosse(long le_mosse)
	{
		mosse = le_mosse;
	}
	
	public long getMovimenti()
	{
		return movimenti;
	}
	public void setMovimenti(long i_movimenti)
	{
		movimenti = i_movimenti;
	}
	
	public long getTime()
	{
		return time;
	}
	public void setTime(long un_time)
	{
		time = un_time;
	}
	public String toString() 
	{
	    return String.format("(%s, %d)", nome, time);
	}
}
