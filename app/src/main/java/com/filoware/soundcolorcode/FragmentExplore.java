package com.filoware.soundcolorcode;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentExplore extends Fragment{
    ListView listView;
    ArrayList<ExploreRow> exploreRowList = new ArrayList<>();
    MediaPlayer mPlayer;
    //View Model variable
    private MyViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Initializing view model
        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MyViewModel.class);


        //Toast.makeText(getActivity(),viewModel.getSoundCode(), Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String soundCode = viewModel.getSoundCode();


        // Parse color data from XML resource
        try {
            getExploreList(getActivity(), soundCode);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Audio stuff
        mPlayer = new MediaPlayer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
        }

        // Prepare UI for the list of colors and add interaction
        listView=(ListView) view.findViewById(R.id.listView);
        ArrayAdapterExplore adapter = new ArrayAdapterExplore(getActivity(), exploreRowList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                String value=adapter.getItem(position).getColorName();
//                Toast.makeText(getActivity(),value, Toast.LENGTH_SHORT).show();
                // Audio
                mPlayer.reset();
                try {
                    Uri uri = Uri.parse("android.resource://com.filoware.soundcolorcode/" + getContext().getResources().getIdentifier(adapter.getItem(position).getAudio(), "raw", getContext().getPackageName()));
                    mPlayer.setDataSource(getContext(), uri);
                    mPlayer.prepare();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mPlayer){
                            mPlayer.start();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    public void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void getExploreList(Activity activity, String code)
        throws XmlPullParserException, IOException
    {
        StringBuffer colorName = new StringBuffer();
        StringBuffer bgColor = new StringBuffer();
        StringBuffer audio = new StringBuffer();

        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.soundcolorcodes);
        xpp.next();
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT)
        {

//            if(eventType == XmlPullParser.END_TAG)
//                Toast.makeText(getActivity(),xpp.getName(), Toast.LENGTH_SHORT).show();
//            eventType = xpp.next();

            if (eventType == XmlPullParser.START_TAG  && xpp.getName().equals(code)) {
                xpp.next(); // colors
                xpp.next(); // type
                while (eventType != XmlPullParser.END_TAG) {
                    xpp.next(); // hue
                    xpp.next(); // hue-data
                    colorName.append(xpp.getText()+" ");
                    xpp.next(); // /hue
                    xpp.next(); // value
                    xpp.next(); // value-data
                    colorName.append(xpp.getText());
                    xpp.next(); // /value
                    xpp.next(); // rgb
                    xpp.next(); // rgb-value
                    bgColor.append(xpp.getText());
                    xpp.next(); // /rgb
                    xpp.next(); // audio
                    xpp.next(); // audio-value
                    audio.append(xpp.getText());
                    xpp.next(); // /audio
                    xpp.next(); // lvl
                    xpp.next(); // lvl-value
                    xpp.next(); // /lvl
                    xpp.next(); // /type

                    //Toast.makeText(getActivity(),colorName.toString(), Toast.LENGTH_SHORT).show();

                    exploreRowList.add(
                            new ExploreRow(
                                    colorName.toString(),
                                    bgColor.toString(),
                                    audio.toString()));

                    colorName.setLength(0);
                    bgColor.setLength(0);
                    audio.setLength(0);

                    eventType = xpp.next();
                }
            }
            eventType = xpp.next();
            if (eventType == XmlPullParser.END_TAG && xpp.getName().equals(code))
                break;
        }
    }


    private String getEventsFromAnXML(Activity activity)
            throws XmlPullParserException, IOException
    {
        StringBuffer stringBuffer = new StringBuffer();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.soundcolorcodes);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_DOCUMENT)
            {
                stringBuffer.append("--- Start XML ---");
            }
            else if(eventType == XmlPullParser.START_TAG)
            {
                stringBuffer.append("\nSTART_TAG: "+xpp.getName());
            }
            else if(eventType == XmlPullParser.END_TAG)
            {
                stringBuffer.append("\nEND_TAG: "+xpp.getName());
            }
            else if(eventType == XmlPullParser.TEXT)
            {
                stringBuffer.append("\nTEXT: "+xpp.getText());
            }
            eventType = xpp.next();
        }
        stringBuffer.append("\n--- End XML ---");
        return stringBuffer.toString();
    }

}