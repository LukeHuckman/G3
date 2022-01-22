package com.g3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagRecViewAdapter extends  RecyclerView.Adapter<TagRecViewAdapter.ViewHolder>{

    public ArrayList<Tag> tags = new ArrayList<Tag>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_listview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tag tag = tags.get(position);

        holder.tagName.setText(tag.getName());

    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tagName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagName = (TextView) itemView.findViewById(R.id.tagName);

        }
    }
}
