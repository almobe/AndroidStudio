package com.example.usuario.app_horarios;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this ajustes must implement the
 * {@link FragmentCalendario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCalendario#newInstance} factory method to
 * create an instance of this ajustes.
 */
public class FragmentCalendario extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the ajustes initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private EditText fromHourEtxt;
    private EditText toHourEtxt;
    private EditText motivo;
    private Switch allDaySwitch;

    private Button boton_guardar;

    private DatePickerDialog datePickerFrom;
    private DatePickerDialog datePickerTo;

    private FirebaseUser user;

    private DatabaseReference bbdd;

    private Date firstDate;
    private Date secondDate;

    private Peticion idMasAlta;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public FragmentCalendario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this ajustes using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of ajustes FragmentCalendario.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCalendario newInstance(String param1, String param2) {
        FragmentCalendario fragment = new FragmentCalendario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this ajustes
        View v = inflater.inflate(R.layout.fragment_calendario, container, false);

        /*Iniciamos diferentes variables con sus componentes del layout*/
        fromDateEtxt = (EditText) v.findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) v.findViewById(R.id.etxt_todate);
        fromHourEtxt = (EditText) v.findViewById(R.id.etxt_fromhour);
        toHourEtxt = (EditText) v.findViewById(R.id.etxt_tohour);
        motivo = (EditText) v.findViewById(R.id.reason);
        boton_guardar = (Button) v.findViewById(R.id.button_subir_peticion);
        allDaySwitch = (Switch) v.findViewById(R.id.switch1);
        allDaySwitch.requestFocus();/*Pongo el foco en el switch ya que es a única manera de que no
        salte el teclado de pantalla nada más abrir el activity*/

        //Esta es la forma de que con un solo click en el los siguientes elementos se pueda realizar el listener
        fromDateEtxt.setKeyListener(null);
        toDateEtxt.setKeyListener(null);
        fromHourEtxt.setKeyListener(null);
        toHourEtxt.setKeyListener(null);

        /*Listener de lo que hace tanto las fechas como las horas, sacan un datepickerdialog o un
        timepickerdialog respectivamente*/
         fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);


                datePickerFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String fecha = String.format("%02d",dayOfMonth) +"/"+String.format("%02d",monthOfYear+1)
                                +"/"+String.valueOf(year);
                        fromDateEtxt.setText(fecha);

                    }
                }, yy, mm, dd);

                datePickerFrom.show();
            }
        });


        toDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);


                datePickerTo = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String fecha = String.format("%02d",dayOfMonth) +"/"+String.format("%02d",monthOfYear+1)
                                +"/"+String.valueOf(year);
                        toDateEtxt.setText(fecha);
                    }
                }, yy, mm, dd);

                datePickerTo.show();
            }
        });

        allDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    // do something when check is selected
                    fromHourEtxt.setText("00:01");
                    toHourEtxt.setText("23:59");
                } else {
                    //do something when unchecked
                    fromHourEtxt.setText("");
                    toHourEtxt.setText("");
                }

            }
        });



        fromHourEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                fromHourEtxt.setText(String.format("%02d:%02d",hourOfDay,minute));
                                       // hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        toHourEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                toHourEtxt.setText(String.format("%02d:%02d",hourOfDay,minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });





        //Inicializo referencia de la DDBB
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_peticiones));

        /*
        Hago esta función para que busque las id's de las peticiones y me traiga la más alta.
        Inicializo la Peticion idMasAlta y le paso el id de la petición rescatada de la DDBB.
         */
        bbdd.orderByChild("id").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Peticion pet = dataSnapshot.getValue(Peticion.class);

                idMasAlta = new Peticion();
                idMasAlta.setId(pet.getId());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });

        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("¿Desea enviar la petición con motivo: \""+motivo.getText().toString()+"\"?")
                        .setTitle("Advertencia")
                        .setCancelable(false)
                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // metodo que se debe implementar
                                        try {
                                            //Pasar fechas al formato escogido
                                            firstDate = formatter.parse(fromDateEtxt.getText().toString());
                                            secondDate = formatter.parse(toDateEtxt.getText().toString());

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        //En caso de que la segunda fecha sea anterior a la primera...
                                        if (secondDate.before(firstDate)) {
                                            Toast.makeText(getActivity(), "Las segunda fecha es anterior a la primera, cámbialas.", Toast.LENGTH_LONG).show();

                                        } else {
                                            //Si hay algún campo vacío
                                        if (!TextUtils.isEmpty(fromDateEtxt.getText().toString()) ||
                                                !TextUtils.isEmpty(toDateEtxt.getText().toString()) ||
                                                !TextUtils.isEmpty(fromHourEtxt.getText().toString()) ||
                                                !TextUtils.isEmpty(toHourEtxt.getText().toString()) ||
                                                !TextUtils.isEmpty(motivo.getText().toString())) {


                                            user = FirebaseAuth.getInstance().getCurrentUser();

                                            //Al id de la Peticion idMasAlta le sumo 1 qeu será el nuevo id.
                                            Peticion p = new Peticion(String.valueOf(Integer.parseInt(idMasAlta.getId())+1),fromDateEtxt.getText().toString(),
                                                    toDateEtxt.getText().toString(),
                                                    fromHourEtxt.getText().toString(),
                                                    toHourEtxt.getText().toString(),
                                                    motivo.getText().toString(), user.getUid());

                                            String clave = bbdd.push().getKey();

                                            bbdd.child(clave).setValue(p);

                                            Toast.makeText(getActivity(), "Petición relaizada con éxito", Toast.LENGTH_LONG).show();
                                            allDaySwitch.setChecked(false);
                                            fromDateEtxt.setText("");
                                            fromHourEtxt.setText("");
                                            toDateEtxt.setText("");
                                            toHourEtxt.setText("");
                                            motivo.setText("");
                                        } else {
                                            Toast.makeText(getActivity(), "Debes de introducir todos los campos", Toast.LENGTH_LONG).show();

                                        }

                                    }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        return v;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * ajustes to allow an interaction in this ajustes to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
