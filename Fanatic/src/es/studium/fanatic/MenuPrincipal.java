package es.studium.fanatic;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MenuPrincipal implements WindowListener, ActionListener 
{
	//creamos objeto clase Frame
	Frame ventana = new Frame("Menú Principal");
	Log fichero = new Log();
	//creamos objeto clase MenuBar
	MenuBar barraMenu = new MenuBar();
	//creamos los componentes de la barraMenu que son de la clase Menu
	
	//Menú de Articulos
	Menu mnuArticulos = new Menu("Artículos");
	Menu mnuBajaArticulos = new Menu("Baja Artículos");
	Menu mnuConsultaArticulos = new Menu ("Listado");
	Menu mnuAltaArticulo = new Menu("Alta Artículo");
	Menu mnuModArticulo = new Menu("Modificar Artículo");
	
	//Menú Item Artículos
	MenuItem mniAltaArticulo = new MenuItem("Alta Nuevo Artículo");
	MenuItem mniAltaArtiProve = new MenuItem("Asignar Artículo a Proveedor");
	MenuItem mniConsultaArticulo = new MenuItem("Listado Artículos");
	MenuItem mniConsultaArtiProve = new MenuItem("Artículos Proveedor/Precio Compra");
	MenuItem mniBajaArticulo = new MenuItem("Borrar Artículo");
	MenuItem mniBajaArtiProve = new MenuItem("Borrar Proveedor/Artículo");
	MenuItem mniModificaArticulo = new MenuItem("Modificar sólo Artículo");
	MenuItem mniModificaArtiProve = new MenuItem("Modificar Proveedor de Artículo");
	//Menú Proveedores
	Menu mnuProveedores = new Menu("Proveedores");
	MenuItem mniAltaProveedor = new MenuItem("Nuevo Proveedor");
	MenuItem mniConsultaProveedor = new MenuItem("Listado Proveedores");
	MenuItem mniBajaProveedor = new MenuItem("Borrar Proveedor");
	MenuItem mniModificaProveedor = new MenuItem("Modificar Proveedor");
	//Menú Almacenes
	Menu mnuAlmacenes = new Menu("Almacenes");
	MenuItem mniAltaAlmacen = new MenuItem("Nueva Línea");
	MenuItem mniConsultaAlmacen = new MenuItem("Cantidad/Ubicación");
	MenuItem mniBajaAlmacen = new MenuItem("Borrar Línea");
	MenuItem mniModificaAlmacen = new MenuItem("Modificar Línea");
	//Menú Ayuda
	Menu mnuAyuda = new Menu("?");
	MenuItem mniAyuda = new MenuItem("Ayuda");
	MenuItem mniAcerca = new MenuItem("Acerca de...");
	
	Label lblUsuario = new Label("FANATIC3D");
	
	//Acerca de...
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Label lblMensaje = new Label("XXXXXXXXXXXXXXXXXXXXXXXXX");
	
	
	
	//Constructor
	public MenuPrincipal(int tipoUsuario)
	{
		//Distribución de la ventana
		ventana.setLayout(new FlowLayout());
		// añadimos listener
		ventana.addWindowListener(this);
		mniConsultaArticulo.addActionListener(this);
		mniConsultaArtiProve.addActionListener(this);
		mniAltaArticulo.addActionListener(this);
		mniAltaArtiProve.addActionListener(this);
		mniBajaArtiProve.addActionListener(this);
		mniBajaArticulo.addActionListener(this);
		mniModificaArticulo.addActionListener(this);
		mniModificaArtiProve.addActionListener(this);
		mniConsultaAlmacen.addActionListener(this);
		mniAltaAlmacen.addActionListener(this);
		mniBajaAlmacen.addActionListener(this);
		mniModificaAlmacen.addActionListener(this);
		mniConsultaProveedor.addActionListener(this);
		mniAltaProveedor.addActionListener(this);
		mniBajaProveedor.addActionListener(this);
		mniModificaProveedor.addActionListener(this);
		mniAyuda.addActionListener(this);
		mniAcerca.addActionListener(this);
		dlgMensaje.addWindowListener(this);

		// añadimos compponentes
		// debemos elegir el orden de añadido en función de su uso
		//Vamos añadiendo los Item a la barra de Menú
		mnuAltaArticulo.add(mniAltaArticulo);
		mnuAltaArticulo.add(mniAltaArtiProve);
		mnuArticulos.add(mnuAltaArticulo);

		mnuConsultaArticulos.add(mniConsultaArticulo);
		mnuConsultaArticulos.add(mniConsultaArtiProve);
		mnuArticulos.add(mnuConsultaArticulos);
		//Sólo los Usuario de tipo 1 pueden accder a estos menú
		if (tipoUsuario == 1) {
			mnuBajaArticulos.add(mniBajaArticulo);
			mnuBajaArticulos.add(mniBajaArtiProve);
			mnuArticulos.add(mnuBajaArticulos);
			mnuModArticulo.add(mniModificaArticulo);
			mnuModArticulo.add(mniModificaArtiProve);
			mnuArticulos.add(mnuModArticulo);
		}

		barraMenu.add(mnuArticulos);
		
		mnuAlmacenes.add(mniAltaAlmacen);
		mnuAlmacenes.add(mniConsultaAlmacen);
		if (tipoUsuario == 1) {
			mnuAlmacenes.add(mniBajaAlmacen);
			mnuAlmacenes.add(mniModificaAlmacen);
		}

		barraMenu.add(mnuAlmacenes);
		
		mnuProveedores.add(mniAltaProveedor);
		mnuProveedores.add(mniConsultaProveedor);
		if (tipoUsuario == 1) {
			mnuProveedores.add(mniBajaProveedor);
			mnuProveedores.add(mniModificaProveedor);
		}

		barraMenu.add(mnuProveedores);

		mnuAyuda.add(mniAcerca);
		mnuAyuda.add(mniAyuda);
		barraMenu.add(mnuAyuda);
		// añadimos la barra de menú a la ventana
		ventana.setMenuBar(barraMenu);
		//añadimos una etiqueta
		ventana.add(lblUsuario);

		// configuramos proporción y visibilidad
		ventana.setLocationRelativeTo(null);
		ventana.setSize(300, 150);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	//Seleccionando menú en if-else anidados
	@Override
	public void actionPerformed(ActionEvent pulsarMenu) {
		if (pulsarMenu.getSource().equals(mniConsultaProveedor)) {
			new consultaProveedores();
		} else if (pulsarMenu.getSource().equals(mniAltaProveedor)) {
			new altaProveedor();
		} else if (pulsarMenu.getSource().equals(mniBajaProveedor)) {
			new bajaProveedor();
		} else if (pulsarMenu.getSource().equals(mniModificaProveedor)) {
			new modificarProveedor();
		} else if (pulsarMenu.getSource().equals(mniAltaArticulo)) {
			new AltaArticulo();
		} else if (pulsarMenu.getSource().equals(mniAltaArtiProve)) {
			new AltaArtiProve();
		} else if (pulsarMenu.getSource().equals(mniConsultaArticulo)) {
			new consultaArticulo();
		} else if (pulsarMenu.getSource().equals(mniConsultaArtiProve)) {
			new consultaArtiProve();
		} else if (pulsarMenu.getSource().equals(mniBajaArticulo)) {
			new bajaArticulo();
		} else if (pulsarMenu.getSource().equals(mniBajaArtiProve)) {
			new bajaArtiProve();
		} else if (pulsarMenu.getSource().equals(mniAltaAlmacen)) {
			new altaLineaAlmacen();
		} else if (pulsarMenu.getSource().equals(mniConsultaAlmacen)) {
			new consultaAlmacen();
		} else if (pulsarMenu.getSource().equals(mniBajaAlmacen)) {
			new bajaAlmacen();
		} else if (pulsarMenu.getSource().equals(mniModificaArticulo)) {
			new modificarArticulo();
		} else if (pulsarMenu.getSource().equals(mniModificaAlmacen)) {
			new modificarAlmacen();
		} else if (pulsarMenu.getSource().equals(mniModificaArtiProve)) {
			new modificarArticuloProveedor();
		} else if (pulsarMenu.getSource().equals(mniAyuda)) {
			try {
				Runtime.getRuntime().exec("hh.exe ayuda.chm");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else if (pulsarMenu.getSource().equals(mniAcerca))
		{
			dlgMensaje.setLayout(new FlowLayout());
					lblMensaje.setText("José Antonio Fernández Dana// Fanatic v0.1");
					dlgMensaje.add(lblMensaje);
					dlgMensaje.setSize(280, 75);
					dlgMensaje.setLocationRelativeTo(null);
					dlgMensaje.setResizable(false);
					dlgMensaje.setVisible(true);
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
		}else {
				//registramos en fichero al salir
				fichero.log(1, 0);
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
