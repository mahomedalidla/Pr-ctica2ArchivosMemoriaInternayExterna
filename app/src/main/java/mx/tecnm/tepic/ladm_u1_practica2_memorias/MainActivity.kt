package mx.tecnm.tepic.ladm_u1_practica2_memorias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import mx.tecnm.tepic.ladm_u1_ejercicio2_trabajotodos.Data
import java.io.*

class MainActivity : AppCompatActivity() {
    val vector = ArrayList<Data>()
    val nombres = ArrayList<String>()


    private val filepath = "MyFileStorageNotas"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leerDesdeSD()

        var inserta = findViewById<Button>(R.id.save)
        inserta.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.txtnombre).text.toString()
            val contenido = findViewById<EditText>(R.id.txtcontenido).text.toString()

            if ((nombre + contenido) != "") {
                metodoInsertar(nombre, contenido)
                findViewById<EditText>(R.id.txtnombre).setText("")
                findViewById<EditText>(R.id.txtcontenido).setText("")
                for ((posicion, valor) in nombres.withIndex()) {
                    println("La posición $posicion contiene el valor ${valor.toString()}")
                }
            } else {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG)
                    .show()
            }

        }

        var guardarExterno = findViewById<Button>(R.id.guardartxt)
        guardarExterno.setOnClickListener {
            if (metodoGuardar()) {
                Toast.makeText(this, "Se guardo con exito sd", Toast.LENGTH_LONG).show()
                Log.d("myTag", "This is my messagebtn");

            } else {
                Toast.makeText(this, "No se pudo guardar", Toast.LENGTH_LONG).show()
            }
        }

        var borrar = findViewById<Button>(R.id.delete)
        borrar.setOnClickListener {
            metodoEliminar()
        }

    }



    fun metodoInsertar(nombre: String, contenido: String) {


        val DatosPersona: Data = Data()

        DatosPersona.nombre = nombre
        DatosPersona.contenido = contenido

        vector.add(DatosPersona)
        nombres.add(nombre)

        val listaDatos = findViewById<ListView>(R.id.linearDatos)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)

        listaDatos.adapter = adaptador
    }


    //SD
    private fun metodoGuardar(): Boolean {
        var myExternalFile: File?=null
        myExternalFile = File(getExternalFilesDir(filepath), "archivoTodos.txt")
        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(nombres.toString().toByteArray()) //Error
            Log.d("myTag", "This is my message metodo");
            for ((posicion, valor) in nombres.withIndex()) {
                println("La posición en metodo $posicion contiene el valor $valor")
            }
            fileOutPutStream.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()
    }

    fun leerDesdeSD(): Boolean{
        try {
            var myExternalFile: File?=null
            myExternalFile = File(getExternalFilesDir(filepath), "archivoTodos.txt")

            val filename = "archivoTodos.txt"
            myExternalFile = File(getExternalFilesDir(filepath),filename)
            if(filename.toString()!=null && filename.toString().trim()!="") {
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show()

                nombres.add(stringBuilder.split(",").toString())
                val listaDatos = findViewById<ListView>(R.id.linearDatos)
                val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)
                listaDatos.adapter = adaptador

            }
            return true;
        } catch (io:Exception){
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show()
            return false;
        }
    }



    private fun metodoEliminar() {
        var nombre = EditText(this)
        nombre.inputType = InputType.TYPE_CLASS_TEXT
        nombre.setHint("nombre de nota a eliminar")

        //try {
        AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("Cual id se eliminara?")
            .setView(nombre)
            .setPositiveButton("Eliminar") { d, i ->
                vector.forEach {
                    if (it.nombre == nombre.text.toString()) {
                        vector.remove(it)
                        nombres.remove(it.nombre)
                    }
                }
                d.dismiss()
            }
            .setNegativeButton("Cancelar") { d, i ->
                d.cancel()
            }
            .show()
        metodoGuardar()
    }

}