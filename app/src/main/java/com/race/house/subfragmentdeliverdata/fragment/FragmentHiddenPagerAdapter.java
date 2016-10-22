package com.race.house.subfragmentdeliverdata.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Henry on 2016/10/20.
 */

public abstract class FragmentHiddenPagerAdapter extends PagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    ;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;


    public FragmentHiddenPagerAdapter(FragmentManager fragmentManager) {
        // 获取 sub fragmentmanager
        this.mFragmentManager = fragmentManager;
    }

    public Fragment getItem(int position) {
        // 便于从list从获取fragment
        if (position >= 0 && position < mFragments.size()) {
            Fragment f = mFragments.get(position);
            return f;
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //创建 ContentView 需要手动创建

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        //第一次调用时 mfragments 的size是0.
        //当执行过一次之后，就不是了。 即做了缓存的。
        if (mFragments.size() > position) {
            Fragment f = mFragments.get(position);
            if (f != null) {
                mCurTransaction.show(f);
                return f;
            }
        }

        //设置tag的名称。container id + position
        final String tag = makeFragmentName(container.getId(), position);

        Fragment fragment = mFragmentManager.findFragmentByTag(tag);


        if (fragment != null) {
            mCurTransaction.remove(fragment);
        }

        fragment = getItem(position); //第一次从集成类中获取fragment。

        while (mFragments.size() <= position) {
            mFragments.add(null);
        }
        fragment.setMenuVisibility(false); //是否显示fragment中的menu
        fragment.setUserVisibleHint(false);//fragment是否处于可见状态。主要用来懒加载，作为标志位的判断。
        //这里使用的是set，因为用户有可能会跳过2 3 ，直接点击最后一个fragment，
        //如果直接使用 add的话， 就尴尬了...
        mFragments.set(position, fragment);
        mCurTransaction.add(container.getId(), fragment, tag);

        return fragment;
    }


    /**
     * 用来做缓存用，当界面不可见的时候，不销毁fragment，做缓存。
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        //用于缓存所有的fragment。不被销毁。不掉用 super.destroyItem
        Fragment fragment = (Fragment) object;
        mCurTransaction.hide(fragment);

    }

    @Override
    public void finishUpdate(ViewGroup container) {


        if (mCurTransaction != null) {
            //如果在提交时， 若activity 已经被销毁，
            // 若我们不关心是否能恢复 transaction状态，
            // 用 commitAllowingStateLoss， 可以避免崩溃。
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;

            mFragmentManager.executePendingTransactions();
        }

    }
    
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {

            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {

                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;

        }

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //这里， fragment并不是View的子类。 所以这里并不是直接返回fragment
        //开始还有点困惑，后来看了他的父类才明白。
        return ((Fragment) object).getView() == view;

    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }


}
