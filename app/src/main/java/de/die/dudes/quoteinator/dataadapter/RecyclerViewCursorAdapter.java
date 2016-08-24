package de.die.dudes.quoteinator.dataadapter;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * RecyclerView CursorAdapter
 * <p>
 * Created by Simon on 28/02/2016.
 */
public abstract class RecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<VH> {
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIDColumn;
    private ClickListener listener;
    private LongClickListener longClickListener;


    public RecyclerViewCursorAdapter(Cursor cursor) {
        setHasStableIds(true);
        swapCursor(cursor);
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract View onBindViewHolder(VH holder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View view = onBindViewHolder(holder, mCursor);
        final int id = mCursor.getInt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(id);
                }
            }
        });
        view.setLongClickable(true);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(id);
                }

                return true;
            }
        });


    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIDColumn);
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    protected Cursor getCursor() {
        return mCursor;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        if (oldCursor != null) {
            if (mDataSetObserver != null) {
                oldCursor.unregisterDataSetObserver(mDataSetObserver);
            }
        }
        mCursor = newCursor;
        if (newCursor != null) {
            if (mDataSetObserver != null) {
                newCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIDColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }


    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            mDataValid = false;
            notifyDataSetChanged();
        }
    };

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface ClickListener {
        void onClick(int id);
    }

    public interface LongClickListener {
        void onClick(int id);
    }
}