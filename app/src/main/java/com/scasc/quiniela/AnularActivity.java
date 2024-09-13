package com.scasc.quiniela;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import java.util.HashMap;
import java.util.Map;

public class AnularActivity extends AppCompatActivity {
    EditText etNumBoleta;
    Button btnAnularJugada;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anular);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DbLogin dbLogin=new DbLogin(AnularActivity.this);
        ArrayList<Logins> logins;
        logins=dbLogin.mostrarLogins();
        if(!logins.isEmpty()){
            token=logins.get(0).getToken();
        }
        etNumBoleta=(EditText)findViewById(R.id.etNumBoleta);
        btnAnularJugada=(Button)findViewById(R.id.btnAnularJugada);
        btnAnularJugada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero=etNumBoleta.getText().toString();
                if(numero.length()<2){
                    Toast aux=Toast.makeText(AnularActivity.this,"Ingrese un numero de boleta valido",Toast.LENGTH_LONG);
                    aux.show();
                    return;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(AnularActivity.this);
                builder.setMessage("Confirmar anulacion de la apuesta");
                builder.setTitle("Anular");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url="https://utic2025.com/stilver_scavone/public/Api/anularBoleta/"+token+"/"+numero;
                        StringRequest request=new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonObject=new JSONObject(response);
                                            if(jsonObject.getBoolean("success")){
                                                ///se creo el sorteo
                                                Toast exito=Toast.makeText(AnularActivity.this,"Boleta anulada",Toast.LENGTH_LONG);
                                                exito.show();
                                                finish();
                                            }else{
                                                ///Error al cargar sorteo
                                                Toast error=Toast.makeText(AnularActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG);
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
                        RequestQueue requestQueue= Volley.newRequestQueue(AnularActivity.this);
                        requestQueue.add(request);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

    }
}