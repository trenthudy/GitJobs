package io.hudepohl.gitjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        mPresenter.init();
    }


    }

    @Override
    public void initializeJobList(List<GitHubJob> jobs) {

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
                getString(R.string.lbl_load_error_text),
                Toast.LENGTH_LONG
        ).show();
    }

    private void goToJobDetails(GitHubJob job) {

    }

}
