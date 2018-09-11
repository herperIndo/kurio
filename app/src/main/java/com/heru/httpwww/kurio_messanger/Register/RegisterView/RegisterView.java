package com.heru.httpwww.kurio_messanger.Register.RegisterView;

import android.content.Intent;

import com.firebase.client.FirebaseError;

public interface RegisterView {
    void IntenToLogin(Intent intent);

    void onFailure(FirebaseError firebaseError);
}
