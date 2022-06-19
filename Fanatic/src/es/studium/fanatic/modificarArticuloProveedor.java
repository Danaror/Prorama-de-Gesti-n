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

public class modificarArticuloProveedor implements WindowListener, ActionListener 
{
	//Atributos o componentes
	Frame ventana = new Frame ("Cambiar Proveedor/Precio Artículo");
	Label lblLista = new Label ("Elegir campo a editar");
	String inicio = "Lista Artículos";
	Choice chLista = new Choice ();
	Button btnEditar = new Button("Editar");
	String[] array = null;
	
	int idProveedorArticulo=0;
	int idArticuloFK=0;
	Log fichero = new Log();
	
	
	Dialog dlgEdicion= new Dialog (ventana, "Edición",true);
	Label lblCabecera = new Label ("Artículo a asignar nuevo  precio");
	TextField txtCabecera = new TextField(15);
	//ver como insertar la línea cuando la tengamos
	TextField txtPrecio = new TextField(6);
	Label lbPrecio = new Label ("Precio");
	Choice chProveedores = new Choice ();
	Label lbArticulo = new Label ("Vendedor a asignar");
	
	Button btnModificar = new Button ("Modificar");
	Button btnCancelar = new Button ("Cancelar");
	
	Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
	
	Label lblEdicion = new Label ("¿Segur@ que desea modificar ?");
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	
	public modificarArticuloProveedor()
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
	
	
	//Esto rellena el primer Choice donde tengo que elegir la línea de Artículo a modificar
	private void rellenarChoice() 
	{
		//Limpio choice
		chLista.removeAll();
		//meto la primera cadena a visualizar
		chLista.add(inicio);
		//conectar la base de datos
		bd.conectar();
		//necesitamos que nos devuelva un ResultSet
		//cojo sólo  lo que me interesa para mostrar
		rs=bd.rellenarArtiProv();
		try {
			while (rs.next())
			{
				chLista.add(rs.getInt("idProveedorArticulo") + "-"+
						rs.getInt("idArticuloFK") + "-"+
						rs.getString("descripcionArticulo") + "-" +
						rs.getInt("idProveedorFK") + "-"+
						rs.getString("nombreProveedor")+"-"+
						rs.getFloat("precioCompra"));
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
			idProveedorArticulo=Integer.parseInt(array[0]);
			//añado el articulo que está sobre la línea
			chProveedores.removeAll();
			chProveedores.add(array[3]+"-"+array[4]);
			bd.conectar();
			//necesitamos que nos devuelva un ResultSet
			rs=bd.rellenarProveedores();
			try {
				while (rs.next())
				{
					chProveedores.add(rs.getInt("idProveedor")+"-"+rs.getString("nombreProveedor"));
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
			txtCabecera.setText(array[1]+"-"+array[2]);
			idArticuloFK=Integer.parseInt(array[1]);
			txtCabecera.setEditable(false);
			dlgEdicion.add(lbArticulo);
			dlgEdicion.add(chProveedores);
			dlgEdicion.add(lbPrecio);
			dlgEdicion.add(txtPrecio);
			txtPrecio.setText(array[5]);
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
			//actualizamos el nuevo array ya que ahora necesitamos los datos del nuevo
			array = chProveedores.getSelectedItem().split("-");
			String sentencia = "UPDATE proveedoresarticulos SET "+
						"idArticuloFK =" +idArticuloFK+
						", idProveedorFK = " +array[0]+
						", precioCompra =" +txtPrecio.getText()+
						" WHERE idProveedorArticulo =" +idProveedorArticulo+";";
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
			fichero.log(5, 4);
			dlgEdicion.setVisible(false);		
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
