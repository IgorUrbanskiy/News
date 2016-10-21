package com.igor.news24.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.igor.news24.DBHelper;
import com.igor.news24.Utils;
import com.igor.news24.model.Model;
import com.igor.news24.model.ModelImpl;
import com.igor.news24.model.data.News;
import com.igor.news24.model.data.Rss;
import com.igor.news24.view.interfaces.View;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import static com.igor.news24.DBHelper.TABLE_NEWS;

/**
 * Created by Igor on 20.10.2016.
 */

public class NewsPresenter implements Presenter {

    private Model model = new ModelImpl();

    private View mNewsView;
    private Subscription subscription = Subscriptions.empty();
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase database;
    private Rss mRss;
    private ArrayList<News> newsArrayList;

    public NewsPresenter(Context context, View newsView) {
        mNewsView = newsView;
        mContext = context;
        mDBHelper = new DBHelper(context);
        database = mDBHelper.getWritableDatabase();
    }

    @Override
    public void show() {
        mNewsView.showProgress();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = model.getNews()
                .subscribe(new Observer<Rss>() {
                    @Override
                    public void onCompleted() {
                        mNewsView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsView.hideProgress();
                        mNewsView.showError(e.getMessage());
                        if (!Utils.isOnline(mContext)) {
                            readInDatabase();
                            if (newsArrayList != null) {
                                mNewsView.showLocalData(newsArrayList);
                            }
                        }
                    }

                    @Override
                    public void onNext(Rss lists) {
                        if (Utils.isOnline(mContext)) {
                            if (lists != null) {
                                mNewsView.hideProgress();
                                mRss = lists;
                                mNewsView.showData(lists);
                                addToDatabase();
                            }
                        } else {
                            readInDatabase();
                            if (newsArrayList != null) {
                                mNewsView.showLocalData(newsArrayList);
                            }
                        }
                    }
                });
    }

    private void addToDatabase() {
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < mRss.getmChannel().getNewsItems().size(); i++) {
            if (!isSiteExists(database, mRss.getmChannel().getNewsItems().get(i).getLink())) {
                contentValues.put(DBHelper.KEY_TITLE, mRss.getmChannel().getNewsItems().get(i).getTitle());
                contentValues.put(DBHelper.KEY_DATE, mRss.getmChannel().getNewsItems().get(i).getPubDate());
                contentValues.put(DBHelper.KEY_LINK, mRss.getmChannel().getNewsItems().get(i).getLink());
                contentValues.put(DBHelper.KEY_DESCRIPTION, mRss.getmChannel().getNewsItems().get(i).getDescription());
                database.insert(TABLE_NEWS, null, contentValues);
            }
        }
    }

    private boolean isSiteExists(SQLiteDatabase db, String rss_link) {
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NEWS
                + " WHERE link = '" + rss_link + "'", new String[]{});
        boolean exists = (cursor.getCount() > 0);
        return exists;
    }

    private void readInDatabase() {
        Cursor cursor = database.query(TABLE_NEWS, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            int titleColIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int linkColIndex = cursor.getColumnIndex(DBHelper.KEY_LINK);
            int dateColIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int descriptionColIndex = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION);
            newsArrayList = new ArrayList<>();
            do {
                newsArrayList.add(new News(cursor.getString(titleColIndex), cursor.getString(linkColIndex), cursor.getString(descriptionColIndex), cursor.getString(dateColIndex)));
                Log.d("MYLOG", " title = "
                        + cursor.getString(titleColIndex));

            } while (cursor.moveToPrevious());
        } else {
            Log.d("Mylog", " 0 rows");
        }
        Log.d("Mylog", " mewsArray = " + newsArrayList.size());
        cursor.close();
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        if (mNewsView != null) {
            mNewsView = null;
        }
    }
}
