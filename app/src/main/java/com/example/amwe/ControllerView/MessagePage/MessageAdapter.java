package com.example.amwe.ControllerView.MessagePage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amwe.Model.Messaging.Message;
import com.example.amwe.R;

import java.util.List;

/**
 * This class is intended as the adapter of the recycleview that will show recent messages by Author.
 * Sort of like Facebook Messenger
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHold> {
    private final List messagesList;
    private Context context;

    public MessageAdapter(List<Message> messages, Context context) {
        this.messagesList = messages;
        this.context = context;
    }


    public static class MessageViewHold extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;


        /**
         * Constructor for ViewHold.
         *
         * @param itemView
         */
        public MessageViewHold(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.messagetextView);
        }

    }


    @NonNull
    @Override
    public MessageViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_card, parent, false);
        return new MessageViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHold holder, int position) {
        Message message = (Message) messagesList.get(position);
        holder.textViewTitle.setText(message.getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageListActivity.class);

                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}
