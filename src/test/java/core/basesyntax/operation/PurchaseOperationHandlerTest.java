package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitTransaction transaction = new FruitTransaction();
    private final PurchaseOperationHandler purchaseOperationHandler
            = new PurchaseOperationHandler(fruitDao);

    @Test
    public void handle_PurchaseOperation_Ok() {
        fruitDao.addFruit("kiwi", 50);
        fruitDao.addFruit("apple", 5);
        transaction.setFruit("kiwi");
        transaction.setQuantity(25);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseOperationHandler.handle(transaction);
        int actual = fruitDao.getQuantity("kiwi");
        int expected = 25;
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void handle_PurchaseOperationWithEnoughItems_NotOk() {
        fruitDao.addFruit("banana", 5);
        transaction.setFruit("banana");
        transaction.setQuantity(10);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseOperationHandler.handle(transaction);
    }
}
