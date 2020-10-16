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
public class Nomina {
    public double ObtenerSalario(int idEmpleado){
	if(idEmpleado==1)return 5;
	if(idEmpleado==2)return 10;
	if(idEmpleado==3)return 3;
	return 0;
    }
}
