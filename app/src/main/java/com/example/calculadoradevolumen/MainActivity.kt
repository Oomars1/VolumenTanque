package com.example.calculadoradevolumen

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadoradevolumen.ui.theme.CalculadoraDeVolumenTheme

class MainActivity : ComponentActivity() {
    // Declarar spinnerMunicipality como una variable miembro de la clase MainActivity
    private lateinit var spinnerMunicipality: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner1: Spinner = findViewById(R.id.spinner1)
        val spinner2: Spinner = findViewById(R.id.spinner2)

        // Datos para el primer spinner
        val options1 = arrayOf("Selecciona una opción", "Santa Ana", "Ahuachapan", "Sonsonate")

        // Adaptador para el primer spinner
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        // Datos para el segundo spinner
        val options2 = arrayOf("Santa Ana Centro", "Santa Ana  Norte", "Santa Ana Este", "Santa Ana Oeste","Metapan")
        val options3 = arrayOf("Ahuachapan", "Atiquizaya", "Apaneca", "Concepcion Ataco", "El Refugio")
        val options4 = arrayOf("Nahuizalco", "Juayua", "Salcoatitan")

        // Configurar el listener para el primer spinner
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        // Habilitar el segundo spinner con opciones para "Opción 1"
                        val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, options2)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter2
                        spinner2.isEnabled = true
                    }
                    2 -> {
                        // Habilitar el segundo spinner con opciones para "Opción 2"
                        val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, options3)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter2
                        spinner2.isEnabled = true
                    }
                    3 -> {
                        // Habilitar el segundo spinner con opciones para "Opción 3"
                        val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, options4)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter2
                        spinner2.isEnabled = true
                    }
                    else -> {
                        // Deshabilitar el segundo spinner si no se selecciona una opción válida
                        spinner2.isEnabled = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada si no se selecciona nada
                spinner2.isEnabled = false
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "esta es una prueba de  $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraDeVolumenTheme {
        Greeting("Android")
    }
}