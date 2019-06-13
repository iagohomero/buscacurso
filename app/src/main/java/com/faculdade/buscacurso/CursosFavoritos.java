package com.faculdade.buscacurso;

import android.content.Intent;
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

public class CursosFavoritos extends AppCompatActivity {

    CursoUsuarioAdapter adapter;
    ArrayList<Curso> cursoArrayList = new ArrayList();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent().setAction("Already created");
        setContentView(R.layout.activity_cursos_favoritos);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios/"+firebaseUser.getUid()+"/Favoritos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Curso curso = new Curso();
                    curso = objSnapshot.getValue(Curso.class);
                    cursoArrayList.add(curso);
                    if(adapter == null)
                        adapter =new CursoUsuarioAdapter(cursoArrayList, getApplicationContext(),false,true);
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

    @Override
    protected void onResume() {
        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {

            Intent intent = new Intent(getApplicationContext(),CursosFavoritos.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);
        super.onResume();


    }
}
