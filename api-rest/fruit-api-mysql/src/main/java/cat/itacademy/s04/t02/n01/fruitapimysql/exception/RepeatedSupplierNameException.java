package cat.itacademy.s04.t02.n01.fruitapimysql.exception;

public class RepeatedSupplierNameException extends RuntimeException {
    public RepeatedSupplierNameException(String message) {
        super(message);
    }
}
