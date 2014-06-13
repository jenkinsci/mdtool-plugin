package org.jenkinsci.plugins.mdtool;

import hudson.model.AbstractProject;
import hudson.model.Describable;
import hudson.tasks.BuildStep;
import hudson.tasks.BuildStepDescriptor;
import hudson.util.ListBoxModel;

public abstract class MdtoolBuildStepDescriptor<T extends BuildStep & Describable<T>> extends BuildStepDescriptor<T> {
    @Override
    public boolean isApplicable(Class<? extends AbstractProject> jobType) {
        return true;
    }

    public ListBoxModel doFillInstallationNameItems() {
        ListBoxModel result = new ListBoxModel();
        result.add("(Default)", null);
        for (MdtoolInstallation installation : MdtoolInstallation.getUserInstallations()) {
            result.add(installation.getName());
        }
        return result;
    }
}
