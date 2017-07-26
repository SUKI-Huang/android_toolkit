package com.holiestar.toolkit.component.tool.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Suki on 7/26/2017.
 */

public abstract class BaseView {
    private Context context;
    private View view;
    private Integer layoutResourceId;

    private boolean initialized = false;

    abstract void initUI(View v);

    abstract void initUILayout(View v);

    abstract void initAction(View v);

    abstract void onViewReady(View v);

    abstract void onResume();

    abstract void onPause();

    abstract void onDestroy();

    public BaseView(Context context) {
        this.context = context;
    }

    public void setContentView(View view) {
        this.view = view;
    }

    public void setContentView(Integer viewResourceId) {
        this.layoutResourceId = viewResourceId;
    }

    public synchronized View getView() {
        if (initialized) return view;

        boolean hasResourceId = layoutResourceId != null;
        boolean hasView = view != null;
        if (!hasResourceId && !hasView) throw new RuntimeException("Here has no view and resource id");
        if (!hasView) view = LayoutInflater.from(context).inflate(layoutResourceId, null);
        initUI(view);
        initUILayout(view);
        initAction(view);
        onViewReady(view);
        initialized = true;
        return view;
    }

    public void setOnResume(){
        onResume();
    }
    public void setOnPause(){
        onPause();
    }
    public void setOnDestroy(){
        onDestroy();
    }

}