package com.example.usuario.app_horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/*Clase Register que estará unida al XML register y que es la que manejará toda la información
que el usuario introduzca en el formulario de registro, controlará que se introduzcan bien los datos
y
 */

public class Register extends AppCompatActivity {

    //Declaramos objetos de la interfaz
    EditText txtnombre;
    EditText txtapellidos;
    EditText txtcontraseña1;
    EditText txtcontraseña2, txtStartDate;
    Button botonRegistro;
    Spinner txtrestaurante;

    //Contador que nons ayudará a saber si ya hay un nombre igual en la bbdd
    private int contador;

    //Objeto de la clase FirebaseUser necesario para manejar aspectos del usuario conectado
    private FirebaseUser user;

    //String que utilizaremos para actualizar la contraseña del user
    String contrasenya_nueva;

    //Declaramos objeto DatabaseReference
    private DatabaseReference bbdd;
/*
    //Objetos para pasar el objeto String que recibimos del edit de fecha a un Date
    SimpleDateFormat dateFormat;
    Date fecha;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Cargamos layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Iniciamos objetos de la interfaz
        txtnombre = (EditText) findViewById(R.id.editNombre);
        txtapellidos = (EditText) findViewById(R.id.editApellidos);
        txtcontraseña1 = (EditText) findViewById(R.id.editPassword2);
        txtcontraseña2 = (EditText) findViewById(R.id.editPassword3);
        botonRegistro = (Button)findViewById(R.id.buttonRegistro);
        txtrestaurante = (Spinner)findViewById(R.id.spinnerRestaurante);
        txtStartDate = (EditText) findViewById(R.id.startDate);
/*
        //Iniciamos dateFormat para posteriormente pasar a Date el String de txtStartDate
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            fecha = dateFormat.parse(txtStartDate.getText().toString());
        }
        catch (ParseException e)
        {
            // Error, la cadena de texto no se puede convertir en fecha.
        }*/

        //Iniciamos el usuario actual y la conexión a la BBDD
        user = FirebaseAuth.getInstance().getCurrentUser();
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_empleados));

        //¿Qué va a hacer el botón del activity cuando lo pulsemos?
        botonRegistro.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                //Si hay algún campo vacío...
                if (txtcontraseña1.getText().toString().equals("")  || txtnombre.getText().toString().equals("") ||
                        txtapellidos.getText().toString().equals("") ||
                         txtcontraseña2.getText().toString().equals("") ||
                          txtStartDate.getText().toString().equals("")){
                    //..sacará un Toast informando de ello.
                    Toast toast = Toast.makeText(getApplicationContext(),"Campos vacíos", Toast.LENGTH_LONG);
                    toast.show();
                //si hay algo en todos los campos(viene de la condición anterior) y las contraseñas introducidas son iguales...
                }else if (txtcontraseña1.getText().toString().equals(txtcontraseña2.getText().toString())){
                    //...hace lo que tiene que hacer.
                    //Búsqueda Query sobre el id de usuario
                    Query q = bbdd.orderByChild(getString(R.string.campo_id)).equalTo(user.getUid());
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            contador = 0;
                            //Por cada nombre de usuario que encuentre igual el contador subiraá
                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                contador++;
                            }
                            //Si no hay no coincide la id con la de un usuario ya creado...
                            if (contador == 0) {
                                //Creamos objeto empleado pasándole por parámetro lo que se necesita para crear el objeto, en base
                                //a lo que hemos introducido en los campos y a lo que rescatamos del user
                                Empleado empleado = new Empleado(txtnombre.getText().toString(), txtapellidos.getText().toString(),
                                        user.getEmail(),
                                        txtrestaurante.getItemAtPosition(txtrestaurante.getSelectedItemPosition()).toString(),txtStartDate.getText().toString(), user.getUid(), "crew");

                                String clave = bbdd.push().getKey();
                                bbdd.child(clave).setValue(empleado);
                                Toast.makeText(Register.this, "Usuario " + empleado.getNombre() + " añadido", Toast.LENGTH_LONG).show();
                                //Creamos el Intent para pasar de un Activity a otro.
                                Intent intent = new Intent (Register.this, Principal.class);
                                //Al intent le pasamos el objeto creado para que en el siguiente Activity podamos manejarlo
                                intent.putExtra("Empleado", empleado);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this, "El usuario ya está creado", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                        });

                    contrasenya_nueva = txtcontraseña1.getText().toString();

                    user.updatePassword(contrasenya_nueva)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "User password updated.");
                                    }
                                }
                            });
                            //El else maneja la condición de que están todos los campos rellenados, pero las contraseñas difieren
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Contraseñas diferentes", Toast.LENGTH_LONG);
                            toast.show();
                        }
            }
        });



    }

}
