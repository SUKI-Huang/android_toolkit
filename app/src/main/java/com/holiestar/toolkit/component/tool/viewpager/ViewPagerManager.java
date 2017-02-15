package com.holiestar.toolkit.component.tool.viewpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by tony1 on 2/13/2017.
 */

public class ViewPagerManager {
    private CustomPagerAdapter customPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<View> views;
    private ArrayList<String> titles;

    public ViewPagerManager(ViewPager viewPager){
        this(viewPager,null);
    }

    public ViewPagerManager(ViewPager viewPager, TabLayout tabLayout){
        this.viewPager=viewPager;
        this.tabLayout=tabLayout;
        init();
    }

    public void init(){
        views = new ArrayList<>();
        titles=new ArrayList<>();
        customPagerAdapter= new CustomPagerAdapter();
        viewPager.setAdapter(customPagerAdapter);
    }

    public void setSpeed(int millisecond){
        if(viewPager!=null){
            ViewPagerSpeed.setSpeed(viewPager,millisecond);
        }
    }

    public int getViewCount(){
        if(views==null){
            return 0;
        }else{
            return views.size();
        }
    }

    public void addView(View view,String title){
        if(views==null || titles==null){
            views = new ArrayList<>();
            titles=new ArrayList<>();
        }
        views.add(view);
        titles.add(title);
        notifyDataSetChanged();
    }

    public void bindTab2Pager(){
        tabLayout.setupWithViewPager(viewPager);
    }

    public void clean(){
        views=new ArrayList<>();
        titles=new ArrayList<>();
        tabLayout.removeAllTabs();
    }

    //not sure for work
    public void notifyDataSetChanged(){
        this.customPagerAdapter.notifyDataSetChanged();
    }

    public class CustomPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==(arg1);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(final View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));


        }
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
        @Override
        public CharSequence getPageTitle(int position)
        {
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
            this.mDuration=duration;
        }

        public ViewPagerSpeed(Context context){
            super(context);
        }

        public ViewPagerSpeed(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerSpeed(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public static void setSpeed(ViewPager vg, int duration){
            try {
                Field mScroller = null;
                mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                ViewPagerSpeed scroller = new ViewPagerSpeed(vg.getContext(),duration);
                mScroller.set(vg, scroller);
            }catch(NoSuchFieldException e){
            }catch (IllegalArgumentException e){
            }catch (IllegalAccessException e){
            }
        }
    }
}
