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

public class modificarProveedor implements WindowListener, ActionListener 

	{
		//Atributos o componentes
		Frame ventana = new Frame ("Editar Proveedor");
		Label lblLista = new Label ("Elegir campo a editar");
		String inicio = "Lista Proveedores";
		Choice chLista = new Choice ();
		Button btnEditar = new Button("Editar");
		String[] array = null;
		Log fichero = new Log();
		
		int idProveedor;
		
		Dialog dlgEdicion= new Dialog (ventana, "Edición",true);
		Label lblCabecera = new Label ("Modificar");
		Label lbNombreProveedor = new Label ("Nombre Proveedor");
		Label lbDireccionProveedor = new Label ("Dirección Proveedor"); 
		Label lbProvinciaProveedor = new Label ("Provincia Proveedor");
		TextField txtNombreProveedor = new TextField(40);
		TextField txtDireccionProveedor = new TextField(40);
		TextField txtProvinciaProveedor = new TextField(40);
		Label lbVAT = new Label ("V.A.T.");
		TextField txtVAT = new TextField(9);
		Label lbTiempoEnvio = new Label ("Tiempo de envío (días)");
		TextField txtTiempoEnvio = new TextField(4);	
		Label lbDaTrazabilidad = new Label ("Sí/No");
		TextField txtDaTrazabilidad = new TextField(3);
		
		Button btnModificar = new Button ("Modificar");
		Button btnCancelar = new Button ("Cancelar");
		
		Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		
		Label lblEdicion = new Label ("¿Segur@ que desea modificar ?");
		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;
		
		public modificarProveedor()
		{
			//distribución
			ventana.setLayout(new FlowLayout());
			
			//Listener
			ventana.addWindowListener(this);
			
			btnEditar.addActionListener(this);
			btnModificar.addActionListener(this);
			btnCancelar.addActionListener(this);
			dlgFeedback.addWindowListener(this);
			dlgEdicion.addWindowListener(this);
			dlgEdicion.addWindowListener(this);	
			//añadimos los elementos a la ventana
			rellenarChoice();
			ventana.add(lblLista);
			ventana.add(chLista);
			ventana.add(btnEditar);
			
			
			
			//damos parámetros a la ventana y la hacemos visible
			//creamos las propiedades de la ventana y la hacemos visible
			ventana.setLocationRelativeTo(null);//(7)
			ventana.setSize(400, 175);//(8)
			ventana.setResizable(true);//(9)
			ventana.setVisible(true);//(5)
		}
		private void rellenarChoice() 
		{
			chLista.removeAll();
			chLista.add(inicio);
			//conectar la base de datos
			bd.conectar();
			//sacar los datos de proveedores
			//chElegirProveedor.add(bd.consultarProveedores()); no es válido ya que no nos sirve que nos devuelva una cadena
			//necesitamos que nos devuelva un ResultSet
			rs=bd.rellenarProveedores();
			try {
				while (rs.next())
				{
					chLista.add(rs.getInt("idProveedor") + "-" +
							rs.getInt("tiempoEnvioProveedor")+ "-" +
							rs.getString("nombreProveedor")+ "-" +
							rs.getString("direccionProveedor")+ "-" +
							rs.getString("provinciaProveedor")+ "-" +
							rs.getString("vatProveedor")+ "-" +
							rs.getString("daTrazabilidad"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			//cerrar la conexión
			bd.desconectar();
			
			
		}
		
		private void mostrarDialogo()
		{
			dlgFeedback.setLayout(new FlowLayout());
			dlgFeedback.add(lblMensaje);
			dlgFeedback.setSize(200, 90);
			dlgFeedback.setLocationRelativeTo(null);
			dlgFeedback.setVisible(true);
		}
		

		@Override
		public void actionPerformed(ActionEvent pulsarBoton) 
		{
			if (pulsarBoton.getSource().equals(btnEditar))
			{
				//Tomamos el elemento seleccionado
				
				//Mostrar el dialogo
				//Con cada elemento en su sitio
				if (!chLista.getSelectedItem().equals(inicio))
				{
				//Lo troceamos con un split
				array = chLista.getSelectedItem().split("-");
				idProveedor = Integer.parseInt(array[0]);
				
				dlgEdicion.setLayout(new FlowLayout());
				
				dlgEdicion.add(lblCabecera);
				dlgEdicion.add(lbNombreProveedor);
				dlgEdicion.add(txtNombreProveedor);
				txtNombreProveedor.setText(array[2]);
				dlgEdicion.add(lbDireccionProveedor);
				dlgEdicion.add(txtDireccionProveedor);
				txtDireccionProveedor.setText(array[3]);
				dlgEdicion.add(lbProvinciaProveedor);
				dlgEdicion.add(txtProvinciaProveedor);
				txtProvinciaProveedor.setText(array[4]);
				dlgEdicion.add(lbVAT);
				dlgEdicion.add(txtVAT);
				txtVAT.setText(array[5]);
				dlgEdicion.add(lbTiempoEnvio);
				dlgEdicion.add(txtTiempoEnvio);
				txtTiempoEnvio.setText(array[1]);
				dlgEdicion.add(lbDaTrazabilidad);
				dlgEdicion.add(txtDaTrazabilidad);
				txtDaTrazabilidad.setText(array[6]);
				dlgEdicion.add(btnModificar);
				
				
				dlgEdicion.add(btnCancelar);
				dlgEdicion.setSize(360, 300);
				dlgEdicion.setLocationRelativeTo(null);
				dlgEdicion.setVisible(true);
				}
			} 
			else if (pulsarBoton.getSource().equals(btnCancelar))
			{
				dlgEdicion.setVisible(false);
			}
			else  if (pulsarBoton.getSource().equals(btnModificar))
			{
				//Botón Modificar
				//Paso los todos los campos a mi línea de sentencia para luego ejecutarla
				String sentencia = "UPDATE proveedores SET "+
							"nombreProveedor = '" +txtNombreProveedor.getText()+
							"', direccionProveedor ='" +txtDireccionProveedor.getText()+
							"', provinciaProveedor ='" +txtProvinciaProveedor.getText()+
							"', vatProveedor ='" +txtVAT.getText()+
							"', tiempoEnvioProveedor ='" +txtTiempoEnvio.getText()+
							"', daTrazabilidad ='" +txtDaTrazabilidad.getText()+
							"' WHERE idproveedor =" +idProveedor;
				System.out.println(sentencia); //para ver la sentencia quitar //
				
				//conectar a la base de datos
				bd.conectar();
				//realizar una modificación o UPDATE 
				int resultado = bd.altaBaseDatos(sentencia);
				if (resultado==0)
				{
				lblMensaje.setText("Modificación correcta");
				fichero.log(5, 1);
				mostrarDialogo();
				rellenarChoice();
						
				}
			else 
				{
				lblMensaje.setText("Fallo en Modificación");
				mostrarDialogo();
				}
			
					//cerrar la conexión
				bd.desconectar();	
				}
				
				
				
			
		}

		
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			if(dlgEdicion.isActive()) //le preguntamos si se ha producido este evento por la ventana de dialogo Confirmacion
			{
				dlgEdicion.setVisible(false); //cerramos el cuadro de dialogo
			}
			else
				if(dlgFeedback.isActive()) //le preguntamos si se ha producido este evento por la ventana de dialogo Feedback
			
				{
					dlgFeedback.setVisible(false);
					
				}else
				{
					ventana.setVisible(false);;
				}
					
		}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}

	}
