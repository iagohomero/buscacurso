package com.faculdade.buscacurso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.underscore.$;
import com.github.underscore.Function;

public class HomeCorporativo extends AppCompatActivity
{


    private CardView btCadastro;
    private CardView btPerfil;
    private CardView card_usuarios;
    private CardView cursoscard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_corporativo);


        btCadastro = findViewById(R.id.produtoscard);
        btPerfil = findViewById(R.id.meuperfilcard);
        card_usuarios = findViewById(R.id.card_usuarios);
        cursoscard = findViewById(R.id.cursoscard);

        cursoscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushVisualizarCursos();
            }
        });

        card_usuarios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pushCadastroUsuarios();
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                $.setTimeout(new Function<Void>() {
                    public Void apply() {
                        pushCursoCadastro();
                        return null;
                    }
                }, 200);
            }
        });

        btPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Perfil();
            }
        });


    }

    private void pushVisualizarCursos()
    {
        Intent intent = new Intent(getApplicationContext(),CorporativoTipoCursoActivity.class);
        startActivity(intent);
    }


    private void pushCadastroUsuarios()
    {
        Intent intent = new Intent(getApplicationContext(),CadastroCorporativo.class);
        startActivity(intent);
    }


    public void pushCursoCadastro(){
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }
    public void Perfil(){
        Intent intent = new Intent(getApplicationContext(),PerfilCorporativo.class);
        startActivity(intent);
    }

}

