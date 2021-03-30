//package com.example.myplaymusic_kim;
//
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Handler;
//import java.util.logging.LogRecord;
//
//public class MediaPlayerActivity extends AppCompatActivity {
//    private ImageButton btnForward,btnPause,btnPlay,btnBackward;
//    private MediaPlayer mediaPlayer;
//    private double startTime = 0;
//    private double finalTime = 0;
//    private Handler myHandler = new Handler() {
//        @Override
//        public void publish(LogRecord record) {
//
//        }
//
//        @Override
//        public void flush() {
//
//        }
//
//        @Override
//        public void close() throws SecurityException {
//
//        }
//    };;
//    private int forwardTime = 5000;
//    private int backwardTime = 5000;
//    private SeekBar seekbar;
//    private TextView tvStatus,tvStartTime,tvFinalTime,tvSongName;
//    public static int oneTimeOnly = 0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        // Tham chiếu id của các View
//        // ...
//        tvSongName.setText("Nguoi Mien Tay");
//        mediaPlayer = MediaPlayer.create(this,R.raw.nguoi_mien_tay);
//        seekbar=(SeekBar)findViewById(R.id.seekBar);
//        seekbar.setClickable(false);
//        btnPause.setEnabled(false);
//    }
//    private Runnable UpdateSongTime = new Runnable() {
//        public void run() {
//            startTime = mediaPlayer.getCurrentPosition();
//            tvStartTime.setText(String.format("%d min, %d sec",
//                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
//                                    toMinutes((long) startTime)))
//            );
//            seekbar.setProgress((int)startTime);
//            myHandler.postDelayed(this, 100);
//        }
//    };
//    public void playAction(View view){
//        tvStatus.setText("Playing sound");
//        mediaPlayer.start();
//        finalTime = mediaPlayer.getDuration();
//        startTime = mediaPlayer.getCurrentPosition();
//        if (oneTimeOnly == 0) {
//            seekbar.setMax((int) finalTime);
//            oneTimeOnly = 1;
//        }
//        tvFinalTime.setText(String.format("%d min, %d sec",
//                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
//                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
//        );
//        tvStartTime.setText(String.format("%d min, %d sec",
//                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
//        );
//        seekbar.setProgress((int)startTime);
//        myHandler.postDelayed(UpdateSongTime,100);
//    }
//    public void pauseAction(View v) {
//        tvStatus.setText("Pausing sound");
//        mediaPlayer.pause();
//    }
//    public void forwardAction(View v) {
//        int temp = (int)startTime;
//        if((temp+forwardTime)<=finalTime){
//            startTime = startTime + forwardTime;
//            mediaPlayer.seekTo((int) startTime);
//            tvStatus.setText("You have Jumped forward 5 seconds");
//        }
//        else{
//            tvStatus.setText("Cannot jump forward 5 seconds");
//        }
//    }
//    public void backwardAction(View v) {
//        int temp = (int)startTime;
//        if((temp-backwardTime)>0){
//            startTime = startTime - backwardTime;
//            mediaPlayer.seekTo((int) startTime);
//            tvStatus.setText("You have Jumped backward 5 seconds");
//        }
//        else{
//            tvStatus.setText("Cannot jump backward 5 seconds");
//        }
//    }
//}
