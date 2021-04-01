package com.example.myplaymusic_kim;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.blurimg.BlurImage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView  imgCD;
//    ImageView imageViewBlurimg;
    ImageButton btnPlay, btnPreview, btnNext;
    MediaPlayer player;
    CountDownTimer countDownTimer;
    SeekBar seekBar;
    TextView tvTimeRun, tvTimeAll, tvName;
    float gocquay = 0;
    ArrayList<Song> arraySong = new ArrayList<>();
    int id_song = 0;
    Song song = new Song();

    @RequiresApi(api=Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        Into();
        anhxa();
        setUp();
        setClick();
    }
    private void anhxa()
    {
//        imageViewBlurimg = findViewById(R.id.imageViewBlurimg);
        btnPlay = findViewById(R.id.btnPlay);
        seekBar = findViewById(R.id.seekBar);
        tvTimeRun = findViewById(R.id.tvTimeRun);
        tvTimeAll = findViewById(R.id.tvTimeAll);
        imgCD = findViewById(R.id.imgCD);
        tvName = findViewById(R.id.tvName);
        btnPreview = findViewById(R.id.btnPreview);
        btnNext = findViewById(R.id.btnNext);
    }
    @RequiresApi(api=Build.VERSION_CODES.O)
    private void  setUp() {
        chuyenBai(0);
        dungNhacLai();
        countDownTimer = new CountDownTimer(100000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                upDate();
//                upDateLyric();
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();

    }
    private void setClick() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(player ==null) {
                    batDauChayNhac(song.src);
                }
                else
                if(player.isPlaying() == true){
                    dungNhacLai();
                }
                else chayTiepNhac();
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api=Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (player == null)
                {
                    batDauChayNhac(song.src);
                }
                else {
                    dungNhacLai();
                    chuyenBai(-1);
                    chayTiepNhac();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api=Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (player == null)
                {
                    batDauChayNhac(song.src);
                }
                else {
                    dungNhacLai();
                    chuyenBai(+1);
                    chayTiepNhac();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                countDownTimer.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(player == null)
                {
                    return;
                }
                if (player.isPlaying() == false){
                    countDownTimer.start();
                    player.pause();
                    player.seekTo(seekBar.getProgress());
                    tvTimeRun.setText(timeToString(player.getCurrentPosition()));
                    dungNhacLai();
                }
                else {
                    countDownTimer.start();
                    player.pause();
                    player.seekTo(seekBar.getProgress());
                    tvTimeRun.setText(timeToString(player.getCurrentPosition()));
                    player.start();
                }
            }
        });
    }
    private void Into() {
        Song song1= new Song();
        song1.name = "Anh đã sai";
        song1.img = R.drawable.anh_da_sai;
        song1.src = R.raw.anh_da_sai;
        song1.singer = "Only C";
        arraySong.add(song1);

        Song song2 = new Song();
        song2.id = 1;
        song2.name = "I love you";
        song2.img = R.drawable.i_love_you;
        song2.src = R.raw.i_love_you;
        arraySong.add(song2);

        song = arraySong.get(0);
    }

//    private void setBlurimg(int anh){
//        BlurImage.withContext(this)
//                .setBlurRadius(1.5f)
//                .setBitmapScale(0.1f)
//                .blurFromResource(anh)
//                .into(imageViewBlurimg);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void batDauChayNhac(int index_song) {
        player = MediaPlayer.create(this, index_song);
        player.start();
        btnPlay.setImageResource(R.drawable.iconpause);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(0);
        }
        seekBar.setMax(player.getDuration());
        tvTimeAll.setText(timeToString(player.getDuration()));

    }
    private void dungNhacLai() {
        btnPlay.setImageResource(R.drawable.iconplay);
        player.pause();
    }
    private void chayTiepNhac() {
        btnPlay.setImageResource(R.drawable.iconpause);
        player.start();
    }
    private void upDate(){
        if(player == null ) {
            return;
        }
        seekBar.setProgress(player.getCurrentPosition());
        tvTimeRun.setText(timeToString(player.getCurrentPosition()));
        if(player.isPlaying() == true){
            gocquay = gocquay + 0.5f;
            if(gocquay == 360) gocquay = 0;
            imgCD.setRotation(gocquay);
        }else dungNhacLai();
    }
    private String timeToString(int time) {
        int s = time / 1000;
        int p = s/60;
        s = s % 60;
        return checkNum10(p) + ":" + checkNum10(s);
    }
    private String checkNum10(int number) {
        if(number < 10) return "0" + number;
        return "" + number;
    }
    @RequiresApi(api=Build.VERSION_CODES.O)
    private void chuyenBai(int x) {
        id_song = id_song + x;
        if(id_song == -1)id_song = arraySong.size() - 1;
        if (id_song == arraySong.size()) id_song = 0;
        song = arraySong.get(id_song);
//        setBlurimg(song.img);
        batDauChayNhac(song.src);
        imgCD.setImageResource(song.img);

       tvName.setText(song.name);
    }
}