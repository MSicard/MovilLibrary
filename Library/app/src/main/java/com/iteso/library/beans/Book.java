package com.iteso.library.beans;

/**
 * Created by Maritza on 22/09/2017.
 */

public class Book {
    protected String title;
    protected String autor;
    protected String synapsis;
    protected String editorial;
    protected String edicion;
    protected String year;
    protected int pages;
    protected String ISBN;

    public Book(String title, String autor, String synapsis){
        this.title = title;
        this.autor = autor;
        this.synapsis = synapsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSynapsis() {
        return synapsis;
    }

    public void setSynapsis(String synapsis) {
        this.synapsis = synapsis;
    }
}
