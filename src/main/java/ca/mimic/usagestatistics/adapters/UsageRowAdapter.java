/*
 * Copyright © 2014 Jeff Corcoran
 *
 * This file is part of Hangar.
 *
 * Hangar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hangar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Hangar.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package ca.mimic.usagestatistics.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.mimic.usagestatistics.database.DBUsage;
import ca.mimic.usagestatistics.database.TasksDataSource;
import ca.mimic.usagestatistics.R;
import ca.mimic.usagestatistics.models.TasksModel;
import ca.mimic.usagestatistics.models.UsageDayModel;
import ca.mimic.usagestatistics.models.UsageRowItemModel;
import ca.mimic.usagestatistics.utils.helper.IconHelper;

public class UsageRowAdapter extends BaseAdapter {
    Context mContext;
    List<UsageRowItemModel> mRowItems;
    List<UsageDayModel> mDay;
    IconHelper ih;
    boolean completeRedraw = false;
    List<TasksModel> listTasks;
    DBUsage dbUsage;
    private TasksDataSource db;



    public UsageRowAdapter(Context context, List<UsageRowItemModel> rowItems, List<UsageDayModel> mDay, List<TasksModel> listTasks) {
        mContext = context;
        this.mDay = mDay;
        mRowItems = rowItems;
        this.listTasks = listTasks;
        ih = new IconHelper(context);
        db = TasksDataSource.getInstance(context);
    }

    private class ViewHolder {
        //ImageView taskIcon;
        TextView day;
    }

    protected void reDraw(boolean which) {
        completeRedraw = which;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        db.open();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.usage_row,parent,false);

            holder = new ViewHolder();
            holder.day = (TextView) convertView.findViewById(R.id.day_of_month);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        List<UsageRowItemModel> listTempGrid = new ArrayList<>();
        UsageDayModel usageDay = mDay.get(position);
        String dayTemp = usageDay.getmDay();
        dbUsage = new DBUsage(convertView.getContext(), "Usage.sqlite", null, 1);
        Cursor data = dbUsage.GetData("SELECT TENPK,(SUM(TIME)) as TIME,LASTTIME  FROM USAGE_DAY_US WHERE LASTTIME = '"+dayTemp +"' GROUP BY TENPK ");
        try {
            while (data.moveToNext()) {
                String packedName = data.getString(0);
                long total = data.getLong(1);
                String day = data.getString(2);
                listTempGrid.add(new UsageRowItemModel(packedName, total, day));
            }
        }
        finally {
            data.close();
        }
        for (Iterator<TasksModel> iterator = listTasks.iterator(); iterator.hasNext(); ) {
            int value = iterator.next().getSeconds();
            if (value == 0) {
                iterator.remove();
            }
        }
        if(listTempGrid.size()>0 && listTasks.size()>0) {
            for (int i = 0; i < listTempGrid.size(); i++) {
                if (listTasks.get(position).getPackageName().equals(listTempGrid.get(i).getPackedName())) {
                    TasksModel task = db.getTask(listTempGrid.get(i).getPackedName());
                    if (task.getSeconds() == 0) {
                        listTasks.remove(task);
                    }
                }
            }
        }
        final RecyclerView recyclerView = (RecyclerView) convertView.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mContext,listTempGrid,mDay,listTasks);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));

        recyclerView.setAdapter(recyclerViewAdapter);

        holder.day.setText(usageDay.getmDay());
        return convertView;
    }

    @Override
    public int getCount() {
        return mDay.size();
    }

    @Override
    public Object getItem(int position) {
        return mRowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRowItems.indexOf(getItem(position));
    }


}
