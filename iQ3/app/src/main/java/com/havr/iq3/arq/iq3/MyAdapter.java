package com.havr.iq3.arq.iq3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnItemClickListener itemClickListener;
    private int img_res;


    public MyAdapter(List<String> names, int layout, OnItemClickListener listener, int img_res) {
        this.names = names;
        this.layout = layout;
        this.itemClickListener = listener;
        this.img_res = img_res;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la lógica como extraer los datos, referencias...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos al método Bind del ViewHolder pasándole objeto y listener
        holder.bind(names.get(position), itemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos UI a rellenar
        private ImageView imagenView;
        public TextView textViewName;

        public ViewHolder(View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.text_recycler);
            this.imagenView = (ImageView) itemView.findViewById(R.id.image_recycler);
        }

        public void bind(final String name, final OnItemClickListener listener,final int img_res) {
            //Recuperamos la imagen
            int imagen = 0;
            switch (img_res){
                case 0:
                    imagen = R.drawable.perfil_li;
                    break;
                case 1:
                    imagen = R.drawable.perfil_2li;
                    break;
                case 2:
                    imagen = R.drawable.perfil_ce;
                    break;
                case 3:
                    imagen= R.drawable.perfil_i;
                    break;
                case 4:
                    imagen = R.drawable.perfil_i;
                    break;
                case 5:
                    imagen = R.drawable.perfil_tr;
                    break;
                case 6:
                    imagen = R.drawable.perfil_or;
                    break;
                case 9:
                    imagen = R.drawable.perfil_oc;
                    break;
                case 10:
                    imagen = R.drawable.perfil_2li;
                    break;
            }
            // Procesamos los datos a renderizar
            this.textViewName.setText(name);
            this.imagenView.setImageResource(imagen);
            // que se comporta de la siguiente manera...
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // ... pasamos nuestro objeto modelo (este caso String) y posición
                    listener.onItemClick(name, getAdapterPosition());
                }
            });
        }
    }

    // Declaramos nuestra interfaz con el/los método/s a implementar
    public interface OnItemClickListener {
        void onItemClick(String name, int position);
    }


}