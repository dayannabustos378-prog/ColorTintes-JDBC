import java.math.BigDecimal;

public record Producto(
        int idProducto,
        String nombre,
        String marca,
        String color,
        BigDecimal precioUnitario,
        int idCategoria,
        int idProveedor
) {}