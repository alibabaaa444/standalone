package app.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {

    public static TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/"); // Path to templates folder in resources
        templateResolver.setSuffix(".html"); // File extension for templates
        templateResolver.setTemplateMode("HTML"); // Use HTML template mode
        templateResolver.setCharacterEncoding("UTF-8"); // Ensure UTF-8 encoding
        templateResolver.setCacheable(false); // Disable caching for development

        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
}
