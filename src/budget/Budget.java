package budget;

public abstract class Budget {
    private int capital;
    private final String  year;
    private final String companyName;
    private boolean hasProfits;
    private int profits;
    private int losses;
    private int bank;
    private int treasury;
    private int creditors;
    private int debtors;
    private int income;
    private int publicExpenses;
    private int bankBenefits;

    public Budget( String year, String companyName) {
        this.year = year;
        this.companyName = checkCompanyName(companyName);
    }


    public final void setCapital(int capital){
        this.capital=capital;
    }

    public final void setBank(int bank) {
        this.bank = bank;
    }

    public final void setHasProfits(boolean hasProfits) {
        this.hasProfits = hasProfits;
    }

    public final void setProfits(int profits) {
        this.profits = profits;
    }

    public final void setLosses(int losses) {
        this.losses = losses;
    }

    public final void setTreasury(int treasury) {
        this.treasury = treasury;
    }

    public final void setCreditors(int creditors) {
        this.creditors = creditors;
    }

    public final void setDebtors(int debtors) {
        this.debtors = debtors;
    }

    public final void setIncome(int income) {
        this.income = income;
    }

    public final void setPublicExpenses(int publicExpenses) {
        this.publicExpenses = publicExpenses;
    }

    public  final void setBankBenefits(int bankBenefits) {
        this.bankBenefits = bankBenefits;
    }


    public final boolean isHasProfits() {
        return hasProfits;
    }

    public final int getProfits() {
        return profits;
    }

    public final int getLosses() {
        return losses;
    }

    public final int getBank() {
        return bank;
    }

    public final int getTreasury() {
        return treasury;
    }

    public final int getCreditors() {
        return creditors;
    }

    public final int getDebtors() {
        return debtors;
    }

    public final int getIncome() {
        return income;
    }

    public final int getPublicExpenses() {
        return publicExpenses;
    }

    public final int getBankBenefits() {
        return bankBenefits;
    }

    public final int getCapital() {
        return capital;
    }

    public final String getYear() {
        return year;
    }

    public final String getCompanyName() {
        return companyName;
    }

    public final int inRange(int lowerBound , int upperBound) {
        //algorithm for returning an integer in a specified range using the lower and upper bound parameters;
        return  (int)(((Math.random()*Math.floor(upperBound-lowerBound+1)+lowerBound))/5)*5;
    }
    private String checkCompanyName(String companyName){
        //checking whether the user typed the company word in the company name and if so we remove it
        if (!companyName.contains("company")){
            return companyName;
        }
        return companyName.replace("company","");
    }

    @Override
    public String toString() {
        return ("the capital is " +getCapital()+"\n"+
                " and the year is " +getYear()+"\n"+
                " and the name is " +getCompanyName()+"\n"+
                " and the bank is " +getBank()+"\n"+
                " and the treasury is " +getTreasury()+"\n"+
                " and creditors is " +getCreditors()+"\n"+
                " and debtors is " +getDebtors()+"\n"+
                " and income is " +getIncome()+"\n"+
                " and publicExpenses are " +getPublicExpenses()+"\n"+
                " and the bankBenefits are " +getBankBenefits()
        );
    }
}
