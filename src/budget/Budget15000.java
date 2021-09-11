package budget;

public class Budget15000 extends Budget{
    public Budget15000( String year, String companyName) {
        super(year, companyName);
        setCapital(15000);
        setHasProfits(false);
        setLosses(inRange(1000,2000));
        setCreditors(inRange(2500,3500));
        setDebtors(getCreditors()+getLosses());
        int total = getCapital()+getCreditors()+getLosses();
        setBank(inRange(8000,12000));
        setTreasury(total-getDebtors()-getBank());
        setIncome(inRange(30000,40000));
        setBankBenefits((int)(((Math.floor((getLosses()*0.2)/5))*5)));
        setPublicExpenses(getLosses()-getBankBenefits());
    }
    public Budget15000 (String year , String companyName, int profits){
        super(year, companyName );
        setHasProfits(true);
        setCapital(15000);
        setProfits(profits);
        setCreditors(inRange(3500,4000));
        setDebtors(getCreditors()-profits);
        int total = getCapital()+getCreditors();
        setBank(inRange(8000,12000));
        setTreasury(total-getBank()-getCreditors());
        setIncome(inRange(30000,40000));
        setBankBenefits((int)((Math.floor((getProfits()*0.15)/5)))*5);
        setPublicExpenses(getProfits()-getBankBenefits());
    }


    @Override
    public String toString() {
        return isHasProfits()?super.toString()+"\n"+
                " and the profits are "+getProfits(): super.toString()+" and the losses are "+getLosses();
    }
}
