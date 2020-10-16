/*
 * Created by SharpDevelop.
 * User: ASUS
 * Date: 16/10/2020
 * Time: 08:54
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;

namespace PatronFacade
{
	//Sub proceso para obtener el salario
	class Nomina{
		public double ObtenerSalario(int idEmpleado){
			if(idEmpleado==1)return 5;
			if(idEmpleado==2)return 10;
			if(idEmpleado==3)return 3;
			return 0;
		}
	}
	//Sub proceso  para obtener el beneficio
	class Beneficio{
		public double ObtenerBeneficio(int idEmpleado){
			if(idEmpleado==1)return 8;
			if(idEmpleado==2)return 5;
			if(idEmpleado==3)return 6;
			return 0;
		}
	}
	
	//El Facade Empleado encapsula las operaciones complejas del calculos del ingreso total del empleado
	class FacadeEmpleado{
		public static double IngresoTotal(int idEmpleado){
			Nomina n=new Nomina();
			Beneficio b=new Beneficio();
			double salario=n.ObtenerSalario(idEmpleado);
			double beneficio=b.ObtenerBeneficio(idEmpleado);
			return salario+beneficio;
		}
	}
	class Empleado{
		public int id;
		public String nombre;
		public Empleado(int xid,String xnom){
			id=xid;
			nombre=xnom;			
		}
	}
	class Program
	{
		public static void Main(string[] args)
		{
			Empleado a=new Empleado(1,"Juan");
			Empleado b=new Empleado(2,"Maria");
			Empleado c=new Empleado(3,"Pedro");
			
			Console.WriteLine("Ingreso total del empleado "+a.nombre+" es:"+FacadeEmpleado.IngresoTotal(a.id));
			Console.WriteLine("Ingreso total del empleado "+b.nombre+" es:"+FacadeEmpleado.IngresoTotal(b.id));
			Console.WriteLine("Ingreso total del empleado "+c.nombre+" es:"+FacadeEmpleado.IngresoTotal(c.id));
			
			Console.ReadKey(true);
		}
	}
}