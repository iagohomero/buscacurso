package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeCorporativo extends AppCompatActivity
{

    private LinearLayout btCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_corporativo);

        btCadastro = findViewById(R.id.btCadastro);
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushCursoCadastro();
            }
        });
    }
    public void pushCursoCadastro(){
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }
}
