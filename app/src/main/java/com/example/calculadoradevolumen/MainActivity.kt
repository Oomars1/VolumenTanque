package com.example.calculadoradevolumen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadoradevolumen.ui.theme.CalculadoraDeVolumenTheme
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.pow



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
    private val CREATE_FILE_REQUEST_CODE = 101
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
        val options1 = arrayOf(
            "Selecciona una opción",
            "Ahuachapán",
            "Cabañas",
            "Chalatenango",
            "Cuscatlán",
            "La Libertad",
            "La Paz",
            "La Unión",
            "Morazán",
            "San Miguel",
            "San Salvador",
            "San Vicente",
            "Santa Ana",
            "Sonsonate",
            "Usulután"
        )

        // Adaptador para el primer spinner
        val adapter1 = ArrayAdapter(this, R.layout.spinner_item, options1)
        adapter1.setDropDownViewResource(R.layout.spinner_item)
        spinner1.adapter = adapter1

        // Datos para el segundo spinner


        // Datos para los municipios de cada departamento
        val municipiosMap = mapOf(
            "Ahuachapán" to arrayOf("Selecciona una opción", "Ahuachapán", "Atiquizaya", "Apaneca", "Concepción de Ataco", "El Refugio", "Guaymango", "Jujutla", "San Francisco Menéndez", "San Lorenzo", "San Pedro Puxtla", "Tacuba", "Turín"),
            "Cabañas" to arrayOf("Selecciona una opción", "Sensuntepeque", "Cinquera", "Dolores", "Guacotecti", "Ilobasco", "Jutiapa", "San Isidro", "Tejutepeque", "Victoria"),
            "Chalatenango" to arrayOf("Selecciona una opción", "Chalatenango", "Agua Caliente", "Arcatao", "Azacualpa", "Cancasque", "Citalá", "Comapala", "Concepción Quezaltepeque", "Dulce Nombre de María", "El Carrizal", "El Paraíso", "La Laguna", "La Palma", "La Reina", "Las Vueltas", "Nueva Concepción", "Nueva Trinidad", "Ojos de Agua", "Potonico", "San Antonio de la Cruz", "San Antonio Los Ranchos", "San Fernando", "San Francisco Lempa", "San Francisco Morazán", "San Ignacio", "San Isidro Labrador", "San Luis del Carmen", "San Miguel de Mercedes", "San Rafael", "Santa Rita", "Tejutla"),
            "Cuscatlán" to arrayOf("Selecciona una opción", "Cojutepeque", "Candelaria", "El Carmen", "El Rosario", "Monte San Juan", "Oratorio de Concepción", "San Bartolomé Perulapía", "San Cristóbal", "San José Guayabal", "San Pedro Perulapán", "San Rafael Cedros", "San Ramón", "Santa Cruz Analquito", "Santa Cruz Michapa", "Suchitoto", "Tenancingo"),
            "La Libertad" to arrayOf("Selecciona una opción", "Santa Tecla", "Antiguo Cuscatlán", "Chiltiupán", "Ciudad Arce", "Colón", "Comasagua", "Huizúcar", "Jayaque", "Jicalapa", "La Libertad", "Nuevo Cuscatlán", "San Juan Opico", "Quezaltepeque", "Sacacoyo", "San José Villanueva", "San Matías", "San Pablo Tacachico", "Talnique", "Tamanique", "Teotepeque", "Tepecoyo", "Zaragoza"),
            "La Paz" to arrayOf("Selecciona una opción", "Zacatecoluca", "Cuyultitán", "El Rosario", "Jerusalén", "Mercedes La Ceiba", "Olocuilta", "Paraíso de Osorio", "San Antonio Masahuat", "San Emigdio", "San Francisco Chinameca", "San Pedro Masahuat", "San Juan Nonualco", "San Juan Talpa", "San Juan Tepezontes", "San Luis La Herradura", "San Luis Talpa", "San Miguel Tepezontes", "San Pedro Nonualco", "San Rafael Obrajuelo", "Santa María Ostuma", "Santiago Nonualco", "Tapalhuaca"),
            "La Unión" to arrayOf("Selecciona una opción", "La Unión", "Anamorós", "Bolívar", "Concepción de Oriente", "Conchagua", "El Carmen", "El Sauce", "Intipucá", "Lislique", "Meanguera del Golfo", "Nueva Esparta", "Pasaquina", "Polorós", "San Alejo", "San José", "Santa Rosa de Lima", "Yayantique", "Yucuaiquín"),
            "Morazán" to arrayOf("Selecciona una opción", "San Francisco Gotera", "Arambala", "Cacaopera", "Chilanga", "Corinto", "Delicias de Concepción", "El Divisadero", "El Rosario", "Gualococti", "Guatajiagua", "Joateca", "Jocoaitique", "Jocoro", "Lolotiquillo", "Meanguera", "Osicala", "Perquín", "San Carlos", "San Fernando", "San Isidro", "San Simón", "Sensembra", "Sociedad", "Torola", "Yamabal", "Yoloaiquín"),
            "San Miguel" to arrayOf("Selecciona una opción", "San Miguel", "Carolina", "Chapeltique", "Chinameca", "Chirilagua", "Ciudad Barrios", "Comacarán", "El Tránsito", "Lolotique", "Moncagua", "Nueva Guadalupe", "Nuevo Edén de San Juan", "Quelepa", "San Antonio", "San Gerardo", "San Jorge", "San Luis de la Reina", "San Rafael Oriente", "Sesori", "Uluazapa"),
            "San Salvador" to arrayOf("Selecciona una opción", "San Salvador", "Aguilares", "Apopa", "Ayutuxtepeque", "Cuscatancingo", "Delgado", "El Paisnal", "Guazapa", "Ilopango", "Mejicanos", "Nejapa", "Panchimalco", "Rosario de Mora", "San Marcos", "San Martín", "Santiago Texacuangos", "Santo Tomás", "Soyapango", "Tonacatepeque"),
            "San Vicente" to arrayOf("Selecciona una opción", "San Vicente", "Apastepeque", "Guadalupe", "San Cayetano Istepeque", "San Esteban Catarina", "San Ildefonso", "San Lorenzo", "San Sebastián", "Santa Clara", "Santo Domingo", "Tecoluca", "Tepetitán", "Verapaz"),
            "Santa Ana" to arrayOf("Selecciona una opción", "Santa Ana", "Metapán", "Chalchuapa", "Coatepeque", "El Congo", "Masahuat", "San Antonio Pajonal", "San Sebastián Salitrillo", "Santa Rosa Guachipilín", "Texistepeque"),
            "Sonsonate" to arrayOf("Selecciona una opción", "Sonsonate", "Acajutla", "Armenia", "Caluco", "Cuisnahuat", "Izalco", "Juayúa", "Nahuizalco", "Nahulingo", "Salcoatitán", "San Antonio del Monte", "San Julián", "Santa Catarina Masahuat", "Santa Isabel Ishuatán", "Santo Domingo de Guzmán", "Sonzacate"),
            "Usulután" to arrayOf("Selecciona una opción", "Usulután", "Alegría", "Berlín", "California", "Concepción Batres", "El Triunfo", "Ereguayquín", "Estanzuelas", "Jiquilisco", "Jucuapa", "Jucuarán", "Mercedes Umaña", "Nueva Granada", "Ozatlán", "Puerto El Triunfo", "San Agustín", "San Buenaventura", "San Dionisio", "San Francisco Javier", "Santa Elena", "Santa María", "Santiago de María", "Tecapán")
        )

        // Configurar el listener para el primer spinner
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val departamentoSeleccionado = options1[position]
                val municipios = municipiosMap[departamentoSeleccionado] ?: arrayOf("Selecciona una opción")

                val adapter2 = ArrayAdapter(this@MainActivity, R.layout.spinner_item, municipios)
                adapter2.setDropDownViewResource(R.layout.spinner_item)
                spinner2.adapter = adapter2
                spinner2.isEnabled = municipios.isNotEmpty()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
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

        // Configuración de los RadioButton para población
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

            var ubicacion = 0
            val radioButtonSelected = findViewById<RadioButton>(radioButtonID)
            val consumptionText = when (radioButtonSelected.text.toString().toLowerCase()) {
                "rural" -> "80"
                "urbana" -> "220"
                else -> "Consumo desconocido"
            }

            // Este se utilizo para sacar la l/p/d
            ubicacion = consumptionText.toInt()

            // Verificar si el campo de lotes está vacío
            val lotNumber = lotInput.text.toString().trim()
            if (lotNumber.isEmpty()) {
                // Mostrar un mensaje de error si el campo de lotes está vacío
                Toast.makeText(this, "Por favor, ingrese la cantidad de lotes.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            // Verificar si el número de lotes es un entero positivo
            val lotNumberInt = lotNumber.toIntOrNull()
            if (lotNumberInt == null || lotNumberInt <= 0) {
                // Mostrar un mensaje de error si el número de lotes no es válido
                Toast.makeText(
                    this,
                    "Por favor, ingrese un número de lotes válido (mayor que cero).",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener  // Salir del listener sin continuar
            }

            // Manejar la selección del RadioGroup de "Sí" o "No"
            var tazaCrecimiento = 0f
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
            var periodoDisenio = 0
            val periodoDiseno =
                if (radioGroupPeriodoDiseno.checkedRadioButtonId == R.id.valorDiseno) {
                    val inputPeriodoDisenoText = inputPeriodoDiseno.text.toString().trim()
                    val periodoDisenoInt = inputPeriodoDisenoText.toIntOrNull()
                    if (periodoDisenoInt == null || periodoDisenoInt <= 1) {
                        Toast.makeText(
                            this,
                            "Por favor, ingrese un período de diseño válido (mayor que uno).",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else {
                        // Asignar el valor a periodoDisenio solo si es válido
                        periodoDisenio = periodoDisenoInt
                    }
                    // Convertir el valor a String para usarlo en la UI si es necesario
                    periodoDisenoInt.toString()
                } else {
                    // Si no se selecciona el valor de diseño, asignar el valor predeterminado directamente
                    periodoDisenio = 20
                    "20" // También puedes devolver la cadena "20" aquí si lo deseas
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
            qMedioDiario = (resultado * ubicacion) / 86400
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

            //horas de duracion
            var horasDeAduccion = 20.0
            var fijoDeFormula = 24.0
            var cedimento = 0.1
            var rebose = 0.4
            //volumen 1
            var volumenUno: Double = (qMaxDiarioFormateado.toDouble() * resultado.toDouble())/1000
            val volumenUnoFormatted = String.format("%.2f", volumenUno)
            var volumenDos: Double = (qMaxDiarioFormateado.toDouble() * (horasDeAduccion/fijoDeFormula)*resultado.toDouble())/1000 + cedimento + rebose
            val volumenDosFormatted = String.format("%.2f", volumenDos)

            //Volumen total
            var volumenIncendio = 90
            // Evaluar la condición y calcular la suma correspondiente
            val suma = if (volumenUno >= volumenDos) {
                volumenUno + volumenIncendio
            } else {
                volumenDos + volumenIncendio
            }

            // Redondear al entero más cercano hacia arriba
            val volumenTotal = ceil(suma).toInt()

            // Paso 1: Dividir I24 por PI
            val division = volumenTotal / PI

            // Paso 2: Elevar a la potencia de 1/3 (calcular raíz cúbica)
            val potencia = division.pow(1.0 / 3.0)

            // Paso 3: Redondear hacia arriba al entero más cercano
            val resultadoHcilindro = ceil(potencia).toDouble()

            //para D
            val resuldadoDcilindro = resultadoHcilindro * 2

            //Volumen total del tanque
            // Paso 1: Calcular (1/4) * PI
            val factor = (1.0 / 4.0) * PI

            // Paso 2: Multiplicar el factor por M21
            val intermedio = factor * resultadoHcilindro

            // Paso 3: Elevar M22 al cuadrado
            val potenciaTanque = resuldadoDcilindro.pow(2)

            // Paso 4: Multiplicar el resultado intermedio por M22^2
            val resultadoTanque = intermedio * potenciaTanque

            // Paso 5: Redondear hacia arriba al entero más cercano
            val resultadoFinal = ceil(resultadoTanque).toInt()

            val message = "Departamento: \t $selection1\n" +
                    "Municipio: \t\t\t\t\t $selection2\n" +
                    "Zona:\t\t\t ${radioButtonSelected.text} - Uso: $consumptionText\n" +
                    "Taza de Crecimiento: $tazaCrecimiento\n" +
                    "Número de Lotes: $lotNumber\n" +
                    "Habitantes por lote: $habitantes\n" +
                    "Período de diseño: $periodoDisenio AÑOS\n" +
                    "Poblacion Total del lugar: $poblacion \n" +
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
                    "-------------------------------------------------------------------\n" +
                    "Horas de aduccion 20 por defecto \n"+
                    "Volumen 1: $volumenUnoFormatted M3\n"+
                    "Volumen 2: $volumenDosFormatted M3\n"+
                    "Volumen Incendio: $volumenIncendio M3\n"+
                    "Volumen Total: $volumenTotal M3\n"+
                    "Cilindro H: $resultadoHcilindro M3\n"+
                    "Cilindro D: $resuldadoDcilindro M3\n"+
                    "Volumen Total De Tanque: $resultadoFinal M3\n"




            val builder = AlertDialog.Builder(this)
            builder.setTitle("Información")
            builder.setMessage(message)
            builder.setPositiveButton("Exportar a PDF") { dialog, which ->
                // Aquí implementa la lógica para exportar a PDF
                // Puedes usar bibliotecas como iText o PdfDocument para generar el PDF
                Toast.makeText(this, "Exportando a PDF...", Toast.LENGTH_SHORT).show()
                val text = "Resultados generados"

                textViewSelection.text = text



                val pdfContent = "Departamento: \t $selection1\n" +
                        "Municipio: \t\t\t\t\t $selection2\n" +
                        "Zona:\t\t\t ${radioButtonSelected.text} - Uso: $consumptionText\n" +
                        "Taza de Crecimiento: $tazaCrecimiento\n" +
                        "Número de Lotes: $lotNumber\n" +
                        "Habitantes por lote: $habitantes\n" +
                        "Período de diseño: $periodoDisenio AÑOS\n" +
                        "Poblacion Total del lugar: $poblacion \n" +
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
                        "-------------------------------------------------------------------\n" +
                        "Horas de aduccion 20 por defecto \n"+
                        "Volumen 1: $volumenUnoFormatted M3\n"+
                        "Volumen 2: $volumenDosFormatted M3\n"+
                        "Volumen Incendio: $volumenIncendio M3\n"+
                        "Volumen Total: $volumenTotal M3\n"+
                        "Cilindro H: $resultadoHcilindro M3\n"+
                        "Cilindro D: $resuldadoDcilindro M3\n"+
                        "Volumen Total De Tanque: $resultadoFinal M3\n"


                textViewSelection.text = "$text\n\n$pdfContent"

                // Solicitar al usuario que seleccione la ubicación para guardar el archivo PDF
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/pdf"
                    putExtra(Intent.EXTRA_TITLE, "reporte.pdf")
                }
                startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
            }
            builder.setNegativeButton("Cerrar") { dialog, which ->
                // Cierra el diálogo cuando se hace clic en "Cerrar"
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.also { uri ->
                // Crear el PDF y guardarlo en la ubicación seleccionada
                val content = textViewSelection.text.toString()
                createPdf(content, uri)
            }
        }
    }

    private fun createPdf(content: String, uri: Uri) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 12f

        val lines = content.split("\n")
        var y = 20f
        for (line in lines) {
            canvas.drawText(line, 10f, y, paint)
            y += 20f
        }

        pdfDocument.finishPage(page)

        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { fos ->
                    pdfDocument.writeTo(fos)
                }
            }
            Toast.makeText(this, "PDF guardado correctamente.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Error al guardar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            pdfDocument.close()
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