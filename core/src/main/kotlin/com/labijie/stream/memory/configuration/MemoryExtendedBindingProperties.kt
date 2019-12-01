package com.labijie.stream.memory.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.stream.binder.AbstractExtendedBindingProperties
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider


/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
@ConfigurationProperties("spring.cloud.stream.memory")
class MemoryExtendedBindingProperties :
    AbstractExtendedBindingProperties<MemoryConsumerProperties, MemoryProducerProperties, MemoryBindingProperties>() {

    companion object {
        const val DEFAULTS_PREFIX = "spring.cloud.stream.memory.default"

    }

    override fun getDefaultsPrefix(): String  = DEFAULTS_PREFIX

    override fun getExtendedPropertiesEntryClass(): Class<out BinderSpecificPropertiesProvider> {
       return MemoryBindingProperties::class.java
    }
}