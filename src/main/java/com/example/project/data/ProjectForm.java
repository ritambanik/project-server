package com.example.project.data;

public class ProjectForm {

    private String name;
    private boolean active;

    public ProjectForm() {
        this(createProjectFormBuilder());
    }

    public ProjectForm(Builder builder) {
        this.name = builder.name;
        this.active = builder.active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }


    public static class Builder {
        private String name;
        private boolean active;

        public Builder name (String name) {
            this.name = name;
            return this;
        }

        public Builder active (boolean active) {
            this.active = active;
            return this;
        }

        public ProjectForm build() {
            return new ProjectForm(this);
        }

    }
    public static Builder createProjectFormBuilder() {
        return new Builder();
    }
}
