package com.nejitawo.audiohub.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.nejitawo.audiohub.Adapters.TitleAdapter;
import com.nejitawo.audiohub.Util.GlobalClass;
import com.nejitawo.audiohub.Model.Title;
import com.nejitawo.audiohub.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TitleAdapter adapter;
    private List<Title> titleList;
    CircularProgressView progressView;
    List<ParseObject> ob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final GlobalClass globalClass = (GlobalClass)getApplicationContext();
        getSupportActionBar().setTitle(globalClass.getCategory() + " Music");
        progressView = (CircularProgressView)findViewById(R.id.progress_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        titleList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ob=null;
        new launchParseDialog().execute();
    }

    public class launchParseDialog extends AsyncTask<Void, Void, Void> {
        final GlobalClass globalClass = (GlobalClass)  getApplicationContext();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final GlobalClass globalClass = (GlobalClass)getApplicationContext();
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Titles");
            query.orderByDescending("_created_at");
            query.whereEqualTo("doctype","Music");
            query.whereEqualTo("category",globalClass.getCategory());
            query = query.setLimit(1000);

            try{
                ob = query.find();
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

                    Title t = Title.giveFullDetails(menu, getApplicationContext());
                    if (t != null) {
                        titleList.add(t);
                    }



                }
            } catch (Exception ex) {
                // Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

            adapter = new TitleAdapter( getApplicationContext(),titleList);
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
