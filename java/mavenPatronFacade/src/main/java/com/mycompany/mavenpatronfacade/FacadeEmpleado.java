/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenpatronfacade;

/**
 *
 * @author ASUS
 */
public class FacadeEmpleado {
    public static double IngresoTotal(int idEmpleado){
	Nomina n=new Nomina();
        Beneficio b=new Beneficio();
	double salario=n.ObtenerSalario(idEmpleado);
	double beneficio=b.ObtenerBeneficio(idEmpleado);
	return salario+beneficio;
    }
}
