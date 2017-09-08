package me.apptize.bollyfilmes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.apptize.bollyfilmes.sync.FilmesSyncAdapter;

public class MainActivity extends AppCompatActivity implements MainFragment.CallBack{

    public static final String FILME_DETALHE_URI = "FILME";

    private boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_filme_detalhe) != null) {

            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_filme_detalhe, new FilmeDetalheFragment())
                        .commit();
            }
            isTablet = true;

        } else {
            isTablet = false;
        }

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        mainFragment.setUseFilmeDestaque(!isTablet);

        FilmesSyncAdapter.initializeSyncAdapter(this);

    }

    @Override
    public void onItemSelected(Uri uri) {
        if(isTablet) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            FilmeDetalheFragment fragment = new FilmeDetalheFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(FILME_DETALHE_URI, uri);
            fragment.setArguments(bundle);

            transaction.replace(R.id.fragment_filme_detalhe, fragment);
            transaction.commit();

        } else {

            Intent intent = new Intent(this, FilmeDetalheActivity.class);
            intent.setData(uri);
            startActivity(intent);

        }
    }
}
