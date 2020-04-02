package com.one4ll.xplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private PlayerView playerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer.Builder(MainActivity.this).build();
        playerView.setPlayer(simpleExoPlayer);
        final Uri fileUri =Uri.parse("android.resource://"+ getPackageName() +"/"+ R.raw.video);
        Uri fileUri2 = Uri.parse("https://cdn.videvo.net/videvo_files/video/premium/video0029/small_watermarked/mara049_preview.webm");
        Uri fileUri3 = Uri.parse("http://185.105.103.101/serial/Supernatural/S13/720p.x265/Supernatural.S13E07.720p.HDTV.2CH.x265.HEVC.Filmaneh.mkv");
        if (fileUri.isAbsolute()){
            Log.d(TAG, "onCreate: file uri is absolute");
        }
        Log.d(TAG, "onCreate: app name "+ getString(R.string.app_name));
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, Util.getUserAgent(MainActivity.this, getString(R.string.app_name)));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(fileUri3);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.addAudioListener(audioListener);
        Log.d(TAG, "onCreate: simple exo player is playing " + simpleExoPlayer.isPlaying());
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(eventListener);

        Log.d(TAG, "onCreate: exo player is loading" + simpleExoPlayer.isLoading());

    }
    private Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, int reason) {
            Log.d(TAG, "onTimelineChanged: timeline " + timeline);
            Log.d(TAG, "onTimelineChanged: reason "+ reason);
        }

        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            Log.d(TAG, "onTimelineChanged: timeline " + timeline);
            Log.d(TAG, "onTimelineChanged: reason "+ reason);

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            if (isLoading){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState){
                case Player.STATE_READY:
                    Log.d(TAG, "onPlayerStateChanged: state ready");
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case  Player.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onPlayerStateChanged: state buffering");
                    break;
                case Player.STATE_ENDED:
                    Log.d(TAG, "onPlayerStateChanged: state Ended");
                    break;

            }

        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Log.d(TAG, "onIsPlayingChanged: isPlaying" + isPlaying);
            if (isPlaying){
                progressBar.setVisibility(View.INVISIBLE);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            error.printStackTrace();
            Log.d(TAG, "onPlayerError:" +error.getMessage());
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        @Override
        public void onSeekProcessed() {

        }
    };
    private AudioListener audioListener = new AudioListener() {
        @Override
        public void onAudioSessionId(int audioSessionId) {
            Log.d(TAG, "onAudioSessionId: "+ audioSessionId);
        }

        @Override
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            Log.d(TAG, "onAudioAttributesChanged: audioAttributes content type"+ audioAttributes.contentType);
            Log.d(TAG, "onAudioAttributesChanged: audioAttributes flags"+ audioAttributes.flags);
            Log.d(TAG, "onAudioAttributesChanged: audioAttributes usage"+ audioAttributes.usage);
            Log.d(TAG, "onAudioAttributesChanged: audioAttributes allowed capture policy"+ audioAttributes.allowedCapturePolicy);


        }

        @Override
        public void onVolumeChanged(float volume) {
            Log.d(TAG, "onVolumeChanged: " + volume);
        }
    };

}