package com.qiubw.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private String code;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private List<PermissionDTO> permissions;
}
