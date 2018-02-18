package com.example.saul.searchapp.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul.searchapp.R;
import com.example.saul.searchapp.activities.ArticleActivity;
import com.example.saul.searchapp.adapters.ArticleAdapter;
import com.example.saul.searchapp.adapters.ArticleArrayAdapter;
import com.example.saul.searchapp.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements OnItemClickListenerInterface,CustomDialogFragment.EditCustomDialogListener {
   GridView gvResults;
    ArrayList<Article> articles;
  // ArticleAdapter adapter;
    ArticleArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        bindingData(null);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();

    }

    public void viewSetup(){
     articles=new ArrayList<>();
    RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticle);
    adapter=new ArticleArrayAdapter(this,articles);
    adapter.notifyDataSetChanged();
    rvArticles.setAdapter(adapter);
    adapter.setClicklistener(this);
    // Set layout manager to position the items
    // First param is number of columns and second param is orientation i.e Vertical or Horizontal
    StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    rvArticles.setLayoutManager(gridLayoutManager);

}

    @Override
    public void onClick(View view, int position) {
        Intent in=new Intent(getApplicationContext(), ArticleActivity.class);
        Article article=articles.get(position);
        in.putExtra("article",article);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setBackgroundResource(android.R.drawable.edit_text);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.GRAY);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);


        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                bindingData(null);
                return true;
            }

        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bindingData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 5) {
                    bindingData(newText);
                }
                return false;
            }
        });

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
          //  openDialog();
            showEditDialog();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance("Some Title");
        customDialogFragment.show(fm, "fragment_edit_name");
    }


    public void onArticleSearch(View view) {
        AsyncHttpClient _client=new AsyncHttpClient();
        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params=new RequestParams();
        params.put("api-key","c8ea5b2da7b341a79cf0ee1a45279943");
         _client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
              //  super.onSuccess(statusCode, headers, response);
                JSONArray _articleJson=null;
                try {
                    _articleJson=response.getJSONObject("response").getJSONArray("docs");
                   articles.addAll(Article.fromJsonArray(_articleJson));
              //    adapter.addAll(Article.fromJsonArray(_articleJson));
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG",articles.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void bindingData(String query){
        viewSetup();
        String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";//  https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=news_desk:(%22Education%22%20%22Health%22)&api-key=227c750bb7714fc39ef1559ef1bd8329
        AsyncHttpClient _client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        if(query!=null){
            params.put("query",query.toLowerCase());
        }

        params.put("api-key","c8ea5b2da7b341a79cf0ee1a45279943");
        _client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //  super.onSuccess(statusCode, headers, response);
                JSONArray _articleJson=null;
                try {
                    _articleJson=response.getJSONObject("response").getJSONArray("docs");

                    articles.addAll(Article.fromJsonArray(_articleJson));
            //    adapter.addAll(Article.fromJsonArray(_articleJson));
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG",articles.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
