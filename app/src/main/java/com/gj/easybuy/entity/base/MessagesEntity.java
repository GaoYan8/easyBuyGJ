package com.gj.easybuy.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 返回消息公共类
 *
 * @author Man
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MessagesEntity extends BaseEntity {
    private transient static final long serialVersionUID = 1L;
    /**
     * 相应状态码
     */
    private int code;//": 0,
    private boolean success;//": true,
    private String msg;//": "",
}
