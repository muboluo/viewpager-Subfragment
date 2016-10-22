package com.race.house.subfragmentdeliverdata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.race.house.subfragmentdeliverdata.R;

/**
 * Created by Henry on 2016/10/20.
 */

public class FragmentOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    TextView textView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(getArguments().getString("first"));


    }

    boolean isFirstShow = true;

    /**
     * 设置懒加载
     *
     * 原理：该方法调用，是在 onViewCreated之前。所以有的控件还没有加在出来，
     * 如果在这里进行一些初始化控件的操作，可能会引发控制之内异常。
     * 详情参考这里的博客：
     * http://blog.csdn.net/maosidiaoxian/article/details/38300627
     * 这里就不做别的操作了。
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isFirstShow) {
            isFirstShow = false;
        }

    }
}
