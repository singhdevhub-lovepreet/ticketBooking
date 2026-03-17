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
public class LibrariesForLibsInPluginsBlock extends AbstractExternalDependencyFactory {

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'owner'.
    private final AbstractExternalDependencyFactory owner = this;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'vaccForVersionAccessors'.
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'baccForBundleAccessors'.
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive variable name instead of 'paccForPluginAccessors'.
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a null check for the 'config' parameter.
    public LibrariesForLibsInPluginsBlock(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for guava (com.google.guava:guava)
     * with versionRef 'guava'.
         * This dependency was declared in catalog libs.versions.toml
     * @deprecated Will be removed in Gradle 9.0.
         */
// 💡 [RYFT REVIEW - SUGGESTION] The deprecation message is repeated. Consider extracting it into a separate method.
    @Deprecated
        public Provider<MinimalExternalModuleDependency> getGuava() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a null check for the 'config' parameter.
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
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public BundleAccessors getBundles() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
// 💡 [RYFT REVIEW - SUGGESTION] The deprecation message is repeated. Consider extracting it into a separate method.
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

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: guava (32.1.2-jre)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a null check for the 'config' parameter.
            public Provider<String> getGuava() { return getVersion("guava"); }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
// 💡 [RYFT REVIEW - SUGGESTION] The deprecation message is repeated. Consider extracting it into a separate method.
    public static class BundleAccessors extends BundleFactory {

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive class name instead of 'BundleAccessors'.
        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive class name instead of 'PluginAccessors'.
    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
