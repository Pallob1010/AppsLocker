package expandablerecyclerview;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class AppsType extends ExpandableGroup<Apps> {
    public AppsType(String title, List<Apps> items) {
        super(title, items);
    }



}
