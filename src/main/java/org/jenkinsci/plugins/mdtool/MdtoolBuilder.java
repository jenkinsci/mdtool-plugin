package org.jenkinsci.plugins.mdtool;

import hudson.tasks.Builder;

import java.io.File;

public class MdtoolBuilder extends Builder {

    private final String installationName;

    public MdtoolBuilder(String installationName) {
        this.installationName = installationName;
    }

    public String getInstallationName() {
        return installationName;
    }

    @Override
    public MdtoolBuildStepDescriptor<Builder> getDescriptor() {
        return (MdtoolBuildStepDescriptor<Builder>) super.getDescriptor();
    }

    public File getExecutable() {
        MdtoolInstallation installation = MdtoolInstallation.getInstallationOrDefault(getInstallationName());
        return installation.getExecutable();
    }
}
