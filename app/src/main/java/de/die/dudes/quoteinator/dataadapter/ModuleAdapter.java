package de.die.dudes.quoteinator.dataadapter;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.die.dudes.quoteinator.R;

/**
 * Created by Phil on 19.08.2016.
 */
public class ModuleAdapter extends RecyclerViewCursorAdapter<ModuleAdapter.ViewHolder> {

    private Cursor cursor;

    public ModuleAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_module, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected View onBindViewHolder(ModuleAdapter.ViewHolder holder, Cursor cursor) {
        String moduleName = cursor.getString(1);
        String docentName = cursor.getString(2);

        holder.setModuleName(moduleName);
        holder.setDocentName(docentName);

        return holder.getView();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView moduleName;
        private TextView docentName;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_module);
            moduleName = (TextView) cv.findViewById(R.id.cv_module_name);
            docentName = (TextView) cv.findViewById(R.id.cv_module_docent_name);
        }


        public void setModuleName(String name) {
            this.moduleName.setText(name);
        }

        public View getView() {
            return cv;
        }

        public void setDocentName(String docentName) {
            this.docentName.setText(docentName);
        }
    }
}
