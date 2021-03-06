package com.fyp.melody.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fyp.melody.activity.MenuDetailsActivity;
import com.fyp.melody.helper.ShoppingCartHelper;
import com.fyp.melody.model.Menus;
import com.fyp.melody.R;
import com.fyp.melody.VolleySingleton;

import java.util.List;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class MenuAdapter extends BaseAdapter {

    SharedPreferences settings;

    private Activity mactivity;
    private List<Menus> MenuList;
    private LayoutInflater mInflater;
    private Menus curMenu;
    private boolean mShowQuantity;

    public MenuAdapter (List<Menus> MenuList, LayoutInflater inflater, boolean showQuantity){
        this.mactivity = mactivity;
        mInflater = inflater;
        this.MenuList = MenuList;
        mShowQuantity = showQuantity;
    }

    @Override
    public int getCount() {
        return MenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return MenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (mInflater == null) {

            mInflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.menu_item, parent, false);

        }

        TextView menuName = (TextView) convertView.findViewById(R.id.Name);
        TextView menuPrice = (TextView) convertView.findViewById(R.id.Price);
        NetworkImageView menuImg = (NetworkImageView) convertView.findViewById(R.id.MenuImage);
        TextView menuQuantity = (TextView) convertView.findViewById(R.id.textViewQuantity);

        curMenu = MenuList.get(position);

        menuName.setText(curMenu.getMenuName());
        menuPrice.setText("RM" + String.format("%.2f", curMenu.getMenuPrice()));
        menuImg.setImageUrl("http://mynetsys.com/restaurant/" + (curMenu.getMenuImage()), VolleySingleton.getInstance().getImageLoader());

        if (mShowQuantity) {
            menuQuantity.setText("Quantity: "
                    + ShoppingCartHelper.getProductQuantity(curMenu));
        } else {
            // Hid the view
            menuQuantity.setVisibility(View.GONE);
        }

        return convertView;
    }

    public Menus getMenus(int position) {

        return MenuList.get(position);
    }

}
