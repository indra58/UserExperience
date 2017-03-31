package com.greencomputingnepal.userexperience;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencomputingnepal.userexperience.model.DataItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 3/26/2017.
 */

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public static final String ITEM_KEY = "item_key";
    private List<DataItem> mItems;
    private Context mContext;

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);

        onSharedPreferenceChangeListener  = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.i("preferences", "preference  "+ key);
            }
        };
        settings.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

        boolean grid = settings.getBoolean(mContext.getString(R.string.preference_display_grid), false);

        int layoutId = grid ? R.layout.grid_list : R.layout.grid_list;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        holder.name_product.setText(item.getItemName());
        holder.image_product.setImageResource(R.mipmap.ic_launcher_round);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Details.class);
                intent.putExtra(ITEM_KEY, item);
                mContext.startActivity(intent);

                /*View check = LayoutInflater.from(mContext).inflate(R.layout.check, null, false);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setView(check);

                final AlertDialog dialog = alertDialog.create();
                dialog.show();

                Button btnOk = ButterKnife.findById(check, R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(mContext, item.getItemName() , Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

        /*holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View check = LayoutInflater.from(mContext).inflate(R.layout.check, null, false);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setView(check);

                final AlertDialog dialog = alertDialog.create();
                dialog.show();

                Button btnOk = ButterKnife.findById(check, R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(mContext, item.getItemName() , Toast.LENGTH_SHORT).show();
                    }
                });
*/
            /*    btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(mContext, item.getItemName(), Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_image)
        ImageView image_product;
        @BindView(R.id.product_name)
        TextView name_product;

        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view = itemView;
        }
    }
}