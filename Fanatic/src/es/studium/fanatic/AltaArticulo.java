package es.studium.fanatic;

import java.awt.Button;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;


public class AltaArticulo implements WindowListener, ActionListener

{
	//Atributos
	Frame ventana = new Frame("Alta Artículo");
		Label lblNombre = new Label ("Nombre Artículo");
		TextField txtNombre = new TextField(50);
		Label lblPvp = new Label ("PVP");
		TextField txtPvp = new TextField(50);
		Label lblImpresora = new Label ("¿Es impresora?");
		TextField txtImpresora = new TextField(50);
		Log fichero = new Log();
		Button btnAceptar = new Button ("Aceptar");
		Button btnLimpiar = new Button ("Limpiar");
		String inicio = "Elige Proveedor";
		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;
		
		Dialog dlgMensaje = new Dialog (ventana, "Mensaje",true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
	public AltaArticulo()
	{
		//Listener
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgMensaje.addWindowListener(this);
		//Distribución , añadimos componentes 
		ventana.setLayout(new FlowLayout());
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		ventana.add(lblPvp);
		ventana.add(txtPvp);
		ventana.add(lblImpresora);
		ventana.add(txtImpresora);
		
		
		
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		
		//configuración y visibilidad
		ventana.setLocationRelativeTo(null);
		ventana.setSize(400, 250);
		ventana.setResizable(true);
		ventana.setVisible(true);
	}

	
	private void mostrarDialogo()
	{
		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.add(lblMensaje);
		dlgMensaje.setSize(200, 90);
		dlgMensaje.setLocationRelativeTo(null);
		dlgMensaje.setVisible(true);
	}
	//Eventos
	@Override
		public void actionPerformed(ActionEvent pulsarBoton) 
		{
		if (pulsarBoton.getSource().equals(btnLimpiar))
		{
			limpiarCampos();
			
		}
		else if (pulsarBoton.getSource().equals(btnAceptar)) 
		{
			//conecto base datos
			bd.conectar();
			//Identificar los campos y crear sentencia
			//sentencia INSERT
			String sentencia = "INSERT INTO articulos VALUE (null,'"+txtNombre.getText()+"',"+txtPvp.getText()+",'"+txtImpresora.getText()+"');";
			System.out.println(sentencia);
			int resultado =bd.altaBaseDatos(sentencia); //enviamos la sentencia para su alta
			if (resultado==0)
			{
			lblMensaje.setText("Alta correcta");
			mostrarDialogo();
			limpiarCampos();
			fichero.log(2, 2);
			}
		else 
			{
			lblMensaje.setText("Fallo en alta");
			mostrarDialogo();
			}
			//Identificar su ID consultando el artículo
			//Introducir Proveedor/Artículo con
				//idArticulo-idProveedor
			bd.desconectar();
			
		}
		}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtNombre.setText("");
		txtPvp.setText("");
		txtImpresora.setText("");
		txtNombre.requestFocus();
		
	}

	@Override
	public void windowClosing(WindowEvent cerrarVentana)
	{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}else
		{
			ventana.setVisible(false);
		}
	}
	@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
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


	
}
