package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faculdade.buscacurso.Objetos.Usuario;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PerfilUsuario extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private Button btCadastrar;
    private Button btVoltar;

    private EditText edtNome;
    private EditText edtTelefone;
    private EditText edtSenhaAtual;
    private EditText edtSenha;
    private EditText edtRepetirSenha;

    private Usuario usuario = new Usuario();
    private String nome;
    private String senhaAtual;
    private String novaSenha;
    private String repetirSenha;
    private String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtSenhaAtual = findViewById(R.id.edtSenhaAtual);
        edtSenha = findViewById(R.id.edtSenha);
        edtRepetirSenha = findViewById(R.id.edtRepetirSenha);

        btCadastrar = findViewById(R.id.btCadastrar);
        btVoltar = findViewById(R.id.btVoltar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Usuarios/"+firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Usuario usuario = new Usuario();
                    usuario = dataSnapshot.getValue(Usuario.class);
                    edtNome.setText(usuario.getNome());
                    edtTelefone.setText(usuario.getTelefone());
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
        usuario.setNome(nome);
        usuario.setTelefone(telefone);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("Usuarios/"+firebaseUser.getUid()).setValue(usuario);

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
        telefone = edtTelefone.getText().toString();
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
        else if(telefone.equalsIgnoreCase("")) {
            edtTelefone.setError("Telefone é obrigatório");
            return false;
        }
        else{
            return true;
        }

    }

}
