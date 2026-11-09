import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductoDAO {  public boolean actualizarPrecio(int idProducto, java.math.BigDecimal nuevoPrecio)
        throws SQLException {
    String sql = "UPDATE producto SET precio_unitario = ? WHERE id_producto = ?";

    try (Connection conexion = Conexion.abrir();
         PreparedStatement consulta = conexion.prepareStatement(sql)) {
        consulta.setBigDecimal(1, nuevoPrecio);
        consulta.setInt(2, idProducto);
        return consulta.executeUpdate() == 1;
    }
}

    public  boolean eliminarProducto(int idProducto) throws SQLException {
    String sql = "DELETE FROM producto WHERE id_producto = ?";

    try (Connection conexion = Conexion.abrir();
         PreparedStatement consulta = conexion.prepareStatement(sql)) {
        consulta.setInt(1, idProducto);
        return consulta.executeUpdate() == 1;
    }
}
 void mostrarProductos() throws SQLException {
        String sql = "SELECT nombre, precio_unitario FROM producto ORDER BY id_producto";

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet filas = consulta.executeQuery()) {

            while (filas.next()) {
                String nombre = filas.getString("nombre");
                var precio = filas.getBigDecimal("precio_unitario");
                System.out.println(nombre + " - $" + precio);
            }
        }
    }

    public int registrarProducto(Producto producto) throws SQLException {
        String sql = """
            INSERT INTO producto
                (nombre, marca, color, precio_unitario, id_categoria, id_proveedor)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            consulta.setString(1, producto.nombre());
            consulta.setString(2, producto.marca());
            consulta.setString(3, producto.color());
            consulta.setBigDecimal(4, producto.precioUnitario());
            consulta.setInt(5, producto.idCategoria());
            consulta.setInt(6, producto.idProveedor());
            consulta.executeUpdate();

            try (ResultSet claves = consulta.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
                throw new SQLException("No se recibió el ID del producto.");
            }
        }
    }
}