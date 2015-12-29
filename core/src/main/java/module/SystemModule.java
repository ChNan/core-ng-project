package module;

/**
 * @author ChNan
 */
public class SystemModule implements Module {

    private final String propertyFileName;

    public SystemModule(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }

    @Override
    public void initialized() {
        System.out.println("System module initialized, propertyFileName is " + propertyFileName);
    }
}
