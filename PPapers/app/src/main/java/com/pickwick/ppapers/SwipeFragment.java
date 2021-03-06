package com.pickwick.ppapers;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.AppBarLayout;

public class SwipeFragment extends Fragment {

    TextView bookName;
    TextView excerpt;
    ImageView bookImage;
    private BottomNavigationView navigation;
    boolean animateBottom = false;

    public SwipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.individual_excerpt_card_gradient, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (animateBottom){
            navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
            navigation.setVisibility(View.GONE);
            navigation.animate().translationY(navigation.getHeight() * 2).setStartDelay(0).setDuration(10).start();
        }


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        bookName = view.findViewById(R.id.book_name);
        excerpt  = view.findViewById(R.id.excerpt_content);
        bookImage = view.findViewById(R.id.book_image);

        Bundle bundle = getArguments();
        String bookNameText = bundle.get("bookName").toString();
        String excerptContent = bundle.get("excerptText").toString();
        byte[] imageBlob = bundle.getByteArray("imageBlob");

        bookName.setText(bookNameText);
        excerpt.setText(excerptContent);
        bookImage.setImageBitmap(BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length));

        if(animateBottom){
            NestedScrollView nested_content = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
            nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < oldScrollY) { // up
                        animateNavigation(true);
                    }
                    if (scrollY > oldScrollY) { // down
                        animateNavigation(false);
                    }
                }
            });
        }

        return view;
    }

    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        int height = navigation.getHeight() * 2;
        navigation.setVisibility(View.VISIBLE);
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * navigation.getHeight()) : 0;

        navigation.animate().translationY(moveY).setStartDelay(0).setDuration(1000).start();
    }
}
