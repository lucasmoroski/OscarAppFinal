package com.example.oscarapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oscarapp.Filme;
import com.example.oscarapp.R;
import com.example.oscarapp.model.Diretor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Scanner;


public class BoasVindasActivity extends AppCompatActivity {

    public static Filme salvarFilme;
    public static int salvarDiretorId;
    public static Diretor salvarDiretorName;
    private  static final  String KEY_TITLE = "token";
    private FirebaseDatabase database;
    private static int value;
    private static Integer token_no_banco;
    private static int token_no_banco_int;
    private static boolean validatk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        database = FirebaseDatabase.getInstance();

        salvarDiretorId = 0;
        salvarDiretorName = new Diretor();
        salvarFilme = new Filme();
        String uid = FirebaseAuth.getInstance().getUid();
        int valuee = Integer.parseInt(uid.replaceAll("[^0-9]", ""));

        // below, %02d says to java that I want my integer to be formatted as a 2 digit representation
        String temp = String.format("%2d", valuee);
        // and if you want to do the reverse
        int i = Integer.parseInt(temp);

        long num = i;
        int n = 2;
        long first_n = (long) (num / Math.pow(10, Math.floor(Math.log10(num)) - n + 1));

        int res = new Scanner(uid).useDelimiter("\\D+").nextInt();

        FirebaseFirestore.getInstance().collection("usuario").document(uid).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            value = documentSnapshot.getLong("token").intValue();

                            TextView tokentxt = findViewById(R.id.intToken);
                            tokentxt.setText(Integer.toString((int) first_n));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        DatabaseReference reference = database.getReference().child("Voto").child(String.valueOf(first_n));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    int token_no_bancoo = snapshot.child("token").getValue(Integer.class);
                    if (token_no_bancoo > 0) {

                        Integer token_local = (int) (long) first_n;

                        if (token_local == token_no_bancoo) {
                            Toast.makeText(BoasVindasActivity.this, "VOCE JA VOTOU", Toast.LENGTH_SHORT).show();
                            validatk = true;
                        } else {
//                            Toast.makeText(BoasVindasActivity.this, "VOCE AINDA NAO VOTOU", Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (NullPointerException npe){
//                    Toast.makeText(BoasVindasActivity.this, "VOCE AINDA NAO VOTOU", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //validacao se tiver token registrado no BD e for o mesmo quer dizer que ele ja votou

    }

    public void btn_votar_diretor(View view){
        Intent i = new Intent(BoasVindasActivity.this, DiretorActivity.class);
        startActivity(i);
    }

    public void btn_votar_filme(View view){
        Intent i = new Intent(BoasVindasActivity.this, ListaFilme.class);
        startActivity(i);
    }

    public void btn_confirmar_voto(View view){
        if(validatk == true){
            Intent i = new Intent(BoasVindasActivity.this, ValidavotoActivity.class);
            startActivity(i);
        }else {
            Intent i = new Intent(BoasVindasActivity.this, ConfirmarActivity.class);
            startActivity(i);
        }
    }

    public void btn_sair(View view){
        Intent i = new Intent( BoasVindasActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
        System.exit(0);

    }

    private void ouvinte_1(){


    }
}