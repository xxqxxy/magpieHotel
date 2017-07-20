package com.innouni.magpie.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.innouni.magpie.main.fragment.chatFragment;
import com.innouni.magpie.main.fragment.findFragment;
import com.innouni.magpie.main.fragment.homeFragment;
import com.innouni.magpie.main.fragment.personalFragment;

public class mainActivity extends Activity implements View.OnClickListener {

    TextView tv_home, tv_find, tv_chat, tv_personal;

    Fragment fragments[];
    android.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFindByIds();

        fragments = new Fragment[4];
        fragmentManager = getFragmentManager();

        initfooter(R.id.tv_home);
        initFragment(0);

    }

    private void initFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        if (fragments[index] == null) {
            switch (index) {
                case 0:
                    fragments[index] = new homeFragment();
                    break;
                case 1:
                    fragments[index] = new findFragment();
                    break;
                case 2:
                    fragments[index] = new chatFragment();
                    break;
                case 3:
                    fragments[index] = new personalFragment();
                    break;
            }
            transaction.add(R.id.content, fragments[index]);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(fragments[index]);
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (Fragment tFragment : fragments) {
            if (tFragment != null) {
                transaction.hide(tFragment);
            }
        }
    }


    /***
     * 底部按钮点击效果切换
     * @param id  控件id
     */
    private void initfooter(int id) {

    }


    /***
     * 控件初始化
     */
    private void initFindByIds() {
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);

        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_find.setOnClickListener(this);

        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_chat.setOnClickListener(this);

        tv_personal = (TextView) findViewById(R.id.tv_personal);
        tv_personal.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                case R.id.tv_home:
                    initfooter(R.id.tv_home);
                    initFragment(0);
                    break;
                case R.id.tv_find:
                    initfooter(R.id.tv_find);
                    initFragment(1);
                    break;
                case R.id.tv_chat:
                    initfooter(R.id.tv_chat);
                    initFragment(2);
                    break;
                case R.id.tv_personal:
                    initfooter(R.id.tv_personal);
                    initFragment(3);
                    break;
            default:
                break;
        }

    }
}
