package org.jenkinsci.plugins.mdtool.buildarguments;

import hudson.Extension;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class ProjectArgument extends OptionalArgument {
    @DataBoundConstructor
    public ProjectArgument(String value) {
        super(value);
    }

    @Extension
    public static class DescriptorImpl extends OptionalArgumentDescriptor {
        @Override
        public FormValidation doCheckValue(@QueryParameter String value) {
            if (value.length() == 0) {
                return FormValidation.error("Please specify project");
            }
            return super.doCheckValue(value);
        }
    }
}
