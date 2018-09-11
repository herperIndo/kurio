package com.heru.httpwww.kurio_messanger.Chat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.heru.httpwww.kurio_messanger.Chat.ChatPresenter.chatPresenter;
import com.heru.httpwww.kurio_messanger.Chat.ChatView.chatView;
import com.heru.httpwww.kurio_messanger.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements chatView {

    @BindView(R.id.llTittle)
    TextView llTittle;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    RelativeLayout layout2;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.AddParticipan)
    ImageView AddParticipan;
    @BindView(R.id.messageArea)
    EditText messageArea;
    @BindView(R.id.sendButton)
    ImageView sendButton;
    private Bundle extras;
    private String nameRoom;
    Activity activity = this;
    Context context = this;
    chatPresenter presenter;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        ButterKnife.bind(this);
        presenter = new chatPresenter(this);
        extras = getIntent().getExtras();
        if (extras != null) {
            nameRoom = extras.getString("roomName");
        }
        llTittle.setText(nameRoom);
    }

    @OnClick({R.id.AddParticipan, R.id.sendButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.AddParticipan:
                dialog = new Dialog(activity);
                dialog.setContentView(R.layout.popup_add_participan);
                final ListView listParticipant = (ListView) dialog.findViewById(R.id.listPartcipant);
                String url = "https://kuriomessanger-4fb20.firebaseio.com/users.json";
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        doOnSuccess(s, listParticipant);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("" + volleyError);
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(context);
                rQueue.add(request);

                dialog.show();
                break;
            case R.id.sendButton:
                String chat = messageArea.getText().toString();
                presenter.postChat(chat);
                break;
        }
    }
    private void doOnSuccess(String s, ListView listParticipant) {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();
                al.add(key);
                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listParticipant.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
    }

}


