package com.example.samuelh.memoryleak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntryFragment extends Fragment {

    LinearLayout itemContainer;
    List<BrowserPresenter.ItemData> list = new ArrayList<>();

    public EntryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemContainer = view.findViewById(R.id.item_container);
        for (BrowserPresenter.ItemData data : list) {
            addViewToContainer(data);
        }
    }

    void addBrowserData(BrowserPresenter.ItemData data) {
        list.add(data);
        addViewToContainer(data);
    }

    private void addViewToContainer(BrowserPresenter.ItemData data) {
        BrowserItemViewHolder holder = new BrowserItemViewHolder(itemContainer);
        holder.bindBrowserItem(data);
        itemContainer.addView(holder.itemView);
    }
}
