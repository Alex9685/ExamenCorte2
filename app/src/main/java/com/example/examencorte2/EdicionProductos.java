package com.example.examencorte2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EdicionProductos extends AppCompatActivity {

    private EditText editTextCodigo;
    private EditText editTextNombre;
    private EditText editTextMarca;
    private EditText editTextPrecio;
    private RadioGroup radioGroup;
    private DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        editTextCodigo = findViewById(R.id.Codigo);
        editTextNombre = findViewById(R.id.Nombre);
        editTextMarca = findViewById(R.id.Marca);
        editTextPrecio = findViewById(R.id.Precio);
        radioGroup = findViewById(R.id.buttons);

        if (radioGroup == null) {
            Log.e("EdicionProductos", "RadioGroup is null");
        }

        dbHelper = new DbHelper(this);



    }




    public void Buscar(View view) {
        String codigoStr = editTextCodigo.getText().toString();

        if (!codigoStr.isEmpty()) {
            int codigo = Integer.parseInt(codigoStr);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selection = DbHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(codigo)};

            Cursor cursor = db.query(
                    DbHelper.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                int nombreColumnIndex = cursor.getColumnIndex(DbHelper.COLUMN_NOMBRE);
                int marcaColumnIndex = cursor.getColumnIndex(DbHelper.COLUMN_MARCA);
                int precioColumnIndex = cursor.getColumnIndex(DbHelper.COLUMN_PRECIO);
                int tipoColumIndex = cursor.getColumnIndex(DbHelper.COLUMN_TIPO);

                if (nombreColumnIndex != -1 && marcaColumnIndex != -1 && precioColumnIndex != -1 && tipoColumIndex != -1) {
                    String nombre = cursor.getString(nombreColumnIndex);
                    String marca = cursor.getString(marcaColumnIndex);
                    String precio = cursor.getString(precioColumnIndex);
                    int tipo = cursor.getInt(tipoColumIndex);

                    editTextNombre.setText(nombre);
                    editTextMarca.setText(marca);
                    editTextPrecio.setText(precio);

                    // Actualizar la selección del botón de radio según el valor de 'tipo'
                    if (tipo == 0) {
                        radioGroup.check(R.id.radioButtonPerecedero);
                    } else if (tipo == 1) {
                        radioGroup.check(R.id.radioButtonNoPerecedero);
                    }
                } else {
                    editTextNombre.setText("");
                    editTextMarca.setText("");
                    editTextPrecio.setText("");
                    radioGroup.clearCheck();
                    Toast.makeText(this, "Columnas no encontradas", Toast.LENGTH_SHORT).show();
                }
            } else {
                editTextNombre.setText("");
                editTextMarca.setText("");
                editTextPrecio.setText("");
                radioGroup.clearCheck();
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            db.close();
        } else {
            Toast.makeText(this, "Debes ingresar el código del producto", Toast.LENGTH_SHORT).show();
        }
    }


    public void Actualizar(View view) {
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
            int codigoInt = Integer.parseInt(codigo);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbHelper.COLUMN_NOMBRE, nombre);
            values.put(DbHelper.COLUMN_MARCA, marca);
            values.put(DbHelper.COLUMN_PRECIO, precio);
            values.put(DbHelper.COLUMN_TIPO, tipo.equals("Perecedero") ? 0 : 1); // Convertir a valor numérico

            String selection = DbHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(codigoInt)};

            long result = db.update(DbHelper.TABLE_NAME, values, selection, selectionArgs);
            db.close();

            if (result > 0) {
                Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }





    public void Borrar(View view) {
        String codigoStr = editTextCodigo.getText().toString();

        if (!codigoStr.isEmpty()) {
            int codigo = Integer.parseInt(codigoStr);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = DbHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(codigo)};

            int result = db.delete(DbHelper.TABLE_NAME, selection, selectionArgs);
            db.close();

            if (result > 0) {
                Toast.makeText(this, "Producto borrado correctamente", Toast.LENGTH_SHORT).show();
                editTextCodigo.setText("");
                editTextNombre.setText("");
                editTextMarca.setText("");
                editTextPrecio.setText("");
            } else {
                Toast.makeText(this, "Error al borrar el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes ingresar el código del producto", Toast.LENGTH_SHORT).show();
        }
    }

    public void salir(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
