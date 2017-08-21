package com.innouni.magpie.main.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comutils.main.utils.SharePreferences;
import com.innouni.magpie.main.R;
import com.innouni.magpie.main.fragment.chatFragment;
import com.innouni.magpie.main.fragment.homeFragment;
import com.innouni.magpie.main.fragment.personalFragment;



/**
 * 框架构建页
 * @author  xxq 2017-0816-1728
 */
public class mainActivity extends Activity implements View.OnClickListener {

    TextView tv_home,  tv_chat, tv_personal;
    ImageView iv_home_bg, iv_chat_bg,iv_personal_bg;
    Fragment fragments[];
    android.app.FragmentManager fragmentManager;
    SharePreferences isPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      isPreferences = new SharePreferences(this);
        isPreferences.updateSp("wlact_from" , "main");

        initFindByIds();

        fragments = new Fragment[3];
        fragmentManager = getFragmentManager();

        //初始化第一个
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
              /*  case 1:
                    fragments[index] = new findFragment();
                    break;*/
                case 1:
                    fragments[index] = new chatFragment();
                    break;
                case 2:
                    fragments[index] = new personalFragment();
                    break;
            }
            transaction.add(R.id.content, fragments[index]);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(fragments[index]);
        }

        Log.d("", "[Fragment] = "+index);


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
        iv_home_bg.setVisibility(View.INVISIBLE);
        iv_chat_bg.setVisibility(View.INVISIBLE);
        iv_personal_bg.setVisibility(View.INVISIBLE);
        switch (id){
            case R.id.tv_home:
                iv_home_bg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_chat:
                iv_chat_bg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_personal:
                iv_personal_bg.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

    }
    /***
     * 控件初始化
     */
    private void initFindByIds() {
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);

        tv_chat = (TextView) findViewById(R.id.tv_chat);
        tv_chat.setOnClickListener(this);

        tv_personal = (TextView) findViewById(R.id.tv_personal);
        tv_personal.setOnClickListener(this);

        iv_home_bg = (ImageView) findViewById(R.id.iv_home_bg);
        iv_chat_bg = (ImageView) findViewById(R.id.iv_chat_bg);
        iv_personal_bg = (ImageView) findViewById(R.id.iv_personal_bg);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                Log.d("", "[Fragment] = tv_home");
                initfooter(R.id.tv_home);
                initFragment(0);
                break;

            case R.id.tv_chat:
                Log.d("", "[Fragment] = tv_chat ");
                initfooter(R.id.tv_chat);
                initFragment(1);
                break;
            case R.id.tv_personal:
                Log.d("", "[Fragment] = tv_personal");
                initfooter(R.id.tv_personal);
                initFragment(2);
                break;
            default:
                break;
        }

    }
}
