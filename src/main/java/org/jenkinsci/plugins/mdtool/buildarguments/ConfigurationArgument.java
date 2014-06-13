package org.jenkinsci.plugins.mdtool.buildarguments;

import hudson.Extension;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class ConfigurationArgument extends OptionalArgument {
    @DataBoundConstructor
    public ConfigurationArgument(String value) {
        super(value);
    }

    @Extension
    public static class DescriptorImpl extends OptionalArgumentDescriptor {
        @Override
        public FormValidation doCheckValue(@QueryParameter String value) {
            if (value.length() == 0) {
                return FormValidation.error("Please specify configuration");
            }
            return super.doCheckValue(value);
        }
    }
}
