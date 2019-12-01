package com.labijie.stream.memory.configuration

import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MemoryBindingProperties(
    private var consumer: MemoryConsumerProperties = MemoryConsumerProperties(),
    private var producer:MemoryProducerProperties = MemoryProducerProperties()
) : BinderSpecificPropertiesProvider {

    fun setConsumer(consumerProperties: MemoryConsumerProperties){
        this.consumer = consumerProperties
    }

    fun setProducer(producerProperties: MemoryProducerProperties){
        this.producer = producerProperties
    }

    override fun getConsumer(): Any {
        return this.consumer
    }

    override fun getProducer(): Any {
        return this.producer
    }


}