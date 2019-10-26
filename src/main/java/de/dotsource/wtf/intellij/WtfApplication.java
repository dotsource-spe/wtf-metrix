package de.dotsource.wtf.intellij;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageExtensionPoint;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.PluginId;
import de.dotsource.wtf.intellij.editor.WtfExternalAnnotator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Plugin-Initialization.
 */
public class WtfApplication implements BaseComponent {
    private static final Logger LOG = LoggerFactory.getLogger(WtfApplication.class);
    private IdeaPluginDescriptor plugin;
    private ConcurrentMap<String, LanguageExtensionPoint> annotatorsByLanguage;

    @Override
    public void initComponent() {
        annotatorsByLanguage = new ConcurrentHashMap<>();
        plugin = PluginManager.getPlugin(PluginId.getId("de.dotsource.wtf.intellij"));
        Language.getRegisteredLanguages().forEach(this::registerExternalAnnotatorFor);

        deregisterRemovedLanguages();
    }

    private void registerExternalAnnotatorFor(final Language language) {

        LanguageExtensionPoint<WtfExternalAnnotator> extensionPoint = new LanguageExtensionPoint<>();
        extensionPoint.language = language.getID();
        extensionPoint.implementationClass = WtfExternalAnnotator.class.getName();
        extensionPoint.setPluginDescriptor(plugin);
        getExternalAnnotatorExtensionPoint().registerExtension(extensionPoint);

        annotatorsByLanguage.put(language.getID(), extensionPoint);
    }

    @NotNull
    private ExtensionPoint<Object> getExternalAnnotatorExtensionPoint() {
        return Extensions.getRootArea().getExtensionPoint("com.intellij.externalAnnotator");
    }

    private void deregisterRemovedLanguages() {
        Set<String> toRemove = annotatorsByLanguage.keySet()
                .stream()
                .filter(id -> Language.findLanguageByID(id) == null)
                .collect(Collectors.toSet());

        toRemove.forEach(l -> {
            getExternalAnnotatorExtensionPoint().unregisterExtension(annotatorsByLanguage.get(l));
            annotatorsByLanguage.remove(l);
        });
    }
}
