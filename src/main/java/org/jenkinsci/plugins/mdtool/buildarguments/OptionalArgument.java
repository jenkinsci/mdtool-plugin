package org.jenkinsci.plugins.mdtool.buildarguments;

import hudson.model.AbstractDescribableImpl;

public abstract class OptionalArgument extends AbstractDescribableImpl<OptionalArgument> {

    private final String value;

    public OptionalArgument(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
