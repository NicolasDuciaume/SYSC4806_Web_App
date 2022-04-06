package SYSC6.Project;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
<<<<<<< HEAD
        registry.addResourceHandler("/css/**", "/Javascript/**", "/Images/**", "datatable/**", "jquery/**")
                .addResourceLocations("classpath:/css/", "classpath:/Javascript/", "classpath:/Images/", "classpath:/Datatable/", "classpath:/jquery/");
=======
        registry.addResourceHandler("/css/**", "/Javascript/**", "/Images/**")
                .addResourceLocations("classpath:/css/", "classpath:/Javascript/", "classpath:/Images/");
>>>>>>> 35-delete-user-portal
    }

}
