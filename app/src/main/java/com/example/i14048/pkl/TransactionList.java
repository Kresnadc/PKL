package com.example.i14048.pkl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kresn on 2/25/2017.
 */

public class TransactionList extends ArrayAdapter {
    private int productId[];
    private String productName[];
    private int basePrice[];
    private int sellPrice[];
    private LayoutInflater inflater;

    public TransactionList(LayoutInflater inflater, Context context, int[] productId, String[] productName, int[] basePrice, int[] sellPrice, String[] productIdStr) {
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
        nameKatalogRow.setText((position+1) + ". " + productName[position]);
        return view;
    }
}
