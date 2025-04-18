package com.ducbn.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ducbn.shopapp.models.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
