package com.scasc.quiniela;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scasc.quiniela.basedatos.DbLogin;
import com.scasc.quiniela.entidad.Logins;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerificarActivity extends AppCompatActivity {
    Button btnMapa,btnVerificar,btnReimprimir;
    EditText etVerficarBoleta;
    TextView tvFecha,tvMonto,tvTipo,tvSorteado,tvPremio;
    String ticket,ubicacion,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verificar);
        btnMapa=findViewById(R.id.btnMapa);
        btnVerificar=findViewById(R.id.btnVerificar);
        btnReimprimir=findViewById(R.id.btnReimprimir);
        etVerficarBoleta=findViewById(R.id.etVerficarBoleta);
        tvFecha=findViewById(R.id.tvFecha);
        tvMonto=findViewById(R.id.tvMontoT);
        tvTipo=findViewById(R.id.tvTipo);
        tvSorteado=findViewById(R.id.tvSorteado);
        tvPremio=findViewById(R.id.tvPremio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //btnMapa.setEnabled(false);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ubicacion.isEmpty()){
                    Uri gmmIntentUri = Uri.parse("geo:" + ubicacion);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        Toast.makeText(VerificarActivity.this,"No hay manejador de mapa",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(VerificarActivity.this,"No hay datos para mostrar",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnReimprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ticket.isEmpty()){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ticket);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Compartir con:"));
                }else{
                    Toast.makeText(VerificarActivity.this,"No hay datos para imprimir",Toast.LENGTH_LONG).show();
                }

            }
        });
        DbLogin dbLogin=new DbLogin(VerificarActivity.this);
        ArrayList<Logins> logins;
        logins=dbLogin.mostrarLogins();
        if(!logins.isEmpty()){
            token=logins.get(0).getToken();
        }
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://utic2025.com/stilver_scavone/public/Api/infoBoleta/"+token+"/"+etVerficarBoleta.getText().toString();
                StringRequest request=new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.getBoolean("success")){
                                        ticket=jsonObject.getJSONObject("datos").getString("ticket");
                                        ubicacion=jsonObject.getJSONObject("datos").getString("ubicacion");
                                        tvFecha.setText(jsonObject.getJSONObject("datos").getString("fecha"));
                                        tvMonto.setText(jsonObject.getJSONObject("datos").getString("monto"));
                                        tvTipo.setText(jsonObject.getJSONObject("datos").getString("nombreSorteo"));
                                        tvSorteado.setText(jsonObject.getJSONObject("datos").getString("estado"));
                                        tvPremio.setText(jsonObject.getJSONObject("datos").getString("premio"));
                                    }else{
                                        ///Error al cargar sorteo
                                        ticket=null;
                                        ubicacion=null;
                                        tvFecha.setText("-");
                                        tvMonto.setText("-");
                                        tvTipo.setText("-");
                                        tvSorteado.setText("-");
                                        tvPremio.setText("-");
                                        Toast error=Toast.makeText(VerificarActivity.this,"No se encontro ticket ingresado",Toast.LENGTH_LONG);
                                        error.show();
                                    }
                                }catch (JSONException e){

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(VerificarActivity.this);
                requestQueue.add(request);
            }
        });
    }
}