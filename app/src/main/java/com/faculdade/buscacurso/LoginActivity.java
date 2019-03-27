package com.faculdade.buscacurso;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{

    private Button btCadastrarUsuario;
    private Button btCadastroEmpresa;
    private Button btLogin;

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
        //método responsável por realizar o login

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Função não implementada")
                .setMessage("Função não realizada até o momento..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        return;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        return;
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
