package com.example.examencorte2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCodigo;
    private EditText editTextNombre;
    private EditText editTextMarca;
    private EditText editTextPrecio;
    private RadioGroup radioGroup;
    private DbHelper dbHelper;

    // Variable para controlar el estado de edición
    private boolean isEditingEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCodigo = findViewById(R.id.Codigo);
        editTextNombre = findViewById(R.id.Nombre);
        editTextMarca = findViewById(R.id.Marca);
        editTextPrecio = findViewById(R.id.Precio);
        radioGroup = findViewById(R.id.buttons);

        dbHelper = new DbHelper(this);

        // Deshabilitar los campos de entrada al inicio
        setEditingEnabled(false);
    }

    private void setEditingEnabled(boolean enabled) {
        editTextCodigo.setEnabled(enabled);
        editTextNombre.setEnabled(enabled);
        editTextMarca.setEnabled(enabled);
        editTextPrecio.setEnabled(enabled);
        radioGroup.setEnabled(enabled);
    }


    public void limpiar(View view) {
        editTextCodigo.setText("");
        editTextNombre.setText("");
        editTextMarca.setText("");
        editTextPrecio.setText("");
        radioGroup.clearCheck();
    }

    public void Nuevo(View view) {
        editTextCodigo.setText("");
        editTextNombre.setText("");
        setEditingEnabled(true); // Habilitar los campos de entrada al presionar "Nuevo"
    }

    public void salir(View view) {
        finish();
    }

    private static final int REQUEST_CODE_EDICION = 1;

    public void Editar(View view) {
        Intent intent = new Intent(this, EdicionProductos.class);
        startActivityForResult(intent, REQUEST_CODE_EDICION);
    }

}
