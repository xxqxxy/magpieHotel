package com.innouni.magpie.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innouni.magpie.main.R;

/**
 * 客服
 * Created by Administrator on 2017/7/20.
 */

public class chatFragment extends Fragment {
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          mView = inflater.inflate(R.layout.fragment_chat, container, false) ;
        return mView;
    }
}
