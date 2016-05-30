import com.google.inject.AbstractModule;
import conf.PropertiesReader;

public class PlaytestModule extends AbstractModule {

    private final PropertiesReader propertiesReader;

    public PlaytestModule(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    protected void configure() {
        bind(PropertiesReader.class).toInstance(propertiesReader);
    }
}
