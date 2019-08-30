package applicaation.example.chatboxproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        EditText ed;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.textMessage);


        }
    }

    List<ResponseMessage> responseMessages;

    public MessageAdapter(List<ResponseMessage > responseMessages) {

        this.responseMessages=responseMessages;



    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessages.get(position).isMe){
            return R.layout.me_bubble;
        }

        return R.layout.bot_bubble;


    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.CustomViewHolder holder, int position) {

        holder.textView.setText(responseMessages.get(position).getTextMessage());

    }

    @Override
    public int getItemCount() {
        return responseMessages.size();
    }


}
