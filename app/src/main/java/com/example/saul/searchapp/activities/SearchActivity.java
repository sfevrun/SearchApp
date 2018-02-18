package com.example.saul.searchapp.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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
import com.example.saul.searchapp.models.QueryClass;
import com.example.saul.searchapp.servives.CustomTabActivityHelper;
import com.example.saul.searchapp.servives.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements OnItemClickListenerInterface, CustomDialogFragment.EditCustomDialogListener {
    GridView gvResults;
    ArrayList<Article> articles;
     ProgressDialog pd;
    // ArticleAdapter adapter;
    ArticleArrayAdapter adapter;
    RequestParams params;
    private EndlessRecyclerViewScrollListener scrollListener;
    String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

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
        viewSetup();
        params = new RequestParams();
         pd = new ProgressDialog(this);
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
boolean b=isOnline();

        if(b){

            bindingData(params, null,0);

        }
        else {
            pd.dismiss();
            alertDialogInternet();
          //  Toast.makeText(this, "Hi, Pa gen internet tonton" , Toast.LENGTH_SHORT).show();
        }


    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public void viewSetup() {
        articles = new ArrayList<>();
        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticle);
        adapter = new ArticleArrayAdapter(this, articles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter.notifyDataSetChanged();
        rvArticles.setAdapter(adapter);
        adapter.setClicklistener(this);
       StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(gridLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                bindingData(params,null,page);
                Log.d("DEBUG", "" + totalItemsCount + " Page : " + page);
            }
        };
        rvArticles.addOnScrollListener(scrollListener);
    }
    @Override
    public void onClick(View view, int position) {
        Intent in = new Intent(getApplicationContext(), ArticleActivity.class);
        Article article = articles.get(position);
        ////////////////////////////////////////
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);
        Intent intent = new Intent(Intent.ACTION_SEND);
       intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,  article.getWebUrl().toString());
       int requestCode = 100;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
        CustomTabsIntent customTabsIntent = builder.build();
// set toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.addDefaultShareMenuItem();
        customTabsIntent.launchUrl(this, Uri.parse(article.getWebUrl()));


     CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse(article.getWebUrl()),
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
       // Intent intent = new Intent(this, MyActivity.class);
     /*   in.putExtra("article", Parcels.wrap(article));
          //  in.putExtra("article", article);
        startActivity(in);*/
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
                bindingData(params, null,0);
                return true;
            }

        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                articles.clear();
                scrollListener.resetState();
                adapter.notifyDataSetChanged();
                bindingData(params, query,0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 5) {
                    articles.clear();
                    scrollListener.resetState();
                    adapter.notifyDataSetChanged();
                    bindingData(params, newText,0);
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

    @Override
    public void onFinishEditDialog(QueryClass _queryclass) {
        //  params=new RequestParams();
        String _param = "news_desk:" + _queryclass.getDeskValue().toString().replace('[', '(').replace(']', ')');
        //  RequestParams params=new RequestParams();
        params = new RequestParams();
        if (_queryclass.getDeskValue().toArray().length > 0) {
            params.put("fq", _param);
        }
        String sort = _queryclass.getSort().toString().toLowerCase();
        if(_queryclass.getDate()!=""){
            params.put("begin_date", _queryclass.getDate().toString());
        }

        params.put("sort", sort);
      //  Toast.makeText(this, "Hi, " +params.toString(), Toast.LENGTH_SHORT).show();
            articles.clear();
          scrollListener.resetState();
          adapter.notifyDataSetChanged();
        bindingData(params, null,0);
    }
    public void bindingData(RequestParams params, String query,int page) {
     //    Toast.makeText(this, "Hi, " +params.toString(), Toast.LENGTH_SHORT).show();
        // String url="http://api.nytimes.com/svc/search/v2/articlesearch.json";//  https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=20160112&sort=oldest&fq=news_desk:(%22Education%22%20%22Health%22)&api-key=227c750bb7714fc39ef1559ef1bd8329
        AsyncHttpClient _client = new AsyncHttpClient();
        //   RequestParams params=new RequestParams();
        if (query != null) {
            params.put("query", query.toLowerCase());
        }
        params.put("page", page);
        params.put("api-key", "c8ea5b2da7b341a79cf0ee1a45279943");
        _client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //  super.onSuccess(statusCode, headers, response);
                JSONArray _articleJson = null;
                try {
                    _articleJson = response.getJSONObject("response").getJSONArray("docs");
                    // Remove all books from the adapter
                //    articles.clear();
                //    adapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
// 3. Reset endless scroll listener when performing a new search
                    //    articles.clear();
                  //  scrollListener.resetState();
                    //   adapter.notifyDataSetChanged();
                    int curSize = adapter.getItemCount();

                    articles.addAll(Article.fromJsonArray(_articleJson));
                    //    adapter.addAll(Article.fromJsonArray(_articleJson));
                    //   adapter.notifyDataSetChanged();
                    adapter.notifyItemRangeInserted(curSize, _articleJson.length());

                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.dismiss();
            }
        });
    }

    public void alertDialogInternet(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Connection check");

        // Setting Dialog Message
        alertDialog.setMessage("Please check your internet connection");
              alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Write your code here to invoke YES event
                Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }
        });
              // Showing Alert Message
        alertDialog.show();
    }
}
