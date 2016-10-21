package com.igor.news24.model.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Igor on 21.10.2016.
 */

@Root(name = "rss", strict = false)
public class Rss implements Serializable {

    @Element(name = "channel")
    private Channel mChannel;

    public Channel getmChannel() {
        return mChannel;
    }

    public Rss() {
    }

    public Rss(Channel mChannel) {
        this.mChannel = mChannel;
    }
}