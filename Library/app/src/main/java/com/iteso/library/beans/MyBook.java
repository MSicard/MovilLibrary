package com.iteso.library.beans;


/**
 * Created by Maritza on 25/09/2017.
 */

public class MyBook extends Book {
    protected boolean reading;
    protected int pagesRead;

    public MyBook(String title, String autor, String synapsis) {
        super(title, autor, synapsis);
    }

}
