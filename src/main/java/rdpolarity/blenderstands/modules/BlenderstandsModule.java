package rdpolarity.blenderstands.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import rdpolarity.blenderstands.Blenderstand;
import rdpolarity.blenderstands.BlenderstandManager;
import rdpolarity.blenderstands.Blenderstands;

public class BlenderstandsModule extends AbstractModule {

    private final Blenderstands plugin;
    private final BlenderstandManager manager;

    public BlenderstandsModule(Blenderstands plugin, BlenderstandManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(Blenderstands.class).toInstance(this.plugin);
        this.bind(BlenderstandManager.class).toInstance(this.manager);
        this.bind(Blenderstand.class).toInstance(new Blenderstand());
    }
}
