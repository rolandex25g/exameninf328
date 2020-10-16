/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.mycompany.mavenpatronfacade.Empleado;
import com.mycompany.mavenpatronfacade.FacadeEmpleado;

/**
 *
 * @author ASUS
 */
public class Principal {
    public static void main(String[] args)
    {
        Empleado a=new Empleado(1,"Juan");
	Empleado b=new Empleado(2,"Maria");
	Empleado c=new Empleado(3,"Pedro");
        
        System.out.println("Ingreso total del empleado "+a.nombre+" es:"+FacadeEmpleado.IngresoTotal(a.id));
        System.out.println("Ingreso total del empleado "+b.nombre+" es:"+FacadeEmpleado.IngresoTotal(b.id));
        System.out.println("Ingreso total del empleado "+c.nombre+" es:"+FacadeEmpleado.IngresoTotal(c.id));
    }
}
