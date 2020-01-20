package com.gj.easybuy.entity.base;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页对象
 *
 * @param <T>
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PageEntity<T> extends BaseEntity {
    private int pagesize;//":null,
    private int pageindex;//":null,
    private int totalpage;//":null,
    private int totalrow;

    private List<T> rows = new ArrayList<>();
}
