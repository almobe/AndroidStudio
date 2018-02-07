package com.example.usuario.mynavigationdrawer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.usuario.mynavigationdrawer.Fragments.FragmentCamara;
import com.example.usuario.mynavigationdrawer.Fragments.FragmentMaps;
import com.example.usuario.mynavigationdrawer.Fragments.Fragment_Dinamico;
import com.example.usuario.mynavigationdrawer.Fragments.MyFragment;
import com.example.usuario.mynavigationdrawer.Model.Elemento;

import static android.R.style.Theme;

//En la declaración de la clase implementamos todos los fragments que vamos a utilizar
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentMaps.OnFragmentInteractionListener, FragmentCamara.OnFragmentInteractionListener, MyFragment.OnFragmentInteractionListener, Fragment_Dinamico.OnFragmentInteractionListener,IComunica_Fragments {

    //Declaramos el fragment dinámico para posteriormente iniciarlo y utilizarlo
    Fragment_Dinamico fragment_dinamico;

    //MyFragment myFragment;

    //Declaramos objeto de la clase MediaPlayer que crearemos para que suene un sonido al ejecutar la aplicación
    MediaPlayer sonido;

    private TextView texto;

    //El onCreate empezará con la llamada al layout activity_main y cargando el drawer_layout en un toolbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        texto= (TextView) findViewById(R.id.texto_bienvenida);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Creamos el MediaPlayer en base a un sonido guardado en la carpeta raw
        sonido = MediaPlayer.create(getApplicationContext(),R.raw.sonido2);
        //Lo iniciamos para que suene
        sonido.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //Condicional para que haga una cosa u otra marcando la opción del drawer que elijamos
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Declaramos Fragment
        Fragment miFragment = null;

        //Booleano para saber si hay algún fragment seleccionado
        boolean fragmentSeleccionado=false;

        //Dependiendo de la opción seleccionada del Drawer, asignara un Fragment u otro al miFragment
        //y pasará el boleano a verdadero
        if (id == R.id.nav_camera) {

            miFragment = new FragmentCamara();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_gallery) {

            miFragment = new MyFragment();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_slideshow) {

            miFragment = new FragmentMaps();
            fragmentSeleccionado=true;

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //Si el boleano es verdadero
        if (fragmentSeleccionado){
            //Cargamos el nuevo fragment en el content_main
            texto.setText("");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Cargamos el método que nos creamos para pasar elementos de un Fragment a otro
    @Override
    public void enviarElemento(Elemento elemento) {

        fragment_dinamico = new Fragment_Dinamico();
        //Generamos el objeto Bundle para enviar información
        Bundle bundle_envio=new Bundle();
        //Al bundle envio le metemos un objeto serializable
        bundle_envio.putSerializable("objeto",elemento);

        fragment_dinamico.setArguments(bundle_envio);

        //cargamos el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment_dinamico).addToBackStack(null).commit();
        //el addToBackStack optimiza el reemplazo
    }



}
