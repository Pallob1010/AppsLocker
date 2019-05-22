package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Adapters.AppsTypeAdapter;
import LockSavingRemoving.SharedPreference;
import expandablerecyclerview.Apps;
import expandablerecyclerview.AppsType;
import spark.loop.appslocker.R;

@SuppressLint("ValidFragment")
public class AllApps extends Fragment {
    View view;
    RecyclerView recyclerView;
    AppsTypeAdapter adapter;
    Context context;
    Apps apps;
    public AllApps(Context context,Apps apps) {
        this.context = context;
        this.apps=apps;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_apps, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewofApps);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ArrayList<AppsType> appsTypes = new ArrayList<>();
        ArrayList<Apps> systemApps =apps.systemAppsCaller();
        ArrayList<Apps> installedApps =apps.installedAppsCaller();
        appsTypes.add(new AppsType("System Apps",systemApps));
        appsTypes.add(new AppsType("Installed Apps",installedApps));
        adapter = new AppsTypeAdapter(appsTypes);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        for (int i = adapter.getGroups().size() - 1; i >= 0; i--) {
            expandGroup(i);
        }

        return view;
    }

    public void expandGroup(int gPos) {
        if (adapter.isGroupExpanded(gPos)) {
            return;
        }
        adapter.toggleGroup(gPos);
    }

}
