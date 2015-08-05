package com.yahoo.gridimagesearch.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.gridimagesearch.R;
import com.yahoo.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.gridimagesearch.listener.EndlessScrollListener;
import com.yahoo.gridimagesearch.models.AdvanceFilterParam;
import com.yahoo.gridimagesearch.models.ImageResult;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String query;
    private final int REQUEST_CODE = 20;
    private AdvanceFilterParam param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK  && requestCode == REQUEST_CODE) {
            param = (AdvanceFilterParam)data.getSerializableExtra("queryParam");
            queryBtnAction();
        }
    }

    private void setupViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                i.putExtra("result", result);
                startActivity(i);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        AsyncHttpClient client = new AsyncHttpClient();
        String serchUrl = "";
        if(param != null){
            serchUrl = param.getQtyStr(query, offset);
        }else{
            serchUrl = new AdvanceFilterParam().getQtyStr(query, offset);
        }
        //String serchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8&label="+offset+"&start="+(8*offset);
        client.get(serchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray imageResultJson = null;
                try {
                    if (response.getInt("responseStatus") != 400) {
                        imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                        //imageResults.clear();
                        imageResults.addAll(ImageResult.fromJSONArray(imageResultJson));
                        aImageResults.notifyDataSetChanged();
                        System.out.println(imageResults.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void queryBtnAction(){

        query = etQuery.getText().toString();

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        //String serchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&rsz=8&start=8";
        String serchUrl = "";
        if(param != null){
            serchUrl = param.getQtyStr(query, 1);
        }else{
            serchUrl = new AdvanceFilterParam().getQtyStr(query, 1);
        }
        client.get(serchUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray imageResultJson = null;
                try{
                    imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    imageResults.addAll(ImageResult.fromJSONArray(imageResultJson));
                    aImageResults.notifyDataSetChanged();
                    System.out.println(imageResults.size());
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    public void onImageSearch(View v){
        queryBtnAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(SearchActivity.this, AdvanceSearchOptionsActivity.class);
            i.putExtra("queryParam", param);
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
