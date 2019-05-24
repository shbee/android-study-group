package com.example.samuelh.memoryleak;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Samuel on 2019-05-15.
 */
public class BrowserItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView title;
    private TextView description;

    BrowserItemViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_browser_item, parent, false));

        imageView = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
    }

    void bindBrowserItem(BrowserPresenter.ItemData itemData) {
        itemView.setTag(itemData);
        title.setText(itemData.presenterName);
        description.setText(itemData.itemName);
        imageView.setImageBitmap(itemData.bitmap);
    }

    void setClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
