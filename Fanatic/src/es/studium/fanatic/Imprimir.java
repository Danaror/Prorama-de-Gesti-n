package es.studium.fanatic;



import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Imprimir extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public Imprimir()
	{
		
	}
	
	
	public void imprimepdf(String cadena)
	{
		// Se obtiene el objeto PrintJob
		PrintJob pjob = this.getToolkit().getPrintJob(this, cadena, null);
		// Se obtiene el objeto graphics sobre el que pintar
		Graphics pg = pjob.getGraphics();
		// Se fija la fuente de caracteres con la que escribir
		pg.setFont(new Font("SansSerif",Font.PLAIN,12));
		// posición con respecto a la parte superior izquierda
		pg.drawString(cadena,20,20);
		// Se finaliza la página
		pg.dispose();
		// Se hace que la impresora termine el trabajo y suelte la página
		pjob.end();
		}
		
		public void windowActivated(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowClosing(WindowEvent we)
		{
		System.exit(0);
		}
		public void windowDeactivated(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowOpened(WindowEvent we) {}


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		
		}
		




