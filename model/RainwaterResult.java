package model;

public class RainwaterResult {

    // ---------------- WATER CALCULATION RESULTS ----------------
    private double annualHarvestedWater;     // litres/year
    private double dailyWaterNeed;            // litres/day
    private double annualWaterNeed;           // litres/year
    private double runoffCoefficient;         // dimensionless

    // ---------------- STORAGE RESULTS ----------------
    private double storageTankCapacity;       // litres
    private String storageDecision;            // decision message
    private StorageTankType storageTankType;  // NEW (for recommendations)

    // ---------------- COST ESTIMATION RESULTS ----------------
    private double annualSavings;              // ₹/year
    private double totalSystemCost;             // ₹
    private double paybackPeriod;               // years

    // ---------------- CONSTRUCTOR ----------------
    public RainwaterResult(double annualHarvestedWater,
                           double dailyWaterNeed,
                           double annualWaterNeed,
                           double runoffCoefficient,
                           double storageTankCapacity,
                           String storageDecision,
                           StorageTankType storageTankType,
                           double annualSavings,
                           double totalSystemCost,
                           double paybackPeriod) {

        this.annualHarvestedWater = annualHarvestedWater;
        this.dailyWaterNeed = dailyWaterNeed;
        this.annualWaterNeed = annualWaterNeed;
        this.runoffCoefficient = runoffCoefficient;
        this.storageTankCapacity = storageTankCapacity;
        this.storageDecision = storageDecision;
        this.storageTankType = storageTankType;
        this.annualSavings = annualSavings;
        this.totalSystemCost = totalSystemCost;
        this.paybackPeriod = paybackPeriod;
    }

    // ---------------- GETTERS ----------------
    public double getAnnualHarvestedWater() {
        return annualHarvestedWater;
    }

    public double getDailyWaterNeed() {
        return dailyWaterNeed;
    }

    public double getAnnualWaterNeed() {
        return annualWaterNeed;
    }

    public double getRunoffCoefficient() {
        return runoffCoefficient;
    }

    public double getStorageTankCapacity() {
        return storageTankCapacity;
    }

    public String getStorageDecision() {
        return storageDecision;
    }

    public StorageTankType getStorageTankType() {
        return storageTankType;
    }

    public double getAnnualSavings() {
        return annualSavings;
    }

    public double getTotalSystemCost() {
        return totalSystemCost;
    }

    public double getPaybackPeriod() {
        return paybackPeriod;
    }

    @Override
    public String toString() {
        return
        "Annual Harvested Water: " + annualHarvestedWater + " L\n" +
        "Daily Water Need: " + dailyWaterNeed + " L\n" +
        "Annual Water Need: " + annualWaterNeed + " L\n" +
        "Storage Tank Capacity: " + storageTankCapacity + " L\n" +
        "Storage Decision: " + storageDecision + "\n" +
        "Storage Tank Type: " + storageTankType + "\n" +
        "Annual Savings: ₹" + annualSavings + "\n" +
        "Total System Cost: ₹" + totalSystemCost + "\n" +
        "Payback Period: " + paybackPeriod + " years";
    }
}