package com.faculdade.buscacurso;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.faculdade.buscacurso.Objetos.Corporativo;

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



        //setando manualmente os dados corporativo de um usuário, os dados devem ser mapeados de acordo com o que o usuário digitar
        // nas caixas de textos /edittexts
        /*Corporativo corporativo = new Corporativo();
        corporativo.setBairro("Eldorado");
        corporativo.setCidade("Contagem");
        corporativo.setCNPJ("19.973.0001-40");
        corporativo.setComplemento(" ");
        corporativo.setEmail("teste@teste.com.br");
        corporativo.setEstado("MG");
        corporativo.setNumero("10");
        corporativo.setReferencia("proximo ao posto");
        corporativo.setResponsavel("Joao Goualrt");
        corporativo.setRua("Ruaaaaaaaaaa");
        corporativo.setUrlImagem("google.com");
        corporativo.setUserId("IdUsuario");
        corporativo.setLatitude("latitude");
        corporativo.setLongitude("longitd");*/


        //teste de como cadastrar no database
        // databaseReference.child("UsuariosCorporativos/"+corporativo.getUserId()).setValue(corporativo);


        /*
        só pegar os dados do usuario e passar ali onde tem email e senha
        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                corporativo.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid();)
                databaseReference.child("UsuariosCorporativos/"+corporativo.getUserId()).setValue(corporativo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/






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
                    || senhaConfirm.equalsIgnoreCase("")){
                if(email.equalsIgnoreCase("") ) edtEmail.setError("Email é obrigatório");
                if(senha.equalsIgnoreCase("") ) edtSenha.setError("Senha é obrigatório");
                if(cnpj.equalsIgnoreCase("") ) edtCnpj.setError("CNPJ é obrigatório");
                if(senhaConfirm.equalsIgnoreCase("") ) edtConfirmaSenha.setError("Confirme a senha por favor");
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
        if(success == true){
            builder.setTitle("Usuário cadastrado com sucesso");
            builder.setMessage("Basta retornar a tela inicial e efetuar o login");
            builder.setPositiveButton("Ok"  ,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
        }
        else{
            builder.setTitle("Houve um erro no cadastro");
            builder.setMessage("Revise as informações e tente novamente");
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

    public void cadastroCorporativo(){
        corpUser.setEmail(email);
        corpUser.setCNPJ(cnpj);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(corpUser.getEmail(), this.senha).addOnCompleteListener
                (this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful()){
                            corpUser.setUserId(firebaseAuth.getCurrentUser().getUid());
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
