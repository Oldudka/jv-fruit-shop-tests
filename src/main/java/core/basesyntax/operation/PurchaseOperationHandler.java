package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;

public class PurchaseOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public PurchaseOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int amount = fruitDao.getQuantity(fruit);
        int purchaseAmount = transaction.getQuantity();
        if (purchaseAmount <= amount) {
            fruitDao.addFruit(fruit, amount - purchaseAmount);
        } else {
            throw new RuntimeException("Can't purchase required amount of " + fruit);
        }
    }
}