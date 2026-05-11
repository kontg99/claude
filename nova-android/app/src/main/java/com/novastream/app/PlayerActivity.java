package com.novastream.app;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class PlayerActivity extends AppCompatActivity {

    private ExoPlayer player;
    private PlayerView playerView;
    private TextView channelNameView;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen και keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_player);

        playerView     = findViewById(R.id.player_view);
        channelNameView = findViewById(R.id.channel_name);
        errorView      = findViewById(R.id.error_view);

        String url         = getIntent().getStringExtra("stream_url");
        String channelName = getIntent().getStringExtra("channel_name");

        if (channelName != null) {
            channelNameView.setText(channelName);
        }

        // Κουμπί κλεισίματος
        ImageButton closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(v -> finish());

        if (url != null) {
            initPlayer(url);
        } else {
            showError("Δεν βρέθηκε URL stream.");
        }
    }

    private void initPlayer(String url) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                showError("Σφάλμα αναπαραγωγής: " + error.getMessage());
            }

            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    errorView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showError(String msg) {
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(msg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
