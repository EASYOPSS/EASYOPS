package com.tsoap.sat.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsoap.sat.easyops.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 17/10/15.
 */
public class NavigationViewHolder extends  RecyclerView.ViewHolder {

    @Bind(R.id.navigation_item_image_icon)
    ImageView imageIcon;

    @Bind(R.id.navigation_item_text_view)
    TextView navigationTextView;



    @Bind(R.id.item_layout)
    LinearLayout itemLayout;



    @Bind(R.id.nav_list_saperator)
    LinearLayout navListSaperator;

    public NavigationViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public ImageView getNavigationImageIcon() {
        return imageIcon;
    }


    public TextView getNevigationTextView() {
        return navigationTextView;
    }

    public LinearLayout getNavListSaperator() {
        return navListSaperator;
    }

    public LinearLayout getItemLayout() {
        return itemLayout;
    }

}
