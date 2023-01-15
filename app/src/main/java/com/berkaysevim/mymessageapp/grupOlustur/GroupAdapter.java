package com.berkaysevim.mymessageapp.grupOlustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkaysevim.mymessageapp.GroupModel;
import com.berkaysevim.mymessageapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    List<GroupModel> groupModelList;

    public GroupAdapter(List<GroupModel> groupModelList) {
        this.groupModelList = groupModelList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grup_olustur, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel groupModel = groupModelList.get(position);
        holder.setData(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView groupImageView;
        TextView groupNameTextView, groupDescriptionTextView;

        public GroupViewHolder(View itemView) {
            super(itemView);

            groupImageView = itemView.findViewById(R.id.nav_grupolustur_groupImageImageView);
            groupNameTextView = itemView.findViewById(R.id.nav_grupolustur_groupnamename);
            groupDescriptionTextView = itemView.findViewById(R.id.nav_grupolustur_grupdescription);
        }

        public void setData(GroupModel groupModel) {
            groupNameTextView.setText(groupModel.getName());
            groupDescriptionTextView.setText(groupModel.getDescription());

            if (groupModel.getImage() != null) {
                Picasso.get().load(groupModel.getImage()).into(groupImageView);
            }
        }
    }
}