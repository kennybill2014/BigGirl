package com.yibao.biggirl.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.music.MusicItem;

import java.util.ArrayList;
import java.util.Random;


public class AudioPlayService
        extends Service
{

    private        MediaPlayer mediaPlayer;
    private        AudioBinder mAudioBinder;
    private static int         PLAY_MODE;
    public static final int PLAY_MODE_ALL    = 0;
    public static final int PLAY_MODE_SINGLE = 1;
    public static final int PLAY_MODE_RANDOM = 2;
    String path = Environment.getExternalStorageDirectory()
                             .getAbsolutePath() + "/Music/Song/1773377275_AZ.mp3";
    private int position = -2;
    private ArrayList<MusicItem> mMusicItem;
    private SharedPreferences    sp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAudioBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBinder = new AudioBinder();
        sp = getSharedPreferences("config", MODE_PRIVATE);
        //初始化播放模式
        PLAY_MODE = sp.getInt("play_mode", 0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mMusicItem = intent.getParcelableArrayListExtra("musicItem");

        int enterPosition = intent.getIntExtra("position", -1);
        if (enterPosition != position && enterPosition != -1) {
            position = enterPosition;
            //执行播放
            mAudioBinder.play();
        } else if (enterPosition != -1 && enterPosition == position) {
            //通知播放界面更新
            MyApplication.getIntstance()
                         .bus()
                         .post(mMusicItem.get(position));
        }
        return START_NOT_STICKY;
    }


    public class AudioBinder
            extends Binder
            implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener

    {
        private void play() {

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(AudioPlayService.this,
                                             Uri.parse(mMusicItem.get(position)
                                                                 .getPath()));

            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }


        //准备完成回调
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            //开启播放
            mediaPlayer.start();
            //通知播放界面更新
            MyApplication.getIntstance()
                         .bus()
                         .post(mMusicItem.get(position));
        }

        //获取当前播放进度

        public int getProgress() {
            return mediaPlayer.getCurrentPosition();
        }

        //获取音乐总时长
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        //音乐播放完成监听
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //自动播放下一首歌曲
            autoPlayNext();
        }


        //自动播放下一曲
        private void autoPlayNext() {
            switch (PLAY_MODE) {
                case PLAY_MODE_ALL:
                    position = (position + 1) % mMusicItem.size();
                    break;
                case PLAY_MODE_SINGLE:

                    break;
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    break;
            }
            play();
        }

        //获取当前的播放模式
        public int getPalyMode() {
            return PLAY_MODE;
        }

        //设置播放模式
        public void setPalyMode(int playmode) {
            PLAY_MODE = playmode;
            //保存播放模式
            sp.edit()
              .putInt("play_mode", PLAY_MODE)
              .commit();
        }

        //手动播放上一曲
        public void playPre() {
            switch (PLAY_MODE) {
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    if (position == 0) {
                        position = mMusicItem.size() - 1;
                    } else {
                        position--;
                    }

                    break;
            }
            play();
        }

        //手动播放下一曲
        public void playNext() {
            switch (PLAY_MODE) {
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    position = (position + 1) % mMusicItem.size();
                    break;
            }
            play();
        }

        //true 当前正在播放
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        public void start() {
            mediaPlayer.start();
        }

        //暂停播放
        public void pause() {
            mediaPlayer.pause();
        }

        //播放小列表中当前位置的歌曲
        public void playPosition(int position) {
            AudioPlayService.this.position = position;
            play();
        }
    }

}