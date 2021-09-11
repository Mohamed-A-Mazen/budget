package budget;

public class Budget100000 extends Budget{

    public Budget100000(String year, String companyName) {
        super(year, companyName);
        setCapital(100000);
        setHasProfits(false);
        setLosses(inRange(2500,3500));
        setCreditors(inRange(4000,5000));
        int total = getCapital()+getCreditors()+getLosses();
        setDebtors(getCreditors()+getLosses());
        setBank(inRange(60000,70000));
        setTreasury(total-getBank()-getDebtors());
        setIncome(inRange(210000,220000));
        setBankBenefits((int)(((Math.floor((getLosses()*0.2)/5))*5)));
        setPublicExpenses(getLosses()-getBankBenefits());
    }
    public Budget100000( String year , String companyName , int profits){
        super(year, companyName);
        setCapital(100000);
        setHasProfits(true);
        setProfits(profits);
        setCreditors(inRange(4000,5500));
        setDebtors(getCreditors()-profits);
        int total = getCapital()+getCreditors();
        setBank(inRange(60000,70000));
        setTreasury(total-getCreditors()-getBank());
        setIncome(inRange(210000,220000));
        setBankBenefits((int)((Math.floor((getProfits()*0.15)/5)))*5);
        setPublicExpenses(profits-getBankBenefits());
    }



    @Override
    public String toString() {
        return isHasProfits()? super.toString()+"\n"+
                " and the profits are "+getProfits() : super.toString()+" and the losses are "+ getLosses();
    }
}
