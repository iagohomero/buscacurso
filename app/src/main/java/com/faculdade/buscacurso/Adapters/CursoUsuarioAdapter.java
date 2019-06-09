package com.faculdade.buscacurso.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faculdade.buscacurso.Objetos.Curso;
import com.faculdade.buscacurso.R;

import java.util.ArrayList;

public class CursoUsuarioAdapter extends RecyclerView.Adapter<CursoUsuarioAdapter.MyViewHolder>

{
    ArrayList<Curso> arrayList = new ArrayList<>();
    Context context;
    public CursoUsuarioAdapter(ArrayList<Curso> arrayList, Context mContext)
    {
        this.arrayList = arrayList;
        this.context = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cursos_usuario,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.tvEndereco.setText(arrayList.get(position).getEndereco());
        holder.tvValor.setText(arrayList.get(position).getBolsa());
        holder.tvInscricao.setText(arrayList.get(position).getData_Fim_Inscricao());
        holder.tvNomeEstabelecimento.setText("x");
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

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvNomeCurso = (TextView) itemView.findViewById(R.id.tvNomeCurso) ;
            tvValor =(TextView) itemView.findViewById(R.id.tvValor);
            tvEndereco =(TextView)itemView.findViewById(R.id.tvEndereco);
            tvNomeEstabelecimento = itemView.findViewById(R.id.tvNomeEstabelecimento);
            tvInscricao = (TextView) itemView.findViewById(R.id.tvInscricao);

        }
    }
}
