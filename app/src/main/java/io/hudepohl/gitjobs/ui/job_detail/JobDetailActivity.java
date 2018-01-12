package io.hudepohl.gitjobs.ui.job_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.hudepohl.github_jobs.data.api.model.GitHubJob;
import io.hudepohl.gitjobs.R;
import io.hudepohl.gitjobs.ui.BaseActivity;
import io.hudepohl.gitjobs.util.ConstKey;

/**
 * Created by trent on 1/12/18.
 */

public class JobDetailActivity extends BaseActivity implements JobDetailPresenter.View {

    private JobDetailPresenter mPresenter;

    @BindView(R.id.job_detail_image) ImageView mJobDetailImageView;
    @BindView(R.id.job_detail_company_name_tv) TextView mJobDetailCompanyTextView;
    @BindView(R.id.job_detail_position_title_tv) TextView mJobDetailPositionTitleTextView;
    @BindView(R.id.job_detail_position_location_tv) TextView mJobDetailPositionLocationTextView;
    @BindView(R.id.job_detail_desc_tv) TextView mJobDetailDescriptionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        mPresenter = new JobDetailPresenter(this);
        String foundId = getIntent().getExtras().getString(ConstKey.JOB_ID);
        Log.i(JobDetailActivity.class.getSimpleName(), foundId);
        mPresenter.getJobInfo(foundId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void configureJobInfo(GitHubJob job) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(job.getTitle());
        }

        Glide
                .with(mJobDetailImageView.getContext())
                .load(job.getCompany_logo())
                .into(mJobDetailImageView);

        mJobDetailCompanyTextView.setText(job.getCompany());
        mJobDetailPositionTitleTextView.setText(job.getTitle());
        mJobDetailPositionLocationTextView.setText(job.getLocation());

        mJobDetailDescriptionTextView.setText(
                Html.fromHtml(job.getDescription())
        );
    }

    @Override
    public void showFetchJobInfoError() {
        Toast.makeText(
                this.getApplicationContext(),
                getString(R.string.err_failed_to_load_job_details),
                Toast.LENGTH_SHORT
        ).show();
    }

}
