package com.labijie.stream.memory.queue

import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-27
 */
class MemoryMessageQueue {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MemoryMessageQueue::class.java)
        private val index: AtomicLong = AtomicLong()
        private val threadFactory = ThreadFactory {
            Thread(it).apply {
                this.isDaemon = true
                this.name = "stream-memory-queue-poll-${index.incrementAndGet()}"
            }
        }

        val Default: MemoryMessageQueue by lazy {
            MemoryMessageQueue()
        }
    }


    private var startingSync = Any();

    var isRunning: Boolean = false
        private set

    private var queue: LinkedBlockingQueue<QueueItem>? = null
    private var handlers: List<MessageWorkHandler>? = null

    fun start(queueSize: Int, workerPoolSize: Int? = null) {
        if (!isRunning) {
            synchronized(startingSync) {
                if (!isRunning) {
                    val defaultSize = workerPoolSize ?: -1
                    val concurrentLevel =
                        if (defaultSize <= 0)
                            (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
                        else
                            defaultSize

                    queue = LinkedBlockingQueue(queueSize)
                    this.startHandlers(concurrentLevel)
                    Runtime.getRuntime().addShutdownHook(Thread {
                        this.shutdown()
                    })
                    isRunning = true
                    LOGGER.info("Cloud stream memory queue was started ( qs: $queueSize, ps: $concurrentLevel ).")
                }
            }
        }
    }

    private fun startHandlers(poolSize: Int) {
        val q = this.queue
        this.handlers = if (q != null) {
            (0 until poolSize).map {
                val h = MessageWorkHandler(q)
                threadFactory.newThread(h).start()
                h
            }
        } else {
            null
        }

    }

    fun shutdown() {
        if (isRunning) {
            synchronized(startingSync) {
                if (isRunning) {
                    this.handlers?.forEach {
                        it.stop()
                    }
                    LOGGER.info("Cloud stream memory queue was stopped.")
                    isRunning = false
                }
            }
        }
    }

    fun produce(destination: String, message: Message<*>) {
        if (!isRunning) {
            throw RuntimeException("MemoryMessageQueue is not running .")
        }
        var added = false
        while (!added) {
            added = queue?.offer(QueueItem(message, destination), 2, TimeUnit.SECONDS) ?: true
            if (!added) {
                queue?.poll()
                LOGGER.warn("Message queue was full, the earlier messages have been dropped.")
            }
        }
    }

}