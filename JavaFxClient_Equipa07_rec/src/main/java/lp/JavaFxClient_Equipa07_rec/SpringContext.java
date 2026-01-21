package lp.JavaFxClient_Equipa07_rec;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringContext {

    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext ctx) {
        context = ctx;
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
