package com.igor.news24.model.data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Igor on 21.10.2016.
 */

@Root(name = "channel", strict = false)
public class Channel implements Serializable {

    @ElementList(inline = true, name = "item")
    private List<News> mNewsItems;

    public List<News> getNewsItems() {
        return mNewsItems;
    }

    public Channel() {
    }

    public Channel(List<News> mFeedItems) {
        this.mNewsItems = mFeedItems;
    }
}
