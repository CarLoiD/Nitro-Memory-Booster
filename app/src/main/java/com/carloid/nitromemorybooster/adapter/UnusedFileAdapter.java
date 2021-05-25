package com.carloid.nitromemorybooster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carloid.nitromemorybooster.R;
import com.carloid.nitromemorybooster.model.UnusedFile;
import com.carloid.nitromemorybooster.util.U;

import java.util.List;

public class UnusedFileAdapter extends RecyclerView.Adapter<UnusedFileAdapter.ViewHolder> {

    private final List<UnusedFile> mUnusedFileList;
    private final OnClickProvider listener;

    public interface OnClickProvider {
        void onProviderSelected(int position, boolean checked);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView path;
        public TextView size;
        public CheckBox deleteCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTitle);
            path = itemView.findViewById(R.id.pathContent);
            size = itemView.findViewById(R.id.sizeContent);
            deleteCheck = itemView.findViewById(R.id.deleteCheck);
        }

        public void bind(int position, final OnClickProvider listener) {
            deleteCheck.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onProviderSelected(position, isChecked));
        }
    }

    public UnusedFileAdapter(List<UnusedFile> list, OnClickProvider listener) {
        this.listener = listener;
        mUnusedFileList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unused_file, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setIsRecyclable(false);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UnusedFile file = mUnusedFileList.get(position);

        holder.name.setText(file.name);
        holder.path.setText(file.path);
        holder.size.setText(U.bytesToHuman(file.size));
        holder.deleteCheck.setChecked(file.delete);

        holder.bind(position, listener);
    }

    @Override
    public int getItemCount() {
        return mUnusedFileList.size();
    }

    public void changeCheckedItem(int position, boolean checked) {
        mUnusedFileList.get(position).delete = checked;
        notifyItemChanged(position);
    }
}
