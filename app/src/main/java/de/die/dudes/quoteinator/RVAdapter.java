//package de.die.dudes.quoteinator;
//
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by Bjoern on 25.06.2016.
// */
//public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
//    private ArrayList quotes = new ArrayList();
//
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView mTextView;
//
//        public ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public RVAdapter(ArrayList myDataset) {
//        quotes = myDataset;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//       View v = LayoutInflater.from(parent.getContext())
//              //  .inflate(R.layout.cardview, parent, false);
//
//       ViewHolder vh = new ViewHolder((TextView) v);
//       return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.mTextView.setText(quotes.indexOf(position));
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return quotes.size();
//    }
//}