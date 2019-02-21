package com.myubeo.retrofitdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<Item> mItems;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTv;
        public Button btn_true;
        public Button btn_false;
        PostItemListener mItemListener;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.txt_text);
            btn_true = itemView.findViewById(R.id.btn_true);
            btn_false = itemView.findViewById(R.id.btn_false);

            titleTv.setOnClickListener(this);

            btn_true.setOnClickListener(this);

            btn_false.setOnClickListener(this);

        }

        public void PostItemListener(PostItemListener postItemListener){
            this.mItemListener = postItemListener;
        }

        @Override
        public void onClick(View view) {
            mItemListener.onPostClick(view, getAdapterPosition(), false);
            Item item1 = getItem(getAdapterPosition());
            switch (view.getId()) {
                case R.id.btn_true:
                    Toast.makeText(view.getContext(), "Đúng rồi " + item1.getAnswerId(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.btn_false:
                    Toast.makeText(view.getContext(), "Sai rồi " + item1.getQuestionId(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        }
    }

    public AnswersAdapter(Context context, List<Item> posts) {
        mItems = posts;
        mContext = context;
    }

    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View postView = inflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AnswersAdapter.ViewHolder holder, int position) {
        final Item item = mItems.get(position);
        TextView textView = holder.titleTv;
        textView.setText(item.getOwner().getDisplayName());

        if(item.isShow()){
            holder.btn_true.setVisibility(View.VISIBLE);
            holder.btn_false.setVisibility(View.VISIBLE);
        }else {
            holder.btn_true.setVisibility(View.GONE);
            holder.btn_false.setVisibility(View.GONE);
        }

        holder.PostItemListener(new PostItemListener() {
            @Override
            public void onPostClick(View view, int position, boolean isLongClick) {
                if(item.isShow()){
                    item.setShow(false);
                }else {
                    item.setShow(true);
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(View view, int position,boolean isLongClick);
    }
}
