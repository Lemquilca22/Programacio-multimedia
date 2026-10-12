package com.example.calculadora_multiproposito;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerOrigen, spinnerDestino;
    private TextView tvMontoOrigen, tvMontoDestino;
    private ConversorDivisas conversor;
    private String montoActual = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversor = new ConversorDivisas();

        spinnerOrigen = findViewById(R.id.spinner);
        spinnerDestino = findViewById(R.id.spinner2);
        tvMontoOrigen = findViewById(R.id.textView5);
        tvMontoDestino = findViewById(R.id.textView4);

        // adaptador usando la lista de monedas de strings.xml y   item_spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lista_monedas,
                R.layout.item_spinner
        );
        adapter.setDropDownViewResource(R.layout.item_spinner);

        spinnerOrigen.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        // iniciales por defecto
        spinnerOrigen.setSelection(0);
        spinnerDestino.setSelection(1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        configurarTeclado();
    }
    private void configurarTeclado(){
        int[] idsNumericos = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };
        View.OnClickListener listenerNumero = v -> {
            Button boton = (Button) v;
            String digito = boton.getText().toString();

            if (montoActual.equals("0")) {
                montoActual = digito;
            } else {
                montoActual += digito;
            }
            actualizarInterfaz();
        };
        for (int id : idsNumericos) {
            findViewById(id).setOnClickListener(listenerNumero);
        }

        //  BOTÓN PUNTO DECIMAL
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            // Lógica de validación: Solo permitimos un punto decimal en todo el número
            if (!montoActual.contains(".")) {
                montoActual += ".";
                actualizarInterfaz();
            }
        });

        //  BOTÓN DE BORRADO (⌫)
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (montoActual.length() >  1) {
                // Quitamos el último carácter de la cadena
                montoActual = montoActual.substring(0, montoActual.length() - 1);
            } else {
                // Si solo queda un dígito, lo regresamos a "0"
                montoActual = "0";
            }
            actualizarInterfaz();
        });

    }
    private void actualizarInterfaz() {
        // el número que el usuario está escribiendo en el campo superior
        tvMontoOrigen.setText(montoActual);

        // cálculo automático del resultado
        calcularResultado();
    }
    private void calcularResultado() {
        if (montoActual.isEmpty() || montoActual.equals(".")) {
            tvMontoDestino.setText("0.00");
            return;
        }

        try {
            // De String a Double
            double cantidad = Double.parseDouble(montoActual);

            // textos de las monedas en los Spinners
            String monedaOrigen = spinnerOrigen.getSelectedItem().toString();
            String monedaDestino = spinnerDestino.getSelectedItem().toString();

            // Funcionamiento  ConvertirDivisas
            double resultado = conversor.convertir(cantidad, monedaOrigen, monedaDestino);

            tvMontoDestino.setText(String.format("%.2f", resultado));

        } catch (NumberFormatException e) {
            tvMontoDestino.setText("0.00");
        }
    }
}