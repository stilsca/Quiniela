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
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scasc.quiniela.basedatos.DbLogin;
import com.scasc.quiniela.entidad.Logins;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etDocumento,etPin;
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etDocumento=(EditText) findViewById(R.id.etDocumento);
        etPin=(EditText) findViewById(R.id.etPin);
        btnIngresar=(Button) findViewById(R.id.btnIngresar);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        verificarSiHayLoginVigente();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

    }

    private void verificarSiHayLoginVigente(){
        Toast loginNoValido= Toast.makeText(this,"Ocurrio un error al verificar la sesión", Toast.LENGTH_LONG);

        DbLogin dbLogin=new DbLogin(MainActivity.this);
        ArrayList<Logins> logins;
        logins=dbLogin.mostrarLogins();
        if(!logins.isEmpty()){
            String token=logins.get(0).getToken();
            String url="https://utic2025.com/stilver_scavone/public/Api/validezToken/"+token;
            StringRequest request=new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getBoolean("valid")){
                                    //Login valido redirigir al principal
                                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                                    startActivity(intent);
                                }else{
                                    DbLogin dbLogin=new DbLogin(MainActivity.this);
                                    dbLogin.eliminarTokens();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loginNoValido.show();
                }
            });
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }

    private void validarLogin(){
        String documento=etDocumento.getText().toString();
        String pin=etPin.getText().toString();
        Toast errorSesion= Toast.makeText(this,"Ocurrio un error al inicial la sesión", Toast.LENGTH_LONG);
        if(documento.trim().length()>1 & pin.trim().length()>3){
            String url="https://utic2025.com/stilver_scavone/public/Api/login";
            StringRequest request=new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getBoolean("exito")){
                                    //Login valido redirigir al principal
                                    DbLogin dbLogin=new DbLogin(MainActivity.this);
                                    dbLogin.insertarToken(jsonObject.getString("token"));
                                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                                    startActivity(intent);
                                }else{
                                    errorSesion.show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    etDocumento.setText(error.getMessage());
                    errorSesion.show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    // Aquí puedes agregar los parámetros que deseas enviar al servidor
                    Map<String, String> params = new HashMap<>();
                    params.put("documento", documento);
                    params.put("pin", pin);
                    return params;
                }};
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(request);
        }else{
            Toast toast= Toast.makeText(this,"Ingrese los datos requeridos", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}