package cat.itacademy.s04.t02.n01.fruitapimysql.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(String message) {
        super(message);
    }
}
