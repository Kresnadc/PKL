package com.example.i14048.pkl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kresn on 2/26/2017.
 */

public class RecapList extends ArrayAdapter {
    private int productId[];
    private String productName[];
    private int basePrice[];
    private int sellPrice[];
    private int quantity[];
    private LayoutInflater inflater;

    public RecapList(LayoutInflater inflater, Context context, int[] productId, String[] productName, int[] basePrice, int[] sellPrice, String[] productIdStr, int[] quantity) {
        super(context, R.layout.recap_list, productIdStr);
        this.productId = productId;
        this.productName = productName;
        this.basePrice = basePrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(R.layout.recap_list, null, true);
        TextView nameKatalogRow = (TextView) view.findViewById(R.id.productDescriptionTV);
        TextView priceKatalogRow = (TextView) view.findViewById(R.id.productPriceTV);
        int pricePerRow = quantity[position] * sellPrice[position];
        nameKatalogRow.setText((position+1) + ". " + productName[position]+"  " + quantity[position] +"x" +sellPrice[position]);
        priceKatalogRow.setText(pricePerRow+"");
        return view;
    }

    public int totalHarga(){
        int res = 0;
        for (int i = 0; i < quantity.length; i++) {
            res+= quantity[i]*sellPrice[i];
        }
        return res;
    }
}
