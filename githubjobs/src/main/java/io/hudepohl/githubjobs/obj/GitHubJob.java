package io.hudepohl.githubjobs.obj;

/**
 * Created by trent on 1/11/18.
 */

public class GitHubJob {

    public String id;
    public String created_at;
    public String title;
    public String location;
    public String type;
    public String description;
    public String how_to_apply;
    public String company;
    public String company_url;
    public String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToApply() {
        return how_to_apply;
    }

    public void setHowToApply(String howToApply) {
        this.how_to_apply = howToApply;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyUrl() {
        return company_url;
    }

    public void setCompanyUrl(String companyUrl) {
        this.company_url = companyUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
