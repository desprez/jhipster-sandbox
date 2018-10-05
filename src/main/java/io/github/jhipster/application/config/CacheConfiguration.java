package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Customer.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Customer.class.getName() + ".tasks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Task.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Owner.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Owner.class.getName() + ".pets", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Pet.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Blog.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Entry.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Entry.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Tag.class.getName() + ".entries", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
