package com.faculdade.buscacurso;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faculdade.buscacurso.Adapters.CursoUsuarioAdapter;
import com.faculdade.buscacurso.Objetos.Corporativo;
import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Objetos.Estabelecimentos;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CorporativoTipoCursoActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Corporativo corporativo = new Corporativo();
    CursoUsuarioAdapter adapter;
    ArrayList<Estabelecimentos> estabelecimentosArrayList = new ArrayList();
    ArrayList<Curso> cursosArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporativo_tipo_curso);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        corporativo = Singleton.getInstance(getApplicationContext()).getCorporativo();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        CarregarCursos();
    }

    private void CarregarCursos()
    {
            databaseReference.child("Cursos"+corporativo.getNomeEstabelecimento()+corporativo.getCodigoEstabelecimento()).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                    {
                        Curso curso = new Curso();
                        curso = objSnapshot.getValue(Curso.class);
                        curso.setArea_Curso(objSnapshot.getRef().getKey());
                        curso.setCodigo_Estabelecimento(corporativo.getCodigoEstabelecimento());
                        curso.setNome_Estabelecimento(corporativo.getNomeEstabelecimento());
                        cursosArrayList.add(curso);
                        if(adapter == null)
                            adapter = new CursoUsuarioAdapter(cursosArrayList, getApplicationContext(),true,"x");
                        else
                            adapter.notifyDataSetChanged();

                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });

    }
}
