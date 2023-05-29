package com.example.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.company.entity.UserRole;
import com.example.company.service.usersRoleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/roles")
public class usersRoleController {
	
	@Autowired
    private usersRoleService roleService;

    @GetMapping("/getall")
    @Operation(summary = "Get all roles for companies", description = "")
    public List<UserRole> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get single role using roleId", description = "")
    public UserRole getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @PostMapping("/add")
    @Operation(summary = "Add new role", description = "")
    public UserRole saveRole(@RequestBody UserRole role) {
        return roleService.saveRole(role);
    }
    
    
    @PutMapping("/update/{roleId}")
    @Operation(summary = "Update existing role using roleId", description = "")
    public String updateRole(@PathVariable Long roleId, @RequestBody UserRole role) {
        roleService.updateRole(roleId, role);
        return "Role updated successfully.";
    }
   
    @DeleteMapping("/delete/{roleId}")
    @Operation(summary = "Delete any role using roleId", description = "")
    public String deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return "Role deleted successfully.";
    }

}
