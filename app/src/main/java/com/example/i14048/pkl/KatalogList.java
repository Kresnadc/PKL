package com.example.i14048.pkl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by kresn on 2/22/2017.
 */

public class KatalogList extends ArrayAdapter {
    private int productId[];
    private String productName[];
    private int basePrice[];
    private int sellPrice[];
    private LayoutInflater inflater;

    public KatalogList(LayoutInflater inflater, Context context, int[] productId, String[] productName, int[] basePrice, int[] sellPrice, String[] productIdStr) {
        super(context, R.layout.katalog_list, productIdStr);
        this.productId = productId;
        this.productName = productName;
        this.basePrice = basePrice;
        this.sellPrice = sellPrice;
        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(R.layout.katalog_list, null, true);
        TextView nameKatalogRow = (TextView) view.findViewById(R.id.nameKatalogList);
        nameKatalogRow.setText((position+1) + ". " + productName[position] + " sell Price : " + sellPrice[position]);
        return view;
    }

}
