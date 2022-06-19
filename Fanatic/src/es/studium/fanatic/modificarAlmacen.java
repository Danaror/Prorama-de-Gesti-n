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

public class modificarAlmacen implements WindowListener, ActionListener 
{
	//Atributos o componentes
	Frame ventana = new Frame ("Editar Línea Almacén");
	Label lblLista = new Label ("Elegir campo a editar");
	String inicio = "Lista Líneas de Almacén";
	Choice chLista = new Choice ();
	Button btnEditar = new Button("Editar");
	String[] array = null;
	
	int idLineaAlmacen;
	Log fichero = new Log();
	
	Dialog dlgEdicion= new Dialog (ventana, "Edicion",true);
	Label lblCabecera = new Label ("Línea Almacén a editar");
	TextField txtCabecera = new TextField(6);
	//ver como insertar la línea cuando la tengamos
	TextField txtCantidad = new TextField(6);
	Label lbCantidad = new Label ("Cantidad");
	Choice chArticulo = new Choice ();
	Label lbArticulo = new Label ("Artículo ubicado en esta línea");
	
	Button btnModificar = new Button ("Modificar");
	Button btnCancelar = new Button ("Cancelar");
	
	Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
	
	Label lblEdicion = new Label ("¿Segur@ que desea modificar ?");
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	public modificarAlmacen()
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
	
	
	//Esto rellena el primer Choice donde tengo que elegir la línea de Almacén a modificar
	private void rellenarChoice() 
	{
		//Limpio choice
		chLista.removeAll();
		//meto la primera cadena a visualizar
		chLista.add(inicio);
		//conectar la base de datos
		bd.conectar();
		//necesitamos que nos devuelva un ResultSet
		rs=bd.rellenarAlmacenes2();
		try {
			while (rs.next())
			{
				chLista.add(rs.getInt("idLineaAlmacen") + "-" +
						rs.getInt("idArticuloFK") + "-"+
						rs.getString("descripcionArticulo")+ "-" +
						rs.getInt("cantidadArticuloAlmacen"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//cerrar la conexión
		bd.desconectar();
			
	}
	
	//Función para mostrar la ventana de diálogo
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
			if (!chLista.getSelectedItem().equals(inicio)) //esto se ejecuta si hemos seleccionado algo en el choice
			{
			//Lo troceamos con un split
			array = chLista.getSelectedItem().split("-");
			
			//añado el articulo que está sobre la línea
			chArticulo.removeAll();
			chArticulo.add(array[1]+"-"+array[2]);
			
			bd.conectar();
			//necesitamos que nos devuelva un ResultSet
			rs=bd.rellenarAlmacenes2();
			try {
				while (rs.next())
				{
					chArticulo.add(rs.getInt("idArticuloFK")+"-"+rs.getString("descripcionArticulo"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			//cerrar la conexión
			bd.desconectar();
			
			dlgEdicion.setLayout(new FlowLayout());
			//cabecera personalizada que indicará la línea que vamos a modificar
			
			dlgEdicion.add(lblCabecera);
			dlgEdicion.add(txtCabecera);
			txtCabecera.setText(array[0]);
			txtCabecera.setEditable(false);
			dlgEdicion.add(lbArticulo);
			dlgEdicion.add(chArticulo);
			dlgEdicion.add(lbCantidad);
			dlgEdicion.add(txtCantidad);
			txtCantidad.setText(array[3]);
			dlgEdicion.add(btnModificar);
			
			
			dlgEdicion.add(btnCancelar);
			dlgEdicion.setSize(400, 400);
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
			//actualizamos el nuevo array ya que ahora necesitamos los datos del nuevo
			array = chArticulo.getSelectedItem().split("-");
			String sentencia = "UPDATE almacenes SET "+
						"idArticuloFK = '" +array[0]+
						"', cantidadArticuloAlmacen ='" +txtCantidad.getText()+
						"' WHERE idLineaAlmacen =" +txtCabecera.getText()+";";
			System.out.println(sentencia); //para ver la sentencia quitar //
			
			//conectar a la base de datos
			bd.conectar();
			//realizar una modificación o UPDATE 
			int resultado = bd.altaBaseDatos(sentencia);
			if (resultado==0)
			{
			lblMensaje.setText("Modificación correcta");
			mostrarDialogo();
			rellenarChoice();
			dlgEdicion.setVisible(false);
			fichero.log(5, 3);
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
