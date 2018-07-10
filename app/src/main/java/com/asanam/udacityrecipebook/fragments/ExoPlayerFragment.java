package com.asanam.udacityrecipebook.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asanam.udacityrecipebook.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerFragment extends Fragment {

    public static String URI_STRING = "http://techslides.com/demos/sample-videos/small.mp4";
    private boolean playWhenReady;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private DefaultBandwidthMeter bandwidthMeter;

    private long playbackPosition;
    private int currentWindow;
    private MediaSource mediaSource;

    public ExoPlayerFragment() {
        playWhenReady = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.exo_player_fragment_layout, container, false);

        playerView = fragmentView.findViewById(R.id.player_view);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments() != null) {
            URI_STRING = getArguments().getString("VIDEO_URL");
        }

        if (Util.SDK_INT > 23 && URI_STRING != null) {
            initializePlayer(playerView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void initializePlayer(PlayerView playerView) {

        if(player == null) {
            playerView.requestFocus();

            bandwidthMeter = new DefaultBandwidthMeter();
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videoUri = Uri.parse(URI_STRING);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ExoPlayerExample");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

            playerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }

        player.prepare(mediaSource);
    }

    public void releasePlayer() {
        if (Util.SDK_INT > 23) {
            if (player != null) {
                playbackPosition = player.getCurrentPosition();
                currentWindow = player.getCurrentWindowIndex();
                playWhenReady = player.getPlayWhenReady();
                player.release();
                player = null;
            }
        }
    }

    public void showVideo(String url) {
        URI_STRING = url;

        if (Util.SDK_INT > 23 && URI_STRING != null) {
            initializePlayer(playerView);
        }
    }
}
