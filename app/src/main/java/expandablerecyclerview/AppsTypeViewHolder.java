package expandablerecyclerview;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import spark.loop.appslocker.R;

public class AppsTypeViewHolder extends GroupViewHolder {

    private TextView appsType;
    public AppsTypeViewHolder(View itemView) {
        super(itemView);
        appsType=itemView.findViewById(R.id.appstypetextview);


    }
    public void Bind(ExpandableGroup title){
        appsType.setText(title.getTitle());
    }

    @Override
    public void expand() {
        super.expand();
    }
}
