package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CadastroCurso extends AppCompatActivity {
    private Button cadastrar;
    private Button voltar;

    private EditText edtNomeCurso;
    private EditText edtTipoCurso;
    private EditText edtAreaCurso;
    private EditText edtNotaMec;
    private EditText edtValor;
    private EditText edtLimiteAlunos;
    private EditText edtCargaHoraria;
    private EditText edtDataIni;
    private EditText edtDataFim;
    private String Nome;
    private String Carga_Horaria;
    private String Data_Inicio;
    private String Data_Fim;
    private String nome_Estabelecimento;
    private String Codigo_Estabelecimento;
    private String Limite_Alunos;
    private String Tipo_curso;
    private String Nota_Mec;
    private String Preco;
    private String Area_Curso;
    private Curso curso = new Curso();

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);

        cadastrar = findViewById(R.id.btCadastrar);
        voltar = findViewById(R.id.btVoltar);
        edtNomeCurso = findViewById(R.id.edtNomeCurso);
        edtTipoCurso = findViewById(R.id.edtTipoCurso);
        edtAreaCurso = findViewById(R.id.edtAreaCurso);
        edtNotaMec = findViewById(R.id.edtNotaMec);
        edtValor = findViewById(R.id.edtValor);
        edtLimiteAlunos = findViewById(R.id.edtLimiteAlunos);
        edtCargaHoraria = findViewById(R.id.edtCargaHoraria);
        edtDataIni = findViewById(R.id.edtDataIni);
        edtDataFim = findViewById(R.id.edtDataFim);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCursoObject();
            }
        });
        //Voltar ao clickar em voltar;
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void setCursoObject() {
        Nome = edtNomeCurso.getText().toString();
        Carga_Horaria = edtCargaHoraria.getText().toString();
        Data_Inicio = edtDataIni.getText().toString();
        Data_Fim = edtDataFim.getText().toString();
        nome_Estabelecimento = "usuario";
        /*nome_Estabelecimento = Singleton.
                getInstance().
                getCorporativo().
                getNomeEstabelecimento() == null ? "" :
                Singleton.
                        getInstance().
                        getCorporativo().
                        getNomeEstabelecimento();
        Codigo_Estabelecimento = Singleton.
                getInstance().
                getCorporativo().
                getCodigoEstabelecimento() == null ? "" :
                Singleton.
                        getInstance().
                        getCorporativo().
                        getCodigoEstabelecimento();*/
        Codigo_Estabelecimento = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Limite_Alunos = edtLimiteAlunos.getText().toString();
        Tipo_curso = edtTipoCurso.getText().toString();
        Nota_Mec = edtNotaMec.getText().toString();
        Preco = edtValor.getText().toString();
        Area_Curso = edtAreaCurso.getText().toString();
        if (Nome.equalsIgnoreCase("") ||
                Carga_Horaria.equalsIgnoreCase("") ||
                Data_Inicio.equalsIgnoreCase("") ||
                Data_Fim.equalsIgnoreCase("") ||
                Limite_Alunos.equalsIgnoreCase("") ||
                Tipo_curso.equalsIgnoreCase("") ||
                Nota_Mec.equalsIgnoreCase("") ||
                Preco.equalsIgnoreCase("") ||
                Area_Curso.equalsIgnoreCase("")) {
            if (Nome.equalsIgnoreCase("")) edtNomeCurso.setError("Campo obrigarótio!");
            else if (Data_Inicio.equalsIgnoreCase("")) edtDataIni.setError("Campo obrigarótio!");
            else if (Data_Fim.equalsIgnoreCase("")) edtDataFim.setError("Campo obrigarótio!");
            else if (Limite_Alunos.equalsIgnoreCase("")) edtLimiteAlunos.setError("Campo obrigarótio!");
            else if (Tipo_curso.equalsIgnoreCase("")) edtTipoCurso.setError("Campo obrigarótio!");
            else if (Nota_Mec.equalsIgnoreCase("")) edtNotaMec.setError("Campo obrigarótio!");
            else if (Preco.equalsIgnoreCase("")) edtValor.setError("Campo obrigarótio!");
            else if (Area_Curso.equalsIgnoreCase("")) edtAreaCurso.setError("Campo obrigarótio!");

        } else {
            curso.setId("");
            curso.setNome(Nome);
            curso.setRequisito("");
            curso.setCarga_Horaria(Carga_Horaria);
            curso.setData_Inicio(Data_Inicio);
            curso.setData_Fim(Data_Fim);
            curso.getArea_Curso();
            curso.setData_Inicio_Inscricao("");
            curso.setData_Fim_Inscricao("");
            curso.setNome_Estabelecimento(nome_Estabelecimento);
            curso.setCodigo_Estabelecimento(Codigo_Estabelecimento);
            curso.setLimite_Alunos(Limite_Alunos);
            curso.setTipo_curso(Tipo_curso);
            curso.setNota_Mec(Nota_Mec);
            curso.setPreco(Preco);
            curso.setBolsa("");
            curso.setMaterias("");

            databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.child("Cursos/" + curso.getCodigo_Estabelecimento()+"/").setValue(curso);


        }
    }

}