package com.qiubw.domain.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuBO {
    private Long id;
    private String name;
    private String url;
    private String icon;
    private Integer type;
    private Long parentId;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private List<MenuBO> children;
}
