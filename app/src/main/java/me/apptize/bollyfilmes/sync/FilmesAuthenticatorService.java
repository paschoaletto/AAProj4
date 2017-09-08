package me.apptize.bollyfilmes.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FilmesAuthenticatorService extends Service{

    private FilmesAuthenticator filmesAuthenticator;

    @Override
    public void onCreate() {
        filmesAuthenticator = new FilmesAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return filmesAuthenticator.getIBinder();

    }
}
