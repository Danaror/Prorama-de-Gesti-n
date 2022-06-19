package es.studium.fanatic;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import java.util.Scanner;

public class Log {
	
	Scanner teclado = new Scanner (System.in);
		LocalDateTime ahora = LocalDateTime.now();
		
		String[] operacion = {"Login","Salida","Alta","Baja","Consulta","Modificación"};
		String[] sobre = {"Base de Datos","proveedores","articulos","almacenes","preveedoresaarticulos"};
		

	public Log() 
	{
		
	}
	
	public void log (int i, int j)
	{	
			
		String usuario="";
		try
		{
		
			//destino de los datos
			FileReader fr1 = new FileReader("user.txt");  //si ponemos false estaría en modo sobreescritura y machacaría la información que hubiera antes
			//Buffer de escritura
			BufferedReader br1 = new BufferedReader(fr1);
			usuario=br1.readLine();
			
			br1.close();
			
			fr1.close();
			
			System.out.println("Información guardada en el fichero");
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		
			try
			{
			
				//destino de los datos
				FileWriter fw1 = new FileWriter("Log.txt",true);  //si ponemos false estaría en modo sobreescritura y machacaría la información que hubiera antes
				//Buffer de escritura
				BufferedWriter bw1 = new BufferedWriter(fw1);
				//Objeto para la escritura
				PrintWriter salida1 = new PrintWriter(bw1);
				salida1.println("["+ahora+"]"+usuario+"["+operacion[i]+"]"+"["+sobre[j]+"]");
				salida1.close();
				bw1.close();
				
				fw1.close();
				
				System.out.println("Información guardada en el fichero");
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		
			

	}
	public void user (String usuario)
	{	
			
			try
			{
				
				
				//destino de los datos
				FileWriter fw1 = new FileWriter("user.txt",false);  //si ponemos false estaría en modo sobreescritura y machacaría la información que hubiera antes
				//Buffer de escritura
				BufferedWriter bw1 = new BufferedWriter(fw1);
				//Objeto para la escritura
				PrintWriter salida1 = new PrintWriter(bw1);
				salida1.println("["+usuario+"]");
				salida1.close();
				bw1.close();
				
				fw1.close();
				
				System.out.println("Información guardada en el fichero");
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		
			

	}
	
}
