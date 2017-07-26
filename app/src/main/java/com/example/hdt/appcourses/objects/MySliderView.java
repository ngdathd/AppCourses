package com.example.hdt.appcourses.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.hdt.appcourses.R;

public class MySliderView extends BaseSliderView {

    public MySliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.sliderview_custom, null);
        ImageView target = (ImageView) v.findViewById(R.id.slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
