package com.qiubw.domain;

import com.qiubw.domain.bo.MenuBO;
import com.qiubw.domain.bo.PermissionBO;
import com.qiubw.domain.bo.RoleBO;
import com.qiubw.domain.bo.TicketBO;
import com.qiubw.domain.bo.TicketTypeBO;
import com.qiubw.domain.bo.UserBO;
import com.qiubw.domain.dto.MenuDTO;
import com.qiubw.domain.dto.PermissionDTO;
import com.qiubw.domain.dto.RoleDTO;
import com.qiubw.domain.dto.TicketDTO;
import com.qiubw.domain.dto.TicketTypeDTO;
import com.qiubw.domain.dto.UserDTO;
import com.qiubw.domain.entity.Menu;
import com.qiubw.domain.entity.Permission;
import com.qiubw.domain.entity.Role;
import com.qiubw.domain.entity.Ticket;
import com.qiubw.domain.entity.TicketType;
import com.qiubw.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T18:52:46+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 25.0.2 (Homebrew)"
)
public class ConverterImpl implements Converter {

    @Override
    public UserDTO userBOToDTO(UserBO userBO) {
        if ( userBO == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( userBO.getId() );
        userDTO.setUsername( userBO.getUsername() );
        userDTO.setPassword( userBO.getPassword() );
        userDTO.setName( userBO.getName() );
        userDTO.setEmail( userBO.getEmail() );
        userDTO.setPhone( userBO.getPhone() );
        userDTO.setStatus( userBO.getStatus() );
        userDTO.setCreateTime( userBO.getCreateTime() );
        userDTO.setUpdateTime( userBO.getUpdateTime() );
        userDTO.setRoles( roleBOListToDTOList( userBO.getRoles() ) );

        return userDTO;
    }

    @Override
    public UserBO userDTOToBO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserBO userBO = new UserBO();

        userBO.setId( userDTO.getId() );
        userBO.setUsername( userDTO.getUsername() );
        userBO.setPassword( userDTO.getPassword() );
        userBO.setName( userDTO.getName() );
        userBO.setEmail( userDTO.getEmail() );
        userBO.setPhone( userDTO.getPhone() );
        userBO.setStatus( userDTO.getStatus() );
        userBO.setCreateTime( userDTO.getCreateTime() );
        userBO.setUpdateTime( userDTO.getUpdateTime() );
        userBO.setRoles( roleDTOListToBOList( userDTO.getRoles() ) );

        return userBO;
    }

    @Override
    public User userBOToDAO(UserBO userBO) {
        if ( userBO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userBO.getId() );
        user.setUsername( userBO.getUsername() );
        user.setPassword( userBO.getPassword() );
        user.setRoleId( userBO.getRoleId() );
        user.setStatus( userBO.getStatus() );
        user.setCreateTime( userBO.getCreateTime() );
        user.setUpdateTime( userBO.getUpdateTime() );

        return user;
    }

    @Override
    public UserBO userDAOToBO(User user) {
        if ( user == null ) {
            return null;
        }

        UserBO userBO = new UserBO();

        userBO.setId( user.getId() );
        userBO.setUsername( user.getUsername() );
        userBO.setPassword( user.getPassword() );
        userBO.setRoleId( user.getRoleId() );
        userBO.setStatus( user.getStatus() );
        userBO.setCreateTime( user.getCreateTime() );
        userBO.setUpdateTime( user.getUpdateTime() );

        return userBO;
    }

    @Override
    public List<UserDTO> userBOListToDTOList(List<UserBO> userBOList) {
        if ( userBOList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userBOList.size() );
        for ( UserBO userBO : userBOList ) {
            list.add( userBOToDTO( userBO ) );
        }

        return list;
    }

    @Override
    public List<UserBO> userDTOListToBOList(List<UserDTO> userDTOList) {
        if ( userDTOList == null ) {
            return null;
        }

        List<UserBO> list = new ArrayList<UserBO>( userDTOList.size() );
        for ( UserDTO userDTO : userDTOList ) {
            list.add( userDTOToBO( userDTO ) );
        }

        return list;
    }

    @Override
    public RoleDTO roleBOToDTO(RoleBO roleBO) {
        if ( roleBO == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        return roleDTO;
    }

    @Override
    public RoleBO roleDTOToBO(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        RoleBO roleBO = new RoleBO();

        return roleBO;
    }

    @Override
    public Role roleBOToDAO(RoleBO roleBO) {
        if ( roleBO == null ) {
            return null;
        }

        Role role = new Role();

        return role;
    }

    @Override
    public RoleBO roleDAOToBO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleBO roleBO = new RoleBO();

        return roleBO;
    }

    @Override
    public List<RoleDTO> roleBOListToDTOList(List<RoleBO> roleBOList) {
        if ( roleBOList == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( roleBOList.size() );
        for ( RoleBO roleBO : roleBOList ) {
            list.add( roleBOToDTO( roleBO ) );
        }

        return list;
    }

    @Override
    public List<RoleBO> roleDTOListToBOList(List<RoleDTO> roleDTOList) {
        if ( roleDTOList == null ) {
            return null;
        }

        List<RoleBO> list = new ArrayList<RoleBO>( roleDTOList.size() );
        for ( RoleDTO roleDTO : roleDTOList ) {
            list.add( roleDTOToBO( roleDTO ) );
        }

        return list;
    }

    @Override
    public PermissionDTO permissionBOToDTO(PermissionBO permissionBO) {
        if ( permissionBO == null ) {
            return null;
        }

        PermissionDTO permissionDTO = new PermissionDTO();

        return permissionDTO;
    }

    @Override
    public PermissionBO permissionDTOToBO(PermissionDTO permissionDTO) {
        if ( permissionDTO == null ) {
            return null;
        }

        PermissionBO permissionBO = new PermissionBO();

        return permissionBO;
    }

    @Override
    public Permission permissionBOToDAO(PermissionBO permissionBO) {
        if ( permissionBO == null ) {
            return null;
        }

        Permission permission = new Permission();

        return permission;
    }

    @Override
    public PermissionBO permissionDAOToBO(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionBO permissionBO = new PermissionBO();

        return permissionBO;
    }

    @Override
    public List<PermissionDTO> permissionBOListToDTOList(List<PermissionBO> permissionBOList) {
        if ( permissionBOList == null ) {
            return null;
        }

        List<PermissionDTO> list = new ArrayList<PermissionDTO>( permissionBOList.size() );
        for ( PermissionBO permissionBO : permissionBOList ) {
            list.add( permissionBOToDTO( permissionBO ) );
        }

        return list;
    }

    @Override
    public List<PermissionBO> permissionDTOListToBOList(List<PermissionDTO> permissionDTOList) {
        if ( permissionDTOList == null ) {
            return null;
        }

        List<PermissionBO> list = new ArrayList<PermissionBO>( permissionDTOList.size() );
        for ( PermissionDTO permissionDTO : permissionDTOList ) {
            list.add( permissionDTOToBO( permissionDTO ) );
        }

        return list;
    }

    @Override
    public MenuDTO menuBOToDTO(MenuBO menuBO) {
        if ( menuBO == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        return menuDTO;
    }

    @Override
    public MenuBO menuDTOToBO(MenuDTO menuDTO) {
        if ( menuDTO == null ) {
            return null;
        }

        MenuBO menuBO = new MenuBO();

        return menuBO;
    }

    @Override
    public Menu menuBOToDAO(MenuBO menuBO) {
        if ( menuBO == null ) {
            return null;
        }

        Menu menu = new Menu();

        return menu;
    }

    @Override
    public MenuBO menuDAOToBO(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuBO menuBO = new MenuBO();

        return menuBO;
    }

    @Override
    public List<MenuDTO> menuBOListToDTOList(List<MenuBO> menuBOList) {
        if ( menuBOList == null ) {
            return null;
        }

        List<MenuDTO> list = new ArrayList<MenuDTO>( menuBOList.size() );
        for ( MenuBO menuBO : menuBOList ) {
            list.add( menuBOToDTO( menuBO ) );
        }

        return list;
    }

    @Override
    public List<MenuBO> menuDTOListToBOList(List<MenuDTO> menuDTOList) {
        if ( menuDTOList == null ) {
            return null;
        }

        List<MenuBO> list = new ArrayList<MenuBO>( menuDTOList.size() );
        for ( MenuDTO menuDTO : menuDTOList ) {
            list.add( menuDTOToBO( menuDTO ) );
        }

        return list;
    }

    @Override
    public TicketTypeDTO ticketTypeBOToDTO(TicketTypeBO ticketTypeBO) {
        if ( ticketTypeBO == null ) {
            return null;
        }

        TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();

        return ticketTypeDTO;
    }

    @Override
    public TicketTypeBO ticketTypeDTOToBO(TicketTypeDTO ticketTypeDTO) {
        if ( ticketTypeDTO == null ) {
            return null;
        }

        TicketTypeBO ticketTypeBO = new TicketTypeBO();

        return ticketTypeBO;
    }

    @Override
    public TicketType ticketTypeBOToDAO(TicketTypeBO ticketTypeBO) {
        if ( ticketTypeBO == null ) {
            return null;
        }

        TicketType ticketType = new TicketType();

        return ticketType;
    }

    @Override
    public TicketTypeBO ticketTypeDAOToBO(TicketType ticketType) {
        if ( ticketType == null ) {
            return null;
        }

        TicketTypeBO ticketTypeBO = new TicketTypeBO();

        return ticketTypeBO;
    }

    @Override
    public List<TicketTypeDTO> ticketTypeBOListToDTOList(List<TicketTypeBO> ticketTypeBOList) {
        if ( ticketTypeBOList == null ) {
            return null;
        }

        List<TicketTypeDTO> list = new ArrayList<TicketTypeDTO>( ticketTypeBOList.size() );
        for ( TicketTypeBO ticketTypeBO : ticketTypeBOList ) {
            list.add( ticketTypeBOToDTO( ticketTypeBO ) );
        }

        return list;
    }

    @Override
    public List<TicketTypeBO> ticketTypeDTOListToBOList(List<TicketTypeDTO> ticketTypeDTOList) {
        if ( ticketTypeDTOList == null ) {
            return null;
        }

        List<TicketTypeBO> list = new ArrayList<TicketTypeBO>( ticketTypeDTOList.size() );
        for ( TicketTypeDTO ticketTypeDTO : ticketTypeDTOList ) {
            list.add( ticketTypeDTOToBO( ticketTypeDTO ) );
        }

        return list;
    }

    @Override
    public TicketDTO ticketBOToDTO(TicketBO ticketBO) {
        if ( ticketBO == null ) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId( ticketBO.getId() );
        ticketDTO.setTitle( ticketBO.getTitle() );
        ticketDTO.setContent( ticketBO.getContent() );
        ticketDTO.setTicketTypeId( ticketBO.getTicketTypeId() );
        ticketDTO.setCreatorId( ticketBO.getCreatorId() );
        ticketDTO.setStatus( ticketBO.getStatus() );
        ticketDTO.setCreateTime( ticketBO.getCreateTime() );
        ticketDTO.setUpdateTime( ticketBO.getUpdateTime() );

        return ticketDTO;
    }

    @Override
    public TicketBO ticketDTOToBO(TicketDTO ticketDTO) {
        if ( ticketDTO == null ) {
            return null;
        }

        TicketBO ticketBO = new TicketBO();

        ticketBO.setId( ticketDTO.getId() );
        ticketBO.setTitle( ticketDTO.getTitle() );
        ticketBO.setContent( ticketDTO.getContent() );
        ticketBO.setTicketTypeId( ticketDTO.getTicketTypeId() );
        ticketBO.setCreatorId( ticketDTO.getCreatorId() );
        ticketBO.setStatus( ticketDTO.getStatus() );
        ticketBO.setCreateTime( ticketDTO.getCreateTime() );
        ticketBO.setUpdateTime( ticketDTO.getUpdateTime() );

        return ticketBO;
    }

    @Override
    public Ticket ticketBOToDAO(TicketBO ticketBO) {
        if ( ticketBO == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setTypeId( ticketBO.getTicketTypeId() );
        ticket.setUserId( ticketBO.getCreatorId() );
        ticket.setId( ticketBO.getId() );
        ticket.setTitle( ticketBO.getTitle() );
        ticket.setStatus( ticketBO.getStatus() );
        ticket.setCreateTime( ticketBO.getCreateTime() );
        ticket.setUpdateTime( ticketBO.getUpdateTime() );
        ticket.setContent( ticketBO.getContent() );

        return ticket;
    }

    @Override
    public TicketBO ticketDAOToBO(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        TicketBO ticketBO = new TicketBO();

        ticketBO.setTicketTypeId( ticket.getTypeId() );
        ticketBO.setCreatorId( ticket.getUserId() );
        ticketBO.setId( ticket.getId() );
        ticketBO.setTitle( ticket.getTitle() );
        ticketBO.setContent( ticket.getContent() );
        ticketBO.setStatus( ticket.getStatus() );
        ticketBO.setCreateTime( ticket.getCreateTime() );
        ticketBO.setUpdateTime( ticket.getUpdateTime() );

        return ticketBO;
    }

    @Override
    public List<TicketDTO> ticketBOListToDTOList(List<TicketBO> ticketBOList) {
        if ( ticketBOList == null ) {
            return null;
        }

        List<TicketDTO> list = new ArrayList<TicketDTO>( ticketBOList.size() );
        for ( TicketBO ticketBO : ticketBOList ) {
            list.add( ticketBOToDTO( ticketBO ) );
        }

        return list;
    }

    @Override
    public List<TicketBO> ticketDTOListToBOList(List<TicketDTO> ticketDTOList) {
        if ( ticketDTOList == null ) {
            return null;
        }

        List<TicketBO> list = new ArrayList<TicketBO>( ticketDTOList.size() );
        for ( TicketDTO ticketDTO : ticketDTOList ) {
            list.add( ticketDTOToBO( ticketDTO ) );
        }

        return list;
    }
}
