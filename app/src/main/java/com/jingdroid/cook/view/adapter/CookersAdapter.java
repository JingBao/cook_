package com.jingdroid.cook.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Jing on 2017/8/21.
 */

public class CookersAdapter extends BaseAdapter {

    private Context context;
    private List<AuthorEntityModel> list;

    public CookersAdapter (Context context, List<AuthorEntityModel> list) {
        this.context = context;
        this.list = list;
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cooker_author_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.ivHeadimg = (RoundedImageView) convertView.findViewById(R.id.iv_cookers_headimg);
            viewHolder.tvHeadname = (TextView) convertView.findViewById(R.id.tv_headname);
            viewHolder.tvHeadsign = (TextView) convertView.findViewById(R.id.tv_headsign);
            viewHolder.ivCookersAdd = (ImageView) convertView.findViewById(R.id.iv_cookers_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!list.get(position).isAdd()) {
            viewHolder.ivCookersAdd.setImageResource(R.mipmap.ic_add_black_36dp);
        } else {
            viewHolder.ivCookersAdd.setImageResource(R.mipmap.check_alt_32x32);
        }

        viewHolder.ivCookersAdd.setOnClickListener(new AddOnClickListener(viewHolder.ivCookersAdd, position));
        return convertView;
    }

    public class ViewHolder {
        RoundedImageView ivHeadimg;
        TextView tvHeadname;
        TextView tvHeadsign;
        ImageView ivCookersAdd;
    }
    boolean isAdd = false;
    public class AddOnClickListener implements View.OnClickListener {

        ImageView addImg;
        int index;
        public AddOnClickListener(ImageView ivCookersAdd, int index) {
            this.addImg = ivCookersAdd;
            this.index = index;
        }
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (!isAdd) {
                Toast.makeText(context, "添加厨娘成功", Toast.LENGTH_SHORT).show();
                this.addImg.setImageResource(R.mipmap.check_alt_32x32);
                isAdd = true;
                list.get(index).setAdd(true);
            } else {
                this.addImg.setImageResource(R.mipmap.ic_add_black_36dp);
                isAdd = false;
                list.get(index).setAdd(false);
            }
        }
    }
}
