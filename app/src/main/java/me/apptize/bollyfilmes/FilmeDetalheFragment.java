package me.apptize.bollyfilmes;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import me.apptize.bollyfilmes.data.FilmesContract;

public class FilmeDetalheFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri filmeUri;
    private TextView tvTitulo;
    private TextView tvDesc;
    private TextView tvData;
    private RatingBar ratingBar;
    private ImageView imCapa;
    private ImageView imPoster;

    private static final int FILME_DETALHE_LOADER = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            filmeUri = getArguments().getParcelable(MainActivity.FILME_DETALHE_URI);
        }

        getLoaderManager().initLoader(FILME_DETALHE_LOADER, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filme_detalhe, container, false);

        tvTitulo = (TextView) view.findViewById(R.id.item_titulo);

        tvData = (TextView) view.findViewById(R.id.item_data);

        tvDesc = (TextView) view.findViewById(R.id.item_desc);

        ratingBar = (RatingBar) view.findViewById(R.id.item_avaliacao);

        imCapa = (ImageView) view.findViewById(R.id.item_capa);

        imPoster = (ImageView) view.findViewById(R.id.item_poster);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                FilmesContract.FilmeEntry._ID,
                FilmesContract.FilmeEntry.COLUMN_TITULO,
                FilmesContract.FilmeEntry.COLUMN_DESCRICAO,
                FilmesContract.FilmeEntry.COLUMN_POSTER_PATH,
                FilmesContract.FilmeEntry.COLUMN_CAPA_PATH,
                FilmesContract.FilmeEntry.COLUMN_AVALIACAO,
                FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO
        };

        return new CursorLoader(getContext(), filmeUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1) {
            return;
        }

        if (data.moveToFirst()) {
            int tituloIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_TITULO);
            int descricaoIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DESCRICAO);
            int dataIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO);
            int posterIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_POSTER_PATH);
            int capaIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_CAPA_PATH);
            int avaliacaoIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_AVALIACAO);

            String titulo = data.getString(tituloIndex);
            String descricao = data.getString(descricaoIndex);
            String dataLancamento = data.getString(dataIndex);
            String poster = data.getString(posterIndex);
            String capa = data.getString(capaIndex);
            float avaliacao = data.getFloat(avaliacaoIndex);

            tvTitulo.setText(titulo);
            tvDesc.setText(descricao);
            tvData.setText(dataLancamento);
            ratingBar.setRating(avaliacao);

            new DownloadImageTask(imCapa).execute(capa);

            if(imPoster != null) {
                new DownloadImageTask(imPoster).execute(poster);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
