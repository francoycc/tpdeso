package utn.isi.deso.demo.gestion.modelo;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Component;


@Component
public class PagoFactory {
    public Pago crearPago(PagoDTO pagoDTO) {
        switch (pagoDTO.getTipoPago().toUpperCase()) {
            case "EFECTIVO":
                return new Efectivo(LocalDate.now(), pagoDTO.getMonto());
            case "MERCADOPAGO":
                return new MercadoPago(LocalDate.now(), pagoDTO.getMonto(), pagoDTO.getAlias());
            case "TRANSFERENCIA":
                return new Transferencia(LocalDate.now(), pagoDTO.getMonto(), pagoDTO.getCbu(), pagoDTO.getCuit());
            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + pagoDTO.getTipoPago());
        }
    }
}
