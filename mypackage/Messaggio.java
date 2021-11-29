package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.sun.management.OperatingSystemMXBean;

import java.util.Timer;
import java.util.TimerTask;

public class Messaggio extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5495422362573750646L;
	Controllore_campi_classi controllore;
	JButton button0 = null;
	JButton button1 = null;
	JButton button2 = null;
	JButton button3 = null;
	JButton button4 = null;
	JLabel un_msg = new JLabel();
	Timer timer;
	Font font = new Font("Monospaced",Font.PLAIN,12);
	public Messaggio() 
	{
		
		super();
		setLayout(new BorderLayout());
		//setOpaque(true);
		setBackground(Constants.SFONDO);
		
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		p.setOpaque(true);
		p.setBackground(new Color(102,102,204));
		//setPreferredSize(new Dimension(1000,30));
		LineBorder bordo = new LineBorder(Constants.COLORE_BORDO);        
	    TitledBorder title = BorderFactory.createTitledBorder(bordo," Info ");
	    title.setTitleFont(new Font("Monospaced",Font.PLAIN,12));
	    title.setTitleColor(Constants.COLORE_BORDO);
	    setBorder(title);
		//setBorder(new LineBorder(new Color(102,204,204)));
		controllore = new Controllore_campi_classi(this);
		
		button0 = new JButton("");	
		button0.setFont(font);
		String s1 = "MOSSE/MOV.: "+String.format("%4d", 0)+"/"+String.format("%4d", 0);
		button1 = new JButton(s1);	
		button1.setFont(font);
		button2 = new JButton("");
		button2.setFont(font);
		button3 = new JButton("");
		button3.setFont(font);
		button4 = new JButton("Caps Lock");
		button4.setFont(font);
		//un_msg.setOpaque(true);
		un_msg.setForeground(Color.LIGHT_GRAY);
		un_msg.setFont(new Font("Monospaced",Font.PLAIN,14));
		//un_msg.setBorder(new LineBorder(new Color(102,204,204)));
		un_msg.setPreferredSize(new Dimension(600,22));
		
		setCursor(Constants.NO_CURSOR);
		
		
		//Component xx = Box.createHorizontalGlue();
		//xx.setBackground(new Color(202,102,204));
		//p.add(xx);
		p.add(Box.createHorizontalStrut(5));
		p.add(un_msg);		
		p.add(Box.createHorizontalStrut(10));
		p.add(button1);
		p.add(Box.createHorizontalStrut(10));
		p.add(button0);
		p.add(Box.createHorizontalStrut(10));
		p.add(button2);
		p.add(Box.createHorizontalStrut(10));
		p.add(button3);
		p.add(Box.createHorizontalStrut(10));
		p.add(button4);
		p.add(Box.createHorizontalStrut(10));
		
		add(p,BorderLayout.CENTER);
		timer = new Timer();
		crea_timer();
	}
	private void crea_timer() 
	{
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			  @Override
			  public void run() 
			  {
				  mostra_memoria();
				  mostra_cpu();				  
					
			  }			
		}, 1000, 5*1000); // 5 secondi a partire dopo 1 secondo
		
	}
	public void mostra_caps_lock() 
	{
		boolean cl = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		if(cl)
			button4.setBackground(Color.RED);
		else
			button4.setBackground(Color.GREEN);
	}
	protected void mostra_cpu() 
	{
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
		long prevUpTime = runtimeMXBean.getUptime();
		long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
		double cpuUsage;
		try
		{
			Thread.sleep(500);
		}
		catch (Exception ignored) { }

		operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		long upTime = runtimeMXBean.getUptime();
		long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
		long elapsedCpu = processCpuTime - prevProcessCpuTime;
		long elapsedTime = upTime - prevUpTime;

		cpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors));
		   //System.out.println("Java CPU: " + cpuUsage);
		String d_val = String.format("%2.1f%n", cpuUsage);
		button3.setText("CPU: "+d_val + " %");
		
	}
	protected void mostra_memoria() 
	{
		int dataSize = 1024 * 1024;
		 
		Runtime runtime = Runtime.getRuntime();
		 
		//   long max_mem = runtime.maxMemory() / dataSize;
		//   long tot_mem = runtime.totalMemory() / dataSize ;
		//   long lib_mem = runtime.freeMemory() / dataSize ;		   
		double use_mem = (runtime.totalMemory() - runtime.freeMemory()) / dataSize;
		String d_val = String.format("%4.0f%n", use_mem);
		button2.setText("MEMORIA: "+d_val + " MB");
		
	}
	public void mostra_valori_controllati(DatiControllo dc) 
	{	
		//System.out.println("mostra_valori_controllati "+dc.valore );
		if(dc.metodo.contains("num_mosse") || dc.metodo.contains("num_movimenti"))
		{
			//MOSSE/MOV.: "+String.format("%4d", 0)+"/"+String.format("%4d", 0);
			if(dc.metodo.contains("num_mosse"))
			{
				String s_val_mosse = String.format("%4d", dc.valore);
				String old = button1.getText();
				String s = old.substring(0,12)+s_val_mosse+"/"+old.substring(17);
				button1.setText(s);
			}
			else
			if(dc.metodo.contains("num_movimenti"))
			{
				String s_val_movimenti = String.format("%4d", dc.valore);
				String old = button1.getText();
				String s = old.substring(0,12)+old.substring(12,16)+"/"+s_val_movimenti;				
				button1.setText(s);
			
			}
		}
		
		if(dc.metodo.contains("size_coda_mosse"))
		{
			String s_val = String.format("%4d", dc.valore);
			button0.setText("IN CODA: "+s_val);
		}
	}
	public void set_da_controllare(Controllabile controllabile) 
	{
		controllabile.addPropertyChangeListener(controllore);
	}
	public void set_messaggio(String un_msg)
	{
		this.un_msg.setForeground(Color.LIGHT_GRAY);
		this.un_msg.setText(un_msg);
		
	}
	public void set_messaggio(String un_msg, Color color) 
	{
		set_messaggio(un_msg);
		this.un_msg.setForeground(color);
		
	}	
}