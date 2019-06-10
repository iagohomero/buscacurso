package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.faculdade.buscacurso.Objetos.Usuario;
import com.github.underscore.Function;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.github.underscore.$;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity
{

    private CardView btCadastro;
    private CardView vendascurso;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView tvPainel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvPainel = findViewById(R.id.tvPainel);

        btCadastro = findViewById(R.id.produtoscard);
        vendascurso = findViewById(R.id.vendascurso);
        vendascurso.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                VerCursos();
            }
        });
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

    private void VerCursos()
    {
        Intent intent = new Intent(this, TipoCurso.class);
        startActivity(intent);
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
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios/"+firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Usuario usuario = new Usuario();
                    usuario = dataSnapshot.getValue(Usuario.class);
                    tvPainel.setText("Bem vindo, " + usuario.getNome());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });



    }

    public void pushCursoCadastro()
    {
        Intent intent = new Intent(getApplicationContext(),CadastroCurso.class);
        startActivity(intent);
    }

}
