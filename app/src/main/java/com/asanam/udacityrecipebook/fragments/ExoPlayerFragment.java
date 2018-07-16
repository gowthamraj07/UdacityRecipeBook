package com.asanam.udacityrecipebook.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    public static final String TAG = "ExoPlayerFragment";
    public String URI_STRING = null;
    private boolean playWhenReady;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private DefaultBandwidthMeter bandwidthMeter;

    private long playbackPosition;
    private int currentWindow;
    private MediaSource mediaSource;

    private ExoPlayerListener listener = null;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof ExoPlayerListener)) {
            throw new ClassCastException(getActivity().toString() + "Should implement ExoPlayerListener");
        }

        listener = (ExoPlayerListener) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (listener != null && player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            listener.onReleaseExoPlayer(playbackPosition, currentWindow);
        }

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer(PlayerView playerView) {

        if(URI_STRING == null) {
            return;
        }

        if (player == null) {
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
        }

        player.prepare(mediaSource);
        player.seekTo(currentWindow, playbackPosition);
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    public void showVideo(String url) {

        releasePlayer();

        if (!url.equals(URI_STRING)) {
            this.playbackPosition = 0;
            this.currentWindow = 0;
        }

        URI_STRING = url;

        Log.d(TAG, "showVideo: URL : "+URI_STRING);

        if (URI_STRING != null) {
            try {
                initializePlayer(playerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void seekTo(long playbackPosition, int currentWindow) {
        Log.d(TAG, "seekTo: ["+playbackPosition+","+currentWindow+"]");
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
        if(player != null) {
            player.seekTo(currentWindow, playbackPosition);
        }
    }

    public interface ExoPlayerListener {
        void onReleaseExoPlayer(long playbackPosition, int currentWindow);
    }

    private void initializePlayer() {
        if (getArguments() != null) {
            URI_STRING = getArguments().getString("VIDEO_URL");
        }

        if (URI_STRING != null) {
            initializePlayer(playerView);
        }
    }
}
