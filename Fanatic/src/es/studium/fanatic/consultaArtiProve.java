package es.studium.fanatic;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class consultaArtiProve implements WindowListener, ActionListener {
	//atributos
		//creamos ventana y sus componentes
		Frame ventana = new Frame ("Art�culos");
		TextArea txaArticulos = new TextArea (6,40);
		Button btnPdf = new Button("Exportar PDF");
		BaseDatos bd = new BaseDatos();
		Log fichero = new Log();
		public consultaArtiProve()
		{
			ventana.setLayout(new FlowLayout());
			
			//Listener de consultaProveedores
			ventana.addWindowListener(this);
			btnPdf.addActionListener(this);
			//antes de a�adir el txaProveedores la a�adimos los datos
			//conectar
			bd.conectar();
			//sacar la informaci�n
			txaArticulos.setText(bd.consultarArtiProve());
			fichero.log(4, 4);
			//desconectar
			bd.desconectar();
			
			//a�adimos los componentes a la ventana
			ventana.add(txaArticulos);
			ventana.add(btnPdf);
			ventana.setLocationRelativeTo(null);
			ventana.setSize(350, 200);
			ventana.setResizable(true);
			ventana.setLocationRelativeTo(null);
			
			ventana.setVisible(true);
			
		}
		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosing(WindowEvent e) 
		{
			ventana.setVisible(false);//(5)	
			
		}
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(btnPdf))
			{
		    Imprimir pdf = new Imprimir();
			String cadena = txaArticulos.getText();
			pdf.imprimepdf(cadena);
			}
	
			
		}
	}



