package org.jenkinsci.plugins.mdtool.buildarguments;

import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.util.Arrays;

public class TargetArgument extends OptionalArgument {
    @DataBoundConstructor
    public TargetArgument(String value) {
        super(value);
    }

    @Extension
    public static class DescriptorImpl extends OptionalArgumentDescriptor {
        private static final String[] VALUES = new String[] {
                "Build",
                "Clean",
                "SignAndroidPackage"
        };

        public String getDefaultValue() {
            return VALUES[0];
        }

        public ListBoxModel doFillValueItems() {
            ListBoxModel result = new ListBoxModel();
            for (String value : VALUES) {
                result.add(value);
            }
            return result;
        };

        @Override
        public FormValidation doCheckValue(@QueryParameter String value) {
            if (!Arrays.asList(VALUES).contains(value)) {
                return FormValidation.error("Please specify valid target");
            }
            return super.doCheckValue(value);
        }
    }
}
