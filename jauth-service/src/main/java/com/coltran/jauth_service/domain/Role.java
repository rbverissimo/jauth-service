package com.coltran.jauth_service.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

}
