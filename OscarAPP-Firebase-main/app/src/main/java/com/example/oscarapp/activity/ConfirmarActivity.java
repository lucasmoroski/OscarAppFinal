package com.example.oscarapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscarapp.R;
import com.example.oscarapp.model.Usuario;
import com.example.oscarapp.model.Voto;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.oscarapp.activity.BoasVindasActivity.salvarDiretorId;
import static com.example.oscarapp.activity.BoasVindasActivity.salvarDiretorName;
import static com.example.oscarapp.activity.BoasVindasActivity.salvarFilme;

public class ConfirmarActivity extends AppCompatActivity {

    TextView Diretor, Filme;
    Button btnConfirmar,btnVolta;
    EditText text;

    String email;
    public String nomediretor = "1";
    public String filme =  "2";

    Firebase meuFire;
//    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        Diretor = (TextView) findViewById(R.id.txtDiretor);
        Filme = (TextView) findViewById(R.id.txtFilme);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnVolta = (Button) findViewById(R.id.btnVolta);
        text = (EditText)findViewById(R.id.token_validar);

        FirebaseApp.initializeApp(ConfirmarActivity.this);
        Firebase.setAndroidContext(ConfirmarActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        meuFire = new Firebase( "https://oscarapp-cba97-default-rtdb.firebaseio.com/");

        Intent intentR = getIntent();
        Bundle paramR = intentR.getExtras();

        if(paramR != null){
            String nomeDi = paramR.getString("nome");
            int idDi = paramR.getInt("id");
            nomediretor = nomeDi;
                Diretor.setText(salvarDiretorName.getNome());
            if (salvarFilme.getNome() != null) {
                Filme.setText(salvarFilme.getNome());
            }
        }
        if(salvarFilme.getNome() != null){
            Filme.setText(salvarFilme.getNome());
        }
        if(salvarDiretorName.getNome() != null){
            Diretor.setText(salvarDiretorName.getNome());
        }

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String token_validar = text.getText().toString();

                int token_validar_int = Integer.parseInt(token_validar);

                String uid = FirebaseAuth.getInstance().getUid();
                int valuee = Integer.parseInt(uid.replaceAll("[^0-9]", ""));

                // below, %02d says to java that I want my integer to be formatted as a 2 digit representation
                String temp = String.format("%2d", valuee);
                // and if you want to do the reverse
                int i = Integer.parseInt(temp);

                long num = i;
                int n = 2;
                long token_real = (long) (num / Math.pow(10, Math.floor(Math.log10(num)) - n + 1));

                if(token_real == token_validar_int) {

                    int token_int = (int) token_real;
                    String uidd = FirebaseAuth.getInstance().getUid();
                    Usuario usuario = new Usuario(uidd, email, token_int, nomediretor, filme);

                    FirebaseFirestore.getInstance().collection("usuario").document(uid).set(usuario);
                    FirebaseFirestore.getInstance().collection("usuario").document(uidd).set(usuario);
                    if(salvarFilme.getNome() != null && salvarDiretorName.getNome() != null){
                        validar_voto();
                    }else {
                        alert("Favor Selecionar Diretor e Filme.");
                    }
                }else {
                    alert("Token Invalido");
                }
            }
        });

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarInicio();
            }
        });


    }

    public void voltarInicio(){
        Intent i = new Intent(ConfirmarActivity.this, BoasVindasActivity.class);
        startActivity(i);
        finish();
    }

    public void validar_voto(){

            AlertDialog.Builder magBox = new AlertDialog.Builder(this);
            magBox.setTitle("Confirmando...");
            magBox.setIcon(android.R.drawable.ic_menu_save);
            magBox.setMessage("Tem certeza que deseja Salvar os votos?");
            magBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Firebase fb = meuFire.child("Voto");
                    Voto vt = new Voto();
                    vt.setToken(Integer.valueOf(text.getText().toString()));
                    vt.setVoto_diretor(String.valueOf(salvarDiretorName.getNome()));
                    vt.setVoto_filme(String.valueOf(salvarFilme.getNome()));
                    databaseReference.child("Voto").child(vt.getToken().toString()).setValue(vt);
                        alert("Voto Realizado com Sucesso!!");
                        voltarInicio();
                    }
            });
            magBox.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            magBox.show();


    }

    private void alert(String s) {
        Toast.makeText(ConfirmarActivity.this, s, Toast.LENGTH_SHORT).show();
    }



}