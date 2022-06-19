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

public class modificarArticulo implements ActionListener, WindowListener 

	{
		//Atributos o componentes
		Frame ventana = new Frame ("Editar Artículo");
		Label lblLista = new Label ("Elegir campo a editar");
		String inicio = "Lista Articulos";
		Choice choArticulo = new Choice ();
		Button btnEditar = new Button("Editar");
		String[] array = null;
		Log fichero = new Log();
		
		int idArticulo;
		
		Dialog dlgEdicion= new Dialog (ventana, "Edición",true);
		Label lblCabecera = new Label ("Modifique los campos necesarios");
		TextField txtNombreArticulo = new TextField(30);
		TextField txtprecioPVP = new TextField(6);
		TextField txtEsImpresora = new TextField(4);
		
		Label lbNombreArticulo = new Label ("Nombre Artículo");
		Label lbprecioPVP = new Label ("PVP");
		Label lbEsImpresora = new Label ("¿Es Impresora?sí/no");
		
		Button btnModificar = new Button ("Modificar");
		Button btnCancelar = new Button ("Cancelar");
		
		Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		
		Label lblEdicion = new Label ("¿Segur@ que desea modificar ?");
		BaseDatos bd = new BaseDatos();
		ResultSet rs = null;
		
		public modificarArticulo()
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
			rellenarArticulos();
			ventana.add(lblLista);
			ventana.add(choArticulo);
			ventana.add(btnEditar);
			
			
			
			//damos parámetros a la ventana y la hacemos visible
			//creamos las propiedades de la ventana y la hacemos visible
			ventana.setLocationRelativeTo(null);//(7)
			ventana.setSize(400, 175);//(8)
			ventana.setResizable(true);//(9)
			ventana.setVisible(true);//(5)
		}
		private void rellenarArticulos() 
		{
			choArticulo.removeAll();
			choArticulo.add(inicio);
			//conectar la base de datos
			bd.conectar();
			//sacar los datos de proveedores
			//chElegirProveedor.add(bd.consultarProveedores()); no es válido ya que no nos sirve que nos devuelva una cadena
			//necesitamos que nos devuelva un ResultSet
			rs=bd.rellenarArticulos();
			try {
				while (rs.next())
				{
					choArticulo.add(rs.getInt("idArticulo") + "-" +rs.getString("descripcionArticulo")+"-"+
								rs.getFloat("precioPVP")+"-"+rs.getString("esImpresora"));
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
				if (!choArticulo.getSelectedItem().equals(inicio))
				{
				//Lo troceamos con un split
				array = choArticulo.getSelectedItem().split("-");
				idArticulo = Integer.parseInt(array[0]);
				
				dlgEdicion.setLayout(new FlowLayout());
				
				dlgEdicion.add(lblCabecera);
				dlgEdicion.add(lbNombreArticulo);
				dlgEdicion.add(txtNombreArticulo);
				txtNombreArticulo.setText(array[1]);
				dlgEdicion.add(lbprecioPVP);
				dlgEdicion.add(txtprecioPVP);
				txtprecioPVP.setText(array[2]);
				dlgEdicion.add(lbEsImpresora);
				dlgEdicion.add(txtEsImpresora);
				txtEsImpresora.setText(array[3]);
				
				dlgEdicion.add(btnModificar);
				
				
				dlgEdicion.add(btnCancelar);
				dlgEdicion.setSize(400, 200);
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
				String sentencia = "UPDATE articulos SET "+
							"descripcionArticulo = '" +txtNombreArticulo.getText()+
							"', precioPVP ='" +txtprecioPVP.getText()+
							"', esImpresora ='" +txtEsImpresora.getText()+
							"' WHERE idArticulo =" +idArticulo+";";
				System.out.println(sentencia); //para ver la sentencia quitar //
				
				//conectar a la base de datos
				bd.conectar();
				//realizar una modificación o UPDATE 
				int resultado = bd.altaBaseDatos(sentencia);
				if (resultado==0)
				{
				lblMensaje.setText("Modificación correcta");
				mostrarDialogo();
				rellenarArticulos();
				fichero.log(5, 2);
						
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
