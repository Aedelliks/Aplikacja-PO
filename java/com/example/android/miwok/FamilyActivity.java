package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/*** Klasa odpowiedzialna za wyświetlanie i odtwarzanie przetłumaczonych słów z kategorii: rodzina***/

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new Word("tata", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mama", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("syn", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("córka", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("starszy brat", "taachi", R.drawable.family_older_brother,
        R.raw.family_older_brother));
        words.add(new Word("młodszy brat", "chalitti", R.drawable.family_younger_brother,
        R.raw.family_younger_brother));
        words.add(new Word("starsza siostra", "teṭe", R.drawable.family_older_sister,
        R.raw.family_older_sister));
        words.add(new Word("młodsza siostra", "kolliti", R.drawable.family_younger_sister,
        R.raw.family_younger_sister));
        words.add(new Word("babcia ", "ama", R.drawable.family_grandmother,
        R.raw.family_grandmother));
        words.add(new Word("dziadek", "paapa", R.drawable.family_grandfather,
        R.raw.family_grandfather));
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

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



                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());

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


    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
