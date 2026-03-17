package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
 */
@NonNullApi
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the @NonNullApi annotation does.
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

// 💡 [RYFT REVIEW - SUGGESTION] This field is not used anywhere in the class. Consider removing it.
    private final AbstractExternalDependencyFactory owner = this;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'vaccForVersionAccessors'.
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'baccForBundleAccessors'.
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'paccForPluginAccessors'.
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the @Inject annotation does.
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for guava (com.google.guava:guava)
     * with versionRef 'guava'.
         * This dependency was declared in catalog libs.versions.toml
         */
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the 'create' method does.
        public Provider<MinimalExternalModuleDependency> getGuava() {
            return create("guava");
    }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive class name instead of 'VersionAccessors'.
    public static class VersionAccessors extends VersionFactory  {
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the 'VersionAccessors' class does.

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: guava (32.1.2-jre)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the 'getVersion' method does.
            public Provider<String> getGuava() { return getVersion("guava"); }

    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive class name instead of 'BundleAccessors'.
    public static class BundleAccessors extends BundleFactory {

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the 'BundleAccessors' class does.
        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive class name instead of 'PluginAccessors'.
    public static class PluginAccessors extends PluginFactory {

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a description of what the 'PluginAccessors' class does.
        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
