package com.labijie.stream.memory.queue

import org.springframework.messaging.Message

/**
 * Created with IntelliJ IDEA.
 * @author Anders Xiao
 * @date 2019-11-29
 */
data class QueueItem(val message: Message<*>, val destination: String)