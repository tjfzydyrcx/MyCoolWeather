package com.example.mygobang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018-01-30 0030.
 */
public class BouncingMenu {
    private static View rootView;
    private final ViewGroup mParentVG;

    public BouncingMenu(View view, int resid) {
        //渲染布局
        rootView = LayoutInflater.from(view.getContext()).inflate(resid, null, false);
        rootView.findViewById(R.id.textView);
       mParentVG= findSuitableParent( view);
    }

    private ViewGroup findSuitableParent(View view) {
        do {
         if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return null;
    }


    public static BouncingMenu makeMenu(View view, int resid) {

        return new BouncingMenu(view, resid);
    }

    public  void show(){
        //添加View
        if (rootView.getParent()!=null){
            mParentVG.removeView(rootView);
        }
        ViewGroup.LayoutParams lparams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mParentVG.addView(rootView,lparams);
        //开启特效
       //biew.   show();

    }
}
