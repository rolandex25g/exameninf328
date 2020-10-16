/*
 * Created by SharpDevelop.
 * User: ASUS
 * Date: 15/10/2020
 * Time: 08:00
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace Calculadora
{
	/// <summary>
	/// Description of MainForm.
	/// </summary>
	public partial class MainForm : Form
	{
		int nr = 0;
        int np = 0;
        String cadnumero="";
        String[] resultado; //Pila de expresion postfija
        String[] pila;  //Pila de operadores
        
		public MainForm()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
			
			//
			// TODO: Add constructor code after the InitializeComponent() call.
			//
		}
		
		
		
		void Button18Click(object sender, System.EventArgs e)
		{   int i;
			
			if(button18.Text=="<x"){
				//Borrar un simbolo de la derecha (numero u operaodr) de la expresion y vuelve a evaluar la expresion
				String cad=textBox1.Text;
				String res="";
				if(cad.Length>0){
					for(i=0;i<cad.Length-1;i++){
						res=res+cad[i];
					}
					textBox1.Text=res;
					
					nr=0;
					np=0;
					cad=textBox1.Text;
					textBox1.Text="";
					cadnumero="";
					for(i=0;i<cad.Length;i++){
						AgregarSimbolo(cad[i]+"");
					}
				}
			} else{
				//Limpiar Todo para empezar de nuevo
				button18.Text="<x";
				nr=0;
				np=0;
				textBox1.Text="";
				cadnumero="";
				label1.Text = "";
            	label2.Text = "";
            	label3.Text = "";
			}
			
		}
		
		//Agregar simbolo: 0,1,2,3,4,5,6,7,8,9,/,*,-,+,(,)
		void Button1Click(object sender, EventArgs e)
		{
			if(button18.Text=="c"){
				//Limpiar Todo para empezar de nuevo
				button18.Text="<x";
				nr=0;
				np=0;
				textBox1.Text="";
				cadnumero="";
				label1.Text = "";
            	label2.Text = "";
            	label3.Text = "";
			}
			AgregarSimbolo(((Button)sender).Text);			
		}
		
		
		//Mientras se introducen simbolos (numeros u operadores), se va construyendo las pilas de expresion postfija
		void AgregarSimbolo(String t)
		{
			int i;
            int nuevo_np = 0;
            int salir=0;
            if ((t == "+") || (t == "-") ||
                (t == "*") || (t == "/") || 
                (t == "(") || (t == "="))
            {
                textBox1.Text = textBox1.Text + t;

                if(cadnumero.Length>0){//Existe un numero, entonces ponerlo en resultado
                    resultado[nr] = cadnumero;
                    nr++;
                    cadnumero = "";//Vaciar para la siguiente lectura de numero
                }

                if (t != "=")
                {
                    //Analisis de precedencia
                    //Si el operador tiene menor o igual precedencia que el tope de la pila
                    //entonces, ir sacando de la pila y agregando a resultado.
                    
                    salir=0;
                    while ((salir == 0)&&(np>0))
                    {
                        if ((pila[np - 1] == "(") || (t=="(")) salir = 1;
                        else
                        {
                            if (precedencia(t) <= precedencia(pila[np - 1]))
                            {
                                resultado[nr] = pila[np - 1];
                                nr = nr + 1;
                                np = np - 1;
                            }
                            else
                            {
                                salir = 1;
                            }
                        }
                    }
                    
                    //Colocar operador en la pila
                    pila[np] = t;
                    np++;
                }
                else
                {
                    //Se introdujo =
                    //------Ya termino la entrada-------
                    //Vaciar la pila en resultado
                    for (i = np - 1; i >= 0; i--)
                    {
                        resultado[nr] = pila[i];
                        nr++;
                    }
                    evaluarExpPostfija();
                    button18.Text="c";
                }

            }
            else
            {//El operador es el )
                if ((t == ")"))
                {
                    if (cadnumero.Length > 0)
                    {//Existe un numero, entonces ponerlo en resultado
                        resultado[nr] = cadnumero;
                        nr++;
                        cadnumero = "";//Vaciar para la siguiente lectura de numero
                    }
                    textBox1.Text = textBox1.Text + t;

                    //Vaciar la pila hasta la pocision del primer (
                    for(i=np-1;i>=0;i--){
                        if (pila[i] == "(")
                        {
                            nuevo_np = i;
                        }
                    }
                    resultado[nr] = pila[np-1];
                    nr++;
                    np = nuevo_np;
                }
                else
                {
                    //Acumular en cadnumero mientras sea digitos
                    cadnumero = cadnumero + t;
                    textBox1.Text = textBox1.Text + t;
                }

            }

            //Visualizar las pilas mientras se va generando el resultado
            label1.Text = "";
            for (i = 0; i < nr; i++)
            {
                label1.Text = label1.Text + resultado[i]+", ";
            }
            label2.Text = "";
            for (i = 0; i < np; i++)
            {
                label2.Text = label2.Text + pila[i] + ", ";
            }
		}
		
		
		int precedencia(String num)
        {
            int res = 0;
            if (num == "*") return 3;
            if (num == "/") return 3;
            if (num == "+") return 2;
            if (num == "-") return 2;
            //if (num == "(") return 1;
            return res;
        }

        void evaluarExpPostfija()
        {
            int i;
            int valor1;
            int valor2;
            int res=0;
            int error=0;
            np = 0;

            for (i = 0; i < nr; i++)
            {
                if ((resultado[i] == "+") || (resultado[i] == "-") ||
                (resultado[i] == "*") || (resultado[i] == "/"))
                {
                    //Si se encontro operador, entonces sacar los 2 ultimos elementos de la pila
                    //hacer la operacion y guardar en la pila
                    valor1 = Convert.ToInt16(pila[np - 2]);
                    valor2 = Convert.ToInt16(pila[np - 1]);
                    if (resultado[i] == "+") res = valor1 + valor2;
                    if (resultado[i] == "-") res = valor1 - valor2;
                    if (resultado[i] == "*") res = valor1 * valor2;
                    if (resultado[i] == "/"){
                    	if(valor2==0){
                    		error=1;
                    		res=0;
                    	}
                    	else res = valor1 / valor2;
                    }

                    pila[np - 2] = Convert.ToString(res);
                    np = np - 1;
                }
                else
                {
                    pila[np] = resultado[i];
                    np++;
                }
            }
            //Resultado final de evaluar la expresion postfija
            label2.Text = "";
            label3.Text = pila[0];
            if(error==1)label3.Text = "No se puede dividir entre 0";

        }
		
		void MainFormLoad(object sender, EventArgs e)
		{
			pila = new string[100];
            resultado = new string[100];
            label1.Text = "";
            label2.Text = "";
            label3.Text = "";
            
		}
	}
}
