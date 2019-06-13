package com.faculdade.buscacurso;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.faculdade.buscacurso.Objetos.Corporativo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PerfilCorporativo extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private Button btCadastrar;
    private Button btVoltar;

    private EditText edtNome;
    private EditText edtSenhaAtual;
    private EditText edtSenha;
    private EditText edtRepetirSenha;
    private EditText edtCnpj;
    private EditText edtBairro;
    private EditText edtRua;


    private Corporativo corporativo = new Corporativo();
    private String nome;
    private String senhaAtual;
    private String novaSenha;
    private String repetirSenha;
    private String cnpj;
    private String bairro;
    private String rua;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_corporativo);

        edtNome = findViewById(R.id.edtNome);
        edtSenhaAtual = findViewById(R.id.edtSenhaAtual);
        edtSenha = findViewById(R.id.edtSenha);
        edtRepetirSenha = findViewById(R.id.edtRepetirSenha);
        edtCnpj = findViewById(R.id.edtCnpj);
        edtBairro = findViewById(R.id.edtBairro);
        edtRua = findViewById(R.id.edtRua);

        btCadastrar = findViewById(R.id.btCadastrar);
        btVoltar = findViewById(R.id.btVoltar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("UsuariosCorporativos/"+firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Corporativo corporativo = new Corporativo();
                    corporativo = dataSnapshot.getValue(Corporativo.class);
                    edtNome.setText(corporativo.getNomeEstabelecimento());
                    edtCnpj.setText(corporativo.getCNPJ());
                    edtBairro.setText(corporativo.getBairro());
                    edtRua.setText(corporativo.getRua());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            if(validar()){
                editar();
                finish();
            }
        }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean editar(){
        corporativo.setNomeEstabelecimento(nome);
        corporativo.setCNPJ(cnpj);
        corporativo.setBairro(bairro);
        corporativo.setRua(rua);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("UsuariosCorporativos/"+firebaseUser.getUid()).setValue(corporativo);

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), senhaAtual);
        firebaseUser.reauthenticate(credential)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.updatePassword(novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    } else {

                    }
                }
            });

        return true;
    }

    private boolean validar(){
        nome = edtNome.getText().toString();
        cnpj = edtCnpj.getText().toString();
        bairro = edtBairro.getText().toString();
        rua = edtRua.getText().toString();
        senhaAtual = edtSenhaAtual.getText().toString();
        novaSenha = edtSenha.getText().toString();
        repetirSenha = edtRepetirSenha.getText().toString();

        if(nome.equalsIgnoreCase("")){
            edtNome.setError("Nome é obrigatório");
            return false;
        }
        else if(novaSenha.equalsIgnoreCase("") || novaSenha.length() < 6){
            edtSenha.setError("Senha de no mínimo 6 caracteres");
            return false;
        }
        else if(repetirSenha.equalsIgnoreCase("") || repetirSenha.length() < 6){
            edtRepetirSenha.setError("Repita a senha com no mínimo 6 caracteres");
            return false;
        }
        else if(!repetirSenha.equals(novaSenha)){
            edtRepetirSenha.setError("As senhas não são iguais");
            return false;
        }
        else{
            return true;
        }

    }

}
