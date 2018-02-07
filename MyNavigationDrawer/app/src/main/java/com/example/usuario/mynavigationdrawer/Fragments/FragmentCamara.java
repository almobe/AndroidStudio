package com.example.usuario.mynavigationdrawer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.usuario.mynavigationdrawer.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCamara.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCamara#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCamara extends Fragment {

    //Iniciamos el botón dónde residirá la imagen que creemos
    private ImageButton botonImagen;

    //Integer que utilizaremos posteriormente
    public final static int RESP_TOMAR_FOTO = 1000;

    //Objeto Uri donde estará el Uri del objeto creado
    private Uri mImageUri;

    // Required empty public constructor
    public FragmentCamara() {
   }

    //Constructor con newInstance
    public static FragmentCamara newInstance(String param1, String param2) {
        FragmentCamara fragment = new FragmentCamara();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_camara, container, false);
        botonImagen = (ImageButton) v.findViewById(R.id.imageButton2);

        botonImagen.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //Archivo temporal donde guardaremos la fotografía que vamos a hacer
                File photo = null;
                try {
                    // place where to store camera taken picture
                    photo = FragmentCamara.createTemporaryFile("picture", ".jpg", getContext());

                    photo.delete();
                } catch (Exception e) {
                    Log.v(getClass().getSimpleName(),
                            "Can't create file to take picture!");
                }

                //Ruta del archivo
                mImageUri = Uri.fromFile(photo);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(intent, RESP_TOMAR_FOTO);
            }
        });

        return v;
    }

    public static File createTemporaryFile(String part, String ext,
                                           Context myContext) throws Exception {
        //Método de creación del archivo temporal
        File tempDir = myContext.getExternalCacheDir();
        tempDir = new File(tempDir.getAbsolutePath() + "/temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    //Aquí implantamos la imagen en el imageButton
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESP_TOMAR_FOTO && resultCode == RESULT_OK) {
            botonImagen.setImageURI(mImageUri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
