package Members;

import javafx.beans.binding.StringBinding;

public class MemberTotalHoldingsBinding extends StringBinding {
    private final MemberTotalHoldings memberTotalHoldings;

    public MemberTotalHoldingsBinding(MemberTotalHoldings memberTotalHoldings) {
        this.memberTotalHoldings = memberTotalHoldings;
        bind(memberTotalHoldings.totalHoldingsProperty());
    }

    @Override
    protected String computeValue() {
        return "Total Holdings: " + memberTotalHoldings.getTotalHoldings();
    }
}
