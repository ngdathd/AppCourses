package com.example.hdt.appcourses.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hdt.appcourses.R;
import com.example.hdt.appcourses.activities.WebActivity;
import com.example.hdt.appcourses.common.Constants;
import com.example.hdt.appcourses.objects.ItemCourseDetail;
import com.example.hdt.appcourses.util.Util;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class RcItemCourseAdapter extends RecyclerView.Adapter
        implements View.OnClickListener, Constants {

    private ICourseAdapter iCourseAdapter;
    private Context context;

    public RcItemCourseAdapter(ICourseAdapter iCourseAdapter, Context context) {
        this.iCourseAdapter = iCourseAdapter;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_course, parent, false);
        return new ViewHolderItemCourses(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemCourseDetail itemCourseDetail = iCourseAdapter.getItemCourseDetail(position);
        ViewHolderItemCourses viewHolderItemCourses = (ViewHolderItemCourses) holder;
        viewHolderItemCourses.textView.setText(itemCourseDetail.getTitle());
        Picasso.with(context).load(itemCourseDetail.getBanner())
                .transform(new CropSquareTransformation())
                .transform(new RoundedCornersTransformation(26, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(viewHolderItemCourses.imageView);
        viewHolderItemCourses.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isConnected(context)) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra(KEY_URL, itemCourseDetail.getUrl());
                    context.startActivity(intent);
                } else {
                    Snackbar.make(v.getRootView().findViewById(android.R.id.content), "Bạn đang offline", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return iCourseAdapter.getItemCount();
    }

    @Override
    public void onClick(View v) {
        IGetPosition getPosition = (IGetPosition) v.getTag();
        iCourseAdapter.onClickItem(getPosition.getPosition());
    }

    public interface ICourseAdapter {
        void onClickItem(int position);

        int getItemCount();

        ItemCourseDetail getItemCourseDetail(int position);
    }

    private interface IGetPosition {
        int getPosition();
    }

    private class ViewHolderItemCourses extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        ViewHolderItemCourses(View itemView, View.OnClickListener onClick) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_course);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            IGetPosition getPosition = new IGetPosition() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }
            };
            itemView.setTag(getPosition);
            itemView.setOnClickListener(onClick);
        }
    }
}
