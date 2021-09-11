package budget;

public class Budget30000 extends Budget{


    public Budget30000(String year, String companyName) {
        super(year, companyName);
        setHasProfits(false);
        setLosses(inRange(2000,2500));
        setCapital(30000);
        setCreditors(inRange(3500,4000));
        setDebtors(getCreditors()+getLosses());
        int total = getCapital()+getCreditors()+getLosses();
        setBank(inRange(15000,20000));
        setTreasury(total-getBank()-getDebtors());
        setIncome(inRange(35000,50000));
        setBankBenefits((int)(((Math.floor((getLosses()*0.2)/5))*5)));
        setPublicExpenses(getLosses()-getBankBenefits());
    }

    public Budget30000(String year , String companyName ,int profits){
        super(year, companyName);
        setHasProfits(true);
        setProfits(profits);
        setCapital(30000);
        setCreditors(inRange(4000,4800));
        setDebtors(getCreditors()-getProfits());
        int total = getCapital()+getCreditors();
        setBank(inRange(15000,20000));
        setTreasury(total-getCreditors()-getBank());
        setIncome(inRange(35000,50000));
        setBankBenefits((int)((Math.floor((getProfits()*0.15)/5)))*5);
        setPublicExpenses(getProfits()-getBankBenefits());
    }


    @Override
    public String toString() {
        return isHasProfits() ? super.toString()+"\n"+
                "and the profits are "+getProfits(): super.toString()+" and the losses are "+getLosses();
    }
}
