package br.com.notesrelease;

import br.com.notesrelease.componentes.ComercialUserDetailsService;
import br.com.notesrelease.filters.JWTAuthenticationFilter;
import br.com.notesrelease.filters.TokenAuthenticationService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.notesrelease.Util.Util.API_ROUTE;

/**
 * Created by deinf.mhvaz on 08/08/2018.
 */

@Configuration
@EnableWebSecurity
@EnableSwagger2
@ComponentScan(value = {"com.notesrelease.*"})
public class AppConfiguration implements WebMvcConfigurer {

    //Be careful while changing here
    private static final String additionalPorts = "9000";
    private static final String[] pagesAuthorizes  = {
            "/index.html",
            "/js/**",
            "/css/**",
            "/favicon.ico",
            "/html/**",
            "/fonts/**",
            "/assets/**",
            "/resources/**",
            "/api/v2/api-docs",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v2/**",
            "/swagger-resources/**",
            "/",
            "/authenticate",
            "/configuration/**",
            "/api/comum/**"
    };

    private static Logger logger = LoggerFactory.getLogger(AppConfiguration.class);


    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    @Profile({"PROD", "DEV"})
    public DataSource dataSource() {
        logger.info("configurando banco de producao.");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }


    @Bean
    @Profile("DEV")
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(additionalConnector());
        logger.info("Adicionada a porta 9000.");
        return tomcat;
    }


    private Connector[] additionalConnector() {
        String[] ports = additionalPorts.split(",");
        List<Connector> result = new ArrayList<>();
        for (String port : ports) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(port));
            result.add(connector);
        }
        return result.toArray(new Connector[] {});
    }


    @Configuration
    @Order(SecurityProperties.DEFAULT_FILTER_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final ComercialUserDetailsService comercialUserDetailsService;

        private TokenAuthenticationService tokenAuthenticationService;

        private JWTAuthenticationFilter authenticationFilter;

        public SecurityConfiguration(ComercialUserDetailsService comercialUserDetailsService,
                                     TokenAuthenticationService tokenAuthenticationService,
                                     JWTAuthenticationFilter authenticationFilter) {
            this.comercialUserDetailsService = comercialUserDetailsService;
            this.tokenAuthenticationService = tokenAuthenticationService;
            this.authenticationFilter = authenticationFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            /*
            http.csrf().disable().authorizeRequests()
                    //abaixo representa o que pode ser acessado por qualquer pessoa
                    .antMatchers(pagesAuthorizes).permitAll()
                    .antMatchers(HttpMethod.POST, API_ROUTE + "login").permitAll()
                    .antMatchers(API_ROUTE + Util.COMUM + Util.PATTERN_TUDO_A_FRENTE).permitAll()
                    .antMatchers(API_ROUTE + Util.PATTERN_TUDO_A_FRENTE).permitAll()
                    // abaixo representa o que necessita autenticação para acesso
                    .antMatchers(API_ROUTE + Util.RESTRITO + Util.PATTERN_TUDO_A_FRENTE).hasAnyRole(Util.ROLE_ADMIN, Util.ROLE_USUARIO, Util.ROLE_PRODUTOR, Util.ROLE_GESTOR)
                    .antMatchers(API_ROUTE + Util.ADMIN + Util.PATTERN_TUDO_A_FRENTE).hasRole(Util.ROLE_ADMIN)
                    .antMatchers(API_ROUTE + Util.GESTAO + Util.PATTERN_TUDO_A_FRENTE).hasAnyRole(Util.ROLE_ADMIN, Util.ROLE_GESTOR)
                    .antMatchers(API_ROUTE + Util.USUARIO + Util.PATTERN_TUDO_A_FRENTE).hasRole(Util.ROLE_USUARIO)
                    .anyRequest().authenticated()
                    .and()
                    // filtra requisições de login
                    .addFilterBefore(new JWTLoginFilter(API_ROUTE + "login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                    // filtra outras requisições para verificar a presença do JWT no header
                    .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

             */

            http.csrf().disable()
            // Não cheque essas requisições
            .authorizeRequests().antMatchers(pagesAuthorizes).permitAll()
            .antMatchers(HttpMethod.POST, API_ROUTE + "authenticate").permitAll().
            // Qualquer outra requisição deve ser checada
            anyRequest().authenticated().and()
            .exceptionHandling().authenticationEntryPoint(tokenAuthenticationService).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        }

        @Override
        public void configure(AuthenticationManagerBuilder builder) throws Exception {
            builder
                    .userDetailsService(comercialUserDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());

        }

        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }


    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public class CorsFilter implements Filter {

        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            final HttpServletResponse response = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", origin != null && origin.contains("ws") ? "" : origin );
            response.setHeader("Vary", "Origin");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Authorization, Content-Type, Accept, X-CSRF-TOKEN");
            response.setHeader("Access-Control-Max-Age", "3600");
            if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                chain.doFilter(req, res);
            }
        }

        public void destroy() {
        }

        public void init(FilterConfig config) {
        }
    }


}
