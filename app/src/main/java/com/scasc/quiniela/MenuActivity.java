package com.scasc.quiniela;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    Button btnRegistrar,btnAnular,btnVerificar,btnSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);
        btnAnular=(Button)findViewById(R.id.btnAnular);
        btnVerificar=findViewById(R.id.btnVerificar);
        btnSalir=findViewById(R.id.btnSalir);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,JugadaActivity.class);
                startActivity(intent);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
        btnAnular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentw = new Intent(MenuActivity.this,AnularActivity.class);
                startActivity(intentw);
            }
        });
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentx = new Intent(MenuActivity.this,VerificarActivity.class);
                startActivity(intentx);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}