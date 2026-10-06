package br.edu.gestao.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
@Profile("prod")
public class DataSourceConfig {
    @Bean
    DataSource dataSource() {
        // O Render fornece uma URL no formato postgresql://usuario:senha@host:porta/banco.
        // Este método converte esse endereço para o formato JDBC usado pelo Spring.
        URI uri = URI.create(System.getenv("DATABASE_URL"));
        String[] credenciais = uri.getUserInfo().split(":", 2);
        String usuario = URLDecoder.decode(credenciais[0], StandardCharsets.UTF_8);
        String senha = URLDecoder.decode(credenciais[1], StandardCharsets.UTF_8);
        int porta = uri.getPort() == -1 ? 5432 : uri.getPort();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://" + uri.getHost() + ":" + porta + uri.getPath());
        dataSource.setUsername(usuario);
        dataSource.setPassword(senha);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
