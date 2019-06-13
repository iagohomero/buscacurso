package com.faculdade.buscacurso;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.faculdade.buscacurso.Objetos.Corporativo;
import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CorporativoEditarCursosActivity extends AppCompatActivity
{

    EditText tvCurso;
    EditText tvNomeEstabelecimento;
    EditText tvEndereco;
    EditText tvDataInicioCurso;
    EditText tvDataFim;
    EditText tvBolsa;
    EditText tvLimiteAlunos;
    EditText tvNota;
    EditText tvTipoCurso;
    EditText tvPreco;
    EditText tvCargaHoraria;
    EditText tvDataInicioInscricao;
    EditText tvDataFimInscricao;

    Button btAlterarCurso;
    Button btDeletarCurso;

    Curso curso = new Curso();

    Corporativo corporativo = new Corporativo();

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporativo_editar_cursos);

        curso = Singleton.getInstance(getApplicationContext()).getCurso();
        corporativo = Singleton.getInstance(getApplicationContext()).getCorporativo();
        if(curso != null)
        {
            tvCurso = findViewById(R.id.tvNomeCurso);
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
            btAlterarCurso = findViewById(R.id.btAlterarCurso);
            btDeletarCurso = findViewById(R.id.btDeletarCurso);

            tvCurso.setText(curso.getNome());
            tvEndereco.setText(curso.getEndereco());
            tvDataInicioCurso.setText(curso.getData_Inicio());
            tvDataFim.setText(curso.getData_Fim());
            tvBolsa.setText(curso.getBolsa());
            tvLimiteAlunos.setText(curso.getLimite_Alunos());
            tvNota.setText(curso.getNota_Mec());
            tvTipoCurso.setText(curso.getTipo_curso());
            tvPreco.setText(curso.getPreco());
            tvCargaHoraria.setText(curso.getCarga_Horaria());
            tvDataInicioInscricao.setText(curso.getData_Inicio_Inscricao());
            tvDataFimInscricao.setText(curso.getData_Fim_Inscricao());

            btAlterarCurso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetarDados();
                }
            });
            btDeletarCurso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeletarCurso();
                }
            });
        }

    }

    private void SetarDados()
    {   Curso cursoAlterar = new Curso();
        cursoAlterar = curso;

        cursoAlterar.setNome(tvCurso.getText().toString());
        cursoAlterar.setEndereco(tvEndereco.getText().toString());
        cursoAlterar.setData_Inicio(tvEndereco.getText().toString());
        cursoAlterar.setData_Fim(tvEndereco.getText().toString());
        cursoAlterar.setData_Inicio_Inscricao(tvEndereco.getText().toString());
        cursoAlterar.setData_Fim_Inscricao(tvEndereco.getText().toString());
        cursoAlterar.setBolsa(tvEndereco.getText().toString());
        cursoAlterar.setLimite_Alunos(tvEndereco.getText().toString());
        cursoAlterar.setNota_Mec(tvEndereco.getText().toString());
        cursoAlterar.setCarga_Horaria(tvEndereco.getText().toString());
        cursoAlterar.setTipo_curso(tvTipoCurso.getText().toString());
        cursoAlterar.setPreco(tvPreco.getText().toString());


        AlterarCurso(cursoAlterar);
    }

    private void AlterarCurso(Curso cursoAlterar)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Cursos"+corporativo.getNomeEstabelecimento()+corporativo.getCodigoEstabelecimento()+"/"+curso.getArea_Curso()
                                                        +"/"+curso.getId()).setValue(curso).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Curso alterado com sucesso.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void DeletarCurso()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja excluir o curso?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseReference.child("Cursos"+corporativo.getNomeEstabelecimento()+corporativo.getCodigoEstabelecimento()+"/"+curso.getArea_Curso()
                                +"/"+curso.getId()).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "Curso deletado com sucesso.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
