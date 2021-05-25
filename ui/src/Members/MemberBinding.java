package Members;

import javafx.beans.binding.StringBinding;

public class MemberBinding extends StringBinding {
    private final Member member;

    public MemberBinding(Member member) {
        this.member = member;
        bind(member.totalHoldingsProperty());
    }

    @Override
    protected String computeValue() {
        return "Total Holdings: " + member.getTotalHoldings();
    }
}
