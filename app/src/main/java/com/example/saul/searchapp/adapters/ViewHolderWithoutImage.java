package com.example.saul.searchapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

import com.example.saul.searchapp.R;

/**
 * Created by SAUL on 2/18/2018.
 */

public class ViewHolderWithoutImage extends RecyclerView.ViewHolder{
  //  ImageView imageView;
    TextView tvDescription;
    TextView  tvheadline;
    TextView tvTitle;

  /*    public ImageView getImageView() {
        return imageView;
    }

  public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }*/

    public TextView getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(TextView tvDescription) {
        this.tvDescription = tvDescription;
    }

    public TextView getTvheadline() {
        return tvheadline;
    }

    public void setTvheadline(TextView tvheadline) {
        this.tvheadline = tvheadline;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public ViewHolderWithoutImage(View view) {
        super(view);
     ///   imageView=(ImageView) view.findViewById(R.id.ivImage);
        tvTitle=(TextView) view.findViewById(R.id.tvTitle);
        tvDescription=(TextView) view.findViewById(R.id.tvDescription);
        tvheadline=(TextView) view.findViewById(R.id.tvheadline);
    }
}
