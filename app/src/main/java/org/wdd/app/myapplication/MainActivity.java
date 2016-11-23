package org.wdd.app.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View targetView;

    ImageView img1;

    ImageView img2;

    ImageView img3;

    ImageView img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = (ImageView) findViewById(R.id.img1);
        img1.setOnClickListener(this);
        ViewCompat.setTransitionName(img1, "image");

        img2 = (ImageView) findViewById(R.id.img2);
        img2.setOnClickListener(this);
        ViewCompat.setTransitionName(img2, "image");

        img3 = (ImageView) findViewById(R.id.img3);
        img3.setOnClickListener(this);
        ViewCompat.setTransitionName(img3, "image");

        img4 = (ImageView) findViewById(R.id.img4);
        img4.setOnClickListener(this);
        ViewCompat.setTransitionName(img4, "image");

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.clear();
                sharedElements.put("image", targetView);
            }
        });

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        int position = -1;
        if (data != null) {
            position = data.getIntExtra("position", 0);
        }
        switch (position) {
            case 0:
                targetView = img1;
                break;
            case 1:
                targetView = img2;
                break;
            case 2:
                targetView = img3;
                break;
            case 3:
                targetView = img4;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        targetView = view;
        int position = 0;
        Intent intent = new Intent(this, PreviewActivity.class);
        switch (view.getId()) {
            case R.id.img1:
                position = 0;
                break;
            case R.id.img2:
                position = 1;
                break;
            case R.id.img3:
                position = 2;
                break;
            case R.id.img4:
                position = 3;
                break;
        }
        intent.putExtra("position", position);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(view, "image")).toBundle();
        ActivityCompat.startActivity(this, intent, bundle);
    }
}
