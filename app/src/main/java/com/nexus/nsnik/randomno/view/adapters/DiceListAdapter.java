/*
 * Copyright 2017 nsnikhil
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.nexus.nsnik.randomno.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nexus.nsnik.randomno.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiceListAdapter extends RecyclerView.Adapter<DiceListAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Integer> mDiceList;

    public DiceListAdapter(Context context, List<Integer> diceList) {
        mContext = context;
        mDiceList = diceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_dice_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        setDiceImage(mDiceList.get(position), holder);
    }

    private void setDiceImage(final int value, final MyViewHolder holder) {
        switch (value) {
            case 1:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_one));
                break;
            case 2:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_two));
                break;
            case 3:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_three));
                break;
            case 4:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_four));
                break;
            case 5:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_five));
                break;
            case 6:
                holder.mDice.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.dice_six));
                break;
            default:
                throw new IllegalArgumentException("Invalid no : " + value);
        }
    }

    @Override
    public int getItemCount() {
        return mDiceList != null ? mDiceList.size() : 0;
    }

    public void modifyList(List<Integer> newList) {
        mDiceList = newList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.singleDiceItem)
        ImageView mDice;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
