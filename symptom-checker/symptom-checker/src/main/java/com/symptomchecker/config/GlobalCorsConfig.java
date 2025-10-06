@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedOrigins("https://symptom-checker3456734.netlify.app")
          .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
          .allowedHeaders("*")
          .allowCredentials(true);
      }
    };
  }
}
