package com.example.remindme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TerminAdapter extends RecyclerView.Adapter<TerminAdapter.TerminHolder> {
    private List<TerminItem> terminListe = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TerminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.termin_item, parent, false);
        return new TerminHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TerminHolder holder, int position) {
        TerminItem currentTermin = terminListe.get(position);
        holder.ImageViewPrioritaet.setImageResource(currentTermin.getPrioritaet());
        holder.TextViewTermin.setText(currentTermin.getTextTermin());
        holder.TextViewBisZeit.setText(currentTermin.getBisZeit());
    }

    @Override
    public int getItemCount() {
        return terminListe.size();
    }

    public void setTerminListe(List<TerminItem> terminListe) {
        this.terminListe = terminListe;
        notifyDataSetChanged();
    }

    public TerminItem getTerminAt(int position) {
        return terminListe.get(position);
    }

    class TerminHolder extends RecyclerView.ViewHolder {
        private ImageView ImageViewPrioritaet;
        private TextView TextViewTermin;
        private TextView TextViewBisZeit;

        public TerminHolder(@NonNull View itemView) {
            super(itemView);
            ImageViewPrioritaet = itemView.findViewById(R.id.imageViewPrioritaet);
            TextViewTermin = itemView.findViewById(R.id.textViewTermin);
            TextViewBisZeit = itemView.findViewById(R.id.textViewBisZeit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(terminListe.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TerminItem terminItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
