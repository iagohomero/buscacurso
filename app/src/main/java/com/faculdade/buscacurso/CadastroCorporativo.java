package com.faculdade.buscacurso;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.faculdade.buscacurso.Objetos.Corporativo;
import com.google.firebase.database.ValueEventListener;

public class CadastroCorporativo extends AppCompatActivity
{
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private Button btConfirmar;
    private Button btVoltar;

    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtConfirmaSenha;
    private EditText edtCnpj;
    int i = 0;
    private Corporativo corpUser = new Corporativo();
    private String email;
    private String senha;
    private String senhaConfirm;
    private String cnpj;

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

        btConfirmar = findViewById(R.id.btCadastrar);
        btVoltar = findViewById(R.id.btVoltar);

        btConfirmar.setOnClickListener(new View.OnClickListener()
        { @Override
        public void onClick(View v) {
            email = edtEmail.getText().toString();
            senha = edtSenha.getText().toString();
            cnpj = edtCnpj.getText().toString();
            senhaConfirm = edtConfirmaSenha.getText().toString();
            //Checa se todos os campos foram preenchidos devidamente e
            // caso não tenham sido, mostra um Hint pro usuário
            if(email.equalsIgnoreCase("")
                    || senha.equalsIgnoreCase("")
                    || cnpj.equalsIgnoreCase("")
                    || senhaConfirm.equalsIgnoreCase("")
                    || senha.length() < 6){
                if(email.equalsIgnoreCase("") ) edtEmail.setError("Email é obrigatório");
                if(senha.equalsIgnoreCase("") ) edtSenha.setError("Senha é obrigatório");
                //if(cnpj.equalsIgnoreCase("") ) edtCnpj.setError("CNPJ é obrigatório");
                if(senhaConfirm.equalsIgnoreCase("") ) edtConfirmaSenha.setError("Confirme a senha por favor");
                if(senha.length() < 6 && !senha.equalsIgnoreCase("") ) edtConfirmaSenha.setError("Escolha uma senha com pelo menos 6 caracteres");
            }else{
                if(senhasConfirmam()){
                    cadastroCorporativo();
                }
                else{
                    showPasswordMissmatchAlert();
                }

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


        if(this.senha.equals(this.senhaConfirm)) return true;
        else return false;

    }

    public void showOnSignUpTry(boolean success){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(success == true)
        {
            Singleton.getInstance(getApplicationContext()).setCorporativo(corpUser);
            Intent intent = new Intent(CadastroCorporativo.this, HomeCorporativo.class);
            startActivity(intent);
            finish();
        }
        else{
            builder.setTitle("Houve um erro no cadastro");
            builder.setMessage("Revise as informações e tente novamente!");
            builder.setPositiveButton("Ok"  ,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
        }


        this.alerta = builder.create();
        this.alerta.show();

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

    public void showCnpjAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estabelecimento inválido");
        builder.setMessage("CNPJ não cadastrado");
        builder.setPositiveButton("Ok"  ,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        this.alerta = builder.create();
        this.alerta.show();

    }

    public void cadastroCorporativo()
    {
        corpUser.setEmail(email);
        corpUser.setCNPJ(Singleton.getInstance(getApplicationContext()).getCorporativo().getCNPJ());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Estabelecimentos/"+corpUser.getCNPJ().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Estabelecimentos estabelecimentos = new Estabelecimentos();
                    estabelecimentos = dataSnapshot.getValue(Estabelecimentos.class);
                    CreateNewUser(estabelecimentos);
                }
                else
                    showCnpjAlert();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void CreateNewUser(final Estabelecimentos estabelecimentos)
    {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(corpUser.getEmail(), senha).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            corpUser.setCodigoEstabelecimento(estabelecimentos.getCodigoEstabelecimento());
                            corpUser.setNomeEstabelecimento(estabelecimentos.getNomeEstabelecimento());
                            corpUser.setUserId(firebaseAuth.getCurrentUser().getUid());
                            corpUser.setBairro(estabelecimentos.getBairro());
                            corpUser.setRua(estabelecimentos.getRua());
                            corpUser.setCNPJ(estabelecimentos.getCnpj());

                            databaseReference.child("UsuariosCorporativos/"+corpUser.getUserId()).setValue(corpUser);
                            showOnSignUpTry(true);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast=Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT);
                toast.show();
                showOnSignUpTry(false);
            }
        });
    }
}
