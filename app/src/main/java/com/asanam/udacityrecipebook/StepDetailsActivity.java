package com.asanam.udacityrecipebook;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity implements ExoPlayerFragment.ExoPlayerListener {

    public static final String PLAYBACK_POSITION = "playbackPosition";
    public static final String CURRENT_WINDOW = "currentWindow";
    private long playbackPosition;
    private int currentWindow;
    private Fragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        stepDetailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_step_details_fragment);
        Bundle stepDetails = getIntent().getBundleExtra("STEP_DETAILS");

        stepDetailsFragment.setArguments(stepDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow) {
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            Fragment exoPlayer = stepDetailsFragment.getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
            if(exoPlayer != null) {
                ((ExoPlayerFragment)exoPlayer).seekTo(playbackPosition, currentWindow);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}
