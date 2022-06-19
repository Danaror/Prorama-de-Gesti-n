package es.studium.fanatic;


import java.awt.Button;
import java.awt.FlowLayout;
//import java.awt.Font;
import java.awt.Frame;
//import java.awt.Graphics;
//import java.awt.PrintJob;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class consultaProveedores  implements WindowListener, ActionListener 
{
	Log fichero = new Log();
	//atributos
	//creamos ventana y sus componentes
	Frame ventana = new Frame ("Proveedores");
	TextArea txaProveedores = new TextArea (6,40);
	Button btnPdf = new Button("Exportar PDF");
	BaseDatos bd = new BaseDatos();
	
	
	public consultaProveedores()
	{
		ventana.setLayout(new FlowLayout());
		
		//Listener de consultaProveedores
		ventana.addWindowListener(this);
		btnPdf.addActionListener(this);
		//antes de añadir el txaProveedores la añadimos los datos
		//conectar
		bd.conectar();
		//sacar la información
		txaProveedores.setText(bd.consultarProveedores());
		fichero.log(4, 1);
		//desconectar
		bd.desconectar();
		
		//añadimos los componentes a la ventana
		ventana.add(txaProveedores);
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
		if(e.getSource().equals(btnPdf))
		{
	    Imprimir pdf = new Imprimir();
		String cadena = txaProveedores.getText();
		pdf.imprimepdf(cadena);
		}

	
		
	}
}
