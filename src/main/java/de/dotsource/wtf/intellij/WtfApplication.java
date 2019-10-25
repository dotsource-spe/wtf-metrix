package de.dotsource.wtf.intellij;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageExtensionPoint;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.PluginId;
import de.dotsource.wtf.intellij.editor.WtfExternalAnnotator;
import org.jetbrains.annotations.NotNull;

public class WtfApplication implements BaseComponent {
    private IdeaPluginDescriptor plugin;

    @Override
    public void initComponent() {
        plugin = PluginManager.getPlugin(PluginId.getId("de.dotsource.wtf.intellij"));
        Language.getRegisteredLanguages().forEach(this::registerExternalAnnotatorFor);
    }

    private void registerExternalAnnotatorFor(final Language language) {
        LanguageExtensionPoint<WtfExternalAnnotator> extensionPoint = new LanguageExtensionPoint<>();
        extensionPoint.language = language.getID();
        extensionPoint.implementationClass = WtfExternalAnnotator.class.getName();
        extensionPoint.setPluginDescriptor(plugin);
        getExternalAnnotatorExtensionPoint().registerExtension(extensionPoint);
    }

    @NotNull
    private ExtensionPoint<Object> getExternalAnnotatorExtensionPoint() {
        return Extensions.getRootArea().getExtensionPoint("com.intellij.externalAnnotator");
    }
}
