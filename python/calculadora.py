# -*- coding: utf-8 -*-
"""
Created on Thu Oct 15 16:49:13 2020

@author: ASUS
"""

from tkinter import Tk, Label, Entry, Button, ttk, messagebox

nr = 0
np = 0
cadnumero="";
resultado=["" for x in range(100)]; #Pila de expresion postfija
pila=["" for x in range(100)];  #Pila de operadores

def precedencia(num):
    if(num=="*"):return 3
    if(num=="/"):return 3
    if(num=="+"):return 2
    if(num=="-"):return 2
    return 0

def evaluarExpPostfija():
    global np
    global nr
    global resultado
    global pila
    res=0
    np=0
    error=0
    for i in range(0,nr):
        if (resultado[i]=="+" or resultado[i]=="-" or resultado[i]=="*" or resultado[i]=="/"):
            valor1 = int(pila[np - 2])
            valor2 = int(pila[np - 1])
            if(resultado[i]=="+"):res = valor1 + valor2
            if(resultado[i]=="-"):res = valor1 - valor2
            if(resultado[i]=="*"):res = valor1 * valor2
            if(resultado[i]=="/"):
                if(valor2==0):
                    error=1
                    res=0
                else:res = valor1 / valor2
            pila[np - 2] = res
            np = np - 1
        else:
            pila[np] = resultado[i]
            np=np+1
    label2.configure(text="")
    label3.configure(text=pila[0])
    if(error==1):label3.configure(text="No se puede dividir entre 0")
        

def Button18Click(t):
    global np
    global nr
    global resultado
    global pila
    global cadnumero
    if(button18['text']=="<x"):
        cad=textBox1.get()
        res=""
        if(len(cad)>0):
            for i in range(len(cad)-1):
                res=res+cad[i]
            textBox1.delete(0,"end")
            textBox1.insert(0,res)
            nr=0
            np=0
            cad=textBox1.get()
            textBox1.delete(0,"end")
            cadnumero=""
            for i in range(len(cad)):
                AgregarSimbolo(cad[i])
            
    else:
        button18.configure(text="<x")
        nr=0
        np=0
        textBox1.delete(0,"end")
        cadnumero="";
        label1.configure(text="");
        label2.configure(text="");
        label3.configure(text="");

def Button1Click(t):
    global np
    global nr
    global resultado
    global pila
    global cadnumero
    if(button18['text']=="c"):
        button18.configure(text="<x")
        nr=0
        np=0
        textBox1.delete(0,"end")
        cadnumero="";
        label1.configure(text="");
        label2.configure(text="");
        label3.configure(text="");
    AgregarSimbolo(t)

def AgregarSimbolo(t):
    global np
    global nr
    global resultado
    global pila
    global cadnumero
    nuevo_np = 0
    salir = 0
    if(t=="+" or t=="-" or t=="*" or t=="/" or t=="(" or t=="="):
        #label6.configure(text=t)
        tt=textBox1.get()+t
        textBox1.delete(0,"end")
        textBox1.insert(0,tt)
        if(len(cadnumero)>0):
            resultado[nr] = cadnumero
            nr=nr+1
            cadnumero = ""
        if(not(t=="=")):
            salir=0
            while(salir==0 and np>0):
                if(pila[np - 1]=="(" or t=="("):salir=1
                else:
                    if(precedencia(t) <= precedencia(pila[np - 1])):
                        resultado[nr] = pila[np - 1]
                        nr = nr + 1
                        np = np - 1
                    else:
                        salir=1
            pila[np] = t
            np=np+1
        else:
            for i in range(np - 1, -1,-1):
                resultado[nr] = pila[i]
                nr=nr+1
            evaluarExpPostfija()
            button18.configure(text="c")
    else:
        #label3.configure(text=t)
        if(t==")"):
            if(len(cadnumero)>0):
                resultado[nr] = cadnumero
                nr=nr+1
                cadnumero = ""
            tt=textBox1.get()+t
            textBox1.delete(0,"end")
            textBox1.insert(0,tt)
            for i in range(np - 1, -1,-1):
                if(pila[i]=="("):nuevo_np = i
            resultado[nr] = pila[np-1]
            nr=nr+1
            np = nuevo_np
        else:
            cadnumero = cadnumero + t
            tt=textBox1.get()+t
            textBox1.delete(0,"end")
            textBox1.insert(0,tt)
    label1.configure(text="")
    tt=""
    for i in range(0,nr):
        tt=tt+resultado[i]+", "
    label1.configure(text=tt)
    
    label2.configure(text="")
    tt=""
    for i in range(0,np):
        tt=tt+pila[i]+", "
    label2.configure(text=tt)

window=Tk()
window.title("Calculadora")
window.geometry("400x400")

window.columnconfigure(0,weight=1)
window.columnconfigure(1,weight=1)
window.columnconfigure(2,weight=1)
window.columnconfigure(3,weight=1)


textBox1=Entry(window,width=10)

label1=Label(window,text="")
label2=Label(window,text="")
label3=Label(window,text="")
label4=Label(window,text="Exp. Postfija")
label5=Label(window,text="Pila")
label6=Label(window,text="RESULTADO")

button1=Button(window,text="7", command=lambda: Button1Click("7"))
button2=Button(window,text="8", command=lambda: Button1Click("8"))
button3=Button(window,text="9", command=lambda: Button1Click("9"))
button4=Button(window,text="4", command=lambda: Button1Click("4"))
button5=Button(window,text="5", command=lambda: Button1Click("5"))
button6=Button(window,text="6", command=lambda: Button1Click("6"))
button7=Button(window,text="1", command=lambda: Button1Click("1"))
button8=Button(window,text="2", command=lambda: Button1Click("2"))
button9=Button(window,text="3", command=lambda: Button1Click("3"))
button10=Button(window,text="0", command=lambda: Button1Click("0"))
button11=Button(window,text="(", command=lambda: Button1Click("("))
button12=Button(window,text=")", command=lambda: Button1Click(")"))
button13=Button(window,text="/", command=lambda: Button1Click("/"))
button14=Button(window,text="*", command=lambda: Button1Click("*"))
button15=Button(window,text="-", command=lambda: Button1Click("-"))
button16=Button(window,text="+", command=lambda: Button1Click("+"))
button17=Button(window,text="=", command=lambda: Button1Click("="))
button18=Button(window,text="<x", command=lambda: Button18Click("<x"))


textBox1.grid(row=0,column=0,columnspan=4,sticky="ew", padx=5, pady=5)

label4.grid(row=1,column=0,columnspan=1,sticky="w")
label1.grid(row=1,column=1,columnspan=2,sticky="w")

label5.grid(row=2,column=0,columnspan=2,sticky="w")
label2.grid(row=2,column=1,columnspan=2,sticky="w")

label6.grid(row=3,column=0,columnspan=2,sticky="w")
label3.grid(row=3,column=1,columnspan=2,sticky="w")

button18.grid(row=4,column=3,sticky="ew", padx=5, pady=5)

button11.grid(row=5,column=2,sticky="ew", padx=5, pady=5)
button12.grid(row=5,column=3,sticky="ew", padx=5, pady=5)

button1.grid(row=6,column=0,sticky="ew", padx=5, pady=5)
button2.grid(row=6,column=1,sticky="ew", padx=5, pady=5)
button3.grid(row=6,column=2,sticky="ew", padx=5, pady=5)
button13.grid(row=6,column=3,sticky="ew", padx=5, pady=5)

button4.grid(row=7,column=0,sticky="ew", padx=5, pady=5)
button5.grid(row=7,column=1,sticky="ew", padx=5, pady=5)
button6.grid(row=7,column=2,sticky="ew", padx=5, pady=5)
button14.grid(row=7,column=3,sticky="ew", padx=5, pady=5)

button7.grid(row=8,column=0,sticky="ew", padx=5, pady=5)
button8.grid(row=8,column=1,sticky="ew", padx=5, pady=5)
button9.grid(row=8,column=2,sticky="ew", padx=5, pady=5)
button15.grid(row=8,column=3,sticky="ew", padx=5, pady=5)

button10.grid(row=9,column=1,sticky="ew", padx=5, pady=5)
button16.grid(row=9,column=3,sticky="ew", padx=5, pady=5)

button17.grid(row=10,column=3,sticky="ew", padx=5, pady=5)


window.mainloop()

