package com.igor.news24.model.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Igor on 21.10.2016.
 */
@Root(name = "item", strict = false)
public class News implements Serializable {
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;
    @Element(name = "pubDate")
    private String pubDate;

    private String guid;

    private Date date;

    public News() {
    }

    public News(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The pubDate
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * @param pubDate The pubDate
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * @return The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return The date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(Date date) {
        this.date = date;
    }

}

