package org.prajwal.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
/*
JPA Auditing means automatically keeping track of certain information about an entity's lifecycle.
and @EnableJpaAuditing annotation :
"Turns automatic auditing ON."
 */
@Configuration
public class JpaConfig {
}
