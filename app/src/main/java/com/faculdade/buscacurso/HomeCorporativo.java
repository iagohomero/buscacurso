package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.underscore.$;
import com.github.underscore.Function;

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

                $.setTimeout((new Function<Void>() {
                    public Void apply() {
                        pushCursoCadastro();
                        return null;
                    }
                }, 500);
            }
        });
    }
    public void pushCursoCadastro(){
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }
}
