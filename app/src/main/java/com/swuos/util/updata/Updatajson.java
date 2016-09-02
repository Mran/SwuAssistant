package com.swuos.util.updata;

public class Updatajson implements java.io.Serializable {
    private static final long serialVersionUID = -7953694177221578813L;
    private String versionShort;
    private String direct_install_url;
    private String install_url;
    private int updated_at;
    private String build;
    private UpdatajsonBinary binary;
    private String name;
    private String update_url;
    private String changelog;
    private String version;
    private String installUrl;

    public String getVersionShort() {
        return this.versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getDirect_install_url() {
        return this.direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getInstall_url() {
        return this.install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public int getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public UpdatajsonBinary getBinary() {
        return this.binary;
    }

    public void setBinary(UpdatajsonBinary binary) {
        this.binary = binary;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_url() {
        return this.update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public String getChangelog() {
        return this.changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInstallUrl() {
        return this.installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }
}
