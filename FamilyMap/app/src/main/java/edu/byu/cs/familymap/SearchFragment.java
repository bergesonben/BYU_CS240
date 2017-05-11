package edu.byu.cs.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import edu.byu.cs.familymap.Model.Event;
import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Person;

/**
 * Created by User on 8/6/2016.
 */
public class SearchFragment extends Fragment {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static final String TAG = "SearchActivity";
    private static final int ICON_SIZE = 20;

    private FamilyMapData mData;
    private RecyclerView mSearchRecyclerView;
    private SearchView mSearchView;
    private SearchResultAdapter mAdapter;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           REACTIVE METHODS
//**************************************************************************************************
//region REACTIVE METHODS

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mData = FamilyMapData.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchView = (SearchView) v.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
        mSearchRecyclerView = (RecyclerView) v.findViewById(R.id.search_result_recycler_view);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.blank_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//endregion REACTIVE METHODS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS

    private void search(String s) {
        List<Object> results = mData.searchFor(s);

        mAdapter = new SearchResultAdapter(results);
        mSearchRecyclerView.setAdapter(mAdapter);
    }

//endregion PRIVATE METHODS

//**************************************************************************************************
//                                           PRIVATE CLASSES
//**************************************************************************************************
//region PRIVATE CLASSES

    private class SearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mImageView;
        private TextView mDetailsTopTextView;
        private TextView mDetailsBottomTextView;
        private Object mObject;

        public SearchResultHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mImageView = (ImageView) itemView.findViewById(R.id.search_result_icon_view);
            mDetailsTopTextView =
                    (TextView) itemView.findViewById(R.id.search_result_details_top_text_view);
            mDetailsBottomTextView =
                    (TextView) itemView.findViewById(R.id.search_result_details_bottom_text_view);
        }

        public void bindResult(Object object) {
            mObject = object;
            if (object instanceof Event) {
                Event event = (Event) object;

                Drawable markerIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_map_marker)
                    .colorRes(R.color.marker_icon_color).sizeDp(ICON_SIZE);
                mImageView.setImageDrawable(markerIcon);

                mDetailsTopTextView.setText(event.getDetails());

                Person person = mData.getPerson(event.getPersonId());
                mDetailsBottomTextView.setText(person.getFullName());
            } else {
                Person person = (Person) object;
                Person.Gender gender = person.getGender();
                if (gender == Person.Gender.MALE) {
                    Drawable maleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male)
                            .colorRes(R.color.male).sizeDp(ICON_SIZE);
                    mImageView.setImageDrawable(maleIcon);
                } else {
                    Drawable femaleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female)
                            .colorRes(R.color.female).sizeDp(ICON_SIZE);
                    mImageView.setImageDrawable(femaleIcon);
                }

                mDetailsTopTextView.setText(person.getFullName());

                mDetailsBottomTextView.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            if (mObject instanceof  Event) {
                Intent intent = MapActivity.newIntent(getActivity(), ((Event) mObject).getEventId());
                startActivity(intent);
            } else {
                Intent intent = PersonActivity.newIntent(getActivity(), ((Person) mObject).getId());
                startActivity(intent);
            }
        }
    }

    private class SearchResultAdapter extends RecyclerView.Adapter<SearchResultHolder> {

        private List<Object> mSearchResults;

        public SearchResultAdapter(List<Object> searchResults) {
            mSearchResults = searchResults;
        }

        @Override
        public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_search, parent, false);
            return new SearchResultHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchResultHolder holder, int position) {
            Object obj = mSearchResults.get(position);
            holder.bindResult(obj);
        }

        @Override
        public int getItemCount() {
            return mSearchResults.size();
        }
    }

//endregion PRIVATE CLASSES
}
