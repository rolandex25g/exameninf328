/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxcalculadora;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class JavaFXCalculadora extends Application {
    int nr = 0;
    int np = 0;
    String cadnumero="";
    String[] resultado=new String[100]; //Pila de expresion postfija
    String[] pila=new String[100];  //Pila de operadores
    
    TextField textBox1= new TextField();
    Label label1= new Label("");
    Label label2= new Label("");
    Label label3= new Label("");
    Label label4= new Label("Exp. Postfija");
    Label label5= new Label("Pila");
    Label label6= new Label("RESULTADO");
    
    Button button18 = new Button(); 
        
    public int precedencia(String num){
            int res = 0;
            if (num.equals("*")) return 3;
            if (num.equals("/")) return 3;
            if (num.equals("+")) return 2;
            if (num.equals("-")) return 2;
            //if (num == "(") return 1;
            return res;
    }
    public void evaluarExpPostfija()
        {
            int i;
            int valor1;
            int valor2;
            int res=0;
            int error=0;
            np = 0;

            for (i = 0; i < nr; i++)
            {
                if (resultado[i].equals("+") || resultado[i].equals("-") ||
                resultado[i].equals("*") || resultado[i].equals("/"))
                {
                    //Si se encontro operador, entonces sacar los 2 ultimos elementos de la pila
                    //hacer la operacion y guardar en la pila
                    valor1 = Integer.parseInt(pila[np - 2]);
                    valor2 = Integer.parseInt(pila[np - 1]);
                    if (resultado[i].equals("+")) res = valor1 + valor2;
                    if (resultado[i].equals("-")) res = valor1 - valor2;
                    if (resultado[i].equals("*")) res = valor1 * valor2;
                    if (resultado[i].equals("/")){
                        if(valor2==0){
                            error=1;
                            res=0;
                        }
                        else res = valor1 / valor2;
                    }

                    pila[np - 2] = String.valueOf(res);
                    np = np - 1;
                }
                else
                {
                    pila[np] = resultado[i];
                    np++;
                }
            }
            //Resultado final de evaluar la expresion postfija
            label2.setText("");
            label3.setText(pila[0]);            
            if(error==1)label3.setText("No se puede dividir entre 0");
        }
    
    public void Button18Click(String t){
        int i;
        if(t.equals("<x")){
		//Borrar un simbolo de la derecha (numero u operaodr) de la expresion y vuelve a evaluar la expresion
		String cad=textBox1.getText();
                if(cad.length()>0){
                    String res=cad.substring(0, cad.length()-1);
                    textBox1.setText(res);
                    nr=0;
                    np=0;
                    cad=textBox1.getText();
                    textBox1.setText("");
                    cadnumero="";
                    for(i=0;i<cad.length();i++){
                            AgregarSimbolo(cad.substring(i,i+1));
                    }
                }
	} else{
		//Limpiar Todo para empezar de nuevo
		button18.setText("<x");
		nr=0;
		np=0;
		textBox1.setText("");
		cadnumero="";
		label1.setText("");
          	label2.setText("");
          	label3.setText("");
	}
    }
    
    public void Button1Click(String t){    
        if(button18.getText().equals("c")){
            //Limpiar Todo para empezar de nuevo
            button18.setText("<x");
            nr=0;
            np=0;
            textBox1.setText("");
            cadnumero="";
            label1.setText("");
            label2.setText("");
            label3.setText("");
	}
        AgregarSimbolo(t);
    }
    
    public void AgregarSimbolo(String t){        
        int i;
            int nuevo_np = 0;
            int salir=0;
            if (t.equals("+") || t.equals("-") ||
                t.equals("*") || t.equals("/") || 
                t.equals("(") || t.equals("="))
            {
                textBox1.setText(textBox1.getText() + t);

                if(cadnumero.length()>0){//Existe un numero, entonces ponerlo en resultado
                    resultado[nr] = cadnumero;
                    nr++;
                    cadnumero = "";//Vaciar para la siguiente lectura de numero
                }

                if (!t.equals("="))
                {
                    //Analisis de precedencia
                    //Si el operador tiene menor o igual precedencia que el tope de la pila
                    //entonces, ir sacando de la pila y agregando a resultado.
                    
                    salir=0;
                    while ((salir == 0)&&(np>0))
                    {
                        if (pila[np - 1].equals("(") || t.equals("(")) salir = 1;
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
                    button18.setText("c");
                }

            }
            else
            {//El operador es el )
                if (t.equals(")"))
                {
                    if (cadnumero.length() > 0)
                    {//Existe un numero, entonces ponerlo en resultado
                        resultado[nr] = cadnumero;
                        nr++;
                        cadnumero = "";//Vaciar para la siguiente lectura de numero
                    }
                    textBox1.setText(textBox1.getText() + t);

                    //Vaciar la pila hasta la pocision del primer (
                    for(i=np-1;i>=0;i--){
                        if (pila[i].equals("("))
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
                    textBox1.setText(textBox1.getText() + t);
                }

            }

            //Visualizar las pilas mientras se va generando el resultado
            label1.setText("");
            for (i = 0; i < nr; i++)
            {
                label1.setText(label1.getText() + resultado[i]+", ");
            }
            label2.setText("");
            for (i = 0; i < np; i++)
            {
                label2.setText(label2.getText() + pila[i] + ", ");
            }
    }
    
    @Override
    public void start(Stage primaryStage) {

        
        
        Button button1 = new Button();        
        button1.setText("7");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button1.getText());
            }
        });
        Button button2 = new Button();        
        button2.setText("8");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button2.getText());
            }
        });
        Button button3 = new Button();        
        button3.setText("9");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button3.getText());
            }
        });
        Button button4 = new Button();        
        button4.setText("4");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button4.getText());
            }
        });
        Button button5 = new Button();        
        button5.setText("5");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button5.getText());
            }
        });
        Button button6 = new Button();        
        button6.setText("6");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button6.getText());
            }
        });
        Button button7 = new Button();        
        button7.setText("1");
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button7.getText());
            }
        });
        Button button8 = new Button();        
        button8.setText("2");
        button8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button8.getText());
            }
        });
        Button button9 = new Button();        
        button9.setText("3");
        button9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button9.getText());
            }
        });
        Button button10 = new Button();        
        button10.setText("0");
        button10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button10.getText());
            }
        });
        Button button11 = new Button();        
        button11.setText("(");
        button11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button11.getText());
            }
        });
        Button button12 = new Button();        
        button12.setText(")");
        button12.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button12.getText());
            }
        });
        Button button13 = new Button();        
        button13.setText("/");
        button13.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button13.getText());
            }
        });
        Button button14 = new Button();        
        button14.setText("*");
        button14.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button14.getText());
            }
        });
        Button button15 = new Button();        
        button15.setText("-");
        button15.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button15.getText());
            }
        });
        Button button16 = new Button();        
        button16.setText("+");
        button16.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button16.getText());
            }
        });
        Button button17 = new Button();        
        button17.setText("=");
        button17.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button1Click(button17.getText());
            }
        });
        
               
        button18.setText("<x");
        button18.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button18Click(button18.getText());
            }
        });
        
        
        //StackPane root = new StackPane();
        //root.getChildren().add(button1);
        GridPane root = new GridPane();
        
        //Definir 5 columnas de ancho 20% del maximo
        ColumnConstraints col1= new ColumnConstraints();
        col1.setPercentWidth(4);
        ColumnConstraints col2= new ColumnConstraints();
        col2.setPercentWidth(23);
        ColumnConstraints col3= new ColumnConstraints();
        col3.setPercentWidth(23);
        ColumnConstraints col4= new ColumnConstraints();
        col4.setPercentWidth(23);
        ColumnConstraints col5= new ColumnConstraints();
        col5.setPercentWidth(23);
        ColumnConstraints col6= new ColumnConstraints();
        col6.setPercentWidth(4);
        root.getColumnConstraints().addAll(col1,col2,col3,col4,col5,col6);
        
        GridPane.setRowIndex(textBox1, 0);
        GridPane.setColumnIndex(textBox1, 1);    
        GridPane.setColumnSpan(textBox1, 4);
        root.getChildren().add(textBox1);
        
        GridPane.setRowIndex(label4, 1);
        GridPane.setColumnIndex(label4, 1);
        GridPane.setColumnSpan(label4, 2);         
        root.getChildren().add(label4);
        
        GridPane.setRowIndex(label1, 1);
        GridPane.setColumnIndex(label1, 2);        
        GridPane.setColumnSpan(label1, 2);
        root.getChildren().add(label1);
        
        GridPane.setRowIndex(label5, 2);
        GridPane.setColumnIndex(label5, 1);
        GridPane.setColumnSpan(label5, 2);
        root.getChildren().add(label5);
        
        GridPane.setRowIndex(label2, 2);
        GridPane.setColumnIndex(label2, 2);
        GridPane.setColumnSpan(label2, 2);
        root.getChildren().add(label2);
        
        GridPane.setRowIndex(label6, 3);
        GridPane.setColumnIndex(label6, 1);
        GridPane.setColumnSpan(label6, 2);
        root.getChildren().add(label6);
        
        GridPane.setRowIndex(label3, 3);
        GridPane.setColumnIndex(label3, 2);
        GridPane.setColumnSpan(label3, 3);
        root.getChildren().add(label3);
        
        GridPane.setRowIndex(button18, 4);
        GridPane.setColumnIndex(button18, 4);        
        root.getChildren().add(button18);        
        
        GridPane.setRowIndex(button11, 5);
        GridPane.setColumnIndex(button11, 3);        
        root.getChildren().add(button11);        
        GridPane.setRowIndex(button12, 5);
        GridPane.setColumnIndex(button12, 4);        
        root.getChildren().add(button12);
        
        GridPane.setRowIndex(button1, 6);
        GridPane.setColumnIndex(button1, 1);        
        root.getChildren().add(button1);
        GridPane.setRowIndex(button2, 6);
        GridPane.setColumnIndex(button2, 2);        
        root.getChildren().add(button2);
        GridPane.setRowIndex(button3, 6);
        GridPane.setColumnIndex(button3, 3);        
        root.getChildren().add(button3);
        GridPane.setRowIndex(button13, 6);
        GridPane.setColumnIndex(button13, 4);        
        root.getChildren().add(button13);
        
        GridPane.setRowIndex(button4, 7);
        GridPane.setColumnIndex(button4, 1);        
        root.getChildren().add(button4);
        GridPane.setRowIndex(button5, 7);
        GridPane.setColumnIndex(button5, 2);        
        root.getChildren().add(button5);
        GridPane.setRowIndex(button6, 7);
        GridPane.setColumnIndex(button6, 3);        
        root.getChildren().add(button6);
        GridPane.setRowIndex(button14, 7);
        GridPane.setColumnIndex(button14, 4);        
        root.getChildren().add(button14);
        
        GridPane.setRowIndex(button7, 8);
        GridPane.setColumnIndex(button7, 1);        
        root.getChildren().add(button7);
        GridPane.setRowIndex(button8, 8);
        GridPane.setColumnIndex(button8, 2);        
        root.getChildren().add(button8);
        GridPane.setRowIndex(button9, 8);
        GridPane.setColumnIndex(button9, 3);        
        root.getChildren().add(button9);
        GridPane.setRowIndex(button15, 8);
        GridPane.setColumnIndex(button15, 4);        
        root.getChildren().add(button15);
        
        GridPane.setRowIndex(button10, 9);
        GridPane.setColumnIndex(button10, 2);        
        root.getChildren().add(button10);
        GridPane.setRowIndex(button16, 9);
        GridPane.setColumnIndex(button16, 4);        
        root.getChildren().add(button16);
        
        GridPane.setRowIndex(button17, 10);
        GridPane.setColumnIndex(button17, 4);        
        root.getChildren().add(button17);
        
        Scene scene = new Scene(root, 300, 280);
        
        primaryStage.setTitle("Calculadora");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
