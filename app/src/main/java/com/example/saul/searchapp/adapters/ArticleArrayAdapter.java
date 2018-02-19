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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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
        TextView  tvDescription;
        TextView  tvheadline;
        TextView tvTitle;

        public ViewHolder(View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.ivImage);
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            tvDescription=(TextView) view.findViewById(R.id.tvDescription);
            tvheadline=(TextView) view.findViewById(R.id.tvheadline);

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
        TextView tvDescription=viewHolder.tvDescription;
        // Set item views based on your views and data model
        TextView txtTitre = viewHolder.tvTitle;

        TextView tvheadline = viewHolder.tvheadline;


        txtTitre.setText(article.getNew_desk());
        tvheadline.setText(article.getHaedline());
        ImageView imageView=viewHolder.imageView;
        imageView.setImageResource(0);
        String _thumball=article.getThumbNail();
        tvDescription.setText(article.getSnippet().toString());

        if(!TextUtils.isEmpty(_thumball)){
            //    Picasso.with(getContext()).load(_thumball).into(imageView);
            //  imageView.getLayoutParams().height = 150;
            Picasso.with(getContext())
                    .load(_thumball)
                    .resize(300, 400)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });


        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return articles.size();
    }
}



    // Easy access to the context object in the recyclerview
/*    private Context getContext() {
        return mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
    /*    LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);


        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case WITH_IMAGE:
                View vImage = inflater.inflate(R.layout.item_article, parent, false);
                viewHolder = new ViewHolderImage(vImage);
                break;
            case WITHOUT_IMAGE:
                View vWithoutImage = inflater.inflate(R.layout.item_article_no_image, parent, false);
                viewHolder = new ViewHolderWithoutImage(vWithoutImage);
                break;
            default:
                View vDefault = inflater.inflate(R.layout.item_article_no_image, parent, false);
                viewHolder = new ViewHolderWithoutImage(vDefault);
                break;
        }

        return viewHolder;

    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get the data model based on position
        Article article = articles.get(position);

        switch (holder.getItemViewType()) {
            case WITH_IMAGE:
                ViewHolderImage viewHolderImage = (ViewHolderImage) holder;
                viewHolderImage.getTvTitle().setText(article.getNew_desk());
                viewHolderImage.getTvDescription().setText(article.getSnippet().toString());
                viewHolderImage.getTvheadline().setText(article.getHaedline());
                String _thumball=article.getThumbNail();
                if(!TextUtils.isEmpty(_thumball)){



                Picasso.with(getContext())
                        .load(article.getThumbNail())
                        .resize(300, 400)
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(viewHolderImage.getImageView(), new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }

                        }); }
                break;
            case WITHOUT_IMAGE:
                ViewHolderWithoutImage viewNoImage = (ViewHolderWithoutImage) holder;
                viewNoImage.getTvTitle().setText(article.getNew_desk());
                viewNoImage.getTvDescription().setText(article.getSnippet().toString());
                viewNoImage.getTvheadline().setText(article.getHaedline());


                break;

        }
    }
    @Override
    public int getItemViewType(int position) {
      //  return super.getItemViewType(position);
        if (articles.get(position).getThumbNail() != null)
            return WITH_IMAGE;
        else
            return WITHOUT_IMAGE;

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return articles.size();
    }

    public Article getArticle(int position) {
        return articles.get(position);
    }
}
*/