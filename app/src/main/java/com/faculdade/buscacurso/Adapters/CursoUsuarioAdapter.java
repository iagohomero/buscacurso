package com.faculdade.buscacurso.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faculdade.buscacurso.CorporativoEditarCursosActivity;
import com.faculdade.buscacurso.CursoInfo;
import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.R;
import com.faculdade.buscacurso.Singleton.Singleton;
import com.faculdade.buscacurso.VerCursos;
import com.faculdade.buscacurso.VerCursosCorporativo;

import java.util.ArrayList;

public class CursoUsuarioAdapter extends RecyclerView.Adapter<CursoUsuarioAdapter.MyViewHolder>

{
    ArrayList<Curso> arrayList = new ArrayList<>();
    Context context;
    boolean isCurso = false;
    boolean isFavoritos = false;
    String isCorporativo;
    String isCorporativoEditar;
    public CursoUsuarioAdapter(ArrayList<Curso> arrayList, Context mContext)
    {
        this.arrayList = arrayList;
        this.context = mContext;
    }

    public CursoUsuarioAdapter(ArrayList<Curso> arrayList, Context mContext, String isCorporativoEditar)
    {
        this.arrayList = arrayList;
        this.context = mContext;
        this.isCorporativoEditar = isCorporativoEditar;
    }

    public CursoUsuarioAdapter(ArrayList<Curso> arrayList, Context mContext, boolean isCurso , String isCorporativo)
    {
        this.arrayList = arrayList;
        this.context = mContext;
        this.isCurso = isCurso;
        this.isCorporativo = isCorporativo;
    }
    public CursoUsuarioAdapter(ArrayList<Curso> arrayList, Context mContext, boolean isCurso , boolean isFavoritos)
    {
        this.arrayList = arrayList;
        this.context = mContext;
        this.isCurso = isCurso;
        this.isFavoritos = isFavoritos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cursos_usuario,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        Singleton.getInstance(context.getApplicationContext()).setCurso(null);
        if(isCorporativoEditar != null && isCorporativoEditar.equals("x"))
        {
            Log.d("ISFAVORITOS", String.valueOf((isFavoritos)));
            holder.tvNomeEstabelecimento.setText(arrayList.get(position).getNome_Estabelecimento());
            holder.tvInscricao.setVisibility(View.GONE);
            holder.tvValor.setVisibility(View.GONE);
            holder.tvEndereco.setVisibility(View.GONE);
            holder.tvNomeCurso.setText(arrayList.get(position).getNome());
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, CorporativoEditarCursosActivity.class);
                    intent.putExtra("AreaCurso", arrayList.get(position).getArea_Curso());
                    intent.putExtra("IdCurso", arrayList.get(position).getId());
                    Singleton.getInstance(context).setCurso(arrayList.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        else if(!isCurso)
        {

            Log.d("ISFAVORITOS", String.valueOf((isFavoritos)));
            holder.tvEndereco.setText(arrayList.get(position).getEndereco());
            holder.tvValor.setText(arrayList.get(position).getBolsa());
            holder.tvInscricao.setText(arrayList.get(position).getData_Fim_Inscricao());
            holder.tvNomeEstabelecimento.setText("x");
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, CursoInfo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("favoritos",String.valueOf(isFavoritos));
                    Singleton.getInstance(context).setCurso(arrayList.get(position));
                    context.startActivity(intent);
                }
            });
        }
        else if(isCorporativo !=null &&isCorporativo.equals("x"))
        {
            Log.d("ISFAVORITOS", String.valueOf((isFavoritos)));
            holder.tvNomeEstabelecimento.setText(arrayList.get(position).getNome_Estabelecimento());
            holder.tvInscricao.setVisibility(View.GONE);
            holder.tvValor.setVisibility(View.GONE);
            holder.tvEndereco.setVisibility(View.GONE);
            holder.tvNomeCurso.setText(arrayList.get(position).getArea_Curso());
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, VerCursosCorporativo.class);
                    intent.putExtra("AreaCurso", arrayList.get(position).getArea_Curso());
                    intent.setAction("asd");

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        else
        {
            Log.d("ISFAVORITOS", String.valueOf((isFavoritos)));
            holder.tvNomeEstabelecimento.setText(arrayList.get(position).getNome_Estabelecimento());
            holder.tvInscricao.setVisibility(View.GONE);
            holder.tvValor.setVisibility(View.GONE);
            holder.tvEndereco.setVisibility(View.GONE);
            holder.tvNomeCurso.setText(arrayList.get(position).getArea_Curso());
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, VerCursos.class);
                    intent.putExtra("NomeEstabelecimento",String.valueOf(arrayList.get(position).getNome_Estabelecimento()));
                    intent.putExtra("CodigoEstabelecimento", arrayList.get(position).getCodigo_Estabelecimento());
                    intent.putExtra("AreaCurso", arrayList.get(position).getArea_Curso());


                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNomeCurso;
        TextView tvValor;
        TextView tvEndereco;
        TextView tvNomeEstabelecimento;
        TextView tvInscricao;
        CardView cardView;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tvNomeCurso = (TextView) itemView.findViewById(R.id.tvNomeCurso) ;
            tvValor =(TextView) itemView.findViewById(R.id.tvValor);
            tvEndereco =(TextView)itemView.findViewById(R.id.tvEndereco);
            tvNomeEstabelecimento = itemView.findViewById(R.id.tvNomeEstabelecimento);
            tvInscricao = (TextView) itemView.findViewById(R.id.tvInscricao);

        }
    }
}
