package dev.proveedores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="proveedor")
public class proveedor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id_proveedor;
    @NotNull
    private String nombre_proveedor;
    @NotNull
    private String direccion_proveedor;
    @NotNull
    private String telefono_proveedor;
    @NotNull
    private String email_proveedor;
    @NotNull
    private String Descripcion;

}
