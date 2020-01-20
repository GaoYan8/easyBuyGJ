package com.gj.easybuy.entity.user;


import com.gj.easybuy.entity.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/12/30
 * @Describe
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class UserEntity extends BaseEntity {

    private String useridentity;//": "2",
    private String usersid;//": "69cf87bd9e6344898bdc39f4eb",
    private String token;//": "vuqxdfvfbmg44thht0r61byefb",
    private String username;//": "领导"
}
