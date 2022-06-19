package es.studium.fanatic;

import java.awt.Button;
import java.awt.Choice;
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
import java.sql.SQLException;

public class altaLineaAlmacen implements WindowListener, ActionListener

{
	//Atributos
	Frame ventana = new Frame("Alta Producto en Almacén");
		Label lblNombre = new Label ("Nombre Artículo");
		Choice choArticulo = new Choice();
		Label lblCantidad = new Label ("Cantidad Artículo");
		TextField txtCantidad = new TextField(9);
		Button btnAceptar = new Button ("Aceptar");
		Button btnLimpiar = new Button ("Limpiar");
		String inicio1 = "Elige Artículo";
		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;
		Log fichero = new Log();
		Dialog dlgMensaje = new Dialog (ventana, "Mensaje",true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
	public altaLineaAlmacen ()
	{
		//Listener
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		dlgMensaje.addWindowListener(this);
		//Distribución , añadimos componentes 
		ventana.setLayout(new FlowLayout());
		ventana.add(lblNombre);
		rellenarChoiceArti();
		ventana.add(choArticulo);
		ventana.add(lblCantidad);
		ventana.add(txtCantidad);
		ventana.add(btnAceptar);
		ventana.add(btnLimpiar);
		
		//configuración y visibilidad
		ventana.setLocationRelativeTo(null);
		ventana.setSize(350,175);
		ventana.setResizable(true);
		ventana.setVisible(true);
	}

	
	private void rellenarChoiceArti() 
	{
		choArticulo.removeAll();
		choArticulo.add(inicio1);
		//conectar la base de datos
		bd.conectar();
		//sacar los datos de proveedores
		//chElegirProveedor.add(bd.consultarProveedores()); no es válido ya que no nos sirve que nos devuelva una cadena
		//necesitamos que nos devuelva un ResultSet
		rs=bd.rellenarArticulos();
		try {
			while (rs.next())
			{
				choArticulo.add(rs.getInt("idArticulo") + "-" +
							rs.getString("descripcionArticulo"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//cerrar la conexión
		bd.desconectar();
		
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
			//conectamos a la base de datos
			bd.conectar();
			//Identificar los campos y crear sentencia
			//sentencia INSERT
			String[] idProveedor = choArticulo.getSelectedItem().split("-");
			String sentencia = "INSERT INTO almacenes VALUE (null,'"+idProveedor[0]+"',"+txtCantidad.getText()+");";
			System.out.println(sentencia);
			int resultado =bd.altaBaseDatos(sentencia); //enviamos la sentencia para su alta
			if (resultado==0)
			{
			lblMensaje.setText("Alta correcta");
			mostrarDialogo();
			limpiarCampos();
			fichero.log(2, 3);
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
		txtCantidad.setText("");
		rellenarChoiceArti();
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
