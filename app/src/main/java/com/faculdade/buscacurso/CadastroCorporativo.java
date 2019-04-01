package com.faculdade.buscacurso;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroCorporativo extends AppCompatActivity
{

    private Button btConfirmar;
    private Button btVoltar;

    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private EditText edtCnpj;

    private String email;
    private String senha;
    private String senhaConfirm;
    private Long cnpj;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_corporativo);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);
        edtCnpj = findViewById(R.id.edtCnpj);

        btConfirmar = findViewById(R.id.btConfirmar);
        btVoltar = findViewById(R.id.btVoltar);

        btConfirmar.setOnClickListener(new View.OnClickListener()
        { @Override
          public void onClick(View v) {
            if(senhasConfirmam()){

            }
            else{
                showPasswordMissmatchAlert();
            }
        } });

        //Ao clickar em voltar, dá finish na activity
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean senhasConfirmam(){
       this.senha = edtSenha.getText().toString();
       this.senhaConfirm = edtConfirmaSenha.getText().toString();

       if(this.senha.equals(this.senhaConfirm)) return true;
       else return false;

    }

    public void showPasswordMissmatchAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Senhas não correspondem");
        builder.setMessage("Por favor, confirme a senha novamente.");
        builder.setPositiveButton("Ok"  ,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        this.alerta = builder.create();
        this.alerta.show();
    }
}
