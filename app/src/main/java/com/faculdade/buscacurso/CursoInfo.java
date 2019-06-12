package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CursoInfo extends AppCompatActivity
{
    Curso curso = new Curso();
    TextView tvCurso;
    TextView tvNomeEstabelecimento;
    TextView tvEndereco;
    TextView tvDataInicioCurso;
    TextView tvDataFim;
    TextView tvBolsa;
    TextView tvLimiteAlunos;
    TextView tvNota;
    TextView tvTipoCurso;
    TextView tvPreco;
    TextView tvCargaHoraria;
    TextView tvDataInicioInscricao;
    TextView tvDataFimInscricao;
    Button btAdicionarFavorito;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_info);
        Intent intent = getIntent();


        curso = Singleton.getInstance(getApplicationContext()).getCurso();
        if(curso != null)
        {
            tvCurso = findViewById(R.id.tvNomeCurso);
            tvNomeEstabelecimento = findViewById(R.id.tvNomeEstabelecimento);
            tvEndereco = findViewById(R.id.tvEndereco);
            tvDataInicioCurso = findViewById(R.id.tvDataInicioCurso);
            tvDataFim = findViewById(R.id.tvDataFim);
            tvBolsa = findViewById(R.id.tvBolsa);
            tvLimiteAlunos = findViewById(R.id.tvLimiteAlunos);
            tvNota = findViewById(R.id.tvNota);
            tvTipoCurso= findViewById(R.id.tvTipoCurso);
            tvPreco = findViewById(R.id.tvPreco);
            tvCargaHoraria = findViewById(R.id.tvCargaHoraria);
            tvDataInicioInscricao = findViewById(R.id.tvDataInicioInscricao);
            tvDataFimInscricao = findViewById(R.id.tvDataFimInscricao);

            tvCurso.setText(curso.getNome());
            tvNomeEstabelecimento.setText(curso.getNome_Estabelecimento());
            tvEndereco.setText(curso.getEndereco());
            tvDataInicioCurso.setText("Data de Início: " + curso.getData_Inicio());
            tvDataFim.setText("Previsão de formatura: " + curso.getData_Fim());
            tvBolsa.setText("Bolsas: " + curso.getBolsa());
            tvLimiteAlunos.setText("Limite Alunos: " + curso.getLimite_Alunos());
            tvNota.setText("Nota MEC: " + curso.getNota_Mec());
            tvTipoCurso.setText("Tipo Curso: " + curso.getTipo_curso());
            tvPreco.setText("Preço:  " + curso.getPreco());
            tvCargaHoraria.setText("Carga Horária: " + curso.getCarga_Horaria());
            tvDataInicioInscricao.setText("Data de abertura das inscrições: " + curso.getData_Inicio_Inscricao());
            tvDataFimInscricao.setText("Data fim das inscrições: " + curso.getData_Fim_Inscricao());
        }
        btAdicionarFavorito = findViewById(R.id.btAdicionarFavorito);
        String favoritos = intent.getStringExtra("favoritos");
        if(favoritos.equals("true")){
            btAdicionarFavorito.setText("REMOVER DOS FAVORITOS");
            btAdicionarFavorito.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    RemoveFavorite();
                }
            });
        }
        else{
            btAdicionarFavorito.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AddFavorite();
                }
            });
        }

    }

    private void AddFavorite()
    {
        if(curso != null)
        {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Usuarios/"+firebaseUser.getUid()+"/Favoritos/"+curso.getId()).setValue(curso).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "Curso adicionado aos favoritos",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void RemoveFavorite()
    {
        if(curso != null)
        {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Usuarios/"+firebaseUser.getUid()+"/Favoritos/"+curso.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "Curso removido dos favoritos",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
