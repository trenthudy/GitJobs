package io.hudepohl.gitjobs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.hudepohl.githubjobs.obj.GitHubJob;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter mPresenter;

    @BindView(R.id.pagination_loading_layout) RelativeLayout mPageLoadingDialog;

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
