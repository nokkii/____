package com.dmm.noaki_takuya.internshipbaseapplication.logic;

import android.app.Application;

import com.dmm.noaki_takuya.internshipbaseapplication.Model.Recipe;
import com.dmm.noaki_takuya.internshipbaseapplication.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by michiko on 10/4/17.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());

        // テストデータ
        ///*
        RecipeLogic.instance().houses = Recipe.testDataLoading();
        ChoiceHouseLogic.instance().houseName = "すぎやま";
        ChoiceHouseLogic.instance().myHouse = "すぎやま";
        //*/

        // 初期データ
        /*
        RecipeLogic.instance().houses = Recipe.firstDataLoading();
        ChoiceHouseLogic.instance().houseName = "わたし";
        ChoiceHouseLogic.instance().myHouse = "わたし";
        */

        super.onCreate();
    }
}
