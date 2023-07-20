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
    }




    public void Guardar(View view) {
        String codigo = editTextCodigo.getText().toString();
        String nombre = editTextNombre.getText().toString();
        String marca = editTextMarca.getText().toString();
        String precio = editTextPrecio.getText().toString();

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        String tipo = "";
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            tipo = selectedRadioButton.getText().toString();
        }

        if (!codigo.isEmpty() && !nombre.isEmpty() && !marca.isEmpty() && !precio.isEmpty() && !tipo.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_ID, codigo);
            values.put(DbHelper.COLUMN_NOMBRE, nombre);
            values.put(DbHelper.COLUMN_MARCA, marca);
            values.put(DbHelper.COLUMN_PRECIO, precio);
            values.put(DbHelper.COLUMN_TIPO, tipo);

            long result = db.insertWithOnConflict(DbHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();

            if (result != -1) {
                Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
        }
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
