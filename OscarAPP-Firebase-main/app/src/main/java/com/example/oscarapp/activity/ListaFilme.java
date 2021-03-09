package com.example.oscarapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oscarapp.RecyclerItemClickListener;
import com.example.oscarapp.adapter.ListaFilmeAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oscarapp.Filme;
import com.example.oscarapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListaFilme extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView teste;
    ListaFilmeAdapter auxAdapter;
    List<Filme> itemListAux = new ArrayList<>();
    public static List<Filme> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filme);

        teste = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.rViewListaFilme);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                android.content.Intent it = new Intent(ListaFilme.this, DetalheFilme.class);
                                Bundle params =new Bundle();
                                params.putInt("Filmes", position);
                                it.putExtras(params);
                                startActivity(it);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        String urlCompleta = "http://wecodecorp.com.br/ufpr/filme";

        StringRequest filmeRequest = new StringRequest(Request.Method.GET, urlCompleta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray filmeArray = new JSONArray(response);
                    for (int i=0; i < filmeArray.length(); i++) {
                        Filme auxFilme = new Filme();
                        JSONObject obj = filmeArray.getJSONObject(i);
                        auxFilme.setId(obj.getInt("id"));
                        auxFilme.setNome(obj.getString("nome"));
                        auxFilme.setGenero(obj.getString("genero"));
                        auxFilme.setFoto(obj.getString("foto"));
                        itemListAux.add(auxFilme);
                    }
                    auxAdapter = new ListaFilmeAdapter(itemListAux);
                    itemList = itemListAux;
                    recyclerView.setAdapter(auxAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ListaFilme.this);
        requestQueue.add(filmeRequest);
    }

}