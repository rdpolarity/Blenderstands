package rdpolarity.blenderstands.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import rdpolarity.blenderstands.BlenderstandFactory;
import rdpolarity.blenderstands.Blenderstands;

public class BlenderstandsModule extends AbstractModule {

    private final Blenderstands plugin;

    public BlenderstandsModule(Blenderstands plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(Blenderstands.class).toInstance(this.plugin);
        install(new FactoryModuleBuilder().build(BlenderstandFactory.class));
    }
}
