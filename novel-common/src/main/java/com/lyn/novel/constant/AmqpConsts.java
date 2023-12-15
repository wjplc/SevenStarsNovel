package com.lyn.novel.constant;

/**
 * AMQP 相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/25
 */
public class AmqpConsts {

    /**
     * 小说信息改变 MQ
     */
    public static class BookChangeMq {

        /**
         * 小说信息改变交换机
         */
        public static final String EXCHANGE_NAME = "EXCHANGE-BOOK-CHANGE";

        /**
         * Elasticsearch book 索引更新的队列
         */
        public static final String QUEUE_ES_UPDATE = "QUEUE-ES-BOOK-UPDATE";

        /**
         * Redis book 缓存更新的队列
         */
        public static final String QUEUE_REDIS_UPDATE = "QUEUE-REDIS-BOOK-UPDATE";

    }

    /**
     * 验证码发送消息
     */
    public static class VerifyCodeMq{
        public static final String EXCHANGE_DIRECT_VERIFYCODE = "EXCHANGE_DIRECT_VERIFYCODE";

        public static final String QUEUE_VERIFYCODE_PHONE = "QUEUE_VERIFYCODE_PHONE";

        public static final String KEY_VERIFYCODE_PHONE = "KEY_VERIFYCODE_PHONE";

        public static final String QUEUE_VERIFYCODE_EMAIL = "QUEUE_VERIFYCODE_EMAIL";

        public static final String KEY_VERIFYCODE_EMAIL = "KEY_VERIFYCODE_EMAIL";

    }

}
