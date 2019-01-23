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

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

/*** Klasa odpowiedzialna za wyświetlanie i odtwarzanie przetłumaczonych słów z kategorii: liczbyy***/

public class NumbersActivity extends AppCompatActivity {

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
        // words.add("one");
        words.add(new Word("jeden", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("dwa", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("trzy", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("cztery", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("pięć", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("sześć", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("siedem", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("osiem", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("dziewięć", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("dziesięć", "na'aacha", R.drawable.number_ten, R.raw.number_ten));
/**
        words.add("one");
        words.add("two");
        words.add("three");
        words.add("four");
        words.add("five");
        words.add("six");
        words.add("seven");
        words.add("eight");
        words.add("nine");
        words.add("ten");

 String[] words = new String[10];
 words[0] = "one";
 words[1] = "two";
 words[2] = "three";
 words[3] = "four";
 words[4] = "five";
 words[5] = "six";
 words[6] = "seven";
 words[7] = "eight";
 words[8] = "nine";
 words[9] = "ten";
 **/
/**
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
/**
        int index = 0;
        while (index < words.size()) {
            TextView wordView = new TextView(this);
            wordView.setText(words.get(index));
            rootView.addView(wordView);
            index++;

        }

        for(int index = 0; index < words.size(); index++){
            TextView wordView = new TextView(this);3
            wordView.setText(words.get(index));
            rootView.addView(wordView);
            index++;;
        }
 */

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

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



                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });


    }

    /***
     *
     *     @Override
     *     protected void onStop(){
     *         super.onStop();
     *          if (MediaPlayer.IsChecked()){
     *         releaseMediaPlayer();
     *         }
     *     }
     */

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
