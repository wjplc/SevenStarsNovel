package com.lyn.novel.constant;

import lombok.*;

/**
 * 响应状态码码枚举类。
 * <p>
 * 响应码为字符串类型，共 5 位，分成两个部分：错误产生来源+四位数字编号。 错误产生来源分为 A/B/C， A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付 超时等问题； B
 * 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题； C 表示错误来源 于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 0001 到 9999，大类之间的
 * 步长间距预留 100。
 * <p>
 *
 * @author wjp
 * @date 2023/12/9
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 正确执行后的返回
     */
    // 1xx Informational
    CONTINUE(100, "继续"),
    SWITCHING_PROTOCOLS(101, "切换协议"),
    PROCESSING(102, "处理中"),
    CHECKPOINT(103, "检查点"),

    // 2xx Success
    OK(200, "成功"),
    CREATED(201, "已创建"),
    ACCEPTED(202, "已接受"),
    NON_AUTHORITATIVE_INFORMATION(203, "非授权信息"),
    NO_CONTENT(204, "无内容"),
    RESET_CONTENT(205, "重置内容"),
    PARTIAL_CONTENT(206, "部分内容"),
    MULTI_STATUS(207, "多状态"),
    ALREADY_REPORTED(208, "已报告"),
    IM_USED(226, "IM 已使用"),

    // 3xx Redirection
    MULTIPLE_CHOICES(300, "多种选择"),
    MOVED_PERMANENTLY(301, "永久移动"),
    FOUND(302, "找到"),
    SEE_OTHER(303, "参见其他"),
    NOT_MODIFIED(304, "未修改"),
    USE_PROXY(305, "使用代理"),
    TEMPORARY_REDIRECT(307, "临时重定向"),
    PERMANENT_REDIRECT(308, "永久重定向"),

    // 4xx Client Error
    BAD_REQUEST(400, "客户端请求错误"),
    UNAUTHORIZED(401, "身份验证失败"),
    PAYMENT_REQUIRED(402, "需要付款"),
    FORBIDDEN(403, "权限不足"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    NOT_ACCEPTABLE(406, "不可接受"),
    PROXY_AUTHENTICATION_REQUIRED(407, "要求代理身份验证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    GONE(410, "已删除"),
    LENGTH_REQUIRED(411, "需要有效长度"),
    PRECONDITION_FAILED(412, "先决条件失败"),
    PAYLOAD_TOO_LARGE(413, "请求实体过大"),
    URI_TOO_LONG(414, "请求的 URI 过长"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "请求范围不符合要求"),
    EXPECTATION_FAILED(417, "未满足期望值"),
    I_AM_A_TEAPOT(418, "我是茶壶"),
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "资源空间不足"),
    METHOD_FAILURE(420, "方法失败"),
    DESTINATION_LOCKED(421, "目标被锁定"),
    UNPROCESSABLE_ENTITY(422, "不可处理的实体"),
    LOCKED(423, "已锁定"),
    FAILED_DEPENDENCY(424, "请求的依赖关系失败"),
    TOO_EARLY(425, "请求过早"),
    UPGRADE_REQUIRED(426, "需要升级"),
    PRECONDITION_REQUIRED(428, "要求先决条件"),
    TOO_MANY_REQUESTS(429, "请求过多"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求头字段太大"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "因法律原因不可用"),

    // 5xx Server Error
    SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "尚未实施"),
    BAD_GATEWAY(502, "错误的网关"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP 版本不支持"),
    VARIANT_ALSO_NEGOTIATES(506, "变体协商"),
    INSUFFICIENT_STORAGE(507, "存储空间不足"),
    LOOP_DETECTED(508, "检测到循环"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "超出带宽限制"),
    NOT_EXTENDED(510, "未扩展"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需要网络身份验证"),

    // 自定义状态码
    CUSTOM_ERROR(1000, "自定义错误");

    //    /**
//     * 一级宏观错误码，用户端错误
//     */
//    USER_ERROR(0001, "用户端错误"),
//
//    /**
//     * 二级宏观错误码，用户注册错误
//     */
//    USER_REGISTER_ERROR("A0100", "用户注册错误"),
//
//    /**
//     * 用户未同意隐私协议
//     */
//    USER_NO_AGREE_PRIVATE_ERROR("A0101", "用户未同意隐私协议"),
//
//    /**
//     * 注册国家或地区受限
//     */
//    USER_REGISTER_AREA_LIMIT_ERROR("A0102", "注册国家或地区受限"),
//
//    /**
//     * 用户验证码错误
//     */
//    USER_VERIFY_CODE_ERROR("A0240", "用户验证码错误"),
//
//    /**
//     * 用户名已存在
//     */
//    USER_NAME_EXIST("A0111", "用户名已存在"),
//
//    /**
//     * 用户账号不存在
//     */
//    USER_ACCOUNT_NOT_EXIST("A0201", "用户账号不存在"),
//
//    /**
//     * 用户密码错误
//     */
//    USER_PASSWORD_ERROR("A0210", "用户密码错误"),
//
//    /**
//     * 二级宏观错误码，用户请求参数错误
//     */
//    USER_REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
//
//    /**
//     * 用户登录已过期
//     */
//    USER_LOGIN_EXPIRED("A0230", "用户登录已过期"),
//
//    /**
//     * 访问未授权
//     */
//    USER_UN_AUTH("A0301", "访问未授权"),
//
//    /**
//     * 用户请求服务异常
//     */
//    USER_REQ_EXCEPTION("A0500", "用户请求服务异常"),
//
//    /**
//     * 请求超出限制
//     */
//    USER_REQ_MANY("A0501", "请求超出限制"),
//
//    /**
//     * 用户评论异常
//     */
//    USER_COMMENT("A2000", "用户评论异常"),
//
//    /**
//     * 用户评论异常
//     */
//    USER_COMMENTED("A2001", "用户已发表评论"),
//
//    /**
//     * 作家发布异常
//     */
//    AUTHOR_PUBLISH("A3000", "作家发布异常"),
//
//    /**
//     * 小说名已存在
//     */
//    AUTHOR_BOOK_NAME_EXIST("A3001", "小说名已存在"),
//
//    /**
//     * 用户上传文件异常
//     */
//    USER_UPLOAD_FILE_ERROR("A0700", "用户上传文件异常"),
//
//    /**
//     * 用户上传文件类型不匹配
//     */
//    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A0701", "用户上传文件类型不匹配"),
//
//    /**
//     * 一级宏观错误码，系统执行出错
//     */
//    SYSTEM_ERROR("B0001", "系统执行出错"),
//
//    /**
//     * 二级宏观错误码，系统执行超时
//     */
//    SYSTEM_TIMEOUT_ERROR("B0100", "系统执行超时"),
//
//    /**
//     * 一级宏观错误码，调用第三方服务出错
//     */
//    THIRD_SERVICE_ERROR("C0001", "调用第三方服务出错"),
//
//    /**
//     * 一级宏观错误码，中间件服务出错
//     */
//    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 中文描述
     */
    private final String message;

}
