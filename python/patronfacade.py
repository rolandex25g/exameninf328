# -*- coding: utf-8 -*-
"""
Created on Fri Oct 16 10:24:09 2020

@author: ASUS
"""

class Nomina():
    def ObtenerSalario(self,idEmpleado):
        if(idEmpleado==1):return 5
        if(idEmpleado==2):return 10
        if(idEmpleado==3):return 3
        return 0

class Beneficio():
    def ObtenerBeneficio(self,idEmpleado):
        if(idEmpleado==1):return 8
        if(idEmpleado==2):return 5
        if(idEmpleado==3):return 6
        return 0

class FacadeEmpleado():
    @staticmethod
    def IngresoTotal(idEmpleado):
        n=Nomina()
        b=Beneficio()
        salario=n.ObtenerSalario(idEmpleado)
        beneficio=b.ObtenerBeneficio(idEmpleado)
        return salario+beneficio

class Empleado():
    def __init__(self,xid,xnom):
        self.id=xid
        self.nombre=xnom

a=Empleado(1,"Juan")
b=Empleado(2,"Maria")
c=Empleado(3,"Pedro")

print("Ingreso total del empleado "+a.nombre+" es:"+str(FacadeEmpleado.IngresoTotal(a.id)))
print("Ingreso total del empleado "+b.nombre+" es:"+str(FacadeEmpleado.IngresoTotal(b.id)))
print("Ingreso total del empleado "+c.nombre+" es:"+str(FacadeEmpleado.IngresoTotal(c.id)))

