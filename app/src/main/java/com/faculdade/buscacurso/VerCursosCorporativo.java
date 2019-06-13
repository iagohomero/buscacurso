package com.faculdade.buscacurso;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faculdade.buscacurso.Adapters.CursoUsuarioAdapter;
import com.faculdade.buscacurso.Objetos.Corporativo;
import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerCursosCorporativo extends AppCompatActivity
{

    CursoUsuarioAdapter adapter;
    ArrayList<Curso> cursoArrayList = new ArrayList();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Corporativo corporativo = new Corporativo();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String TipoCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cursos_corporativo);

        Bundle extras = getIntent().getExtras();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        corporativo = Singleton.getInstance(getApplicationContext()).getCorporativo();

        TipoCurso = extras.getString("AreaCurso");

        LoadCursos();
    }

    private void LoadCursos()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Cursos"+corporativo.getNomeEstabelecimento()+corporativo.getCodigoEstabelecimento()+"/"+TipoCurso).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Curso curso = new Curso();
                    curso = objSnapshot.getValue(Curso.class);
                    cursoArrayList.add(curso);
                    if(adapter == null)
                        adapter =new CursoUsuarioAdapter(cursoArrayList, getApplicationContext(),"x");
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
