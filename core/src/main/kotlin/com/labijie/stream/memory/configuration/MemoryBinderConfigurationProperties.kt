package com.labijie.stream.memory.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-28
 */
@ConfigurationProperties(prefix = "spring.cloud.stream.memory.binder")
data class MemoryBinderConfigurationProperties(
  var workerPoolSize: Int = -1,
  var queueSize: Int = 2048
)