package com.example.remindme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class TerminAdapter extends ListAdapter<TerminItem, TerminAdapter.TerminHolder> {
    private OnItemClickListener listener;

    public TerminAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TerminItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<TerminItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull TerminItem oldItem, @NonNull TerminItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TerminItem oldItem, @NonNull TerminItem newItem) {
            return oldItem.getTextTermin().equals(newItem.getTextTermin()) &&
                    oldItem.getBisZeit().equals(newItem.getBisZeit()) &&
                    oldItem.getPrioritaet() == (newItem.getPrioritaet());

        }
    };

    @NonNull
    @Override
    public TerminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.termin_item, parent, false);
        return new TerminHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TerminHolder holder, int position) {
        TerminItem currentTermin = getItem(position);
        holder.ImageViewPrioritaet.setImageResource(currentTermin.getPrioritaet());
        holder.TextViewTermin.setText(currentTermin.getTextTermin());
        holder.TextViewBisZeit.setText(currentTermin.getBisZeit());
    }

    public TerminItem getTerminAt(int position) {
        return getItem(position);
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
                        listener.onItemClick(getItem(position));
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
