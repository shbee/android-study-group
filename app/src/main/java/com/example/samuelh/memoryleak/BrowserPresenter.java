package com.example.samuelh.memoryleak;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Samuel on 2019-05-15.
 */
public class BrowserPresenter {

    private static final int ITEM_SIZE = 10;

    private static final AtomicInteger presenterId = new AtomicInteger();
    private static final AtomicInteger itemId = new AtomicInteger();

    private String presenterName;
    private BrowserFragment view;
    private CompositeDisposable disposables = new CompositeDisposable();

    BrowserPresenter(BrowserFragment view) {
        presenterName = "BrowserPresenter<" + presenterId.getAndIncrement() + ">";
        this.view = view;
    }

    void subscribe() {
        disposables.add(Single.fromCallable(this::loadData)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<ItemData>>() {
                    @Override
                    public void onSuccess(List<ItemData> itemData) {
                        view.updateBrowserItem(itemData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("EntryActivity", "loadData error", e);
                        view.onError(e);
                    }
                }));
    }

    List<ItemData> loadData() {
        ArrayList<ItemData> list = new ArrayList<>();
        for (int i = 0; i < ITEM_SIZE; i++) {
            ItemData data = new ItemData();
            data.presenterName = BrowserPresenter.this.presenterName;
            data.itemName = "BrowserItem<" + itemId.getAndIncrement() + ">";
            data.bitmap = view.getResImage();
            list.add(data);
        }
        return list;
    }

    void unsubscribe() {
        disposables.clear();
    }

    class ItemData {
        String presenterName;
        String itemName;
        Bitmap bitmap;
    }
}
