package com.example.android.miwok;

        import android.content.Context;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

        import static android.os.Build.VERSION_CODES.M;


/*** Klasa odpowiedzialna za wyświetlanie i odtwarzanie przetłumaczonych słów z kategorii: kolory***/

public class ColorsActivity extends AppCompatActivity {

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
        words.add(new Word("czerwony", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("żółty", "chiwiiṭә", R.drawable.color_mustard_yellow,
        R.raw.color_mustard_yellow));
        words.add(new Word("szarożółty", "ṭopiisә", R.drawable.color_dusty_yellow,
        R.raw.color_dusty_yellow));
        words.add(new Word("zielony", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brązowy", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("szary", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("czarny", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("biały", "kelelli", R.drawable.color_white, R.raw.color_white));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

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



                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

                    }
                } );


        }
        @Override
        protected void onStop(){
            super.onStop();

            releaseMediaPlayer();
        }

    private void releaseMediaPlayer() {
        // sprawdzenie czy media player ma załadowany dźwięk
        if (mMediaPlayer != null) {
            // zresetowanie aktywności media playera, aby poprzednie dźwięki nie były grane
            mMediaPlayer.release();

            // null - jest gotowy do włączenia nowego dźwięku
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}