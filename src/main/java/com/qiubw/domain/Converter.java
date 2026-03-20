package com.qiubw.domain;

import com.qiubw.domain.bo.*;
import com.qiubw.domain.dao.TicketDAO;
import com.qiubw.domain.dao.UserDAO;
import com.qiubw.domain.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface Converter {
    Converter INSTANCE = Mappers.getMapper(Converter.class);

    // User
    UserDTO userBOToDTO(UserBO userBO);
    UserBO userDTOToBO(UserDTO userDTO);
    UserDAO userBOToDAO(UserBO userBO);
    UserBO userDAOToBO(UserDAO userDAO);
    List<UserDTO> userBOListToDTOList(List<UserBO> userBOList);
    List<UserBO> userDTOListToBOList(List<UserDTO> userDTOList);

    // Role
    RoleDTO roleBOToDTO(RoleBO roleBO);
    RoleBO roleDTOToBO(RoleDTO roleDTO);
    com.qiubw.domain.entity.Role roleBOToDAO(RoleBO roleBO);
    RoleBO roleDAOToBO(com.qiubw.domain.entity.Role role);
    List<RoleDTO> roleBOListToDTOList(List<RoleBO> roleBOList);
    List<RoleBO> roleDTOListToBOList(List<RoleDTO> roleDTOList);

    // Permission
    PermissionDTO permissionBOToDTO(PermissionBO permissionBO);
    PermissionBO permissionDTOToBO(PermissionDTO permissionDTO);
    com.qiubw.domain.entity.Permission permissionBOToDAO(PermissionBO permissionBO);
    PermissionBO permissionDAOToBO(com.qiubw.domain.entity.Permission permission);
    List<PermissionDTO> permissionBOListToDTOList(List<PermissionBO> permissionBOList);
    List<PermissionBO> permissionDTOListToBOList(List<PermissionDTO> permissionDTOList);

    // Menu
    MenuDTO menuBOToDTO(MenuBO menuBO);
    MenuBO menuDTOToBO(MenuDTO menuDTO);
    com.qiubw.domain.entity.Menu menuBOToDAO(MenuBO menuBO);
    MenuBO menuDAOToBO(com.qiubw.domain.entity.Menu menu);
    List<MenuDTO> menuBOListToDTOList(List<MenuBO> menuBOList);
    List<MenuBO> menuDTOListToBOList(List<MenuDTO> menuDTOList);

    // TicketType
    TicketTypeDTO ticketTypeBOToDTO(TicketTypeBO ticketTypeBO);
    TicketTypeBO ticketTypeDTOToBO(TicketTypeDTO ticketTypeDTO);
    com.qiubw.domain.entity.TicketType ticketTypeBOToDAO(TicketTypeBO ticketTypeBO);
    TicketTypeBO ticketTypeDAOToBO(com.qiubw.domain.entity.TicketType ticketType);
    List<TicketTypeDTO> ticketTypeBOListToDTOList(List<TicketTypeBO> ticketTypeBOList);
    List<TicketTypeBO> ticketTypeDTOListToBOList(List<TicketTypeDTO> ticketTypeDTOList);

    // Ticket
    TicketDTO ticketBOToDTO(TicketBO ticketBO);
    TicketBO ticketDTOToBO(TicketDTO ticketDTO);
    @org.mapstruct.Mapping(source = "ticketTypeId", target = "typeId")
    @org.mapstruct.Mapping(source = "creatorId", target = "userId")
    TicketDAO ticketBOToDAO(TicketBO ticketBO);
    @org.mapstruct.Mapping(source = "typeId", target = "ticketTypeId")
    @org.mapstruct.Mapping(source = "userId", target = "creatorId")
    TicketBO ticketDAOToBO(TicketDAO ticketDAO);
    List<TicketDTO> ticketBOListToDTOList(List<TicketBO> ticketBOList);
    List<TicketBO> ticketDTOListToBOList(List<TicketDTO> ticketDTOList);
}
