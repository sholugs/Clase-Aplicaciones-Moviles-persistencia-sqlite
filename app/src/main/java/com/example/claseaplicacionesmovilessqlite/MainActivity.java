package com.example.claseaplicacionesmovilessqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextEdad;
    private Button botonAgregar;
    private ListView listViewDatos;
    private MiDBHelper miDBHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        botonAgregar = findViewById(R.id.botonAgregar);
        listViewDatos = findViewById(R.id.listViewDatos);

        // Inicializar DBHelper y SQLiteDatabase
        miDBHelper = new MiDBHelper(this);
        db = miDBHelper.getWritableDatabase();

        // Configurar adaptador para ListView
        String[] fromColumns = {"nombre", "edad"};
        int[] toViews = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, fromColumns, toViews, 0);
        listViewDatos.setAdapter(adapter);

        // Cargar datos iniciales
        cargarDatos();

        // Configurar listener para el botón Agregar
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarRegistro();
            }
        });
    }

    private void cargarDatos() {
        // Consultar datos de la base de datos
        Cursor cursor = db.query("mi_tabla", null, null, null, null, null, null);
        adapter.changeCursor(cursor);
    }

    private void agregarRegistro() {
        String nombre = editTextNombre.getText().toString();
        String edadStr = editTextEdad.getText().toString();
        if (nombre.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa nombre y edad", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);

        // Insertar datos en la base de datos
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("edad", edad);
        long id = db.insert("mi_tabla", null, values);

        // Mostrar mensaje de éxito o fracaso
        if (id != -1) {
            Toast.makeText(this, "Registro agregado correctamente", Toast.LENGTH_SHORT).show();
            cargarDatos(); // Recargar datos en el ListView
            editTextNombre.setText("");
            editTextEdad.setText("");
        } else {
            Toast.makeText(this, "Error al agregar registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al destruir la actividad
        db.close();
    }
}
