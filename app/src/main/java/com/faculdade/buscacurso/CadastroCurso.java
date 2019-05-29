package com.faculdade.buscacurso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.faculdade.buscacurso.Objetos.Curso;

public class CadastroCurso extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);

        edtNomeCurso = findViewById(R.id.edtNomeCurso);
        edtTipoCurso = findViewById(R.id.edtTipoCurso);
        edtAreaCurso = findViewById(R.id.edtAreaCurso);
        edtNotaMec = findViewById(R.id.edtNotaMec);
        edtValor = findViewById(R.id.edtValor);
        edtLimiteAlunos = findViewById(R.id.edtLimiteAlunos);
        edtCargaHoraria = findViewById(R.id.edtCargaHoraria);
        edtDataIni = findViewById(R.id.edtDataIni);
        edtDataFim = findViewById(R.id.edtDataFim);
    }
}
