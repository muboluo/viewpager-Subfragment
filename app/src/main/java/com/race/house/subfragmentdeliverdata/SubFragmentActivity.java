package com.race.house.subfragmentdeliverdata;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.race.house.subfragmentdeliverdata.fragment.FragmentHiddenPagerAdapter;
import com.race.house.subfragmentdeliverdata.fragment.FragmentOne;
import com.race.house.subfragmentdeliverdata.fragment.FragmentTwo;

import java.util.ArrayList;

public class SubFragmentActivity extends AppCompatActivity {

    private TextView mTextView;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mfragments = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_fragment);

        mTextView = (TextView) findViewById(R.id.textview);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mfragments.add(new FragmentOne());
        mfragments.add(new FragmentTwo());
        mAdapter  = new MyPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                  case 0:
                      mTextView.setText("This is the first page");

                      break;
                    case 1:
                        mTextView.setText("This is the second page");

                    break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyPagerAdapter extends FragmentHiddenPagerAdapter {


        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = super.getFragment(position);

            if (fragment == null) {
                fragment = mfragments.get(position);
                Bundle bundle = new Bundle();
                //从父窗口 向 子 fragment 传递数据。
                bundle.putString("first","窗外的麻雀~");
                bundle.putString("second","在电线杆上多嘴~~");
                fragment.setArguments(bundle);
            }

            return fragment;


        }

        @Override
        public int getCount() {
            return mfragments.size();
        }
    }

}
