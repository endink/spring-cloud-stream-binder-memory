package com.labijie.stream.memory

import com.labijie.stream.memory.configuration.MemoryBinderConfigurationProperties
import com.labijie.stream.memory.configuration.MemoryProducerProperties
import com.labijie.stream.memory.queue.MemoryMessageQueue
import org.springframework.cloud.stream.binder.ExtendedProducerProperties
import org.springframework.cloud.stream.provisioning.ProducerDestination
import org.springframework.context.Lifecycle
import org.springframework.integration.handler.AbstractMessageHandler
import org.springframework.messaging.Message

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MemoryMessageHandler(
    private val configurationProperties: MemoryBinderConfigurationProperties,
    private val destination: ProducerDestination,
    private val producerProperties: ExtendedProducerProperties<MemoryProducerProperties>
) : AbstractMessageHandler(), Lifecycle {

    @Volatile
    private var started = false

    override fun handleMessageInternal(message: Message<*>?) {
        if(message != null) {
            MemoryMessageQueue.Default.produce(this.destination.name, message)
        }
    }

    override fun isRunning(): Boolean = started

    override fun start() {
        if(!started){
            started = true
            MemoryMessageQueue.Default.start(
                configurationProperties.queueSize,
                configurationProperties.workerPoolSize)
        }
    }

    override fun stop() {
        if(started){
            started = false
            MemoryMessageQueue.Default.shutdown()
        }
    }
}