package com.heru.httpwww.kurio_messanger.Main.MainPresenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.firebase.client.Firebase;
import com.heru.httpwww.kurio_messanger.Main.MainView.MainView;

public class MainPresenter {
    MainView view;
    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void pushRoom(Activity activity, Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
        String url = "https://kuriomessanger-4fb20.firebaseio.com/room.json";
        Firebase reference = new Firebase("https://kuriomessanger-4fb20.firebaseio.com/room");
    }
}
