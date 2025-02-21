const fs = require("fs");

// Function to read JSON data
function readJSONFile(filename) {
    try {
        const data = fs.readFileSync(filename, "utf-8");
        return JSON.parse(data);
    } catch (error) {
        console.error("Error reading JSON file:", error);
        return null;
    }
}

// Convert numbers from different bases to decimal (BigInt)
function convertBaseToDecimal(value, base) {
    return BigInt(parseInt(value, base));
}

// Create Vandermonde matrix
function createVandermondeMatrix(xValues, k) {
    let A = [];
    for (let i = 0; i < k; i++) {
        let row = [];
        let x = BigInt(xValues[i]);
        for (let j = 0; j < k; j++) {
            row.push(x ** BigInt(j)); // x^j
        }
        A.push(row);
    }
    return A;
}

// Gaussian Elimination to solve Ax = b
function gaussianElimination(A, b, k) {
    let x = new Array(k).fill(BigInt(0));

    // Forward elimination
    for (let i = 0; i < k; i++) {
        // Find pivot row and swap if necessary
        let pivotRow = i;
        while (pivotRow < k && A[pivotRow][i] === BigInt(0)) {
            pivotRow++;
        }
        if (pivotRow === k) {
            throw new Error("Matrix is singular or nearly singular");
        }
        if (pivotRow !== i) {
            [A[i], A[pivotRow]] = [A[pivotRow], A[i]];
            [b[i], b[pivotRow]] = [b[pivotRow], b[i]];
        }

        let pivot = A[i][i];

        // Normalize row
        for (let j = i; j < k; j++) {
            A[i][j] = A[i][j] / pivot;
        }
        b[i] = b[i] / pivot;

        // Eliminate column entries below pivot
        for (let j = i + 1; j < k; j++) {
            let factor = A[j][i];
            for (let l = i; l < k; l++) {
                A[j][l] -= factor * A[i][l];
            }
            b[j] -= factor * b[i];
        }
    }

    // Backward substitution
    for (let i = k - 1; i >= 0; i--) {
        x[i] = b[i];
        for (let j = i + 1; j < k; j++) {
            x[i] -= A[i][j] * x[j];
        }
    }

    return x;
}

// Process JSON and solve using the matrix method
function processJSON(jsonObject) {
    if (!jsonObject) return;

    const n = parseInt(jsonObject.keys.n);
    const k = parseInt(jsonObject.keys.k);
    console.log(n: ${n}, k: ${k});

    let xValues = [];
    let yValues = [];
    let index = 1;

    for (const key in jsonObject) {
        if (key !== "keys" && index <= k) {
            const base = parseInt(jsonObject[key].base);
            const value = jsonObject[key].value;
            xValues.push(index);
            yValues.push(convertBaseToDecimal(value, base));
            index++;
        }
    }

    console.log("X Values:", xValues);
    console.log("Y Values:", yValues);

    let A = createVandermondeMatrix(xValues, k);
    let solution = gaussianElimination(A, yValues, k);
    
    console.log("Polynomial Coefficients:", solution);
    console.log("The secret constant term (c) is:", solution[0].toString());
}

// Run program
const jsonObject1 = readJSONFile("Testcase-1.json");                          // TESTCASE 1
processJSON(jsonObject1);

const jsonObject = readJSONFile("Testcase-2.json");                           // TESTCASE 2
processJSON(jsonObject);