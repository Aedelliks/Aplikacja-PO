/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

/*** Klasa decydująca czy dana kategoria posiada pliki graficzne, po czym uruchamia odpowiednią metodę.
 *   Dla zwrotów nie ma plików graficznych, dla pozostałych klas istnieją.
 * ***/

public class Word {


    private String mDefaultTranslation;

    //translacja słowa
    private String mMiwokTranslation;


    private int mGetImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourceId;


    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;

    }
    public Word(String defaultTranslation, String miwokTranslation, int getImageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mGetImageResourceId = getImageResourceId;
        mAudioResourceId = audioResourceId;
    }


    //pobranie słowa
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }


    //tłumaczenie słowa
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }


    // Id obrazu
    public int getImageResourceId() {
        return mGetImageResourceId;
    }



    //sprawdzenie czy jest przypisany plik graficzny ( zwroty - brak; liczby, rodzina, kolory - jest)
    public boolean hasImage(){
        return mGetImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }

}
