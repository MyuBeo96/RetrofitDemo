package com.myubeo.retrofitdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PhoneListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<CompanyModel> companyModels;

    public PhoneListAdapter(Context context, List<CompanyModel> companyModels) {
        this.context = context;
        this.companyModels = companyModels;
    }

    @Override
    public int getGroupCount() {
        return companyModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return companyModels.get(groupPosition).getDevices().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return companyModels.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return companyModels.get(groupPosition).getDevices().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String companyName = ((CompanyModel) getGroup(groupPosition)).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_company_list, null);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.company_name);
        tvName.setTypeface(null);
        tvName.setText(companyName);

        ImageView img_icon = convertView.findViewById(R.id.img_icon);

        if(isExpanded){
            img_icon.setImageResource(R.drawable.ic_up);
        }else {
            img_icon.setImageResource(R.drawable.ic_down);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String deviceName = ((DeviceModel) getChild(groupPosition, childPosition)).getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_device_list, null);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.device_name);
        tvName.setText(deviceName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,  " Child " + deviceName, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
