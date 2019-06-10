package com.faculdade.buscacurso;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faculdade.buscacurso.Adapters.CursoUsuarioAdapter;
import com.faculdade.buscacurso.Objetos.Curso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerCursos extends AppCompatActivity
{
    CursoUsuarioAdapter adapter;
    ArrayList<Curso> cursoArrayList = new ArrayList();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String NomeEstabelecimento;
    String CodigoEstabelecimento;
    String TipoCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cursos);

        Bundle extras = getIntent().getExtras();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        NomeEstabelecimento = extras.getString("NomeEstabelecimento");
        CodigoEstabelecimento = extras.getString("CodigoEstabelecimento");
        TipoCurso = extras.getString("AreaCurso");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Cursos"+NomeEstabelecimento+CodigoEstabelecimento+"/"+TipoCurso).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Curso curso = new Curso();
                    curso = objSnapshot.getValue(Curso.class);
                    cursoArrayList.add(curso);
                    if(adapter == null)
                        adapter =new CursoUsuarioAdapter(cursoArrayList, getApplicationContext());
                    else
                        adapter.notifyDataSetChanged();

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
