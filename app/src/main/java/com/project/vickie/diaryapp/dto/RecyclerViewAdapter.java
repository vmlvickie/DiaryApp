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
    RecyclerView recyclerView;
    List<Item> itemsList;
    Context context;

    public RecyclerViewAdapter(List<Item> itemList, Context context, RecyclerView rcView) {
        this.context = context;
        this.itemsList = itemList;
        this.recyclerView = rcView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item item = itemsList.get(position);
        holder.tv_date.setText(item.getDate());
        holder.tv_title.setText(item.getTitle());

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //allow user to view Diary entry

                Intent i = new Intent(context, ViewItemEntry.class);
                i.putExtra("CLICKED_ITEM_ID",  position);
                context.startActivity(i);//getApplicationContext().

                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                       // editItemEntry(item.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper dbHelper = new DatabaseHelper(context);
                        //dbHelper.deleteItem(item.getId(), context);

                        itemsList.remove(position);
                        recyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, itemsList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_date;
        TextView tv_title;
        FrameLayout single_row;
        public View layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
