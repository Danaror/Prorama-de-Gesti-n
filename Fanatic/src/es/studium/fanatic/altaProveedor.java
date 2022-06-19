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



public class altaProveedor implements WindowListener, ActionListener
{
	//declaramos todos los objetos
		Log fichero = new Log();
		Frame ventana = new Frame ("Alta Proveedor");
		TextField txtNombreProveedor = new TextField(50);
		TextField txtDireccionProveedor = new TextField(50);
		TextField txtProvinciaProveedor = new TextField(50);
		TextField txtVAT = new TextField(15);
		TextField txtTiempoEnvio = new TextField(2);
		TextField txtDaTrazabilidad = new TextField(2);
		Label lbNombreProveedor = new Label ("Nombre Proveedor");
		Label lbDireccionProveedor = new Label ("Direccion Proveedor");
		Label lbProvinciaProveedor = new Label ("Provincia Proveedor");
		Label lbVAT = new Label ("V.A.T.");
		Label lbTiempoEnvio = new Label ("Tiempo de envío en días");
		Label lbDaTrazabilidad = new Label ("Trazabilidad Sí/No");
		Button btnAlta = new Button ("Alta");
		Button btnLimpiar = new Button ("Limpiar");
		//Añadimos cuadro dialogo para verificación
		//añadimos un cuadro de dialogos, la ventana a la que pertenece, el nombre y modal
		Dialog dlgFeedback = new Dialog (ventana, "Mensaje",true);
		Label lblMensaje = new Label ("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		
		//añadimos un objeto de la clase BaseDatos
		BaseDatos bd = new BaseDatos();
		
		//Constructor
		public altaProveedor()
		{
			ventana.setLayout(new FlowLayout());
			
			//le añadimos las orejas a la ventana
			ventana.addWindowListener(this);
			btnAlta.addActionListener(this);
			btnLimpiar.addActionListener(this);
			dlgFeedback.addWindowListener(this);
			
			ventana.add(lbNombreProveedor);
			ventana.add(txtNombreProveedor);
			ventana.add(lbDireccionProveedor);
			ventana.add(txtDireccionProveedor);
			ventana.add(lbProvinciaProveedor);
			ventana.add(txtProvinciaProveedor);
			ventana.add(lbVAT);
			ventana.add(txtVAT);
			ventana.add(lbTiempoEnvio);
			ventana.add(txtTiempoEnvio);
			ventana.add(lbDaTrazabilidad);
			ventana.add(txtDaTrazabilidad);
			ventana.add(btnAlta);
			ventana.add(btnLimpiar);
						
			ventana.setLocationRelativeTo(null);
			ventana.setSize(400, 300);
			ventana.setResizable(true);
			
			
			ventana.setVisible(true);
			
		}
		//
		private void limpiarCampos() 
		{
			txtTiempoEnvio.setText("");
			txtNombreProveedor.setText("");
			txtDireccionProveedor.setText("");
			txtProvinciaProveedor.setText("");
			txtVAT.setText("");
			txtDaTrazabilidad.setText("");
			txtNombreProveedor.requestFocus(); //centramos el cursor en el primer campo
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
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) 
		{
			if (dlgFeedback.isActive())
			{
				dlgFeedback.setVisible(false);
			}else
			{
				ventana.setVisible(false);
			}
			
			
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
		public void actionPerformed(ActionEvent pulsarBoton) 
		{
			//pulso Alta
			if (pulsarBoton.getSource().equals(btnAlta))
				{
				//conecto base datos
				bd.conectar();
				//añado los textArea a la misma
				String sentencia = "INSERT INTO proveedores VALUES (null,'" + txtTiempoEnvio.getText() + "','"+ txtNombreProveedor.getText() +"','" + 
				txtDireccionProveedor.getText() +"','" +txtProvinciaProveedor.getText() +"','"+txtVAT.getText() +"','"+txtDaTrazabilidad.getText() + "');";
				System.out.println(sentencia);
				int resultado = bd.altaBaseDatos(sentencia);
				if (resultado==0)
					{
					lblMensaje.setText("Alta correcta");
					mostrarDialogo();
					limpiarCampos();
					fichero.log(2, 1);
					
					}
				else 
					{
					lblMensaje.setText("Fallo en alta");
					mostrarDialogo();
					}
				//desconecto
				bd.desconectar();
				}
			//pulso Limpia
			else if (pulsarBoton.getSource().equals(btnLimpiar))
				{
				//borro los textArea
				limpiarCampos();
				}
				
			
				
		}

}
