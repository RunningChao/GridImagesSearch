package com.yahoo.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.gridimagesearch.R;
import com.yahoo.gridimagesearch.models.ImageResult;

import java.util.List;

/**
 * Created by Mars on 2015/8/4.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult>{

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        ImageResult imageInfo = getItem(position);
        if(converView == null){
            converView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) converView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) converView.findViewById(R.id.tvTitle);
        ivImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).into(ivImage);
        return converView;
    }

}
