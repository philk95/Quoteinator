//package de.die.dudes.quoteinator;
//
//import android.app.Activity;
//import  android.support.v4.app.Fragment;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * Created by Bjoern on 25.06.2016.
// */
//public class Quotes extends FragmentActivity {
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private MockDB db = new MockDB();
//
//   public static Quotes newInstance() {
//      Quotes quotes = new Quotes();
//       return quotes;
//    }
//    @Override
//    public void onStart(){
//    super.onStart();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.recyclerview, container, false);
//
//
//       /// mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
//
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // specify an adapter (see also next example)
//        mAdapter = new RVAdapter(db.getQuotes());
//        mRecyclerView.setAdapter(mAdapter);
//
//        return view;
//    }
//
//
//}
