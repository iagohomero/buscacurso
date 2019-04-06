package com.faculdade.buscacurso;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
>>>>>>> cbaa73cf4342016a1bc6267253aead8b40ba348c
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.faculdade.buscacurso.Objetos.Corporativo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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



        //setando manualmente os dados corporativo de um usuário, os dados devem ser mapeados de acordo com o que o usuário digitar
        // nas caixas de textos /edittexts
        Corporativo corporativo = new Corporativo();
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
        corporativo.setLongitude("longitd");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //teste de como cadastrar no database
        // databaseReference.child("UsuariosCorporativos/"+corporativo.getUserId()).setValue(corporativo);

        firebaseAuth= FirebaseAuth.getInstance();
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
