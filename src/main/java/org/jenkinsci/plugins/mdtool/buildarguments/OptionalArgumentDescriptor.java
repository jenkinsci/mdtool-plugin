package org.jenkinsci.plugins.mdtool.buildarguments;

import hudson.model.Descriptor;
import hudson.util.FormValidation;
import org.kohsuke.stapler.QueryParameter;


public class OptionalArgumentDescriptor extends Descriptor<OptionalArgument> {
    @Override
    public String getDisplayName() {
        return null;
    }

    public FormValidation doCheckValue(@QueryParameter String value) {
        return FormValidation.ok();
    }
}
