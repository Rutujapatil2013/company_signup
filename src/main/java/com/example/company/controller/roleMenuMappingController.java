package com.example.company.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.company.entity.menu;
import com.example.company.entity.roleMenuMapping;
import com.example.company.service.roleMenuMappingService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rolemenumappings")
public class roleMenuMappingController {
	
	@Autowired
    private roleMenuMappingService service;

    @GetMapping("/getall")
    @Operation(summary = "Get list of all role-menu mappings", description = "Retrieve a list of all role-menu mappings")
    public List<roleMenuMapping> getAllRoleMenuMappings() {
        return service.getAllRoleMenuMappings();
    }

    @GetMapping("/getById/{roleId}")
    @Operation(summary = "Get menu list by role ID", description = "Retrieve a list of menus accessible by a specific role")
    public List<menu> getMenuByRoleId(@PathVariable Long roleId) {
        return service.getMenuByRoleId(roleId);
    }

    @PostMapping("/add")
    @Operation(summary = "Add role-menu mapping", description = "Add a new role-menu mapping")
    public roleMenuMapping saveRoleMenuMapping(@RequestBody roleMenuMapping mapping) {
        return service.saveRoleMenuMapping(mapping);
    }
    
    

    @DeleteMapping("/delete/{mappingId}")
    @Operation(summary = "Delete role-menu mapping", description = "Delete a role-menu mapping by its ID")
    public void deleteRoleMenuMapping(@PathVariable Long mappingId) {
    	service.deleteRoleMenuMapping(mappingId);
    }
}
    
    


