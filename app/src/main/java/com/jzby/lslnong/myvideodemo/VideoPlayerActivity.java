package com.jzby.lslnong.myvideodemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import java.io.IOException;

public class VideoPlayerActivity extends AppCompatActivity {
    private SurfaceView sfv;//能够播放图像的控件
    private SurfaceHolder holder;
    private MediaPlayer player;//媒体播放器
    private static final String playurl1 = "http://10.7.14.47:8080/e4fc6f8bd66511e8807cfa163e504596/35B9AB5A36F3234DD26DB357FD4A0DC1/2018/11/05/20181105092010-20181105095010.mp4";
    private static final String playurl2 = "udp://@238.0.0.1:1234";
    private static final String playurl3 = "udp://@238.0.0.1:1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前窗口无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置当前窗口保持全屏状态
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.videoplayer);

        initView();
    }

    //初始化控件，并且为进度条和图像控件添加监听
    private void initView() {
        sfv = findViewById(R.id.sfv);
        holder = sfv.getHolder();
        holder.setKeepScreenOn(true);

        player = new MediaPlayer();

        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                player.setDisplay(holder);
                try {
                    Uri uri = Uri.parse(playurl2);
                    player.setDataSource(VideoPlayerActivity.this, uri);
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.prepare();
                    player.setDisplay(holder);
                }catch (IOException e1) {
                    e1.printStackTrace();
                }catch (IllegalStateException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}

        });

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 把视频画面输出到SurfaceView
                player.start();
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //视频播放完成后，释放资源
                if(player!=null){
                    player.release();
                }
            }
        });
    }
}
