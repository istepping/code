package com.xmw.qiyun.ui.adapter.price;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.xmw.qiyun.R;
import com.xmw.qiyun.data.model.net.price.Price;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hongyuan on 2017/9/18.
 */

public class PriceListAdapter extends Adapter<Price> {

    public PriceListAdapter(Context context, @Nullable List<Price> data) {

        super(context, data, R.layout.item_price);
    }

    @Override
    protected void convert(AdapterHelper helper, Price item) {

        helper.getItemView().setTag(item);

        helper.setText(R.id.item_title, item.getStartPoint() + " --> " + item.getEndPoint());

        switch (item.getTruckingType()){

            default:
                break;

            case 1:{
                ((TextView)helper.getView(R.id.item_location)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.price_common, 0, 0, 0);
            }
            break;

            case 2:{
                ((TextView)helper.getView(R.id.item_location)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.price_common, 0, 0, 0);
            }
            break;

            case 3:{
                ((TextView)helper.getView(R.id.item_location)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.price_common, 0, 0, 0);
            }
            break;

            case 4:{
                ((TextView)helper.getView(R.id.item_location)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.price_out, 0, 0, 0);
            }
            break;

            case 5:{
                ((TextView)helper.getView(R.id.item_location)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.price_out, 0, 0, 0);
            }
            break;
        }

        helper.setText(R.id.item_location, item.getStatusName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date = dateFormat.parse(item.getPriceDate());

            helper.setText(R.id.item_calendar, dateFormat.format(date));

        } catch (ParseException e) {

            e.printStackTrace();
        }

        helper.setText(R.id.item_money, "¥" + item.getFreight());
        helper.setText(R.id.item_count, String.valueOf(item.getChain()));
    }
}
