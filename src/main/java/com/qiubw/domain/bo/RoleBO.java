package com.qiubw.domain.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleBO {
    private Long id;
    private String name;
    private String code;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private List<PermissionBO> permissions;
}
