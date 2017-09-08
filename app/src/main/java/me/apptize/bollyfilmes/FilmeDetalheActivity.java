package me.apptize.bollyfilmes;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FilmeDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_detalhe);

        Intent intent = getIntent();

        Uri uri = intent.getData();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FilmeDetalheFragment filmeDetalheFragment = new FilmeDetalheFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.FILME_DETALHE_URI, uri);
        filmeDetalheFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragment_filme_detalhe, filmeDetalheFragment).commit();

    }
}
