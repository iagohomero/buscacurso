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

public class HomeCorporativo extends AppCompatActivity {


    private CardView btCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_corporativo);
/*

        btCadastro = (CardView) findViewById(R.id.cardViewCadastro);
        btCadastro.setOnClickListener(new View.OnClickListener()
        {

        btCadastro = findViewById(R.id.produtoscard);
        btCadastro.setOnClickListener(new View.OnClickListener() {

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
    }
    public void pushCursoCadastro(){
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }
    */
    }
}
