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
import io.hudepohl.githubjobs.obj.GitHubJob;
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

        mPresenter = new MainPresenter(this);
        mPresenter.init();
    }

    @Override
    public void initializeJobList(List<GitHubJob> jobs) {

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

    private class GitHubJobAdaptor extends ArrayAdapter<GitHubJob> {

        ImageView mCompanyLogo;
        TextView mCompanyNameText, mPositionTitleText, mPositionLocationText;

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

            // TODO: Refactor this to use ButterKnife.
            mCompanyLogo = view.findViewById(R.id.job_list_item_image);
            mCompanyNameText = view.findViewById(R.id.job_list_item_company_name_tv);
            mPositionTitleText = view.findViewById(R.id.job_list_item_position_title_tv);
            mPositionLocationText = view.findViewById(R.id.job_list_item_position_location_tv);

            final GitHubJob currentJob = mJobList.get(position);

            Glide
                    .with(mCompanyLogo.getContext())
                    .load(currentJob.getCompanyLogo())
                    .into(mCompanyLogo);

            mCompanyNameText.setText(currentJob.getCompany());
            mPositionTitleText.setText(currentJob.getTitle());
            mPositionLocationText.setText(currentJob.getLocation());

            return view;
        }

    }

}
