package com.faculdade.buscacurso.Objetos;

public class Usuario
{
    private String UserId;
    private String Nome;
    private String Telefone;
    private String Email;

    public String getUserId()
    {
        return UserId;
    }

    public void setUserId(String userId)
    {
        UserId = userId;
    }

    public String getNome()
    {
        return Nome;
    }

    public void setNome(String nome)
    {
        Nome = nome;
    }

    public String getTelefone()
    {
        return Telefone;
    }

    public void setTelefone(String telefone)
    {
        Telefone = telefone;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }
}
