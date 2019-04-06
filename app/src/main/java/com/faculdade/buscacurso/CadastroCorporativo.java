package com.faculdade.buscacurso;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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





    }
}
