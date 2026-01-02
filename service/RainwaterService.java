package service;

import dao.ReferenceDataDAO;
import model.RainwaterInput;
import model.RainwaterResult;
import model.StorageTankType;

public class RainwaterService {
     private final ReferenceDataDAO referenceDataDAO = new ReferenceDataDAO();

    public RainwaterResult calculateResults(RainwaterInput input) {

        // ---------------- VALIDATION ----------------
        if (input.getRoofArea() <= 0 ||
            input.getFamilyMembers() <= 0 ||
            input.getPerCapitaWaterUsage() <= 0 ||
            input.getAnnualRainfallMm() <= 0) {
            throw new IllegalArgumentException("Invalid input values");
        }

        // ---------------- RUNOFF COEFFICIENT ----------------
        double runoffCoefficient =
        referenceDataDAO.getRunoffCoefficient(input.getRoofMaterial());


        // ---------------- WATER AVAILABLE FROM ROOF ----------------
        double rainfallMeters = input.getAnnualRainfallMm() / 1000;

        double annualHarvested =
                input.getRoofArea()
                * rainfallMeters
                * runoffCoefficient
                * 1000;

        // ---------------- WATER REQUIREMENT ----------------
        double dailyNeed =
                input.getFamilyMembers()
                * input.getPerCapitaWaterUsage();

        double annualNeed = dailyNeed * 365;

        // ---------------- STORAGE TANK CAPACITY ----------------
        int storageDays = 90;
        double storageTankCapacity = dailyNeed * storageDays;

        // ---------------- STORAGE DECISION ----------------
        String storageDecision;
        if (annualHarvested <= storageTankCapacity) {
            storageDecision =
                "Household shall use the harvested rainwater during water scarcity period.";
        } else {
            storageDecision =
                "Harvested rainwater exceeds storage tank capacity. " +
                "Excess water should be diverted for groundwater recharge.";
        }

        // ---------------- STORAGE TANK TYPE (NEW) ----------------
        StorageTankType storageTankType;
        if (storageTankCapacity <= 15000) {
            storageTankType = StorageTankType.FERRO_CEMENT;
        } else if (storageTankCapacity <= 50000) {
            storageTankType = StorageTankType.BRICK_STONE;
        } else {
            storageTankType = StorageTankType.RCC;
        }

        // ---------------- COST ESTIMATION ----------------
        double localWaterRatePerLitre = 0.02;

        double annualSavings =
                annualHarvested * localWaterRatePerLitre;

        double gutterCostPerMeter = 200;
        double drainPipeCostPerMeter = 150;
        double downPipeCostPerMeter = 180;
        double tankCostPerLitre = 5;

        double firstFlushCost = 1500;
        double filterUnitCost = 3000;
        double pumpCost = 5000;
        double pumpUnitCost = 4000;

        double gutterLength = input.getRoofArea() * 0.5;
        double drainPipeLength = 6;
        double downPipeLength = 4;

        double gutterCost = gutterLength * gutterCostPerMeter;
        double drainPipeCost = drainPipeLength * drainPipeCostPerMeter;
        double downPipeCost = downPipeLength * downPipeCostPerMeter;
        double tankCost = storageTankCapacity * tankCostPerLitre;

        double totalSystemCost =
                gutterCost +
                drainPipeCost +
                downPipeCost +
                firstFlushCost +
                filterUnitCost +
                tankCost +
                pumpCost +
                pumpUnitCost;

        double paybackPeriod =
                annualSavings > 0
                ? totalSystemCost / annualSavings
                : 0;

        // ---------------- RETURN RESULT ----------------
        return new RainwaterResult(
                annualHarvested,
                dailyNeed,
                annualNeed,
                runoffCoefficient,
                storageTankCapacity,
                storageDecision,
                storageTankType,     // ✅ NEW
                annualSavings,
                totalSystemCost,
                paybackPeriod
        );
    }
}