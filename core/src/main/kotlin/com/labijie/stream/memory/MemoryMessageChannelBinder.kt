package com.labijie.stream.memory

import com.labijie.stream.memory.configuration.MemoryBinderConfigurationProperties
import com.labijie.stream.memory.configuration.MemoryConsumerProperties
import com.labijie.stream.memory.configuration.MemoryExtendedBindingProperties
import com.labijie.stream.memory.configuration.MemoryProducerProperties
import org.springframework.cloud.stream.binder.*
import org.springframework.cloud.stream.provisioning.ConsumerDestination
import org.springframework.cloud.stream.provisioning.ProducerDestination
import org.springframework.integration.core.MessageProducer
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MemoryMessageChannelBinder(
    private val configurationProperties: MemoryBinderConfigurationProperties,
    private val extendedBindingProperties: MemoryExtendedBindingProperties,
    provisioningProvider: MemoryProvisioningProvider) :
    AbstractMessageChannelBinder<
            ExtendedConsumerProperties<MemoryConsumerProperties>,
            ExtendedProducerProperties<MemoryProducerProperties>,
            MemoryProvisioningProvider>(arrayOf(), provisioningProvider),
    ExtendedPropertiesBinder<MessageChannel, MemoryConsumerProperties, MemoryProducerProperties>{

    override fun getExtendedConsumerProperties(channelName: String): MemoryConsumerProperties {
        return this.extendedBindingProperties.getExtendedConsumerProperties(channelName)
    }

    override fun getExtendedProducerProperties(channelName: String): MemoryProducerProperties {
        return this.extendedBindingProperties.getExtendedProducerProperties(channelName)
    }


    override fun getDefaultsPrefix(): String {
        return this.extendedBindingProperties.defaultsPrefix
    }

    override fun getExtendedPropertiesEntryClass(): Class<out BinderSpecificPropertiesProvider> {
        return this.extendedBindingProperties.extendedPropertiesEntryClass
    }


    override fun createConsumerEndpoint(
        destination: ConsumerDestination,
        group: String?,
        properties: ExtendedConsumerProperties<MemoryConsumerProperties>?
    ): MessageProducer {
        return MemoryConsumerEndpoint(
            destination,
            group.orEmpty(),
            properties ?: ExtendedConsumerProperties(MemoryConsumerProperties())
        )
    }

    override fun createProducerMessageHandler(
        destination: ProducerDestination,
        producerProperties: ExtendedProducerProperties<MemoryProducerProperties>?,
        errorChannel: MessageChannel?
    ): MessageHandler {
        return MemoryMessageHandler(
            configurationProperties,
            destination,
            producerProperties ?: ExtendedProducerProperties(MemoryProducerProperties())
        )
    }
}