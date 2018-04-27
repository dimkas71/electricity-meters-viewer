package compsevice.ua.app;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compsevice.ua.app.adapter.ContractInfoAdapter;
import compsevice.ua.app.model.ContractInfo;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


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

        contractInfoAdapter = new ContractInfoAdapter(getApplicationContext(), fromJson());

        recylcerView.setAdapter(contractInfoAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recylcerView.getContext(), layoutManager.getOrientation());

        recylcerView.addItemDecoration(itemDecoration);

        filter = contractInfoAdapter.getFilter();


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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SearchView", "Query:" + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("SearchView", "Query a new text " + newText);
        filter.filter(newText);
        return false;
    }
}
