package cl.inacap.profecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //que bacaneria
    //1 Creamos los Atributos
    private EditText etCodigo, etNombre, etApellido, etRut, etCorreo, etContraseña, etPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2 Creamos la relación entre la parte lógica y gráfica
        etCodigo = (EditText)findViewById(R.id.etCodigo);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etApellido = (EditText)findViewById(R.id.etApellido);
        etRut = (EditText)findViewById(R.id.etRut);
        etCorreo = (EditText)findViewById(R.id.etCorreo);
        etContraseña = (EditText)findViewById(R.id.etContraseña);
        etPais = (EditText)findViewById(R.id.etPais);
    }

    //3 Creamos el Método para guardar
    public void guardarProducto(View view){
        //Creamos un objeto AdminSQLite
        //this el contexto y name el nombre de la BD
        //1 la versión de la BD
        AdminSQLite admin = new AdminSQLite(this, "globalService", null, 1);

        //4 Creamos un objeto para abrir la BD en modo lectura y escritura
        // el metodo getWritableDatabase() permite abrir la base en modo lectura y escritura
        SQLiteDatabase bd = admin.getWritableDatabase();

        //5 Creamos variables locales para almacenar
        String codigo = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String rut = etRut.getText().toString();
        String correo = etCorreo.getText().toString();
        String contraseña = etContraseña.getText().toString();
        String pais = etPais.getText().toString();

        //6 Validamos los campos
        if(!codigo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !rut.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty() && !pais.isEmpty()){
            //7 Creamos un objeto para crear el registro
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("nombre",nombre);
            registro.put("apellido",apellido);
            registro.put("rut",rut);
            registro.put("correo",correo);
            registro.put("contraseña",contraseña);
            registro.put("pais",pais);
            //8 Ahora insertamos el registro en la BD
            bd.insert("usuarios", null,registro);
            //9 Cerramos la BD
            bd.close();
            //Limpiamos los EditText
            etCodigo.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etRut.setText("");
            etCorreo.setText("");
            etContraseña.setText("");
            etPais.setText("");
            //10 Mostramos un mensaje de éxito
            Toast.makeText(this, "Usuario Guardado con Éxito",Toast.LENGTH_SHORT).show();
        }else{
            //Si el usuario no ingreso los Datos
            Toast.makeText(this, "Los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    //11 Creamos el método Buscar
    public void buscarProducto(View view){
        //Creamos un objeto AdminSQLite
        AdminSQLite admin = new AdminSQLite(this, "globalService", null, 1);
        //12 Creamos un objeto para abrir la BD en modo lectura y escritura
        //getwritableDatabase() cumple esta función
        SQLiteDatabase bd = admin.getWritableDatabase();
        //13 creamos una variable para almacenar lo que estamos buscando
        String codigo = etCodigo.getText().toString();
        //14 Validamos que el campo no este vacio
        if(!codigo.isEmpty()){
            //15 Creamos un objeto tipo cursor para recorrer el resultado de la query
            Cursor fila = bd.rawQuery("select nombre, apellido, rut, correo, contraseña, pais from usuarios where codigo = " +codigo, null);
            //16 Consultamos si encontro o no el registro
            if(fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etApellido.setText(fila.getString(1));
                etRut.setText(fila.getString(2));
                etCorreo.setText(fila.getString(3));
                etContraseña.setText(fila.getString(4));
                etPais.setText(fila.getString(5));

            }else{
                Toast.makeText(this, "No se registran Usuarios con este Código",Toast.LENGTH_SHORT).show();
            }
            //17 Cerramos BD
            bd.close();
        }else{
            //Si el editText esta vacio
            Toast.makeText(this, "Debe ingresar el Código del Usuario", Toast.LENGTH_SHORT).show();
        }
    }

    //18 Creamos el método modificar
    public void modificarProducto(View view){
        AdminSQLite admin = new AdminSQLite(this, "globalService", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Creamos variables para almacenar lo que vamos a buscar
        String codigo = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String rut = etRut.getText().toString();
        String correo = etCorreo.getText().toString();
        String contraseña = etContraseña.getText().toString();
        String pais = etPais.getText().toString();
        //Validamos los campo
        if(!codigo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !rut.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty() && !pais.isEmpty()){
            //Agregamos un objeto para hacer el registro
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("nombre",nombre);
            registro.put("apellido",apellido);
            registro.put("rut",rut);
            registro.put("correo",correo);
            registro.put("contraseña",contraseña);
            registro.put("pais",pais);
            //Creamos una variable para recatar la cantidad de registro afectados a la query
            int cantidad = bd.update("usuarios",registro, "codigo = " + codigo, null);
            //19 Cerramos la BD
            bd.close();
            //Verificamos que se hayan realizado los cambios
            if(cantidad == 0){
                Toast.makeText(this, "El Código no se encuetra en los registros", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Usuario Modificado con Éxito", Toast.LENGTH_SHORT).show();
            }
            //Limpiamos los EditText
            etCodigo.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etRut.setText("");
            etCorreo.setText("");
            etContraseña.setText("");
            etPais.setText("");
        }else {
            Toast.makeText(this,"Los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }

    }

    //20 Creamos el método eliminar
    public void eliminarProducto(View view){
        AdminSQLite admin = new AdminSQLite(this,"globalService",null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = etCodigo.getText().toString();
        if(!codigo.isEmpty()){
            //creamos una variable para rescatar la cantidad de registros eliminados
            int cantidad = bd.delete("usuarios", "codigo = "+codigo, null);
            bd.close();
            //Limpiamos los EditText
            etCodigo.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etRut.setText("");
            etCorreo.setText("");
            etContraseña.setText("");
            etPais.setText("");

            if(cantidad==0){
                Toast.makeText(this,"No se Registran Uusuarios con ese Código", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Uusuario Eliminado Éxitosamente", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe ingresar el Código del Uusario",Toast.LENGTH_SHORT).show();
        }
    }

}
