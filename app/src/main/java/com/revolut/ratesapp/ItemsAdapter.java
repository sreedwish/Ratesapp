package com.revolut.ratesapp;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.revolut.ratesapp.models.BeanRate;
import com.revolut.ratesapp.dagger.GlideApp;
import com.revolut.ratesapp.databinding.RecyclerItemRateBinding;
import com.revolut.ratesapp.utils.Logger;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private static final String TAG = "~~ItemsAdapter";

    Context context;
    List<BeanRate> rateList;
    ItemClickListener listener;

    LayoutInflater inflater;

    public ItemsAdapter(Context context, List<BeanRate> rateList, ItemClickListener listener) {
        this.context = context;
        this.rateList = rateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }

        RecyclerItemRateBinding binding = DataBindingUtil.inflate(inflater, R.layout.recycler_item_rate,
                parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final BeanRate item = rateList.get(position);



        try {
            holder.binding.setDat(item);

            //formatting with comma seperator
            String rate = "" + item.getC_value();

            try {
                if (item.getC_value() > 100 ) {
                    rate = NumberFormat.getNumberInstance(Locale.US).format(item.getC_value());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.binding.edtRate.setText(rate);

            //Flag image loading
            GlideApp.with(context)
                    .load(item.getC_flag())
                    .circleCrop()
                    .placeholder(R.drawable.grey_circle)
                    .into(holder.binding.imgFlag);

            holder.binding.edtRate.setInputType(InputType.TYPE_NULL);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onClick(position, item);

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return rateList == null ? 0 : rateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerItemRateBinding binding;

        public ViewHolder(@NonNull RecyclerItemRateBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
