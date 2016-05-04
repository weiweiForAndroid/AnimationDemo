package com.test.animationdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyHorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (MyHorizontalScrollView) findViewById(R.id.myHorizontalScrollView);
        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout, scrollView, false);
            layout.getLayoutParams().width = width;
            layout.getLayoutParams().height = height;
            TextView textView = (TextView) layout.findViewById(R.id.textView2);
            textView.setText("page:" + i);
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            creatList(layout);
            scrollView.addView(layout);
        }
    }

    private void creatList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.listView);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, R.id.textView, datas);
        listView.setAdapter(adapter);
//        PatchManager  patchManager = new PatchManager(this);
//        patchManager.init();//current version
    }
}
