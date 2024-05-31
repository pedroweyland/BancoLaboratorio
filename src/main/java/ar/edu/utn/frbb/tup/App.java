package ar.edu.utn.frbb.tup;

import ar.edu.utn.frbb.tup.service.MenuBanco;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);


        MenuBanco processor = applicationContext.getBean(MenuBanco.class);

        processor.menuInicio();

    }

}
