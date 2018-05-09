package compsevice.ua.app;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compsevice.ua.app.activity.ContractInfoDetailActivity;
import compsevice.ua.app.activity.ExperimentalActivity;
import compsevice.ua.app.adapter.ContractInfoAdapter;
import compsevice.ua.app.model.ContractInfo;
import compsevice.ua.app.rest.ApiUtils;
import compsevice.ua.app.rest.ContractInfoService;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ContractInfoAdapter.RecyclerTouchListener.ClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();

    private String lastQuery = "NO_SUCH_ELEMENT";

    private RecyclerView recylcerView;
    private ProgressBar progressBar;
    private ContractInfoAdapter contractInfoAdapter;
    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. ProgressBar
        progressBar = findViewById(R.id.progress_bar);
        recylcerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recylcerView.setLayoutManager(layoutManager);

        contractInfoAdapter = new ContractInfoAdapter(getApplicationContext(), new ArrayList<ContractInfo>());

        recylcerView.setAdapter(contractInfoAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recylcerView.getContext(), layoutManager.getOrientation());

        recylcerView.addItemDecoration(itemDecoration);

        filter = contractInfoAdapter.getFilter();

        recylcerView.addOnItemTouchListener(new ContractInfoAdapter.RecyclerTouchListener(getApplicationContext(), recylcerView, this));

        recylcerView.setHasFixedSize(true);


    }


    private List<ContractInfo> fromJson() {

        ObjectMapper mapper = new ObjectMapper();

        List<ContractInfo> infos = new ArrayList<>();

        InputStream is = null;

        try {

            is = getResources().openRawResource(R.raw.data);

            ContractInfo[] cis = mapper.readValue(is, ContractInfo[].class);
            infos.addAll(Arrays.asList(cis));
            Log.i("Info", infos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return infos;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager sm = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView.setSearchableInfo(sm.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings has been selected here..", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_run:
                Intent intent = new Intent(getApplicationContext(), ExperimentalActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SearchView", "Query:" + query);

        if (!query.isEmpty()) {
            query(query);
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void query(String query) {
        AsyncTask<String, Void, List<ContractInfo>> asyncTask = new Downloader(this, progressBar);
        asyncTask.execute(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("SearchView", "Query a new text " + newText);
        filter.filter(newText);
        return false;
    }

    public ContractInfoAdapter getContractInfoAdapter() {
        return contractInfoAdapter;
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public void onClick(View v, int position) {
        ContractInfo info = contractInfoAdapter.getDataAt(position);
        Log.i(TAG, "Data for the position" + info);

        Intent intent = new Intent(getApplicationContext(), ContractInfoDetailActivity.class);

        startActivity(intent);

    }

    @Override
    public void onLongClick(View v, int position) {}



    private static class Downloader extends AsyncTask<String, Void, List<ContractInfo>> {

        private WeakReference<ProgressBar> progressBar;
        private WeakReference<MainActivity> activity;

        public Downloader(MainActivity activity, ProgressBar progressBar) {
            this.progressBar = new WeakReference<ProgressBar>(progressBar);
            this.activity = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected List<ContractInfo> doInBackground(String... queries) {

            ContractInfoService service = ApiUtils.service();

            List<ContractInfo> cis = new ArrayList<>();
            try {
                Response<List<ContractInfo>> response = service.contractInfos(queries[0]).execute();
                cis = response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return cis;
        }

        @Override
        protected void onPreExecute() {
            progressBar.get().setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ContractInfo> cis) {
            progressBar.get().setVisibility(View.GONE);

            ContractInfoAdapter adapter = activity.get().getContractInfoAdapter();
            adapter.update(cis);

            ContractInfoAdapter.ContractInfoFilter filter = (ContractInfoAdapter.ContractInfoFilter) activity.get().getFilter();

            filter.update(cis);

        }

    }



}
