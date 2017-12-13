package com.holiestar.toolkit.component.tool.viewpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class ViewPagerFragmentManager {
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CustomFragmentPageAdapter customFragmentPageAdapter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPagerFragmentManager(FragmentManager fragmentManager, ViewPager viewPager, TabLayout tabLayout) {
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.fragmentManager=fragmentManager;
        this.customFragmentPageAdapter = new CustomFragmentPageAdapter();
        this.viewPager.setAdapter(this.customFragmentPageAdapter);
        ViewPagerSpeed.setSpeed(this.viewPager, 640);
    }

    public void addFragment(Fragment fragment, String title) {
        if (this.fragments == null || this.titles == null) {
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        this.fragments.add(fragment);
        this.titles.add(title);
        this.customFragmentPageAdapter.notifyDataSetChanged();
    }

    public void addFragmentSilent(Fragment fragment, String title){
        if (this.fragments == null || this.titles == null) {
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        this.fragments.add(fragment);
        this.titles.add(title);
    }

    public void reBind(){
        this.viewPager.setAdapter(this.customFragmentPageAdapter);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.customFragmentPageAdapter.notifyDataSetChanged();
    }

    public void bindTab2Pager() {
        this.tabLayout.setupWithViewPager(this.viewPager);
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public Fragment getCurrentFragment() {
        if (customFragmentPageAdapter == null) return null;
        return customFragmentPageAdapter.getItem(viewPager.getCurrentItem());
    }

    public void reset(){
        if(fragments!=null){
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            for(Fragment f:fragments) fragmentTransaction.remove(f);
            fragmentTransaction.commitAllowingStateLoss();
        }
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    private class CustomFragmentPageAdapter extends FragmentPagerAdapter {

        public CustomFragmentPageAdapter() {
            super(fragmentManager);
        }

        @Override
        public int getItemPosition(Object object) {
            // POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            boolean hasFragment = fragments != null && !fragments.isEmpty();
            return hasFragment ? fragments.size() : 0;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = fragments.get(position);
            fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return titles.get(position);
            } catch (Exception e) {
                return "";
            }
        }
    }

    public static class ViewPagerSpeed extends Scroller {
        private int mDuration = 640;

        public ViewPagerSpeed(Context context, int duration) {
            super(context);
            this.mDuration = duration;
        }

        public ViewPagerSpeed(Context context) {
            super(context);
        }

        public ViewPagerSpeed(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerSpeed(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

        public static void setSpeed(ViewPager vg, int duration) {
            try {
                Field mScroller = null;
                mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                ViewPagerManager.ViewPagerSpeed scroller = new ViewPagerManager.ViewPagerSpeed(vg.getContext(), duration);
                mScroller.set(vg, scroller);
            } catch (Exception e) {
            }

        }
    }
}
