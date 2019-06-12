package com.faculdade.buscacurso;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faculdade.buscacurso.Adapters.CursoUsuarioAdapter;
import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Objetos.Estabelecimentos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TipoCurso extends AppCompatActivity
{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    CursoUsuarioAdapter adapter;
    ArrayList<Estabelecimentos> estabelecimentosArrayList = new ArrayList();
    ArrayList<Curso> cursosArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_curso);

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Estabelecimentos").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                int Size = 0;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {

                        estabelecimentosArrayList.add( (Estabelecimentos) objSnapshot.getValue(Estabelecimentos.class));
                        Size++;
                }
                if(Size == dataSnapshot.getChildrenCount())
                {
                    CarregarCursos();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void CarregarCursos()
    {
        for (final Estabelecimentos  item : estabelecimentosArrayList)
        {
            databaseReference.child("Cursos"+item.getNomeEstabelecimento()+item.getCodigoEstabelecimento()).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                    {
                        Curso curso = new Curso();
                        curso = objSnapshot.getValue(Curso.class);
                        curso.setArea_Curso(objSnapshot.getRef().getKey());
                        curso.setCodigo_Estabelecimento(item.getCodigoEstabelecimento());
                        curso.setNome_Estabelecimento(item.getNomeEstabelecimento());
                        cursosArrayList.add(curso);
                        if(adapter == null)
                            adapter = new CursoUsuarioAdapter(cursosArrayList, getApplicationContext(),true,false);
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
}
