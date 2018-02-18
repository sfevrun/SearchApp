package com.example.saul.searchapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saul.searchapp.R;
import com.example.saul.searchapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SAUL on 2/15/2018.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context,List<Article> articles){
        super(context,android.R.layout.simple_list_item_1,articles);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Article article=this.getItem(position);

        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.item_article,parent,false);

        }
        ImageView imageView=(ImageView) convertView.findViewById(R.id.ivImage);
        imageView.setImageResource(0);
        TextView tvTitle=(TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDescription=(TextView) convertView.findViewById(R.id.tvDescription);

        tvTitle.setText(article.getHaedline());
        tvDescription.setText(article.getSnippet());

        String _thumball=article.getThumbNail();

        if(!TextUtils.isEmpty(_thumball)){
            Picasso.with(getContext()).load(_thumball).into(imageView);
        }
        return convertView;
    }
}
