package com.labijie.stream.memory.queue

import org.springframework.messaging.Message
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
object MemoryListenerContainer {
    private val listeners = ConcurrentHashMap<String, MutableSet<Consumer<Message<*>>>>()

    fun findListener(dest: String): Set<Consumer<Message<*>>> {
        return listeners[dest] ?: setOf()
    }

    fun registerListener(dest:String, consumer: Consumer<Message<*>>){
         val set = this.listeners.getOrPut(dest){
            mutableSetOf()
        }
        set.add(consumer)
    }

    fun unregisterListener(dest:String){
        this.listeners.remove(dest)
    }
}