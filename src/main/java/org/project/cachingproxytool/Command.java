package org.project.cachingproxytool;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@Configuration
@CommandLine.Command(name = "caching-proxy", description = "Command to start caching server")
public class Command implements Runnable {

    @Option(names = {"-p", "--port"}, required = true, defaultValue = "8080", description = "Port for server to run")
    private int port;

    @Option(names = {"-o", "--origin"}, required = true, description = "Remote destination URL")
    private String origin;

    @Override
    public void run() {

        System.setProperty("server.port", String.valueOf(port));
        System.setProperty("caching-proxy.origin", origin);

        SpringApplication app = new SpringApplication(Command.class);
        app.run();

    }
}
