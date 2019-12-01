package com.labijie.stream.memory.configuration

import com.labijie.stream.memory.MemoryMessageChannelBinder
import com.labijie.stream.memory.MemoryProvisioningProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.stream.binder.Binder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
@Configuration
@ConditionalOnMissingBean(Binder::class)
@EnableConfigurationProperties(
    MemoryBinderConfigurationProperties::class,
    MemoryExtendedBindingProperties::class)
class MemoryBinderConfiguration {

    @Bean
    fun memoryMessageChannelBinder(
        configurationProperties: MemoryBinderConfigurationProperties,
        extendedProperties: MemoryExtendedBindingProperties): MemoryMessageChannelBinder {
        val provisioningProvider = MemoryProvisioningProvider()

        return MemoryMessageChannelBinder(
            configurationProperties,
            extendedProperties,
            provisioningProvider)
    }
}