package com.labijie.stream.memory

import com.labijie.stream.memory.configuration.MemoryBinderConfigurationProperties
import com.labijie.stream.memory.configuration.MemoryConsumerProperties
import com.labijie.stream.memory.configuration.MemoryExtendedBindingProperties
import com.labijie.stream.memory.configuration.MemoryProducerProperties
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties
import org.springframework.cloud.stream.binder.ExtendedProducerProperties
import org.springframework.cloud.stream.provisioning.ConsumerDestination
import org.springframework.cloud.stream.provisioning.ProducerDestination
import org.springframework.cloud.stream.provisioning.ProvisioningProvider

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MemoryProvisioningProvider : ProvisioningProvider<
        ExtendedConsumerProperties<MemoryConsumerProperties>,
        ExtendedProducerProperties<MemoryProducerProperties>> {


    override fun provisionProducerDestination(
        name: String?,
        properties: ExtendedProducerProperties<MemoryProducerProperties>
    ): ProducerDestination = object : ProducerDestination {
        override fun getName(): String {
            return name.orEmpty()
        }
        override fun getNameForPartition(partition: Int): String = name.orEmpty()
    }

    override fun provisionConsumerDestination(
        name: String?,
        group: String?,
        properties: ExtendedConsumerProperties<MemoryConsumerProperties>?
    ): ConsumerDestination  = ConsumerDestination { name.orEmpty() }
}