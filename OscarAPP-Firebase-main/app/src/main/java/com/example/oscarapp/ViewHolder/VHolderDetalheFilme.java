package com.example.oscarapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oscarapp.R;

public class VHolderDetalheFilme extends RecyclerView.ViewHolder {

    public ImageView imagemDetalhe;
    public TextView textoDetalheNome, textoDetalheGenero;
    public Button btnVotoFilme;

    public VHolderDetalheFilme(@NonNull View itemView) {
        super(itemView);

        imagemDetalhe = itemView.findViewById(R.id.imagemDetalheCell);
        textoDetalheNome = itemView.findViewById(R.id.textoDetalheNomeCell);
        textoDetalheGenero = itemView.findViewById(R.id.textoDetalheGeneroCell);
        btnVotoFilme = itemView.findViewById(R.id.btnVotoFilmeCell);
    }

}
