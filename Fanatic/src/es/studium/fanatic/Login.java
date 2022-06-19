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

public class Login implements WindowListener, ActionListener
{
	//Atributos, componentes ,campos...
	//Diseño ventana
	Frame ventana = new Frame("Login");
	
	Log fichero = new Log();
	
	Label lblUsuario= new Label("Usuario");
	Label lblClave =new Label("Clave");
	TextField txtUsuario =new TextField(15);
	TextField txtClave =new TextField(15);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Limpiar");
	//Creamos dialogos
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXXXXXXXX");
	String usuario;
	
	
	//Construcctor
	public Login()
		{
			
		//Distribución de la ventana
		ventana.setLayout(new FlowLayout());
		//añadimos listener
		ventana.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		dlgMensaje.addWindowListener(this);
		//añadimos compponentes
		ventana.add(lblUsuario);
		ventana.add(txtUsuario);
		ventana.add(lblClave);
		ventana.add(txtClave);
		//método para que la clave sea "oculta"
		txtClave.setEchoChar('*');//cambia los caracteres por el carácter puesto entre comillas simples
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		//configuramos proporción y visibilidad
		ventana.setLocationRelativeTo(null);
		ventana.setSize(220,150);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		}

	public static void main(String[] args)
	{
		new Login();
	}

	//métodos para los eventos
	@Override
	public void actionPerformed(ActionEvent pulsarBoton) 
		{
			if (pulsarBoton.getSource().equals(btnCancelar))
			{
				txtClave.setText("");
				txtUsuario.setText("");
				txtUsuario.requestFocus(); //método para que el cursor se centre en este textfield
			}else if (pulsarBoton.getSource().equals(btnAceptar))
			{
				
				// Coger los textos de la ventana: usuario y clave
				usuario = txtUsuario.getText();
				//extraigo el nombre de usuario para el registro Log
				
				String clave = txtClave.getText();
				//envío el usuario que accede ese momento al fichero user
				fichero.user(usuario);
				// Conectar a la base de datos
				BaseDatos bd = new BaseDatos(); //creamos un objeto bd de la clase BaseDatos
				bd.conectar();
				// Hacer un SELECT
				int resultado =bd.consultar("SELECT * FROM usuarios WHERE nombreUsuario= '"+usuario+"' AND claveUsuario= SHA2('"+clave+"', 256);");
				System.out.println(resultado);
				// Caso negativo(-1): Mostrar mensaje de error
				if (resultado==-1)
					{
					
					}
					
				else
				{
				// Caso afirmativo (0-1): Mostrar menú principal
					new MenuPrincipal(resultado);
					ventana.setVisible(false);
					//Envío el codigo de secuencia de acceso a la base de datos
					fichero.log(0, 0);
				}
				// Desconectar BD
				bd.desconectar();
			}
			
		}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent cierraVentana) 
		{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}else
		{
			
		
		System.exit(0);
		
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
