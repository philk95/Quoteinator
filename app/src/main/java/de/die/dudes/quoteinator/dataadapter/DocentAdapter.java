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
public class DocentAdapter extends RecyclerViewCursorAdapter<DocentAdapter.ViewHolder> {

    private Cursor cursor;

    public DocentAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public DocentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_docent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected View onBindViewHolder(DocentAdapter.ViewHolder holder, Cursor cursor) {
        String name = cursor.getString(1);

        holder.setName(name);

        return holder.getView();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_docent);
            name = (TextView) cv.findViewById(R.id.cv_docent_name);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public View getView() {
            return cv;
        }
    }
}
