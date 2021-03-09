package com.example.oscarapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscarapp.Filme;
import com.example.oscarapp.R;
import com.example.oscarapp.activity.ListaFilme;
import com.example.oscarapp.adapter.ListaFilmeAdapter;
import com.squareup.picasso.Picasso;
import static com.example.oscarapp.activity.BoasVindasActivity.salvarFilme;

import java.util.ArrayList;
import java.util.List;

public class DetalheFilme extends AppCompatActivity {

    TextView textNome, textGenero;
    ImageView img;
    Button btnVotoFilme;
    Filme film;
    //public static Filme salvarFilme;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        img = findViewById(R.id.imagemDetalhe);
        textNome = findViewById(R.id.textoDetalheNome);
        textGenero = findViewById(R.id.textoDetalheGenero);
        btnVotoFilme = findViewById(R.id.btnVotoFilme);

        android.content.Intent it2 = getIntent();
        if(it2!=null){
            Bundle params = it2.getExtras();
            if(params!=null){
                int pos = params.getInt("Filmes");
                salvarFilme = film = ListaFilme.itemList.get(pos);
                Picasso.get().load(film.getFoto()).resize(680, 680).into(img);
                textNome.setText(film.getNome());
                textGenero.setText(film.getGenero());
            }
        }

        btnVotoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Id:" + film.getNome());
                Intent intent = new Intent(getApplicationContext(), ConfirmarActivity.class);
                Bundle paramFilme = new Bundle();

                paramFilme.putInt("idFilme",salvarFilme.getId());
                paramFilme.putString("nomeFilme", salvarFilme.getNome());

                intent.putExtras(paramFilme);
//                startActivity(intent);

                alert("Voto Encaminhado.");

            }
        });

    }

    private void alert(String s) {
        Toast.makeText(DetalheFilme.this, s, Toast.LENGTH_SHORT).show();
    }
}