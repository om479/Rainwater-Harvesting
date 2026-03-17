package model;

public class RainwaterInput {

    private String state;
    private String city;
    private double roofArea;                // m²
    private int familyMembers;
    private double perCapitaWaterUsage;     // L/person/day
    private String roofMaterial;
    private double annualRainfallMm;        // from dataset

    public RainwaterInput(String state,
                          String city,
                          double roofArea,
                          int familyMembers,
                          double perCapitaWaterUsage,
                          String roofMaterial,
                          double annualRainfallMm) {
        this.state = state;
        this.city = city;
        this.roofArea = roofArea;
        this.familyMembers = familyMembers;
        this.perCapitaWaterUsage = perCapitaWaterUsage;
        this.roofMaterial = roofMaterial;
        this.annualRainfallMm = annualRainfallMm;
    }

    public String getState() { return state; }
    public String getCity() { return city; }
    public double getRoofArea() { return roofArea; }
    public int getFamilyMembers() { return familyMembers; }
    public double getPerCapitaWaterUsage() { return perCapitaWaterUsage; }
    public String getRoofMaterial() { return roofMaterial; }
    public double getAnnualRainfallMm() { return annualRainfallMm; }
}