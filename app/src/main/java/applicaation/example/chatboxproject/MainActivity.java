package applicaation.example.chatboxproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText userInput;
    RecyclerView recyclerView;
    List<ResponseMessage> responseMessages;
    MessageAdapter messageAdapter;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput =findViewById(R.id.userInput);
        img=findViewById(R.id.video);
        recyclerView=findViewById(R.id.conversation);
        responseMessages=new ArrayList<>();
        messageAdapter=new MessageAdapter(responseMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_SEND){

                    ResponseMessage message=new ResponseMessage(userInput.getText().toString(),true);
                    responseMessages.add(message);

                    ResponseMessage message2=new ResponseMessage(userInput.getText().toString(),false);
                    responseMessages.add(message2);

                    messageAdapter.notifyDataSetChanged();

                    if(!isVisiable()){
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                    }


                }
                userInput.setText(" ");

                return true;
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Videchat.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Now your Video chart start wait for few second",Toast.LENGTH_LONG).show();
            }
        });


    }


    public boolean isVisiable(){

        LinearLayoutManager linearLayoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
        int position=linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int item=recyclerView.getAdapter().getItemCount();
        return(position>=item);
    }
}
