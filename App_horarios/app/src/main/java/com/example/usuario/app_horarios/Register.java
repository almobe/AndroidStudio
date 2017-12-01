package com.example.usuario.app_horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import model.Empleado;

/*Clase Register que estará unida al XML register y que es la que manejará toda la información
que el usuario introduzca en el formulario de registro, controlará que se introduzcan bien los datos
y
 */

public class Register extends AppCompatActivity {

    //Declaramos objetos de la interfaz
    EditText txtnombre;
    EditText txtapellidos;
    EditText txtemail;
    EditText txtcontraseña1;
    EditText txtcontraseña2;
    Button botonRegistro;
    Spinner txtrestaurante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Iniciamos objetos de la interfaz
        txtnombre = (EditText) findViewById(R.id.editNombre);
        txtapellidos = (EditText) findViewById(R.id.editApellidos);
        txtemail = (EditText) findViewById(R.id.editEmail2);
        txtcontraseña1 = (EditText) findViewById(R.id.editPassword2);
        txtcontraseña2 = (EditText) findViewById(R.id.editPassword3);
        botonRegistro = (Button)findViewById(R.id.buttonRegistro);
        txtrestaurante = (Spinner)findViewById(R.id.spinnerRestaurante);

        //¿Qué va a hacer el botón del activity cuando lo pulsemos?
        botonRegistro.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                //Si hay algún campo vacío...
                if (txtcontraseña1.getText().toString().equals("")  || txtnombre.getText().toString().equals("") ||
                        txtapellidos.getText().toString().equals("") ||
                        txtemail.getText().toString().equals("") || txtcontraseña2.getText().toString().equals("")){
                    //..sacará un Toast informando de ello.
                    Toast toast = Toast.makeText(getApplicationContext(),"Campos vacíos", Toast.LENGTH_LONG);
                    toast.show();
                //si hay algo en todos los campos(viene de la condición anterior) y las contraseñas introducidas son iguales...
                }else if (txtcontraseña1.getText().toString().equals(txtcontraseña2.getText().toString())){
                    //...hace lo que tiene que hacer.
                    //Creamos objeto empleado pasándole por parámetro lo que se necesita para crear el objeto, en base
                    //a lo que hemos introducido en los campos
                    Empleado empleado = new Empleado(txtnombre.getText().toString(),txtapellidos.getText().toString(),
                            txtemail.getText().toString(),txtcontraseña1.getText().toString(),
                            txtrestaurante.getItemAtPosition(txtrestaurante.getSelectedItemPosition()).toString());

                    //Creamos el Intent para pasar de un Activity a otro.
                    Intent new_intent3 = new Intent(Register.this, WaitAuthorization.class);
                    //Al intent le pasamos el objeto creado para que en el siguiente Activity podamos manejarlo
                    new_intent3.putExtra("Empleado", empleado);
                    //Cambiamos de Activity
                    startActivity(new_intent3);
                    //TODO: Aquí deberíamos hacer la llamada a la BBDD para gestionar el login

                //El else maneja la condición de que están todos los campos rellenados, pero las contraseñas difieren
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Contraseñas diferente", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });



    }

}
