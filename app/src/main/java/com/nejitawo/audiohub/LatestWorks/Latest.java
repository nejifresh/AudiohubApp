package com.nejitawo.audiohub.LatestWorks;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.nejitawo.audiohub.Adapters.TitleAdapter;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Title;
import com.nejitawo.audiohub.R;
import com.nejitawo.audiohub.Util.RunQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Latest extends Fragment implements LastestContract {


    private RecyclerView recyclerView;
    private TitleAdapter adapter;
    private List<Title> titleList;
    CircularProgressView progressView;
    List<ParseObject> ob;
    LastestPresenter presenter;
    RunQuery query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.menu_content, container, false);

        presenter = new LastestPresenter(this);

        progressView = (CircularProgressView)rootView.findViewById(R.id.progress_view);

        recyclerView = (RecyclerView)rootView. findViewById(R.id.recycler_view);

        titleList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ob=null;
        presenter.showLatestItems();

        return rootView;
    }

    @Override
    public void loadLatestWorks() {

        new LoadItems().execute();

    }

    @Override
    public ParseQuery<ParseObject> runQuery(String className, String orderBy, int limit) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                className);
        query.orderByDescending(orderBy);
        query = query.setLimit(limit);
        return query;
    }


    class LoadItems extends AsyncTask<Void, Void, Void> {
        final GlobalClass globalClass = (GlobalClass) getActivity(). getApplicationContext();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //load available downloads
            ParseQuery<ParseObject> downQuery = new ParseQuery<ParseObject>("Player");
            downQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            downQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null & objects.size()>0){
                        for (ParseObject ob: objects){
                            globalClass.setDownloads(ob.getInt("downloads"));
                        }


                    }
                }
            });

            //Use instance of RunQuery class created

           // query = new RunQuery("Titles","_created_at", 20);


            //Run the query using the presenter instance, this will enable us test the result
            try{
                ob = presenter.queryRunning("Titles","_created_at", 20).find();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            titleList.clear();
            try {
                for (ParseObject menu : ob) {

                    Title t = Title.giveFullDetails(menu, getActivity(). getApplicationContext());
                    if (t != null) {
                        titleList.add(t);
                    }

                    // adapter.notifyDataSetChanged();
                    // mAdapter.notifyDataSetChanged();

                }
            } catch (Exception ex) {
                // Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

            adapter = new TitleAdapter(getActivity(). getApplicationContext(),titleList);
            recyclerView.setAdapter(adapter);
            progressView.setVisibility(View.INVISIBLE);
            progressView.stopAnimation();


        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
