package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.underscore.Function;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.github.underscore.$;

public class Home extends AppCompatActivity
{

    private CardView btCadastro;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        VerifyLogin();


    }

    private void VerifyLogin()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseAuth == null)
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

    }

    public void pushCursoCadastro(){
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }

}
