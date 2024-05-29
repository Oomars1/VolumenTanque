package com.example.calculadoradevolumen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
import android.content.DialogInterface
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    private lateinit var spinnerMunicipality: Spinner
    private lateinit var textViewSelection: TextView
    private lateinit var radioGroup: RadioGroup



    // Para el botón que aparece si o no
    private lateinit var radioGroupYesNo: RadioGroup
    private lateinit var radioYes: RadioButton
    private lateinit var radioNo: RadioButton
    private lateinit var additionalInput: EditText
        private lateinit var lotInput: EditText

    //para 5 habitantes
    private lateinit var radioGrouppoblacion: RadioGroup
    private lateinit var porDefecto: RadioButton
    private lateinit var agregaNuevo: RadioButton
    private lateinit var personaXLote: EditText

    private lateinit var habitantesInput: EditText

    //PARA LO DE PERIODO DE DISENO
    private lateinit var radioGroupPeriodoDiseno: RadioGroup
    private lateinit var valorDiseno: RadioButton
    private lateinit var porDefectoPeriodo: RadioButton
    private lateinit var inputPeriodoDiseno: EditText
    private lateinit var clearButton: Button
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner1: Spinner = findViewById(R.id.spinner1)
        val spinner2: Spinner = findViewById(R.id.spinner2)
        spinnerMunicipality = spinner2
        val button: Button = findViewById(R.id.button)
        textViewSelection = findViewById(R.id.textViewSelection)
        radioGroup = findViewById(R.id.radioGroup)
        lotInput = findViewById(R.id.lotInput)
        clearButton = findViewById(R.id.clearButton)

        // Datos para el primer spinner
        val options1 = arrayOf("Selecciona una opción", "Santa Ana", "Ahuachapan", "Sonsonate")

        // Adaptador para el primer spinner
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        // Datos para el segundo spinner
        val options2 = arrayOf("Santa Ana Centro", "Santa Ana Norte", "Santa Ana Este", "Santa Ana Oeste", "Metapan")
        val options3 = arrayOf("Ahuachapan", "Atiquizaya", "Apaneca", "Concepcion Ataco", "El Refugio")
        val options4 = arrayOf("Nahuizalco", "Juayua", "Salcoatitan")

        // Configurar el listener para el primer spinner
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    1 -> {
                        // Habilitar el segundo spinner con opciones para "Santa Ana"
                        val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, options2)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter2
                        spinner2.isEnabled = true
                    }
                    2 -> {
                        // Habilitar el segundo spinner con opciones para "Ahuachapan"
                        val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, options3)
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner2.adapter = adapter2
                        spinner2.isEnabled = true
                    }
                    3 -> {
                        // Habilitar el segundo spinner con opciones para "Sonsonate"
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

        // Configuración de los RadioButton para sí o no con input adicional
        radioGroupYesNo = findViewById(R.id.radioGroupYesNo)
        radioYes = findViewById(R.id.radioYes)
        radioNo = findViewById(R.id.radioNo)
        additionalInput = findViewById(R.id.additionalInput)

        // Mostrar/ocultar el EditText basado en la selección del RadioButton
        radioGroupYesNo.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioYes -> additionalInput.visibility = EditText.VISIBLE
                R.id.radioNo -> additionalInput.visibility = EditText.GONE
            }
        }

        //pregunta si 5 por defecto o desea agregar cuantos habitantes por vivienda
        // Configuración de los RadioButton para sí o no con input adicional
        radioGrouppoblacion = findViewById(R.id.radioGrouppoblacion)
        porDefecto = findViewById(R.id.porDefecto)
        agregaNuevo = findViewById(R.id.agregaNuevo)
        personaXLote = findViewById(R.id.personaXLote)


// Establecer el evento de clic para el botón de limpiar
        clearButton.setOnClickListener {
            // Restablecer los spinners de departamento y municipio
            spinner1.setSelection(0)
            spinnerMunicipality.adapter = null  // Esto desactivará el segundo spinner hasta que el primero sea nuevamente seleccionado

            // Limpiar y restablecer los campos de entrada de texto para lotes y periodo de diseño
            lotInput.text.clear()
            personaXLote.text.clear()
            inputPeriodoDiseno.text.clear()

            // Limpiar la selección de los RadioButtons
            radioGroup.clearCheck()
            radioGroupYesNo.clearCheck()
            radioGrouppoblacion.clearCheck()
            radioGroupPeriodoDiseno.clearCheck()
            // Ocultar los EditTexts asociados con las selecciones de RadioButtons
            additionalInput.visibility = View.GONE
            personaXLote.visibility = View.GONE
            inputPeriodoDiseno.visibility = View.GONE

            // Limpia el TextView de la ventana de resultados
            textViewSelection.text = ""
            // Mostrar un mensaje de confirmación, opcional
            Toast.makeText(this, "Los campos y selecciones han sido limpiados", Toast.LENGTH_SHORT).show()
        }




        // Configuración del período de diseño
        radioGroupPeriodoDiseno = findViewById(R.id.radioGroupPeriodoDiseno)
        valorDiseno = findViewById(R.id.valorDiseno)
        porDefectoPeriodo = findViewById(R.id.porDefectoPeriodo)
        inputPeriodoDiseno = findViewById(R.id.inputPeriodoDiseno)

        radioGroupPeriodoDiseno.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.valorDiseno -> inputPeriodoDiseno.visibility = EditText.VISIBLE
                R.id.porDefectoPeriodo -> inputPeriodoDiseno.visibility = EditText.GONE
            }
        }

        // Mostrar/ocultar el EditText basado en la selección del RadioButton
        radioGrouppoblacion.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.porDefecto -> personaXLote.visibility = EditText.VISIBLE
                R.id.agregaNuevo -> personaXLote.visibility = EditText.GONE
            }
        }


        button.setOnClickListener {
            val selection1 = spinner1.selectedItem.toString()
            val selection2 = if (spinner2.isEnabled) spinner2.selectedItem.toString() else "Vacio"
            val radioButtonID = radioGroup.checkedRadioButtonId

            var ubicacion=0
            val radioButtonSelected = findViewById<RadioButton>(radioButtonID)
            val consumptionText = when (radioButtonSelected.text.toString().toLowerCase()) {
                "rural" -> "80"
                "urbana" -> "220"
                else -> "Consumo desconocido"
            }

            //este se utilizo para sacar la l/p/d
            ubicacion = consumptionText.toInt()

            // Verificar si el campo de lotes está vacío
            val lotNumber = lotInput.text.toString().trim()
            if (lotNumber.isEmpty()) {
                // Mostrar un mensaje de error si el campo de lotes está vacío
                Toast.makeText(this, "Por favor, ingrese la cantidad de lotes.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // Salir del listener sin continuar
            }

            // Verificar si el número de lotes es un entero positivo
            val lotNumberInt = lotNumber.toIntOrNull()
            if (lotNumberInt == null || lotNumberInt <= 0) {
                // Mostrar un mensaje de error si el número de lotes no es válido
                Toast.makeText(this, "Por favor, ingrese un número de lotes válido (mayor que cero).", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // Salir del listener sin continuar
            }

            // Manejar la selección del RadioGroup de "Sí" o "No"
            var tazaCrecimiento =0f
            val yesNoSelectionID = radioGroupYesNo.checkedRadioButtonId
            val yesNoSelected = findViewById<RadioButton>(yesNoSelectionID)
            val additionalInfo = if (yesNoSelected.id == R.id.radioYes) {
                // Si se selecciona "Sí", obtener el texto del EditText
                additionalInput.text
                val texto = additionalInput.text.toString()
                val numero: Float? = texto.toFloatOrNull()
                if (numero != null) {
                    // La conversión fue exitosa, asignar el número a habitantes
                    tazaCrecimiento = numero
                    // println("El número de habitantes es: $habitantes")
                } else {
                    // La conversión falló, manejar el error
                    println("El texto no es un número válido")
                }
            } else {
                // Si se selecciona "No", usar el valor predeterminado
                tazaCrecimiento = 3.5f

            }
            //habitantes
            // Manejar la selección del RadioGroup de "por defecto" o "asignas habitantes"
            var habitantes = 0
            val respuestaSelectionID = radioGrouppoblacion.checkedRadioButtonId
            val sinoSelected = findViewById<RadioButton>(respuestaSelectionID)
            val Info = if (sinoSelected.id == R.id.porDefecto) {
                // Si se selecciona "Sí", obtener el texto del EditText
                personaXLote.text
                val texto = personaXLote.text.toString()
                val numero: Int? = texto.toIntOrNull()
                if (numero != null) {
                    // La conversión fue exitosa, asignar el número a habitantes
                    habitantes = numero
                   // println("El número de habitantes es: $habitantes")
                } else {
                    // La conversión falló, manejar el error
                    println("El texto no es un número válido")
                }
            } else {
                // Si se selecciona "No", usar el valor predeterminado

                habitantes = 5
            }
            var periodoDisenio =0
            val periodoDiseno = if (radioGroupPeriodoDiseno.checkedRadioButtonId == R.id.valorDiseno) {
                val inputPeriodoDisenoText = inputPeriodoDiseno.text.toString().trim()
                val periodoDisenoInt = inputPeriodoDisenoText.toIntOrNull()
                val numero: Int? = inputPeriodoDisenoText.toIntOrNull()
                if (periodoDisenoInt == null || periodoDisenoInt <= 1) {
                    if (numero != null) {
                        // La conversión fue exitosa, asignar el número a habitantes
                        periodoDisenio = numero
                        // println("El número de habitantes es: $habitantes")
                    } else {
                        // La conversión falló, manejar el error
                        println("El texto no es un número válido")
                    }
                    Toast.makeText(this, "Por favor, ingrese un período de diseño válido (mayor que uno).", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                periodoDisenoInt.toString()
            } else {
                periodoDisenio = 20
            }
            //ploblacion total del lugar
                val poblacion = lotNumberInt * habitantes

            //Poblacion futura del lugar
            val resultado = if (poblacion < 10000) {
                ceil(poblacion * (1 + tazaCrecimiento / 100).pow(periodoDisenio))
            } else {
                ceil(poblacion * exp(periodoDisenio * (tazaCrecimiento / 100)))
            }
            //cuadro de variaciones
            var qMedioDiario = 0.0f
            qMedioDiario = (resultado*ubicacion)/86400
            val qMedioDiarioFormateado = String.format("%.2f", qMedioDiario)
            var k1 = 1.2f
            var k2 = 2.4f
            var k3 = 0.3f
            var qMaxDiario = qMedioDiario * k1
            val qMaxDiarioFormateado = String.format("%.2f", qMaxDiario)
            var qMaxHorario = qMedioDiario * k2
            val qMaxHorarioFormateado = String.format("%.2f", qMaxHorario)
            var qMinhorario = qMedioDiario * k3
            val qMinHorarioFormateado = String.format("%.2f", qMinhorario)


            // Mostrar la información en el TextView solo funciona para ello
            val message = "Departamento: \t $selection1\n" +
                    "Municipio: \t\t\t\t\t\t $selection2\n" +
                    "Zona:\t\t\t ${radioButtonSelected.text} - Uso: $consumptionText\n" +
                    "Taza de Crecimiento: $tazaCrecimiento\n"+
                    "Número de Lotes: $lotNumber\n"+
                    "Habitantes por lote: $habitantes\n"+
                    "Período de diseño: $periodoDisenio AÑOS\n"+
                    "Poblacion Total del lugar: $poblacion \n"+
                    "Poblacion futura del lugar: $resultado\n" +
                    "\n" +
                    "\t\t\t\tVARIACIONES DE CONSUMO \n\t\t\tPARA POBLACION DE DISEÑO\n" +
                    "------------------------------------------------------------------\n" +
                    "| \t\t\t  Tipo \t\t\t   | K   | Factor | Cantidad |\n" +
                    "------------------------------------------------------------------\n" +
                    "|QmedioDiario|   \t\t\t| $qMedioDiarioFormateado  | lts/s     |\n" +
                    "|QmaxDiario    | K1  | 1.2    | $qMaxDiarioFormateado      | lts/s|\n" +
                    "|QmaxHorario | K2  | 2.4    | $qMaxHorarioFormateado   | lts/s|\n" +
                    "|QminHorario  | K3  | 0.3    | $qMinHorarioFormateado      | lts/s|\n" +
                    "-------------------------------------------------------------------\n"

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Información")
            builder.setMessage(message)
            builder.setPositiveButton("Exportar a PDF") { dialog, which ->
                // Aquí implementa la lógica para exportar a PDF
                // Puedes usar bibliotecas como iText o PdfDocument para generar el PDF
                Toast.makeText(this, "Exportando a PDF...", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cerrar") { dialog, which ->
                // Cierra el diálogo cuando se hace clic en "Cerrar"
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

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