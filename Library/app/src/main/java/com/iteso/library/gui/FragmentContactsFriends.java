package com.iteso.library.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.iteso.library.R;

/**
 * Created by Maritza on 17/10/2017.
 */

public class FragmentContactsFriends extends Fragment {

    protected ListView listView;
    protected EditText editText;
    //protected AdapterContactFriend adapterContactFriend;
    private String search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        listView = (ListView)view.findViewById(R.id.fragment_contact_friend_list);
        editText = (EditText)view.findViewById(R.id.fragment_contact_friend_search);
        /*editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = "%" + editText.getText().toString() + "%";
                    getLoaderManager().restartLoader(0, null, this);
                    return true;
                }
                return false;
            }
        });*/
        return view;
    }

    // An adapter that binds the result Cursor to the ListView

/*    private MyAdapter mCursorAdapter;
    private static final

    // Sets the columns to retrieve for the user profile
            String[] PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_ID};

    private static final String SELECTION =
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";

    // Defines a variable for the search stringprivate
    String mSearchString;

    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = {mSearchString};

    private final static String[] FROM_COLUMNS = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private final static int[] TO_IDS = {R.id.contact_list_item_1};

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        editText = (EditText) findViewById(R.id.activity_main_search);
        mCursorAdapter = new MyAdapter(this,
                R.layout.contact_list_item,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0);
        listView.setAdapter(mCursorAdapter);
        mSearchString = "%%";

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchString = "%" + editText.getText().toString() + "%";
                    getLoaderManager().restartLoader(0, null, ActivityMain.this);
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mSelectionArgs[0] = mSearchString;
        // Starts the query
        return new CursorLoader(
                ActivityMain.this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }
    public void onResume(){
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    { Manifest.permission.READ_CONTACTS }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{
            getLoaderManager().initLoader(0, null, this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    getLoaderManager().initLoader(0, null, this);
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

}*/


}
