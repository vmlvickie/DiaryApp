package com.project.vickie.diaryapp.dto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.vickie.diaryapp.R;
import com.project.vickie.diaryapp.ViewItemEntry;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;

import java.io.Serializable;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private RecyclerView recyclerView;
    private List<Item> itemsList;
    private Context context;

    private RecyclerViewAdapterOnClickHandler mClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(int itemPosition);
    }



    public RecyclerViewAdapter(List<Item> itemList, Context context, RecyclerView rcView, RecyclerViewAdapterOnClickHandler clickHandler) {
        this.context = context;
        this.itemsList = itemList;
        this.recyclerView = rcView;
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item item = itemsList.get(position);
        holder.tv_date.setText(item.getDate());
        holder.tv_title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_date;
        TextView tv_title;
        FrameLayout single_row;
        public View layout;
        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
