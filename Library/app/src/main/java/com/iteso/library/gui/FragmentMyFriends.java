package com.iteso.library.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.iteso.library.R;
import com.iteso.library.adapters.AdapterFriends;
import com.iteso.library.beans.Friend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maritza on 18/10/2017.
 */

public class FragmentMyFriends extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private EditText search;
    private ListView listView;
    private ArrayList mDataSet;
    private AdapterFriends adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_contact_friend_list);
        search = (EditText)view.findViewById(R.id.fragment_contact_friend_search);

        mlayoutManager = new LinearLayoutManager(getActivity());
        getFriendsFromFacebook();
        mDataSet = new ArrayList();
        adapter = new AdapterFriends(getActivity(), mDataSet);
        listView.setAdapter(adapter);

        return view;
    }

    public void getFriendsFromFacebook(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    JSONArray data = object.getJSONObject("friends").getJSONArray("data");
                    mDataSet = new ArrayList();
                    for(int i = 0; i < data.length(); i++){
                        JSONObject dataObj = data.getJSONObject(i);
                        JSONObject dataPicture = dataObj.getJSONObject("picture").getJSONObject("data");
                        Friend friend = new Friend(dataObj.getString("name"),
                                dataPicture.getString("url"),
                                dataObj.getString("id"));
                        mDataSet.add(friend);
                        Log.v("friends", friend.toString());
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends{name,picture{url}}");
        request.setParameters(parameters);
        request.executeAsync();
    }


}
