package com.teksystems.tekathon.teamup.recyclerview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teksystems.tekathon.teamup.R;
import com.teksystems.tekathon.teamup.databinding.TagListItem2Binding;
import com.teksystems.tekathon.teamup.model.Tag;
import com.teksystems.tekathon.teamup.ui.databinding.viewmodel.TagViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayank Tiwari on 24/09/16.
 */

public class TagAdapter2 extends RecyclerView.Adapter<TagAdapter2.ViewHolder> {

    private List<Tag> tagList;
    private Context context;

    public TagAdapter2(Context context, List<Tag> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    public List<Tag> getTagList() {
        if (tagList == null) {
            tagList = new ArrayList<>();
        }
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tag tag = tagList.get(position);
        TagViewModel tagViewModel = new TagViewModel(tag, context, position);
        holder.bindTo(tagViewModel);
    }

    @Override
    public int getItemCount() {
        return getTagList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TagListItem2Binding binding;

        public ViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public TagListItem2Binding getBinding() {
            return binding;
        }

        public void bindTo(TagViewModel tagViewModel) {
            binding.setTagViewModel(tagViewModel);
            binding.executePendingBindings();
        }
    }
}