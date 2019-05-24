package com.example.samuelh.memoryleak;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 2019-05-15.
 */
public class BrowserFragment extends Fragment {

    private BrowserPresenter presenter;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private BrowserItemSelectedListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new BrowserPresenter(this);
        adapter = new Adapter();
        adapter.setClickListener(this::onBrowserItemClicked);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        presenter.subscribe();
    }

    private void onBrowserItemClicked(View view) {
        BrowserPresenter.ItemData data = (BrowserPresenter.ItemData) view.getTag();
        if (data == null || listener == null) {
            return;
        }
        listener.onBrowserItemSelected(data);
    }

    void setBrowserItemSelectedListener(BrowserItemSelectedListener l) {
        listener = l;
    }

    Bitmap getResImage() {
        if (getContext() == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeResource(getResources(), R.raw.miumiu, options);
    }

    void onError(Throwable e) {
        if (getActivity() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(e.getMessage())
                .setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }

    void updateBrowserItem(List<BrowserPresenter.ItemData> list) {
        adapter.addAll(list);
    }

    private static class Adapter extends RecyclerView.Adapter<BrowserItemViewHolder> {

        private List<BrowserPresenter.ItemData> list = new ArrayList<>();
        private View.OnClickListener listener;

        void setClickListener(View.OnClickListener l) {
            listener = l;
        }

        void addAll(List<BrowserPresenter.ItemData> l) {
            list.clear();
            list.addAll(l);
            notifyDataSetChanged();
        }

        BrowserPresenter.ItemData getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public BrowserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BrowserItemViewHolder holder = new BrowserItemViewHolder(parent);
            holder.setClickListener(listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BrowserItemViewHolder holder, int position) {
            holder.bindBrowserItem(getItem(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    interface BrowserItemSelectedListener {
        void onBrowserItemSelected(BrowserPresenter.ItemData data);
    }
}
