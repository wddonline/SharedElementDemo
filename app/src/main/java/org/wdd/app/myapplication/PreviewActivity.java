package org.wdd.app.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

public class PreviewActivity extends AppCompatActivity {

    private ImageView[] views;
    ViewPager viewPager;
    View targetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        supportStartPostponedEnterTransition();
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        views = new ImageView[4];
        for (int i = 0; i < views.length; i++) {
            views[i] = new ImageView(getBaseContext());
            views[i].setImageResource(R.mipmap.ic_launcher);
            views[i].setScaleType(ImageView.ScaleType.FIT_XY);
            ViewCompat.setTransitionName(views[i], "image");
        }

        viewPager.setAdapter(new PreviewAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                targetView = views[position];
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int initPos = getIntent().getIntExtra("position", 0);
        targetView = views[initPos];
        viewPager.setCurrentItem(initPos);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.clear();
                sharedElements.put("image", targetView);
            }

        });
    }

    @Override
    public void supportFinishAfterTransition() {
        Intent intent = new Intent();
        intent.putExtra("position", viewPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.supportFinishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", viewPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private class PreviewAdapter extends PagerAdapter {

        private ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views[position], lp);
            return views[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
