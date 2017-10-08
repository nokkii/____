package com.dmm.noaki_takuya.internshipbaseapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmm.noaki_takuya.internshipbaseapplication.logic.ChoiceHouseLogic;
import com.dmm.noaki_takuya.internshipbaseapplication.logic.HousePagerAdapter;
import com.dmm.noaki_takuya.internshipbaseapplication.logic.RecipeLogic;
import com.tmall.ultraviewpager.UltraViewPager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/////////////
// 最初の画面
/////////////

public class ChoiceHouseActivity extends AppCompatActivity{

    // 自分自身のインスタンスを入れておく用
    private ChoiceHouseActivity activity;
    // スワイプジェスチャーの検知用
    GestureDetector gesture;
    private UltraViewPager housePager;
    private PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 自分自身を保持
        activity = this;
        // 触ってもらうクラスに処理を飛ばしています
        ChoiceHouseLogic.instance().onCreate(activity);


        /////////////////////////
        ///  家の選択カルーセル
        /////////////////////////

        // カルーセル本体
        housePager = (UltraViewPager) activity.findViewById(R.id.ultra_viewpager);
        // カルーセルのスクロール方向(水平)
        housePager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        // 家データ流し込みクラスのインスタンス生成
        adapter = new HousePagerAdapter(false);
        // 家データ流し込み
        housePager.setAdapter(adapter);
        // スクロールアニメーションスピード
        housePager.setInfiniteRatio(100);



        // tapを解析するクラスのリスナーを作成
        gesture = new GestureDetector(activity, new GestureDetector.OnGestureListener() {
            // editモード
            boolean isEdit;

            @Override public boolean onDown(MotionEvent e) { return false; }
            @Override public void onShowPress(MotionEvent e) { }
            @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return false; }
            @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { return false; }


            // activityのどこをクリックしても家を選択したことになるイベントを設定
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                if (!isEdit){
                    // pagerから要素番号を取得
                    int position = housePager.getCurrentItem();
                    // houseNamesから現在選択されている家の名前を取得
                    String houseName = ChoiceHouseLogic.instance().houseNames.get(position);
                    // RecipeMenuに遷移
                    ChoiceHouseLogic.instance().toHome(activity, houseName);
                } else {
                    // EditViewにデリゲート
                    EditText eHouseName = (EditText) ( activity.findViewById(R.id.pager_edit) );
                    eHouseName.setFocusableInTouchMode(true);
                    // キーボード呼び出し
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(eHouseName, 0);
                }

                return false;
            }

            // Edit
            @Override
            public void onLongPress(MotionEvent e) {
                Log.w("hoge", "hoge");

                if(isEdit){
                    // 普通モード
                    ChoiceHouseLogic.instance().goStandard(activity);
                } else {
                    // 編集モード
                    ChoiceHouseLogic.instance().goEdit(activity);
                }
                isEdit = !isEdit;

            }
        });
    }

    // Activity.dispatchTouchEvent()
    // ViewGroup.dispatchTouchEvent()
    // View.dispatchTouchEvent()
    // View.onTouchEvent()
    // ViewGroup.onTouchEvent()
    // Activity.onTouchEvent()
    // タッチイベントは上記の順番で呼ばれる。housePagerに使っているライブラリ、UltraPagerはViewGroup層の
    // dispatchTouchEventで以降のイベントを握りつぶす汚い実装だったのでActivity層のdispatchTouchEventで
    // イベントを取り返している。イベントが発火しないのでUltraPagerのソースコードを読み込んで発覚した。
    // Android UIではこういうことがよくあるのでUIの外部ライブラリは極力使いたくない。。。
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        // Activity.dispatchTouchEventを呼んだことで逆にUltraPagerのdispatchTouchEventを殺さないように
        // ここで呼ぶ。ただActivityのEventなので普通に渡してしまうと画面のどこを操作してもイベントが走る
        housePager.dispatchTouchEvent(event);
        // GestureDetectorのイベントを発火させるにはonTouchEventにMotionEventを渡してあげないといけない
        gesture.onTouchEvent(event);

        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
