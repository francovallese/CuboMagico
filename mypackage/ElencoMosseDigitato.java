package mypackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class ElencoMosseDigitato implements ActionListener
{
	JTextField jtf1;
	JTextField jtf;
	boolean continua_lista;
	JDialog jd;
	DocumentListener DL;
	DocumentListener DL1;
	char apice = '\'';
	JRadioButton ITA = null;
	JRadioButton WCA = null;
	
	public ElencoMosseDigitato(Cubo27 cubo27) 
	{
		JPanel jp = crea_pannello();
		creaJDialog(jp,cubo27);
		jd.setVisible(true);
        if(continua_lista) 
        {
        	if(jtf.getText().length() > 0)
        	{
        		for(int i = 0; i<jtf.getText().length(); i++)
        		{
        			char mossa = jtf.getText().charAt(i);
        			cubo27.gestione_faccia_selezionata_da_lista_mosse(mossa,true);        			
        		}
        	}
        }		
	}

	private void creaJDialog(JPanel jp, Cubo27 cubo27) 
	{
		 Object[] message = 
	            {
	                jp
	            	
	               
	            };
			JOptionPane optionPane = new JOptionPane(
	                "",                
	                JOptionPane.QUESTION_MESSAGE,
	                JOptionPane.YES_NO_OPTION,
	                null,message); 
			
			jd = new JDialog(cubo27.forma.mainApp,"ELENCO MOSSE",true);
			jd.setContentPane(optionPane);
			
			optionPane.addPropertyChangeListener(
	                new PropertyChangeListener() {
	                    @Override
	                    public void propertyChange(PropertyChangeEvent e) 
	                    {
	                        String prop = e.getPropertyName();
	                        
	                        if (jd.isVisible() 
	                         && (e.getSource() == optionPane)
	                         && (prop.equals(JOptionPane.VALUE_PROPERTY))) 
	                        {
	                            verifica_dati_lista();                            
	                        }
	                        
	                    }
	                });
	        
	        jd.pack();
	        
	}

	private JPanel crea_pannello() 
	{
		JPanel ret = new JPanel();
        ret.setPreferredSize(new Dimension(950,200));
        ret.setLayout(new BoxLayout(ret,BoxLayout.Y_AXIS));
        ret.add(Box.createVerticalStrut(5));
        
        JPanel riga = new JPanel();
        riga.setLayout(new BoxLayout(riga,BoxLayout.X_AXIS));
        JLabel etic = new JLabel("ABDSFR");
        etic.setFont(new Font("Monospaced",Font.BOLD,14));
        riga.add(etic);
        riga.add(Box.createHorizontalStrut(10));
        jtf = new JTextField();
        jtf.setEditable(true);
		
        jtf.addKeyListener(new KeyAdapter() 
        {
        	public void keyTyped(KeyEvent e) {
        	  char c = e.getKeyChar();
        	  if (c == 'a' || c == 'A' || c == 'b' || c == 'B' || c == 'd' || c == 'D' || c == 's' || c == 'S' ||  c == 'f' || c == 'F' || c == 'r' || c == 'R'  ) 
        	  {
        	     
        	  }
        	  else
        		  e.consume();  // ignore event
        	}
        });
        DL = new DocumentListener() 
        {
        	 public void changedUpdate(DocumentEvent e) {
        		    warn();
        		  }
        		  public void removeUpdate(DocumentEvent e) {
        		    warn();
        		  }
        		  public void insertUpdate(DocumentEvent e) {
        		    warn();
        		  }

        		  public void warn() {
        			  verifica_dati_lista();      
        		  }
        };
        jtf.getDocument().addDocumentListener(DL);
        riga.add(jtf);
        ITA = new JRadioButton("ITA",true);
		ITA.addActionListener(this);
		riga.add(ITA);
        
        ret.add(riga);
        ret.add(Box.createVerticalStrut(5));  
        
        
        JPanel riga1 = new JPanel();
        riga1.setLayout(new BoxLayout(riga1,BoxLayout.X_AXIS));
        JLabel etic1 = new JLabel("UDRLFB");
        etic1.setFont(new Font("Monospaced",Font.BOLD,14));
        riga1.add(etic1);
        riga1.add(Box.createHorizontalStrut(10));
        jtf1 = new JTextField();
        jtf1.setEditable(false);
        jtf1.addKeyListener(new KeyAdapter() 
        {
        	public void keyTyped(KeyEvent e) {
        	  char c = e.getKeyChar();
        	  if (c == 'U' || c == 'D' || c == 'R' || c == 'L' || c == 'F' || c == 'B' || c == apice  ) 
        	  {
        	     
        	  }
        	  else
        		  e.consume();  // ignore event
        	}
        });
        DL1 = new DocumentListener() 
        {
        	 public void changedUpdate(DocumentEvent e) {
        		    warn();
        		  }
        		  public void removeUpdate(DocumentEvent e) {
        		    warn();
        		  }
        		  public void insertUpdate(DocumentEvent e) {
        		    warn();
        		  }

        		  public void warn() {
        			  verifica_dati_lista();      
        		  }
        };
        
        jtf1.getDocument().addDocumentListener(DL1);
        riga1.add(jtf1);
        WCA = new JRadioButton("WCA",false);
		WCA.addActionListener(this);
        riga1.add(WCA);
		
		ButtonGroup scelta = new ButtonGroup();
		scelta.add(ITA);
		scelta.add(WCA);
		
        
        ret.add(riga1);
        ret.add(Box.createVerticalStrut(5)); 
        JPanel riga2 = new JPanel();
        riga2.setLayout(new BoxLayout(riga2,BoxLayout.X_AXIS));
        JButton bot_ok = new JButton("OK");
        bot_ok.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	continua_lista = true; 
                if(jd != null)
                    jd.dispose();                 
            }
    
        });
        JButton bot_annulla = new JButton("ANNULLA");
        bot_annulla.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                continua_lista = false; 
                if(jd != null)
                    jd.dispose(); 
                
            }
    
        });
        riga2.add(bot_ok);
        riga2.add(Box.createHorizontalStrut(10));
        riga2.add(bot_annulla);
        
        ret.add(riga2);
        ret.add(Box.createVerticalStrut(5));  

		return ret;
	}
	
	protected void verifica_dati_lista() 
	{
		if(jtf.isEditable())
		{
			jtf.getDocument().removeDocumentListener(DL);
			jtf1.getDocument().removeDocumentListener(DL1);
			scrivi_jtf1();
		}
		else
		{
			jtf.getDocument().removeDocumentListener(DL);
			jtf1.getDocument().removeDocumentListener(DL1);
			scrivi_jtf();
		}		
	}
	
	private void scrivi_jtf() 
	{
		jtf.setText("");
		for(int i = 0; i<jtf1.getText().length(); i++)
		{
			String s_mossa = "";
			char mossa = jtf1.getText().charAt(i);
			if(mossa == apice)
				continue;
			boolean test_apice = false;
			if(i < jtf1.getText().length()-1)
			{
				char lettura_apice = jtf1.getText().charAt(i+1);
				if(lettura_apice == apice)
					test_apice = true;
			}
			if(mossa == 'U')
			{
				s_mossa = ""+'a';
				if(test_apice)
					s_mossa = ""+'A';
			}
			
			else
			if(mossa == 'D')
			{
				s_mossa = ""+'B';
				if(test_apice)
					s_mossa = ""+'b';
			}
			
			else
			if(mossa == 'F')
			{
				s_mossa = ""+'f';
				if(test_apice)
					s_mossa = ""+'F';
			}
			
			else
			if(mossa == 'B')
			{
				s_mossa = ""+'R';
				if(test_apice)
					s_mossa = ""+'r';
			}			
			else
			if(mossa == 'R')
			{
				s_mossa = ""+'d';
				if(test_apice)
					s_mossa = ""+'D';
			}			
			else
			if(mossa == 'L')
			{
				s_mossa = ""+'S';  
				if(test_apice)
					s_mossa = ""+'s';
			}
			//UUD'D'RRL'L'FFB'B'
			//RU'RURURU'R'U'RR
			//UFRU'RUULLDDR'BBUF'UUBUUF'DDF'UUBFLBUL
			try 
			{
				jtf.getDocument().insertString(jtf.getText().length(), s_mossa, null);
			} 
			catch (BadLocationException e) 
			{
				System.out.println("Errore 1 " + e.getMessage());
			}
			
		}
		jtf.getDocument().addDocumentListener(DL);
		jtf1.getDocument().addDocumentListener(DL1);
		
	}

	private void scrivi_jtf1() 
	{
		jtf1.setText("");
		for(int i = 0; i<jtf.getText().length(); i++)
		{
			String s_mossa = "";
			char mossa = jtf.getText().charAt(i);
			
			if(mossa == 'a')
				s_mossa = ""+'U';
			else
			if(mossa == 'A')
				s_mossa = ""+'U'+apice;  
			else
			if(mossa == 'b')
				s_mossa = ""+'D'+apice;  
			else
			if(mossa == 'B')
				s_mossa = ""+'D';
			else
			if(mossa == 'f')
				s_mossa = ""+'F';
			else
			if(mossa == 'F')
				s_mossa = ""+'F'+apice; 
			else
			if(mossa == 'r')
				s_mossa = ""+'B'+apice;  
			else
			if(mossa == 'R')
				s_mossa = ""+'B';
			else
			if(mossa == 'd')
				s_mossa = ""+'R';
			else
			if(mossa == 'D')
				s_mossa = ""+'R'+apice;  
			else
			if(mossa == 's')
				s_mossa = ""+'L'+apice;  
			else
			if(mossa == 'S')
				s_mossa = ""+'L';
			
			try 
			{
				jtf1.getDocument().insertString(jtf1.getText().length(), s_mossa, null);
			} 
			catch (BadLocationException e) 
			{
				System.out.println("Errore 2 " + e.getMessage());
			}
			
		}
		jtf.getDocument().addDocumentListener(DL);
		jtf1.getDocument().addDocumentListener(DL1);
	}
	
	@Override()
	public void actionPerformed(ActionEvent e) 
	{
		String tipo_notazione = ((JRadioButton)e.getSource()).getText();	
		if(tipo_notazione.equals("ITA"))
		{
			jtf.setEditable(true);
			jtf1.setEditable(false);
			jtf.requestFocus();
		}
		else
		{
			jtf.setEditable(false);
			jtf1.setEditable(true);
			jtf1.requestFocus();
		}
	}
}
