package Members;

import javafx.beans.property.SimpleIntegerProperty;

public class MemberTotalHoldings {
    final private SimpleIntegerProperty totalHoldings = new SimpleIntegerProperty();

    public SimpleIntegerProperty totalHoldingsProperty() {
        return totalHoldings;
    }

    public int getTotalHoldings() {
        return totalHoldings.get();
    }

    public void setTotalHoldings(int totalHoldings) {
        this.totalHoldings.set(totalHoldings);
    }
}
