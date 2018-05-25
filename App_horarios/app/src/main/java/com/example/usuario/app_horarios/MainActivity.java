package com.example.usuario.app_horarios;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Declaramos objetos de la interfaz que utilizaremos
    private Button button_login;
    private Button button_register;
    private EditText editEmail;
    private EditText editPassword;
    private Intent intent = null;
    private TextView textContrasenya;

    private DatabaseReference bbdd;
    private FirebaseAuth mAuth;



    private Empleado empleado, empleadoBuscado;
    private String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicalizamos objetos de la interfaz
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        editEmail = (EditText) findViewById(R.id.editEmail) ;
        editPassword = (EditText) findViewById(R.id.editPassword);
        textContrasenya = (TextView) findViewById(R.id.textRecuperarContrasenya);

        mAuth = FirebaseAuth.getInstance();
        if (editEmail.getText().toString() != "" && editEmail.getText().toString() != null ){
        textContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddress = editEmail.getText().toString();
                    //Método que envía el mail de reestablecimiento de contraseña a la dirección indicada
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(BATTERY_SERVICE, "Email sent.");
                                    }
                                }
                            });
                Toast.makeText(MainActivity.this,"Se ha mandado un email con las instruciones para recuperar la constraseña a la dirección indicada",Toast.LENGTH_LONG).show();
            }
        });
        }
        //Inicializo referencia de la DDBB
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_empleados));

        empleado = new Empleado();

        //Si pulsamos un botón cogerá los datos, creará un intent que pase al activity Principal
        // y le pasamos los datos al método login()
        button_login.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //Strings que rescatamos de los editText
                final String emailL = editEmail.getText().toString();
                final String passwordL = editPassword.getText().toString();


                /*
        Hago esta función para que busque las id's de las peticiones y me traiga la más alta.
        Inicializo la Peticion idMasAlta y le paso el id de la petición rescatada de la DDBB.
         */
                bbdd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        //Recorremos todos los productos
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                           empleadoBuscado = dataSnapshot2.getValue(Empleado.class);

                            //Si coincide la categoría del spinner con  la categoría del producto
                            if(empleadoBuscado.getEmail().equals(emailL)){
                                //añadimos producto al array
                                empleado = empleadoBuscado;
                            }

                        }

                        if (empleado.getId()!=null){
                            rol = empleado.getRol();
                            if (rol.equals("manager")) {
                                intent = new Intent(MainActivity.this, ManagerActivity.class);
                            }else{
                                intent = new Intent(MainActivity.this, Principal.class);
                            }

                            login (emailL,passwordL, intent);
                        }else{
                            Toast.makeText(MainActivity.this,"No estás resgistrado en la app.",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });




            }
        });

        //Si pulsamos el otro botón cogerá los datos, creará un intent que pase al activity Register
        // y le pasamos los datos al método login()
        button_register.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                //Strings que rescatamos de los editText
                String emailL = editEmail.getText().toString();
                String passwordL = editPassword.getText().toString();
                intent = new Intent(MainActivity.this, Register.class);
                login (emailL,passwordL, intent);

            }
        });


    }

    //Método login para el acceso autorizado a la app, al cual también le pasaremos el
    // correspondiente intent para que pase a un Activity u otro
    private void login (String email, String password, final Intent intent){


            if (password.isEmpty()){
                password=" ";
            }

            //mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)//Método sobre el objeto autentication que varía del de registro
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("intent", "hola"+intent);
                                    Toast.makeText(MainActivity.this, "SignIn succesful."+user.getUid(),
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(intent);


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "SignIn failed."+task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


    }
}
