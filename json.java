import java.util.*;

public class ShamirSecretSharing {


    public static int convertToDecimal(String value, int base) {
        return Integer.parseInt(value, base);
    }

    // Method to process the input JSON-like structure and find the constant term
    public static void findConstantTerm(Map<String, Object> testCase) {
        // Extract 'n' and 'k' values
        Map<String, Integer> keys = (Map<String, Integer>) testCase.get("keys");
        int n = keys.get("n"); // Total points
        int k = keys.get("k"); // Degree of the polynomial + 1 (number of points)

        // List to store the converted points
        List<Integer> points = new ArrayList<>();

        // Iterate through each point and convert the base value to decimal
        for (Map.Entry<String, Object> entry : testCase.entrySet()) {
            if (!entry.getKey().equals("keys")) { // Skip the "keys" entry
                Map<String, String> pointData = (Map<String, String>) entry.getValue();
                int base = Integer.parseInt(pointData.get("base"));
                String value = pointData.get("value");
                
                // Convert the value to decimal and store it
                int decimalValue = convertToDecimal(value, base);
                points.add(decimalValue);
            }
        }

        // Print the decimal points
        System.out.println("Points in Decimal: " + points);

        // Further steps would involve polynomial interpolation, which is not implemented here.
        // Placeholder for solving the polynomial and finding constant term (c)
        System.out.println("Constant term (c) calculation logic goes here.");
    }

    public static void main(String[] args) {
        // Example input (from the given JSON structure)
        Map<String, Object> testCase = new HashMap<>();

        // Keys with n and k values
        Map<String, Integer> keys = new HashMap<>();
        keys.put("n", 4);
        keys.put("k", 3);
        testCase.put("keys", keys);

        // Point 1 (base 10, value "4")
        Map<String, String> point1 = new HashMap<>();
        point1.put("base", "10");
        point1.put("value", "4");
        testCase.put("1", point1);

        // Point 2 (base 2, value "111")
        Map<String, String> point2 = new HashMap<>();
        point2.put("base", "2");
        point2.put("value", "111");
        testCase.put("2", point2);

        // Point 3 (base 10, value "12")
        Map<String, String> point3 = new HashMap<>();
        point3.put("base", "10");
        point3.put("value", "12");
        testCase.put("3", point3);

        // Point 6 (base 4, value "213")
        Map<String, String> point6 = new HashMap<>();
        point6.put("base", "4");
        point6.put("value", "213");
        testCase.put("6", point6);

        // Call the method to find the constant term
        findConstantTerm(testCase);
    }
}
