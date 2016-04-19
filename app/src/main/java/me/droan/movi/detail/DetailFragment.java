package me.droan.movi.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import me.droan.movi.MovieListModel.Result;
import me.droan.movi.R;
import me.droan.movi.utils.Constants;

/**
 * Created by drone on 16/04/16.
 */
public class DetailFragment extends Fragment {
  @Bind(R.id.title) TextView title;
  @Bind(R.id.poster) SimpleDraweeView poster;
  @Bind(R.id.backdrop) SimpleDraweeView backdrop;
  @Bind(R.id.rating) RatingBar rating;
  //@Bind(R.id.description) TextView description;
  @Bind(R.id.release) TextView release;
  @Bind(R.id.castRecycler) RecyclerView castRecycler;
  @Bind(R.id.reviewRecycler) RecyclerView reviewRecycler;
  @Bind(R.id.trailerRecycler) RecyclerView tralerRecycler;
  private Result model;

  public static DetailFragment newInstance(Result model) {
    Bundle args = new Bundle();
    args.putSerializable("KEY", model);
    DetailFragment fragment = new DetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    model = (Result) getArguments().getSerializable("KEY");
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    ButterKnife.bind(this, view);
    tralerRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    castRecycler.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    reviewRecycler.setLayoutManager(new UnscrollableLinearLayoutManager(getActivity()));
    tralerRecycler.setAdapter(new TrailerAdapter(getActivity()));
    castRecycler.setAdapter(new CastAdapter(getActivity()));
    reviewRecycler.setAdapter(new ReviewAdapter(getActivity()));
    title.setText(model.title);
    poster.setImageURI(Uri.parse(Constants.POSTER_BASE + model.poster_path));
    backdrop.setImageURI(Uri.parse(Constants.POSTER_BASE + model.backdrop_path));
    rating.setProgress((int) model.vote_average);
    release.setText(model.release_date);
    //description.setText(model.overview
    //    + model.overview
    //    + model.overview
    //    + model.overview);

    return view;
  }

  private static class UnscrollableLinearLayoutManager extends LinearLayoutManager {
    public UnscrollableLinearLayoutManager(Context context) {
      super(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override public boolean canScrollHorizontally() {
      return false;
    }

    @Override public boolean canScrollVertically() {
      return false;
    }
  }
}