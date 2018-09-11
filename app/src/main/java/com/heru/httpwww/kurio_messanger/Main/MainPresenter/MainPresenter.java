package com.heru.httpwww.kurio_messanger.Main.MainPresenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.heru.httpwww.kurio_messanger.Main.MainView.MainView;
import com.heru.httpwww.kurio_messanger.Model.room;

public class MainPresenter {
    MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void pushRoom(Activity activity, Context context, String roomName) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        String url = "https://kuriomessanger-4fb20.firebaseio.com/room.json";
        Firebase reference = new Firebase("https://kuriomessanger-4fb20.firebaseio.com/room");
        String key = reference.child(roomName).push().getKey();

        room roomData = new room();
        roomData.setUid(key);
        roomData.setTittleRoom(roomName);
        reference.push().setValue(roomData);
        pd.dismiss();
        view.CreateRoom();

    }
}
