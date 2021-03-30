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
    ImageView imageViewBlurimg, imgCD;
    ImageButton btnPlay;
    MediaPlayer player;
    CountDownTimer countDownTimer;
    SeekBar seekBar;
    TextView tvTimeRun, tvTimeAll,  tvLoiBaiHat;
    float gocquay = 0;

//    ArrayList<String> arrayLoiBaiHat = new ArrayList<>();
//    int vi_tri_lyric = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Into();
        anhxa();
        setUp();
        setClick();

    }
    private void anhxa()
    {
        imageViewBlurimg = findViewById(R.id.imageViewBlurimg);
        btnPlay = findViewById(R.id.btnPlay);
        seekBar = findViewById(R.id.seekBar);
        tvTimeRun = findViewById(R.id.tvTimeRun);
        tvTimeAll = findViewById(R.id.tvTimeAll);
        imgCD = findViewById(R.id.imgCD);
        tvLoiBaiHat = findViewById(R.id.tvLoiBaiHat);
    }
    private void  setUp() {
        setBlurimg(imageViewBlurimg);

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
                    batDauChayNhac(R.raw.anh_da_sai);
                    Log.d("player", "batdau");
                }
                else
                if(player.isPlaying() == true){
                    dungNhacLai();
                    Log.d("player", "danghat");
                }
                else chayTiepNhac();
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
//        arrayLoiBaiHat.add("Loi1");
//        arrayLoiBaiHat.add("Loi2");
//        arrayLoiBaiHat.add("Loi3");
//        arrayLoiBaiHat.add("Loi4");
//        String data;
//        InputStream in= getResources().openRawResource(R.raw.lyric_anh_da_sai);
//        InputStreamReader inreader=new InputStreamReader(in);
//        BufferedReader bufreader=new BufferedReader(inreader);
//        StringBuilder builder=new StringBuilder();
//        if(in!=null)
//        {
//            try
//            {
//                while((data=bufreader.readLine())!=null)
//                {
//                    builder.append(data);
//                    builder.append("\n");
//                }
//                in.close();
//                arrayLoiBaiHat.add(builder.toString());
//            }
//            catch(IOException ex){
//                Log.e("ERROR", ex.getMessage());
//            }
//        }
//        try {
//            // Open stream to read file.
//            FileInputStream in = this.openFileInput(R.raw.lyric_anh_da_sai);
//
//            BufferedReader br= new BufferedReader(new InputStreamReader(in));
//
//            StringBuilder sb= new StringBuilder();
//            String s= null;
//            while((s= br.readLine())!= null)  {
//                sb.append(s).append("\n");
//            }
//            arrayLoiBaiHat.add(sb.toString());
//
//        } catch (Exception e) {
//            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
    }

    private void setBlurimg(ImageView view){
        BlurImage.withContext(this)
                .setBlurRadius(1.5f)
                .setBitmapScale(0.1f)
                .blurFromResource(R.drawable.quan_trong_la_than_thai)
                .into(view);
    }

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
//    private void  upDateLyric(){
//        if(player == null || player.isPlaying() == false) {
//            return;
//        }
//        vi_tri_lyric++;
//        if(arrayLoiBaiHat.size()== vi_tri_lyric){
//            vi_tri_lyric = 0;
//        }
//
//        new CountDownTimer(4000, 1000){
//            @Override
//            public void onTick(long millisUntilFinished) {
//                tvLoiBaiHat.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.mat_di));
//                tvLoiBaiHat.setText(arrayLoiBaiHat.get(vi_tri_lyric));
//                tvLoiBaiHat.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.hien_ra));
//            }
//
//            @Override
//            public void onFinish() {
//                start();
//            }
//        }.start();
//   }
}