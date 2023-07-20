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
