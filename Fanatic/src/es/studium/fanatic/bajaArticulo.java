package es.studium.fanatic;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bajaArticulo implements WindowListener, ActionListener, ItemListener 
{
	//atributos
	Frame ventana = new Frame ("Baja Articulo");
	Label lblElegir = new Label ("Elegir art�culo a borrar");
	Choice chElegirArticulo = new Choice ();
	Button btnBorrar = new Button ("Borrar");
	Log fichero = new Log();
	Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
	Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	Dialog dlgConfirmacion = new Dialog (ventana, "Confirmaci�n",true);
	Label lblConfirmacion = new Label ("�Segur@ de borrar ?");
	Button btnSi = new Button ("S�");
	Button btnNo = new Button ("No");
	String inicio = "Elegir uno...               ";
	
	BaseDatos bd = new BaseDatos();
	ResultSet rs = null;
	//constructor
	public bajaArticulo()
	{
		//elegimos distribuci�n para la ventana y le a�adimos Listener
		ventana.setLayout(new FlowLayout());
		
		//aqu� pondremos todos los Listener
		ventana.addWindowListener(this);
		btnBorrar.addActionListener(this);
		dlgFeedback.addWindowListener(this);
		dlgConfirmacion.addWindowListener(this);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		chElegirArticulo.addItemListener(this);
		//a�adimos los elementos a la ventana
		ventana.add(lblElegir);
		rellenarChoice();
		//a�adimos el choice a la ventana
		ventana.add(chElegirArticulo);
		//a�adimos bot�n
		ventana.add(btnBorrar);
		
		//creamos las propiedades de la ventana y la hacemos visible
		ventana.setLocationRelativeTo(null);//(7)
		ventana.setSize(400, 150);//(8)
		ventana.setResizable(true);//(9)
		ventana.setVisible(true);//(5)
		
					
		
	
	}
	private void mostrarDialogo()
	{
		dlgFeedback.setLayout(new FlowLayout());
		dlgFeedback.add(lblMensaje);
		dlgFeedback.setSize(200, 90);
		dlgFeedback.setLocationRelativeTo(null);
		dlgFeedback.setVisible(true);
	}
	
	private void rellenarChoice() 
	{
		chElegirArticulo.removeAll();
		chElegirArticulo.add(inicio);
		//conectar la base de datos
		bd.conectar();
		//sacar los datos de proveedores
		//chElegirProveedor.add(bd.consultarProveedores()); no es v�lido ya que no nos sirve que nos devuelva una cadena
		//necesitamos que nos devuelva un ResultSet
		rs=bd.rellenarArticulos();
		try {
			while (rs.next())
			{
				chElegirArticulo.add(rs.getInt("idArticulo") + "-" +
						rs.getString("descripcionArticulo")+ "-" +
						rs.getFloat("precioPVP")+ "-" +
						rs.getString("esImpresora"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//cerrar la conexi�n
		bd.desconectar();
		
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(dlgConfirmacion.isActive()) //le preguntamos si se ha producido este evento por la ventana de dialogo Confirmacion
		{
			dlgConfirmacion.setVisible(false); //cerramos el cuadro de dialogo
		}
		else if(dlgFeedback.isActive()) //le preguntamos si se ha producido este evento por la ventana de dialogo Feedback
		
			{
				dlgFeedback.setVisible(false);
				dlgConfirmacion.setVisible(false);
			}else
			{
				ventana.setVisible(false);
			}
				
		}
	
	@Override
	public void actionPerformed(ActionEvent pulsarBoton) 
	{
		if (pulsarBoton.getSource().equals(btnBorrar))
		{
			//Mostrar el dialogo
			if (!chElegirArticulo.getSelectedItem().equals(inicio))
			{
			dlgConfirmacion.setLayout(new FlowLayout());
			lblConfirmacion.setText("Segur@ de Borrar " + chElegirArticulo.getSelectedItem()+"?");
			dlgConfirmacion.add(lblConfirmacion);
			dlgConfirmacion.add(btnSi);
			dlgConfirmacion.add(btnNo);
			dlgConfirmacion.setSize(400, 100);
			dlgConfirmacion.setLocationRelativeTo(null);
			dlgConfirmacion.setVisible(true);
			}
		} else if (pulsarBoton.getSource().equals(btnNo))
		{
			dlgConfirmacion.setVisible(false);
		}else  if (pulsarBoton.getSource().equals(btnSi))
		{
			bd.conectar();
			//aqu� tomamos ell valos de choice y cortamos para obtener el campo que nos interesa para la sentencia
			String[] array = chElegirArticulo.getSelectedItem().split("-");
			String sentencia = "DELETE FROM articulos WHERE idArticulo ="+array[0]+";";
			int resultado = bd.bajaBaseDatos(sentencia);
			if (resultado==0)
				{
				lblMensaje.setText("Baja correcta");
				mostrarDialogo();
				fichero.log(3, 2);
						
				}
			else 
				{
				lblMensaje.setText("Fallo en baja");
				mostrarDialogo();
				}
			
			bd.desconectar();
			//refrescamos el choice
			rellenarChoice();
			
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
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {}

}
