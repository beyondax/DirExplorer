package com.example.direxplorer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DirRecyclerViewAdapter extends RecyclerView.Adapter<DirRecyclerViewAdapter.MyViewHolder>  {

    private static final String TAG = "DirRecyclerViewAdapter";
    private ArrayList<DirPath> mDirPathsList;
    private OnClickListener mOnClickListener;


    public DirRecyclerViewAdapter(ArrayList<DirPath> dirPathsList, OnClickListener onClickListener) {
        this.mDirPathsList = dirPathsList;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public DirRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dir_name_text_view, parent, false);
        return new MyViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DirRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.dirPathTextView.setText(mDirPathsList.get(position).toString());
        Log.d(TAG, "onBindViewHolder: " + mDirPathsList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mDirPathsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dirPathTextView;
        OnClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            dirPathTextView = itemView.findViewById(R.id.dir_path_text_view);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClickListener.onCellClicked(getAdapterPosition());
        }
    }
}
