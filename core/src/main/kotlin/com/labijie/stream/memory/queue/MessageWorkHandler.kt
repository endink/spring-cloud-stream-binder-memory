package com.labijie.stream.memory.queue

import org.slf4j.LoggerFactory
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MessageWorkHandler(private val queue:BlockingQueue<QueueItem>): Runnable {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MemoryMessageQueue::class.java)
    }

    private var isRunning = false

    fun stop(){
        isRunning = false;
    }

    override fun run() {
        isRunning = true
        while (isRunning){
            val item = queue.poll(5, TimeUnit.SECONDS)
            if(item != null){
                try {
                    val consumers = MemoryListenerContainer.findListener(item.destination)
                    consumers.forEach {
                        it.accept(item.message)
                    }
                }catch (e: Throwable){
                    LOGGER.error("Memory cloud stream handle fault.", e)
                    if(e is VirtualMachineError){
                        this.stop()
                    }
                }
            }else{
                Thread.sleep(100)
            }
        }
    }
}