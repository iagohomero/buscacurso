package com.faculdade.buscacurso;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faculdade.buscacurso.Objetos.Corporativo;
import com.faculdade.buscacurso.Objetos.Estabelecimentos;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{

    private Button btCadastrarUsuario;
    private Button btCadastroEmpresa;
    private Button btLogin;

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    private EditText edtEmail;
    private EditText edtSenha;

    private String Email;
    private String Senha;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        btCadastrarUsuario = findViewById(R.id.btCadastrarUsuario);
        btCadastroEmpresa = findViewById(R.id.btCadastroEmpresa);
        btLogin = findViewById(R.id.btLogin);

        btCadastroEmpresa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), CadastroCorporativo.class);
                startActivity(intent);

            }
        });

        btCadastrarUsuario.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), CadastroUsuario.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidarDados();
            }
        });

    }

    private void ValidarDados()
    {

        Email = edtEmail.getText().toString();
        Senha = edtSenha.getText().toString();

        if(isValidEmail(Email) && Senha.length() >= 6)
            RealizarLogin();

        else
        {
            if(!isValidEmail(Email))
            {
                edtEmail.setError("Email inválido");
                Toast.makeText(this,"Corrija o email", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                edtSenha.setError("Senha de no mínimo 6 caracteres");
                Toast.makeText(this,"Corrija a senha", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private void RealizarLogin()
    {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(Email, Senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        //if the task is successfull
                        if (task.isSuccessful())
                        {
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios/"+firebaseAuth.getCurrentUser().getUid());
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if(dataSnapshot.exists())
                                    {
                                        Intent intent = new Intent(LoginActivity.this, Home.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        DatabaseReference databaseReferenceCorporativo = FirebaseDatabase.getInstance().getReference().child("UsuariosCorporativos/"+firebaseAuth.getCurrentUser().getUid());
                                        databaseReferenceCorporativo.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                if(dataSnapshot.exists())
                                                {
                                                    Corporativo corporativo = new Corporativo();
                                                    corporativo = dataSnapshot.getValue(Corporativo.class);
                                                    Singleton.getInstance(getApplicationContext()).setCorporativo(corporativo);

                                                    Intent intent = new Intent(LoginActivity.this, HomeCorporativo.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                    builder.setTitle("Usuário não cadastrado")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                    AlertDialog alert = builder.create();
                                                    alert.show();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        /*
        firebaseAuth.signInWithEmailAndPassword(Email, Senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios/"+firebaseAuth.getCurrentUser().getUid());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.exists())
                            {
                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                startActivity(intent);
                            }
                            else
                            {
                                DatabaseReference databaseReferenceCorporativo = FirebaseDatabase.getInstance().getReference().child("UsuariosCorporativos/"+firebaseAuth.getCurrentUser().getUid());
                                databaseReferenceCorporativo.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        if(dataSnapshot.exists())
                                        {
                                            Corporativo corporativo = new Corporativo();
                                            corporativo = dataSnapshot.getValue(Corporativo.class);
                                            Singleton.getInstance().setCorporativo(corporativo);

                                            Intent intent = new Intent(LoginActivity.this, HomeCorporativo.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("Usuário não cadastrado")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Email ou senha inválidos")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        */
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
