package org.jenkinsci.plugins.mdtool;

import hudson.Extension;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolProperty;
import hudson.util.FormValidation;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.annotation.CheckForNull;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MdtoolInstallation extends ToolInstallation {
    @DataBoundConstructor
    public MdtoolInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    public File getExecutable() {
        return new File(getHome());
    }

    public static MdtoolInstallation getInstallationOrDefault(String name) {
        for(MdtoolInstallation installation : getUserInstallations()) {
            if(installation.getName().equals(name)) {
                return installation;
            }
        }
        return getDefaultInstallation();
    }

    public static MdtoolInstallation[] getUserInstallations() {
        DescriptorImpl descriptor = Jenkins.getInstance().getDescriptorByType(MdtoolInstallation.DescriptorImpl.class);
        return descriptor.getInstallations();
    }

    private static MdtoolInstallation getDefaultInstallation() {
        return new MdtoolInstallation(null, "/Applications/Xamarin Studio.app/Contents/MacOS/mdtool", null);
    }

    @Extension
    public static class DescriptorImpl extends ToolDescriptor<MdtoolInstallation> {

        public DescriptorImpl() {
            super();
            load();
        }

        @Override
        public String getDisplayName() {
            return "mdtool";
        }

        public FormValidation doCheckHome(@QueryParameter File value) {
            Jenkins.getInstance().checkPermission(Jenkins.ADMINISTER);
            String path = value.getPath();

            return FormValidation.validateExecutable(path);
        }
    }
}
