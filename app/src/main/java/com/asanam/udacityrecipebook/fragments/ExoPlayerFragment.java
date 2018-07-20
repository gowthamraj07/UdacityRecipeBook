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
import com.asanam.udacityrecipebook.utils.Constants;
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

    public static final String TAG = ExoPlayerFragment.class.getSimpleName();
    private boolean playWhenReady;
    private PlayerView playerView;
    private SimpleExoPlayer player;

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
        if (getActivity() != null && !(getActivity() instanceof ExoPlayerListener)) {
            throw new ClassCastException(getActivity().toString() + getString(R.string.should_implement_exo_player_listener));
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
            playWhenReady = player.getPlayWhenReady();
            listener.onReleaseExoPlayer(playbackPosition, currentWindow, playWhenReady);
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

    private void initializePlayer(PlayerView playerView, String videoUrl) {

        if (videoUrl == null) {
            return;
        }

        if (player == null) {
            playerView.requestFocus();

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videoUri = Uri.parse(videoUrl);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(TAG);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);

            playerView.setPlayer(player);
        }

        player.prepare(mediaSource);
        Log.d(TAG, "initializePlayer: seekTo[" + playbackPosition + "," + currentWindow + "]");
        player.seekTo(currentWindow, playbackPosition);
        player.setPlayWhenReady(playWhenReady);
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

    public interface ExoPlayerListener {
        void onReleaseExoPlayer(long playbackPosition, int currentWindow, boolean playWhenReady);
    }

    private void initializePlayer() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            String videoUrl = arguments.getString(Constants.VIDEO_URL);
            playbackPosition = arguments.getLong(Constants.PLAYBACK_POSITION, playbackPosition);
            currentWindow = arguments.getInt(Constants.CURRENT_WINDOW, currentWindow);
            playWhenReady = arguments.getBoolean(Constants.PLAYER_WHEN_READY, true);

            initializePlayer(playerView, videoUrl);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        outState.putInt(Constants.CURRENT_WINDOW, currentWindow);
        outState.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(Constants.PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(Constants.CURRENT_WINDOW);
            playWhenReady = savedInstanceState.getBoolean(Constants.PLAYER_WHEN_READY);
        }
    }
}
