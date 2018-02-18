package com.example.saul.searchapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saul.searchapp.R;
import com.example.saul.searchapp.activities.OnItemClickListenerInterface;
import com.example.saul.searchapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SAUL on 2/16/2018.
 */

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {
    public void setClicklistener(OnItemClickListenerInterface clicklistener) {
        this.clicklistener = clicklistener;
    }

    private OnItemClickListenerInterface clicklistener;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;


        TextView tvTitle;

        public ViewHolder(View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.ivImage);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clicklistener!=null){
                clicklistener.onClick(view,getAdapterPosition());
            }
        }
    }


    private List<Article> articles;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public ArticleArrayAdapter(Context context, List<Article> _articles) {
        articles = _articles;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = articles.get(position);

        // Set item views based on your views and data model
        TextView txtTitre = viewHolder.tvTitle;
        txtTitre.setText(article.getHaedline());
        ImageView imageView=viewHolder.imageView;
        imageView.setImageResource(0);
        String _thumball=article.getThumbNail();

        if(!TextUtils.isEmpty(_thumball)){
            Picasso.with(getContext()).load(_thumball).into(imageView);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
