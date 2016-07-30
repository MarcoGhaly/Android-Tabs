package com.marco.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentFragment extends Fragment {

    private static final String ARGUMENT_ICON_ID = "Icon ID";
    private static final String ARGUMENT_TITLE = "Title";


    // New Instance
    public static ContentFragment newInstance(int iconID, String title) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_ICON_ID, iconID);
        arguments.putString(ARGUMENT_TITLE, title);

        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(arguments);
        return contentFragment;
    }


    private int iconID;
    private String title;

    private ImageView imageView_icon;
    private TextView textView_title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Bundle arguments = getArguments();
        iconID = arguments.getInt(ARGUMENT_ICON_ID);
        title = arguments.getString(ARGUMENT_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        initViews(rootView);
        return rootView;
    }


    // Initialize Views
    private void initViews(View rootView) {
        imageView_icon = (ImageView) rootView.findViewById(R.id.imageView_icon);
        textView_title = (TextView) rootView.findViewById(R.id.textView_title);

        imageView_icon.setImageResource(iconID);
        textView_title.setText(title);
    }

}
