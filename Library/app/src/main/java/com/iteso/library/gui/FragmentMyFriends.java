package com.iteso.library.gui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
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

    private RecyclerView.LayoutManager mlayoutManager;
    private RecyclerView recyclerView;
    private ArrayList mDataSet;
    private AdapterFriends adapter;
    private Button share;
    private TextView net;
    private CallbackManager callbackManager;
    private String url = "https://github.com/MSicard/MovilLibrary";
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_contact_friend_list);
        share = (Button) view.findViewById(R.id.fragment_friends_share);
        net = (TextView)view.findViewById(R.id.fragment_contact_notnet);
        recyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>(){

                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Log.v("share", "sii");
                    }

                    @Override
                    public void onCancel() {
                        Log.v("share", "no");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.v("share", "no");

                    }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(url))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });

        mDataSet = new ArrayList();
        adapter = new AdapterFriends(getActivity(), mDataSet);
        recyclerView.setAdapter(adapter);

        getFriendsFromFacebook();
        if(mDataSet.size() < 1){
            net.setVisibility(View.VISIBLE);
        }else net.setVisibility(View.GONE);
        return view;
    }

    public void getFriendsFromFacebook(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    JSONArray data = object.getJSONObject("friends").getJSONArray("data");
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
                    if(mDataSet.size() < 1){
                        net.setVisibility(View.VISIBLE);
                    }else net.setVisibility(View.GONE);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
