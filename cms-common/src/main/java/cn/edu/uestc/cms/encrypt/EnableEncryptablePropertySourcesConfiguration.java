package cn.edu.uestc.cms.encrypt;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@Configuration
public class EnableEncryptablePropertySourcesConfiguration implements EnvironmentAware {

	private ConfigurableEnvironment environment;

	@Bean
	public EnableEncryptablePropertySourcesPostProcessor enableEncryptablePropertySourcesPostProcessor() {
		return new EnableEncryptablePropertySourcesPostProcessor(environment);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

}
