package com.example.usuario.quicktrade;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usuario.quicktrade.model.Producto;
import com.example.usuario.quicktrade.model.usuarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by USUARIO on 21/12/2017.
 */

public class MisProductos extends AppCompatActivity {

    //Elelementos que hay en el layout
    private EditText edit_nombre_producto;
    private EditText edit_descripicion_producto;
    private Spinner spinner_categoria;
    private EditText edit_precio_producto;
    private Button button_agregar_producto;
    private ImageView imagen;
    private Button boton_imagen;
    private Uri imageUri;
    private Uri selectedImage;
    private static final int PHOTO_SELECTED = 1;

    //Objeto de la autenticación necesario para saber cual es el usuario que está ahora mismo utilizando la app
    private FirebaseAuth mAuth;

    private FirebaseStorage storage;

    //Declaramos objeto DatabaseReference
    DatabaseReference bbdd;
    DatabaseReference bbdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_productos);

        edit_nombre_producto = (EditText) findViewById(R.id.editText_nombre_producto);
        edit_descripicion_producto = (EditText) findViewById(R.id.editText_descripcion_producto);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        edit_precio_producto = (EditText) findViewById(R.id.editText_precio_producto);
        button_agregar_producto = (Button) findViewById(R.id.button_nuevo_producto);
        imagen = (ImageView) findViewById(R.id.imagen);
        boton_imagen = (Button) findViewById(R.id.button_imagen);

        //Iniciamos objeto con la referencia a nuestro nodo de usuarios
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        //Iniciamos objeto autenticathor
        mAuth = FirebaseAuth.getInstance();

        boton_imagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"),
                        PHOTO_SELECTED);

            }
        });}

        protected void onActivityResult(int requestCode, int resultCode,
        Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            Uri selectedImageUri = null;


            String filePath = null;
            switch (requestCode) {
                case PHOTO_SELECTED:
                    if (resultCode == Activity.RESULT_OK) {
                        selectedImage = imageReturnedIntent.getData();
                        String selectedPath=selectedImage.getPath();
                        if (requestCode == PHOTO_SELECTED) {

                            if (selectedPath != null) {
                                InputStream imageStream = null;
                                try {
                                    imageStream = getContentResolver().openInputStream(
                                            selectedImage);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                                Bitmap bmp = BitmapFactory.decodeStream(imageStream);


                                imagen.setImageBitmap(bmp);

                            }
                        }
                    }
                    break;
            }







        button_agregar_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos del producto en varibales
                final String nombre_producto = edit_nombre_producto.getText().toString();
                final String descripicion_producto = edit_descripicion_producto.getText().toString();
                final String categoria = spinner_categoria.getItemAtPosition(spinner_categoria.getSelectedItemPosition()).toString();
                final double precio = Double.parseDouble(edit_precio_producto.getText().toString());

                //El nombre de usuario del producto será el email del usuario actual
                final String nombre_usuario = mAuth.getCurrentUser().getEmail();

                 storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Points to "images"
                StorageReference imagesRef = storageRef.child("images");

                // Points to "images/space.jpg"
                // Note that you can use variables to create child values
                String fileName = "space.jpg";


                //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                StorageReference riversRef = storageRef.child("images/"+selectedImage.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(selectedImage);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

                    //Condicionales para saber si nos dejamos algún campo por rellenar
                    if (!TextUtils.isEmpty(nombre_producto)) {
                        if (!TextUtils.isEmpty(descripicion_producto)) {
                            if (!TextUtils.isEmpty(String.valueOf(precio))) {

                                //Se crea el producto
                                Producto p = new Producto(nombre_producto,descripicion_producto,categoria,precio,nombre_usuario);

                                //Se inicializa el objeto de la bbdd en el nodo productos
                                bbdd2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_productos));

                                //Creamos objeto clave
                                String clave = bbdd2.push().getKey();

                                //Hacemos un setValue sobre el nodo de la bbdd
                                bbdd2.child(clave).setValue(p);

                                Toast.makeText(MisProductos.this, "Producto introducido correctamente", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(MisProductos.this, "Debes de introducir el precio del producto", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MisProductos.this, "Debes de introducir la descripción del producto", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MisProductos.this, "Debes de introducir el nombre del producto", Toast.LENGTH_LONG).show();
                    }

            }
        });

}

}
