package com.elkdeals.mobile.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.elkdeals.mobile.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

//import com.islam.youtubeplayercore.player.PlayerConstants;
//import com.islam.youtubeplayercore.player.YouTubePlayer;
//import com.islam.youtubeplayercore.player.listeners.AbstractYouTubePlayerListener;
//import com.islam.youtubeplayercore.player.views.YouTubePlayerView;

public class Youtube extends BaseFragment {
    public static final String TAG = "YoutubePlayeFragment";
    private static final String DEFAULT_VIDEO = "1P09fbvEaeA";
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;
    String mVideoId;
    private boolean closeOnFinish;

    public static Youtube createInstance(String videoId,boolean closeOnFinish) {
        Youtube youtube = new Youtube();
        Bundle bundle = new Bundle();
        bundle.putString("videoID",videoId);
        bundle.putBoolean("closeOnFinish",closeOnFinish);
        youtube.setArguments(bundle);
        return youtube;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("videoID",mVideoId);
        outState.putBoolean("closeOnFinish",closeOnFinish);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle data = getArguments();
        if (data == null)
            data = savedInstanceState;
        if (data == null)
            data = new Bundle();
        mVideoId = getVideoId(data.getString("videoID"));
        closeOnFinish = data.getBoolean("closeOnFinish");
        return view = inflater.inflate(R.layout.fragment_youtube, container, false);
    }

    private String getVideoId(String videoID) {

        if (TextUtils.isEmpty(videoID)){
            videoID = DEFAULT_VIDEO;
            return videoID;
        }
        if(videoID.contains("="))
        {
            int index=videoID.indexOf("=");
            if (index<0||index>=videoID.length())
                return videoID;
            String [] strings=videoID.split("=");
            if(strings.length==0)
            {
                videoID=DEFAULT_VIDEO;
                return videoID;
            }
            else if(strings.length==1)
            {
                return videoID;
            }
            videoID=strings[1];
        }
        return videoID;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initViews() {
        /*String frameVideo = "<html><body><iframe width=\"100%\" height=\"315\" src=\"https://www.youtube.com/embed//"+mVideoId+"\" frameborder=\"5\" allowfullscreen></iframe></body></html>";

        youTubePlayerView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = youTubePlayerView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayerView.loadData(frameVideo, "text/html", "utf-8");*/

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(mVideoId, 0);
            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                if (closeOnFinish)
                switch (state){
                    case ENDED:activity.onBackPressed();
                    break;
                }
            }
        });
    }
}
