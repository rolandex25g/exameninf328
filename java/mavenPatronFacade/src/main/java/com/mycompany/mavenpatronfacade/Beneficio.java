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
public class Beneficio {
    public double ObtenerBeneficio(int idEmpleado){
	if(idEmpleado==1)return 8;
	if(idEmpleado==2)return 5;
	if(idEmpleado==3)return 6;
	return 0;
    }
}
