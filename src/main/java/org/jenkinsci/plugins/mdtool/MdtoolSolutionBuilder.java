package org.jenkinsci.plugins.mdtool;

import org.jenkinsci.plugins.mdtool.buildarguments.ConfigurationArgument;
import org.jenkinsci.plugins.mdtool.buildarguments.ProjectArgument;
import org.jenkinsci.plugins.mdtool.buildarguments.RuntimeArgument;
import org.jenkinsci.plugins.mdtool.buildarguments.TargetArgument;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class MdtoolSolutionBuilder extends MdtoolBuilder {

    private final String solutionPath;
    private final TargetArgument target;
    private final ConfigurationArgument configuration;
    private final ProjectArgument project;
    private final RuntimeArgument runtime;

    @DataBoundConstructor
    public MdtoolSolutionBuilder(String installationName, String solutionPath, TargetArgument target, ConfigurationArgument configuration, ProjectArgument project, RuntimeArgument runtime) {
        super(installationName);
        this.solutionPath = solutionPath;
        this.target = target;
        this.configuration = configuration;
        this.project = project;
        this.runtime = runtime;
    }

    public String getSolutionPath() {
        return solutionPath;
    }

    public ConfigurationArgument getConfiguration() {
        return configuration;
    }

    public TargetArgument getTarget() {
        return target;
    }

    public ProjectArgument getProject() {
        return project;
    }

    public RuntimeArgument getRuntime() {
        return runtime;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {
        PrintStream log = listener.getLogger();

        int exitCode = launcher.launch()
                .cmds(getExecutable(), getArguments())
                .pwd(build.getWorkspace())
                .envs(build.getEnvironment(listener))
                .stdout(listener)
                .join();

        log.println(String.format("Exit code: %s", exitCode));

        return exitCode == 0;
    }

    private String[] getArguments() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("build");
        if (getTarget() != null) {
            result.add(String.format("--target:%s", getTarget().getValue()));
        }
        if (getConfiguration() != null) {
            result.add(String.format("--configuration:%s", getConfiguration().getValue()));
        }
        if (getProject() != null) {
            result.add(String.format("--project:%s", getProject().getValue()));
        }
        if (getRuntime() != null) {
            result.add(String.format("--runtime:%s", getRuntime().getValue()));
        }
        result.add(getSolutionPath());
        return result.toArray(new String[result.size()]);
    }

    @Extension
    public static final class DescriptorImpl extends MdtoolBuildStepDescriptor<Builder> {
        public DescriptorImpl() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "Build a Xamarin Studio solution using mdtool";
        }

        public FormValidation doCheckSolutionPath(@QueryParameter String value) {
            if (value.length() == 0) {
                return FormValidation.error("Please specify solution file path");
            }
            return FormValidation.ok();
        }
    }
}
