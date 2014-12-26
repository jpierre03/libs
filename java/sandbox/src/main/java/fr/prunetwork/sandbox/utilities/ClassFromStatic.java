package fr.prunetwork.sandbox.utilities;

/**
 * Get a Stacktrace an display some information.
 *
 * @author http://www.rgagnon.com/javadetails/java-0402.html
 */
public final class ClassFromStatic {

    private ClassFromStatic() {
    }

    public static void main(String[] args) {
        someStaticMethod();
    }

    public static void someStaticMethod() {
        System.out.println("I'm in " + new CurrentClassGetter().getClassName() + " class");
    }

    public static class CurrentClassGetter extends SecurityManager {
        public String getClassName() {
            return getClassContext()[1].getName();
        }
    }
}
