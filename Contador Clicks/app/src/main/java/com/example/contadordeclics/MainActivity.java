package com.example.contadordeclics;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NonNls;

public class MainActivity extends AppCompatActivity {
    private int contador=0;
    private TextView tvContador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvContador = findViewById(R.id.textViewContador);
        Button buttonPulsar = findViewById(R.id.buttonPulsar);
        Button buttonReiniciar = findViewById(R.id.buttonReiniciar);

        if (savedInstanceState != null) {
            contador = savedInstanceState.getInt(KEY_CONTADOR, 0);
        }

        // Actualizar la interfaz con el valor del contador
        tvContador.setText(String.valueOf(contador));

        buttonPulsar.setOnClickListener(v -> {
            contador++;
            tvContador.setText(String.valueOf(contador));
        });

        buttonReiniciar.setOnClickListener(v -> {
            contador = 0;
            tvContador.setText(String.valueOf(contador));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private static final String KEY_CONTADOR ="contador";

    @Override
    protected void  onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTADOR, contador);
    }

}