package com.example.oscarapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.assist.AssistStructure;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oscarapp.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.oscarapp.helper.JsonPlaceHolderApi;
import com.example.oscarapp.model.Diretor;
import com.example.oscarapp.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.oscarapp.activity.BoasVindasActivity.salvarDiretorId;
import static com.example.oscarapp.activity.BoasVindasActivity.salvarDiretorName;


public class DiretorActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView txt,escolhaD;
    Button btnValida;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diretor);
        startComponent();
//        iniciaDiretor();

        String urlPrincipal = "http://wecodecorp.com.br/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(urlPrincipal).addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Diretor>> call = jsonPlaceHolderApi.getDiretor();
        call.enqueue(new Callback<List<Diretor>>() {
            @Override
            public void onResponse(Call<List<Diretor>> call, retrofit2.Response<List<Diretor>> response) {
                if(!response.isSuccessful()){
                    alert("Code:" + response.code());
                }

                List<Diretor> post = response.body();

                for(Diretor dire : post){
                    RadioButton radio = new RadioButton(DiretorActivity.this);
                    radio.setId(dire.getId());
                    radio.setText(dire.getNome());

                    radioGroup.addView(radio);
                }
            }

            @Override
            public void onFailure(Call<List<Diretor>> call, Throwable t) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idradio = radioGroup.getCheckedRadioButtonId();
                RadioButton radioescolhaD = (RadioButton) findViewById(idradio);
                int rescolhaD = radioescolhaD.getId();
                salvarDiretorId = radioescolhaD.getId();
                salvarDiretorName.setNome(radioescolhaD.getText().toString());
//                escolhaD.setText(""+rescolhaD);
                escolhaD.setText(radioescolhaD.getText().toString());
            }
        });

        btnValida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idradio = radioGroup.getCheckedRadioButtonId();
                RadioButton radioescolhaD = (RadioButton) findViewById(idradio);
                int rescolhaD = radioescolhaD.getId();
                String nomeD = radioescolhaD.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ConfirmarActivity.class);
                Bundle param = new Bundle();

                param.putInt("id",rescolhaD);
                param.putString("nome", salvarDiretorName.getNome());

                intent.putExtras(param);

                alert("Voto Encaminhado.");
//                startActivity(intent);

            }
        });


    }

    private void startComponent() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupid);
        txt = (TextView) findViewById(R.id.teste);
        escolhaD = (TextView) findViewById(R.id.escolhaDire);
        btnValida = (Button) findViewById(R.id.btnValida);
    }

    private void alert(String s) {
        Toast.makeText(DiretorActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void iniciaDiretor() {
        /*
                String uid = FirebaseAuth.getInstance().getUid();

                FirebaseFirestore.getInstance().collection("usuario").document(uid).get().
                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    email = (String) documentSnapshot.get("usuario");

                                }else {

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

               Integer token = 0;
                Integer voto_diretor = 0;
                Usuario usuario =  new Usuario(uid,email,token,rescolhaD);
*/
        String urlPrincipal = "http://wecodecorp.com.br/ufpr/diretor";
        RequestQueue requestQ = Volley.newRequestQueue(DiretorActivity.this);
        JsonObjectRequest stringR = new JsonObjectRequest(Request.Method.GET, urlPrincipal, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray DiretorArray = new JSONArray(response);
                    for(int i = 0 ; i < DiretorArray.length(); i++) {
                        txt.setText(DiretorArray.length() +"");
                        alert(DiretorArray.toString());
                        JSONObject obj = DiretorArray.getJSONObject(i);
                        String Diretorid = obj.getString("id");
                        String DiretorNome = obj.getString("nome");

                        RadioButton btnRadio = new RadioButton(DiretorActivity.this);
                        btnRadio.setText(Diretorid +"-"+ DiretorNome);
                        radioGroup.addView(btnRadio);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQ.add(stringR);

    }

}