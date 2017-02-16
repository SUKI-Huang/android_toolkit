package com.holiestar.toolkit.component.tool.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SsuChi on 7/22/2015.
 */
public class FragmentAdapter {
    private Context context;
    private PagerAdapter pagerAdapter;
    private List<Fragment> fragments;


    public FragmentAdapter(Context context){
        this.context=context;
        this.fragments=new ArrayList<>();
    }

    public FragmentPagerAdapter getAdapter(FragmentManager fm){
        if(pagerAdapter==null){
            pagerAdapter=new PagerAdapter(fm);
        }
        return pagerAdapter;
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm){
            super(fm);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {

            return fragments==null?0:fragments.size();
        }
    }

}
