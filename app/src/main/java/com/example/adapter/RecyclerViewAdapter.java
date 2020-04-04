package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gu.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mLists;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<Integer> mHeights = new ArrayList<>();
    private Boolean isFall;
    public RecyclerViewAdapter(Context context, List<String> lists, boolean isFall) {
        this.mLists = lists;
        this.mContext = context;
        this.isFall = isFall;
        for (int i=0; i< mLists.size(); i++) {
            mHeights.add((int) (80 + Math.random() * 300));
        }
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_item,
                parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String str = mLists.get(position);
        ViewGroup.LayoutParams lp = holder.tvItem.getLayoutParams();
        // 瀑布流所需
//        if (isFall) {
//            lp.height = mHeights.get(position);
//            holder.tvItem.setLayoutParams(lp);
//        } else {
//            lp.height = 50;
//            holder.tvItem.setLayoutParams(lp);
//        }

        holder.tvItem.setText(str);
        if (mOnItemClickListener != null) {
            holder.tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });
//
//            holder.tvItem.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    int pos = holder.getLayoutPosition();
//                    mOnItemClickListener.onItemLongClick(v, pos);
//                    return  false;
//                }
//            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        public ViewHolder(View view) {
            super(view);
            tvItem = view.findViewById(R.id.tv_item);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        //void onItemLongClick(View view, int position);
    }
}
