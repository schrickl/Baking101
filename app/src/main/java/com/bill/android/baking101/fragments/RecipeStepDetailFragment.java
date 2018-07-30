package com.bill.android.baking101.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bill.android.baking101.R;
import com.bill.android.baking101.models.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String LOG_TAG = RecipeStepDetailFragment.class.getSimpleName();
    private SimpleExoPlayer mPlayer;
    private Recipe mRecipe;
    private int mPosition;
    private long mPlayerPosition = 0;
    @BindView(R.id.exo_player) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_step_description) TextView mDescription;
    @BindView(R.id.btn_next) Button mNextBtn;
    @BindView(R.id.btn_previous) Button mPreviousBtn;

    public RecipeStepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            if (getActivity().getIntent().hasExtra(getResources().getString(R.string.recipe_extra))) {
                mRecipe = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.recipe_extra));
                mPosition = getActivity().getIntent().getIntExtra(getResources().getString(R.string.recipe_position), 0);
            }
        } else {
            mRecipe = savedInstanceState.getParcelable(getResources().getString(R.string.recipe_extra));
            mPosition = savedInstanceState.getInt(getResources().getString(R.string.recipe_position));
            mPlayerPosition = savedInstanceState.getLong(getResources().getString(R.string.player_position));
        }

        initializePlayer();

        mNextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v);
            }
        });
        mPreviousBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(v);
            }
        });
        mDescription.setText(mRecipe.getSteps().get(mPosition).getDescription());

        playVideo(mPosition);

        // Return the root view
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getResources().getString(R.string.recipe_extra), mRecipe);
        outState.putInt(getResources().getString(R.string.recipe_position), mPosition);
        if (mPlayer != null) {
            outState.putLong(getResources().getString(R.string.player_position), mPlayer.getCurrentPosition());
        } else {
            outState.putLong(getResources().getString(R.string.player_position), mPlayerPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);

            mPlayer.addListener(this);
        }
    }

    private void handleButtonClick(View v) {

        if (v == mNextBtn) {
            mPosition++;
        } else {
            mPosition--;
        }

        playVideo(mPosition);
    }

    public void playVideo(int position) {
        mPlayer.stop();
        mPlayerPosition = 0;

        mPosition = position;

        if (mPosition == 0) {
            // Disable the previousBtn
            mPreviousBtn.setEnabled(false);
            mPreviousBtn.setClickable(false);
            mPreviousBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDisabled));
        } else {
            // Enable the previousBtn
            mPreviousBtn.setEnabled(true);
            mPreviousBtn.setClickable(true);
            mPreviousBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        if (mPosition == mRecipe.getSteps().size() -1) {
            // Disable the nextBtn
            mNextBtn.setEnabled(false);
            mNextBtn.setClickable(false);
            mNextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDisabled));
        } else {
            // Enable the nextBtn
            mNextBtn.setEnabled(true);
            mNextBtn.setClickable(true);
            mNextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (position == 0) {
            if (ab != null) {
                ab.setTitle(mRecipe.getName() + " Intro");
            }
        } else {
            if (ab != null) {
                ab.setTitle(mRecipe.getName() + " Step " + mPosition);
            }
        }

        // Update the description for this step
        mDescription.setText(mRecipe.getSteps().get(mPosition).getDescription());

        if (mRecipe.getSteps().get(mPosition).getVideoUrl().isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
        } else {
            ConnectivityManager cm =
                    (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                mPlayerView.setVisibility(View.GONE);
            } else {
                mPlayerView.setVisibility(View.VISIBLE);
                String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mRecipe.getSteps().get(mPosition).getVideoUrl()), new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mPlayer.prepare(mediaSource);
                mPlayer.seekTo(mPlayerPosition);
                mPlayer.setPlayWhenReady(true);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayerPosition = mPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}

