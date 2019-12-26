package cl.inacap.profecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {


    private EditText etCodigo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

      etCodigo = (EditText)findViewById(R.id.etCodigo);


    }




    public void addUser (View view){

        Intent add = new Intent (this,AddUsuario.class);
        startActivity(add);



    }

    public void eddUser (View view) {
        //  Toast.makeText(this,"El Diego se la come",Toast.LENGTH_SHORT).show();

        String cd = etCodigo.getText().toString();

        if (!cd.isEmpty()) {




            Intent edd = new Intent(this, EditActivity.class);

            edd.putExtra("cod",cd);


            startActivity(edd);

        } else {

            Toast.makeText(this, "Ingrese un código válido", Toast.LENGTH_SHORT).show();



        }


    }
}
