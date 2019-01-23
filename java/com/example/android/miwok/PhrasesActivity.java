package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/*** Klasa odpowiedzialna za wyświetlanie i odtwarzanie przetłumaczonych słów z kategorii: zwroty***/

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();


                    }else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp){
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Gdzie idziesz?", "minto wuksus",
        R.raw.phrase_where_are_you_going));
        words.add(new Word("Jak się nazywasz?", "tinnә oyaase'nә",
        R.raw.phrase_what_is_your_name));
        words.add(new Word("Nazywam się...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("Jak się czujesz?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("Czuję się dobrze.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Idziesz?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Tak, idę.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("Idę.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Chodźmy.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Podejdź.", "әnni'nem", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word = words.get(position);
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,

                        AudioManager.STREAM_MUSIC,

                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {



                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        releaseMediaPlayer();
    }
    // uruchomienie modułu do odtwarzania dźwięku
    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
