package com.faculdade.buscacurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faculdade.buscacurso.Objetos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CadastroUsuario extends AppCompatActivity
{
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private Button btCadastrar;
    private Button btVoltar;

    private EditText edtEmail;
    private EditText edtNome;
    private EditText edtSenha;
    private EditText edtRepetirSenha;
    private EditText edtTelefone;

    private Usuario usuario = new Usuario();
    private String email;
    private String nome;
    private String senha;
    private String repetirSenha;
    private String telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtEmail = findViewById(R.id.edtEmail);
        edtNome = findViewById(R.id.edtNome);
        edtSenha = findViewById(R.id.edtSenha);
        edtRepetirSenha = findViewById(R.id.edtRepetirSenha);
        edtTelefone = findViewById(R.id.edtTelefone);

        btCadastrar = findViewById(R.id.btCadastrar);
        btVoltar = findViewById(R.id.btVoltar);

        btCadastrar.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {

                if(validar()){
                    cadastrar();
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

    private boolean cadastrar(){
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setTelefone(telefone);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), senha).addOnCompleteListener
            (new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        usuario.setUserId(firebaseAuth.getCurrentUser().getUid());
                        databaseReference.child("Usuarios/"+usuario.getUserId()).setValue(usuario);
                        finish();
                        Intent intent = new Intent(CadastroUsuario.this, Home.class);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        return true;
    }

    private boolean validar(){
        email = edtEmail.getText().toString();
        nome = edtNome.getText().toString();
        telefone = edtTelefone.getText().toString();
        senha = edtSenha.getText().toString();
        repetirSenha = edtRepetirSenha.getText().toString();

        if(email.equalsIgnoreCase("")){
            edtEmail.setError("Email é obrigatório");
            return false;
        }
        else if(nome.equalsIgnoreCase("")){
            edtNome.setError("Nome é obrigatório");
            return false;
        }
        else if(senha.equalsIgnoreCase("") || senha.length() < 6){
            edtSenha.setError("Senha de no mínimo 6 caracteres");
            return false;
        }
        else if(repetirSenha.equalsIgnoreCase("") || repetirSenha.length() < 6){
            edtRepetirSenha.setError("Repita a senha com no mínimo 6 caracteres");
            return false;
        }
        else if(telefone.equalsIgnoreCase("")) {
            edtTelefone.setError("Telefone é obrigatório");
            return false;
        }
        else if(!isValidEmail(email)){
            edtEmail.setError("Email inválido");
            return false;
        }
        else{
            return true;
        }

    }

    private boolean isValidEmail(String email)
    {
        //método responsável para verificar se o e-mail é válido

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
