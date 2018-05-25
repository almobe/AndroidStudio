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
import com.google.firebase.database.ValueEventListener;

/**
 * Created by USUARIO on 30/03/2018.
 */

public class Ajustes extends AppCompatActivity{

    private EditText editName,editSurnames, editPassword, editPassword2;
    private Spinner spinnerRestaurant;
    private Button update,logout;

    private Empleado empleado;
    private String clave;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;

    //Objeto de la clase FirebaseUser necesario para manejar aspectos del usuario conectado
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes);


        editName = (EditText) findViewById(R.id.editNombre);
        editSurnames = (EditText) findViewById(R.id.editApellidos);
        editPassword = (EditText) findViewById(R.id.editContrasenya1);
        editPassword2 = (EditText) findViewById(R.id.editContasenya2);
        spinnerRestaurant = (Spinner) findViewById(R.id.spinnerRestaurante);
        update = (Button) findViewById(R.id.button2);
        logout = (Button) findViewById(R.id.button_logout);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_empleados));
        user = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbdd.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Recorrer todos los empleados
                        for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                            empleado = dataSnapshot2.getValue(Empleado.class);
                            clave =dataSnapshot2.getKey();
                        }
                                //Si ha variado una de las condiciones se actualiza el empleado y...
                                if (!editName.getText().toString().isEmpty()) {
                                    empleado.setNombre(editName.getText().toString());
                                }
                                if (!editSurnames.getText().toString().isEmpty()) {
                                    empleado.setApellidos(editSurnames.getText().toString());
                                }
                                if (!spinnerRestaurant.getItemAtPosition(spinnerRestaurant.getSelectedItemPosition()).toString().isEmpty()) {
                                    empleado.setRestaurante(spinnerRestaurant.getItemAtPosition(spinnerRestaurant.getSelectedItemPosition()).toString());
                                }
                                if (!editPassword.getText().toString().isEmpty() && editPassword.getText().toString().equals(editPassword2.getText().toString())) {
                                    user.updatePassword(editPassword.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("TAG", "User password updated.");
                                                    }
                                                }
                                            });
                                }else if (!editPassword.getText().toString().isEmpty() && !(editPassword.getText().toString().equals(editPassword2.getText().toString()))){
                                    Toast.makeText(Ajustes.this, "Rellene las dos contraseñas iguales para cambiar las contraseñas", Toast.LENGTH_LONG).show();
                                }

                                ///...se hace un setValue en la bbdd
                                bbdd.child(clave).setValue(empleado);
                                Toast.makeText(Ajustes.this, "Datos modificados", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //Método para hacer un logout del usuario registrado en la app en ese momento y pasar a la pestaña principal.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
