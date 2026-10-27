package cat.itacademy.s04.t02.n01.fruitapimysql.exception;

public class ExistingSupplierNameException extends RuntimeException {
    public ExistingSupplierNameException(String message) {
        super(message);
    }
}
