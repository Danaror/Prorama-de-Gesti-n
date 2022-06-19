package es.studium.fanatic;

import java.io.IOException;

public class ayuda {
	
	 

	public static void main(String[] args)
	{
		try 
		{
			Runtime.getRuntime().exec("hh.exe ayuda.chm");
		}
		catch (IOException e)
		{
		 e.printStackTrace();	
		}
		

	}

}