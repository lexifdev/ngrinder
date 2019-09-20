/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.ngrinder.perftest.service;

import org.ngrinder.infra.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;

/**
 * Dynamic creation of {@link PerfTestService} depending on the cluster enable or disable.
 *
 * @since 3.1
 */
@Configuration
@Profile("production")
@RequiredArgsConstructor
public class PerfTestServiceConfig {

	private final Config config;

	private final ApplicationContext applicationContext;

	/**
	 * Create PerTest service depending on cluster mode.
	 *
	 * @return {@link PerfTestService}
	 */
	@Bean(name = "perfTestService")
	public PerfTestService perfTestService() {
		if (config.isClustered()) {
			return applicationContext.getAutowireCapableBeanFactory().createBean(ClusteredPerfTestService.class);
		} else {
			return applicationContext.getAutowireCapableBeanFactory().createBean(PerfTestService.class);
		}
	}
}
