package com.dmm.noaki_takuya.internshipbaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dmm.noaki_takuya.internshipbaseapplication.logic.InternshipFirstClass;

/////////////
// 最初の画面
/////////////

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 自分自身のインスタンスを入れておく用
    private MainActivity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 自分自身を保持
        activity = this;
        // 触ってもらうクラスに処理を飛ばしています
        InternshipFirstClass.instance().onCreate(activity);


        // idでbuttonを取得、Button型に変換
        Button btn = (Button)( activity.findViewById(R.id.button1) );
        // buttonのクリックイベントに自分を登録、ボタンが押されるとonClickが呼ばれる
        btn.setOnClickListener(activity);
    }


    @Override
    public void onClick(View v) {
        // 触ってもらうクラスに処理を飛ばしています
        InternshipFirstClass.instance().buttonClick(activity);

        // 動作確認のためにログを取っています、無くてもいいです
        Log.v("Button","onClick");
    }
}
