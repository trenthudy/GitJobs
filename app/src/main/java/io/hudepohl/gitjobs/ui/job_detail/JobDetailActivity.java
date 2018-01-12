package io.hudepohl.gitjobs.ui.job_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import io.hudepohl.github_jobs.data.api.model.GitHubJob;
import io.hudepohl.gitjobs.R;
import io.hudepohl.gitjobs.ui.BaseActivity;
import io.hudepohl.gitjobs.util.ConstKey;

/**
 * Created by trent on 1/12/18.
 */

public class JobDetailActivity extends BaseActivity implements JobDetailPresenter.View {

    private JobDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        mPresenter = new JobDetailPresenter(this);
        String foundId = getIntent().getExtras().getString(ConstKey.JOB_ID);
        Log.i(JobDetailActivity.class.getSimpleName(), foundId);
        mPresenter.getJobInfo(foundId);
    }

    @Override
    public void configureJobInfo(GitHubJob job) {
        Toast.makeText(this.getApplicationContext(), job.getLocation(), Toast.LENGTH_SHORT).show();
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
