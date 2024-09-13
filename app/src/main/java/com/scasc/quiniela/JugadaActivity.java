package com.scasc.quiniela;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scasc.quiniela.adaptador.ListaJugadasAdapter;
import com.scasc.quiniela.basedatos.DbLogin;
import com.scasc.quiniela.entidad.Jugadas;
import com.scasc.quiniela.entidad.Logins;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JugadaActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    double lat=0, lon=0;
    RecyclerView listaJugadas;
    ArrayList<Jugadas> listaArrayJugadas;
    ListaJugadasAdapter adapter;
    Spinner cmbPosicion;
    Button btnAgregar, btnFinalizar;
    EditText etNumero, etMonto;
    ItemTouchHelper itemTouchHelper;
    TextView tvTotal;
    private LocationManager locManager;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jugada);

        listaJugadas = findViewById(R.id.listaJugadas);
        listaJugadas.setLayoutManager(new LinearLayoutManager(this));
        cmbPosicion = (Spinner) findViewById(R.id.cmbPosicion);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        etNumero = (EditText) findViewById(R.id.etNumero);
        etMonto = (EditText) findViewById(R.id.etMonto);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(listaJugadas);


        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.jugadas_posiciones, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPosicion.setAdapter(adapter3);

        listaArrayJugadas = new ArrayList<>();
        adapter = new ListaJugadasAdapter(listaArrayJugadas);

        listaJugadas.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int monto = Integer.parseInt(etMonto.getText().toString());
                    int numero = Integer.parseInt(etNumero.getText().toString());
                    int posicion = cmbPosicion.getSelectedItemPosition() + 1;
                    if (monto < 500) {
                        Toast aux2 = Toast.makeText(JugadaActivity.this, "El monto de la apuesta debe ser mayor a 500 Gs.", Toast.LENGTH_LONG);
                        aux2.show();
                        return;
                    }
                    if (numero < 0 || numero > 999) {
                        Toast aux2 = Toast.makeText(JugadaActivity.this, "Los numeros para apostar van del 000 al 999", Toast.LENGTH_LONG);
                        aux2.show();
                        return;
                    }
                    int tipo=1;
                    if((numero+"").length()==2){
                        tipo=2;
                    }else{tipo=3;}
                    listaArrayJugadas.add(new Jugadas(listaArrayJugadas.size() + 1, posicion, monto, tipo, numero));
                    adapter.notifyDataSetChanged();
                    etMonto.setText("");
                    etNumero.setText("");
                    cmbPosicion.setSelection(0, true);
                    actualizarTotal();
                    etMonto.requestFocus();
                } catch (NumberFormatException ex) {
                    Toast aux2 = Toast.makeText(JugadaActivity.this, "Ingrese los datos correctamente", Toast.LENGTH_LONG);
                    aux2.show();
                }
            }
        });
        DbLogin dbLogin=new DbLogin(JugadaActivity.this);
        ArrayList<Logins> logins;
        logins=dbLogin.mostrarLogins();
        if(!logins.isEmpty()){
            token=logins.get(0).getToken();
        }
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaArrayJugadas.isEmpty()) {
                    Toast aux = Toast.makeText(JugadaActivity.this, "Ingrese por lo menos una jugada", Toast.LENGTH_LONG);
                    aux.show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(JugadaActivity.this);
                builder.setMessage("Confirmar la apuesta");
                builder.setTitle("Apostar");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String jugadas = "[";
                        for (int j = 0; j < listaArrayJugadas.size(); j++) {
                            jugadas += listaArrayJugadas.get(j).toString() + ",";
                        }
                        jugadas = jugadas.substring(0, jugadas.length() - 1);
                        jugadas += "]";
                        final String numeros = jugadas;
                        String url = "https://utic2025.com/stilver_scavone/public/Api/cargarSorteo";
                        StringRequest request = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject.getBoolean("success")) {
                                                ///se creo el sorteo
                                                //Toast exito = Toast.makeText(JugadaActivity.this, "Funciona: " + jsonObject.getString("idTicket"), Toast.LENGTH_LONG);
                                                //exito.show();
                                                Intent sendIntent = new Intent();
                                                sendIntent.setAction(Intent.ACTION_SEND);
                                                sendIntent.putExtra(Intent.EXTRA_TEXT, jsonObject.getString("ticket"));
                                                sendIntent.setType("text/plain");
                                                startActivity(Intent.createChooser(sendIntent, "Compartir con:"));
                                                finish();
                                            } else {
                                                ///Error al cargar sorteo
                                                Toast error = Toast.makeText(JugadaActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG);
                                                error.show();
                                            }
                                        } catch (JSONException e) {

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("token", token);
                                params.put("idSorteo", "1");
                                params.put("ubicacion", lat+","+lon);
                                params.put("numeros", numeros);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(JugadaActivity.this);
                        requestQueue.add(request);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    5f,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(JugadaActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            listaArrayJugadas.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
            actualizarTotal();
        }
    };

    public void actualizarTotal() {
        int suma = 0;
        for (int i = 0; i < listaArrayJugadas.size(); i++) {
            suma += listaArrayJugadas.get(i).getMonto();
        }
        tvTotal.setText("Total: " + suma + " Gs.");
    }
}