package es.studium.fanatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos 
{
	//Conector BDD
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tiendaimpresoras3d_pr";
		String login = "fanatic";
		String password = "Studium2020.,";
		String sentencia = "" ;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
	public BaseDatos()
	{
		
	}
	public void conectar()
	{
		try
		{
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión establecida");
			
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
			
			
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
			
		}
			
	}
	
	
	//nos devuelve el tipo de usuario 
	public int consultar(String sentencia)
	{
		int resultado=-1;
		ResultSet rs = null;
		try
			{
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			if(rs.next()) // si hay al menos uno
			{
				resultado = rs.getInt("tipoUsuario");
			}
			}
		catch (SQLException sqle)
			{
				System.out.println("Error 3-"+sqle.getMessage());
				
			}
		return resultado;	
	}
	
	
	//Nos devuelve todo el contenido de Proveedores y le da formato
	public String consultarProveedores()
	{
		String contenido="";
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			// variable especial para guardar las consultas a nuestra base de datos
			// el método next nos permite pasar de elemento a elemento de nuestro RedultSet
			rs = statement.executeQuery("SELECT * FROM proveedores");
			while (rs.next())
			{
				contenido= contenido + (rs.getInt("idProveedor") + "-" +
						rs.getInt("tiempoEnvioProveedor")+ "-" +
						rs.getString("nombreProveedor")+ "-" +
						rs.getString("direccionProveedor")+ "-" +
						rs.getString("provinciaProveedor")+ "-" +
						rs.getString("vatProveedor")+ "-" +
						rs.getString("daTrazabilidad") + "\n");
				
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 4-"+sqle.getMessage());
		}
		return contenido;
		
	}
	
	
	//Nos devuelve todo el contenido de articulos y le da formato
	public String consultarArticulos()
	{
		String contenido="";
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			// variable especial para guardar las consultas a nuestra base de datos
			// el método next nos permite pasar de elemento a elemento de nuestro RedultSet
			rs = statement.executeQuery("SELECT * FROM articulos");
			while (rs.next())
			{
				contenido= contenido + (rs.getInt("idArticulo") + "-" +
						rs.getString("descripcionArticulo")+ "-" +
						rs.getFloat("precioPVP")+ "-" +
						rs.getString("esImpresora")+ "\n");
				
			}
		}
		
		catch (SQLException sqle)
		{
			System.out.println("Error 5-"+sqle.getMessage());
		}
		return contenido;
		
	}
	public String consultarArtiProve()
	{
		String contenido="";
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			// variable especial para guardar las consultas a nuestra base de datos
			// el método next nos permite pasar de elemento a elemento de nuestro RedultSet
			rs = statement.executeQuery("SELECT idArticuloFK, descripcionArticulo, nombreProveedor,"
					+ "precioCompra FROM proveedoresarticulos JOIN articulos ON idArticulo=proveedoresarticulos.idArticuloFK JOIN proveedores ON idProveedorFK=idProveedor;");
			while (rs.next())
			{
				contenido= contenido + (rs.getInt("idArticuloFK") + "-" +
						rs.getString("descripcionArticulo")+ "-" +
						rs.getString("nombreProveedor")+ "-" +
						rs.getFloat("precioCompra")+ "\n");
				
			}
		}
		
		catch (SQLException sqle)
		{
			System.out.println("Error 5-"+sqle.getMessage());
		}
		return contenido;
		
	}
	
	//Nos devuelve todo el contenido de almacenes
	public String consultarAlmacenes()
	{
		String contenido="";
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			// variable especial para guardar las consultas a nuestra base de datos
			// el método next nos permite pasar de elemento a elemento de nuestro RedultSet
			
			//Aplicamos JOIN en la consulta
			rs = statement.executeQuery("SELECT idLineaAlmacen, descripcionArticulo, cantidadArticuloAlmacen FROM almacenes JOIN articulos ON almacenes.idArticuloFK=idArticulo;");
			while (rs.next())
			{
				contenido= contenido + (rs.getInt("idLineaAlmacen") + "-" +
						rs.getString("descripcionArticulo")+ "-" +
						rs.getInt("cantidadArticuloAlmacen")+ "\n");
				
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 6-"+sqle.getMessage());
		}
		return contenido;
		
	}
	
	public int altaBaseDatos(String s)
	{
		int resultado=0;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			statement.executeUpdate(s);
			
		}
		catch (SQLException sqle)
		{
			resultado=-1;
			System.out.println("Error 7-"+sqle.getMessage());
		}
		return(resultado);
		
	
	}
	
	//Nos devuelve tupla a tupla
	public ResultSet rellenarProveedores()
	{	
		ResultSet rs = null;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			rs = statement.executeQuery("SELECT * FROM proveedores");
			
			
		}
		catch (SQLException sqle)
		{
			
			System.out.println("Error 8-"+sqle.getMessage());
		}
		return rs;
		
	}
	
	// Nos devuelve registro a registro
	public ResultSet rellenarArticulos()
	{	
		ResultSet rs = null;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			rs = statement.executeQuery("SELECT * FROM articulos");
			
			
		}
		catch (SQLException sqle)
		{
			
			System.out.println("Error 9-"+sqle.getMessage());
		}
		return rs;
		
	}
	
	//Nos devuelve registro a registro
	public ResultSet rellenarAlmacenes()
	{	
		ResultSet rs = null;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			rs = statement.executeQuery("SELECT idLineaAlmacen, descripcionArticulo, cantidadArticuloAlmacen FROM almacenes JOIN articulos ON almacenes.idArticuloFK=idArticulo;");
			
			
		}
		catch (SQLException sqle)
		{
			
			System.out.println("Error 10-"+sqle.getMessage());
		}
		return rs;
		
	}
	//voy a necesitar el idArticuloFK para la modificcación
	public ResultSet rellenarAlmacenes2()
	{	
		ResultSet rs = null;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			rs = statement.executeQuery("SELECT idLineaAlmacen, idArticuloFK, descripcionArticulo, cantidadArticuloAlmacen FROM almacenes JOIN articulos ON almacenes.idArticuloFK=idArticulo;");
			
			
		}
		catch (SQLException sqle)
		{
			
			System.out.println("Error 11-"+sqle.getMessage());
		}
		return rs;
		
	}
	public ResultSet rellenarArtiProv()
	{	
		ResultSet rs = null;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un SELECT con JOIN
			rs = statement.executeQuery("SELECT idProveedorArticulo, idProveedorFK, nombreProveedor,idArticuloFK, descripcionArticulo, precioCompra FROM proveedoresarticulos JOIN articulos "
					+ "ON proveedoresarticulos.idArticuloFK=idArticulo JOIN proveedores ON proveedoresarticulos.idProveedorFK = idProveedor;");
			
			
		}
		catch (SQLException sqle)
		{
			
			System.out.println("Error 12-"+sqle.getMessage());
		}
		return rs;
		
	}
	
	
	
	public int bajaBaseDatos(String s)
	{
		int resultado=0;
		try
		{
			// Usa un objeto de la clase Connection el cual hemos llamado connection
			statement = connection.createStatement();
			//ejecutamos un INSERT
			statement.executeUpdate(s);
			
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 13-"+sqle.getMessage());
			resultado=-1;
		}
		return resultado;
		
		
	}
	public void desconectar()
	{
		try
		{
			if(connection!=null)
			{
				connection.close();
				System.out.println("Desconectado correctamente");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 14-"+e.getMessage());
		}	
	}
}
