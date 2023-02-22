package com.swms.common.error;

/**
 * ErrorCode规范：
 * 1. 1-2位，代表业务编码
 * 入库IN,出库OT,库内ON,库存ST,基础BA,通用CM
 * 2. 3-4位，代表业务内模块编码
 * 3. 5-8位，代表业务模块内的错误码
 * <p>
 * example: IN010001
 */
public interface IBaseErrorCode {


}
