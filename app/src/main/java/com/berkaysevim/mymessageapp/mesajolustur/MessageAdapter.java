package com.berkaysevim.mymessageapp.mesajolustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkaysevim.mymessageapp.MessageModel;
import com.berkaysevim.mymessageapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    List<MessageModel> messageModelList;

    public MessageAdapter(List<MessageModel> messageModelList) {
        this.messageModelList = messageModelList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mesaj_olustur, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);
        holder.setData(messageModel);
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageNameTextView, messageDescriptionTextView;

        public MessageViewHolder(
                View itemView) {
            super(itemView);

            messageNameTextView = itemView.findViewById(R.id.createmessage_messageNameEditText);
            messageDescriptionTextView = itemView.findViewById(R.id.createmessage_messageDescriptionEditText);
        }

        public void setData(MessageModel groupModel) {
            messageNameTextView.setText(groupModel.getName());
            messageDescriptionTextView.setText(groupModel.getDescription());
        }
    }
}
