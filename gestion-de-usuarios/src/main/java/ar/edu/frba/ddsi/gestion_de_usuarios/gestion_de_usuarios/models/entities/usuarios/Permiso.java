package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public enum Permiso {
  VER_HECHOS,
  VER_COLECCIONES,
  VER_USUARIOS,
  VER_ROLES,
  VER_PERMISOS,
  CREAR_HECHO,
  CREAR_COLECCION,
  CREAR_USUARIO,
  EDITAR_HECHO,
  EDITAR_COLECCION,
  EDITAR_USUARIO,
  ELIMINAR_HECHO,
  ELIMINAR_COLECCION,
  ELIMINAR_USUARIO
}
/*
  CREAR_HECHO,
  CREAR_COLECCION,
  CREAR_USUARIO,
  EDITAR_HECHO,
  EDITAR_COLECCION,
  EDITAR_USUARIO,
  ELIMINAR_HECHO,
  ELIMINAR_COLECCION,
  ELIMINAR_USUARIO
* */