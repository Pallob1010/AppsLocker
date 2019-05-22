package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

import expandablerecyclerview.AppsViewHolder;
import expandablerecyclerview.Apps;
import expandablerecyclerview.AppsType;
import expandablerecyclerview.AppsTypeViewHolder;
import spark.loop.appslocker.R;

public class AppsTypeAdapter extends ExpandableRecyclerViewAdapter<AppsTypeViewHolder, AppsViewHolder> {



    public AppsTypeAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }


    @Override
    public AppsTypeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appscategories,parent,false);
        view.setEnabled(true);
        return new AppsTypeViewHolder(view);
    }

    @Override
    public AppsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleappsview,parent,false);
        return new AppsViewHolder(view,parent.getContext());
    }

    @Override
    public void onBindChildViewHolder(AppsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Apps apps=(Apps)group.getItems().get(childIndex);
        holder.Bind(apps);

    }

    @Override
    public void onBindGroupViewHolder(AppsTypeViewHolder holder, int flatPosition, ExpandableGroup group) {

        final AppsType appsType=(AppsType)group;
        holder.Bind(appsType);
    }

}
