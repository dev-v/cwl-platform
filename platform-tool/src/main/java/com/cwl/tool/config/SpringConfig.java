package com.cwl.tool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring")
@Data
public class SpringConfig {
    private final Profiles profiles = new Profiles();

    private final RedisConfig redis = new RedisConfig();

    @Data
    public static class Profiles {
        private String[] active;

        public boolean isProduct() {
            return "product".equals(active[0]);
        }
    }
}
