package com.faculdade.buscacurso.Singleton;

import android.content.Context;

import com.faculdade.buscacurso.Objetos.Corporativo;
import com.faculdade.buscacurso.Objetos.Curso;

public class Singleton
{
    public static Singleton instance=null;

//    public static Singleton getInstance() {
  //      return instance;
    //}

    public static synchronized Singleton getInstance(Context applicationContext) {
        if(instance == null)
            instance = new Singleton();

        return instance;
    }

    public static void setInstance(Singleton instance) {
        Singleton.instance = instance;
    }

    public Curso curso = new Curso();

    public Curso getCurso()
    {
        return curso;
    }

    public void setCurso(Curso curso)
    {
        this.curso = curso;
    }

    public Corporativo corporativo = new Corporativo();

    public Corporativo getCorporativo() {
        return corporativo;
    }

    public void setCorporativo(Corporativo corporativo) {
        this.corporativo = corporativo;
    }
}
