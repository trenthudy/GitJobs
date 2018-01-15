package io.hudepohl.gitjobs.data.githubJobs.model

data class GitHubJob(

    var id: String,
    var created_at: String,
    var title: String,
    var location: String,
    var type: String,
    var description: String,
    var how_to_apply: String,
    var company: String,
    var company_url: String,
    var company_logo: String,
    var url: String

)
