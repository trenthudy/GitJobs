package io.hudepohl.gitjobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.hudepohl.github_jobs.data.api.model.GitHubJob;
import io.hudepohl.gitjobs.util.EndlessScrollListener;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter mPresenter;

    private List<GitHubJob> mJobList;

    @BindView(R.id.job_list_refresh_layout) SwipeRefreshLayout mJobListRefreshLayout;
    @BindView(R.id.pagination_loading_layout) RelativeLayout mPageLoadingDialog;
    @BindView(R.id.lv_job_list) ListView mJobListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.toolbar_title));

        mPresenter = new MainPresenter(this);

        mJobListRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        mPresenter.init();
    }

    @Override
    public void initializeJobList(List<GitHubJob> jobs) {
        if (mJobListRefreshLayout.isRefreshing()) {
            mJobListRefreshLayout.setRefreshing(false);
        }

        mJobList = new ArrayList<>();
        mJobList.addAll(jobs);

        mJobListView.setAdapter(new GitHubJobAdaptor());
        mJobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        mJobListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                mPresenter.getNextPage();
                return true;
            }
        });
    }

    @Override
    public void addJobsToList(List<GitHubJob> jobs) {
        mJobList.addAll(jobs);
        ((ArrayAdapter) mJobListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void showPageLoadingDialog() {
        mPageLoadingDialog.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePageLoadingDialog() {
        mPageLoadingDialog.setVisibility(View.GONE);
    }

    @Override
    public void showGetJobListError() {
        Toast.makeText(
                getApplicationContext(),
                getString(R.string.err_failed_to_load),
                Toast.LENGTH_LONG
        ).show();
    }

    private void moveToJobDetails(GitHubJob job) {

    }

    class GitHubJobAdaptor extends ArrayAdapter<GitHubJob> {

        @BindView(R.id.job_list_item_image) ImageView mCompanyLogo;
        @BindView(R.id.job_list_item_company_name_tv) TextView mCompanyNameText;
        @BindView(R.id.job_list_item_position_title_tv) TextView mPositionTitleText;
        @BindView(R.id.job_list_item_position_location_tv) TextView mPositionLocationText;

        GitHubJobAdaptor() {
            super(MainActivity.this, R.layout.job_list_item, mJobList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.job_list_item, parent, false);
            }

            ButterKnife.bind(this, view);

            final GitHubJob currentJob = mJobList.get(position);

            Glide
                    .with(mCompanyLogo.getContext())
                    .load(currentJob.getCompany_logo())
                    .into(mCompanyLogo);

            mCompanyNameText.setText(currentJob.getCompany());
            mPositionTitleText.setText(currentJob.getTitle());
            mPositionLocationText.setText(currentJob.getLocation());

            return view;
        }

    }

}
