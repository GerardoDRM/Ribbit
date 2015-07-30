package doge.thecraftcod.ribbit.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import doge.thecraftcod.ribbit.adapters.MessageAdapter;
import doge.thecraftcod.ribbit.utils.ParseConstants;
import doge.thecraftcod.ribbit.R;

/**
 * Created by gerardo on 19/07/15.
 */
public class InboxFragment extends ListFragment {
    protected List<ParseObject> mMessages;
    private SwipeRefreshLayout mSwipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveMessages();
                Toast.makeText(getActivity(), R.string.refresh, Toast.LENGTH_LONG).show();
            }
        });
        mSwipe.setColorSchemeColors(R.color.swipeRefresh1, R.color.swipeRefresh2,
                R.color.swipeRefresh3, R.color.swipeRefresh4);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        retrieveMessages();
    }

    private void retrieveMessages() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
        query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> messages, ParseException e) {

                if (mSwipe.isRefreshing()) {
                    mSwipe.setRefreshing(false);
                }

                if (e == null) {
                    // We found the message
                    mMessages = messages;
                    String[] usernames = new String[mMessages.size()];
                    int i = 0;
                    for (ParseObject message : mMessages) {
                        usernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME);
                        i++;
                    }
                    if (getListView().getAdapter() == null) {
                        MessageAdapter adapter = new MessageAdapter(getListView().getContext(), mMessages);
                        setListAdapter(adapter);
                    }
                    else {
                        // Refill listview
                        ((MessageAdapter) getListView().getAdapter()).refill(mMessages);

                    }
                }

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject message = mMessages.get(position);
        String messageType = message.getString(ParseConstants.KEY_FILE_TYPE);
        ParseFile file = message.getParseFile(ParseConstants.KEY_FILE);
        Uri fileUri = Uri.parse(file.getUrl());

        if (messageType.equals(ParseConstants.TYPE_IMAGE)) {
            // View the image
            Intent i = new Intent(getActivity(), ViewImageActivity.class);
            i.setData(fileUri);
            startActivity(i);
        }
        else {
            // View the video
            Intent vid = new Intent(Intent.ACTION_VIEW, fileUri);
            vid.setDataAndType(fileUri, "video/*");
            startActivity(vid);

        }

        // Delete it!
        List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
        if (ids.size() == 1) {
            // last recipient - delete the whole thing!
            message.deleteInBackground();
        }
        else {
            // remove the recipient and save
            ids.remove(ParseUser.getCurrentUser().getObjectId());


            ArrayList<String> idsToRemove = new ArrayList<String>();
            idsToRemove.add(ParseUser.getCurrentUser().getObjectId());

            message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
            message.saveInBackground();
        }
    }
}
