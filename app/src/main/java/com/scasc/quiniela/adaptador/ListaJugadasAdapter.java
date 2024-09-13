package com.scasc.quiniela.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scasc.quiniela.R;
import com.scasc.quiniela.entidad.Jugadas;

import java.util.ArrayList;

public class ListaJugadasAdapter extends RecyclerView.Adapter<ListaJugadasAdapter.JugadaViewHolder> {
    ArrayList<Jugadas> listaJugadas;
    ArrayList<Jugadas> listaOriginal;

    public ListaJugadasAdapter(ArrayList<Jugadas> listaJugadas) {
        this.listaJugadas = listaJugadas;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaJugadas);
    }

    @NonNull
    @Override
    public JugadaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_jugada,
                null, false);
        return new JugadaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadaViewHolder holder, int position) {
        holder.viewPosicion.setText("Numero: "+listaJugadas.get(position).getNumero()+"");
        holder.viewMonto.setText("Apuesta: "+listaJugadas.get(position).getMonto()+"");
        holder.viewTipo.setText("Posicion: "+listaJugadas.get(position).getPosicion()+"");
    }

    /*public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaJugadas.clear();
            listaJugadas.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Jugadas> collecion = listaJugadas.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaContactos.clear();
                listaContactos.addAll(collecion);
            } else {
                for (Contactos c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaContactos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return listaJugadas.size();
    }

    public class JugadaViewHolder extends RecyclerView.ViewHolder {

        TextView viewPosicion, viewMonto, viewTipo;

        public JugadaViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPosicion = itemView.findViewById(R.id.tvNombre);
            viewMonto = itemView.findViewById(R.id.tvTelefono);
            viewTipo = itemView.findViewById(R.id.tvCorreo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    //Intent intent = new Intent(context, VerActivity.class);
                    //intent.putExtra("ID", listaJugadas.get(getAdapterPosition()).getId());
                    //context.startActivity(intent);
                }
            });
        }
    }
}
