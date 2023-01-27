package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Math.Jama.Matrix;
import frc.robot.Library.FRC_3117_Tools.Math.Jama.QRDecomposition;

public class PolynomialRegression {
    private int degree; // degree of the polynomial regression
    private Matrix beta; // the polynomial regression coefficients
    private double sse; // sum of squares due to error
    private double sst; // total sum of squares

    public PolynomialRegression(Vector2d[] points, int degree)
    {
        double[] x = new double[points.length];
        double[] y = new double[points.length];
        for(int i = 0; i < points.length; ++i)
        {
            x[i] = points[i].X;
            y[i] = points[i].Y;
        }
        Solve(x, y, degree);
    }

    public PolynomialRegression(double[][] xy, int degree) {
        double[] x = new double[xy.length];
        double[] y = new double[xy.length];
        for (int i = 0; i < xy.length; ++i) {
            x[i] = xy[i][0];
            y[i] = xy[i][1];
        }
        Solve(x, y, degree);
    }

    /**
     * Performs a polynomial regression on the data points {@code (y[i], x[i])}.
     *
     * @param x
     *            the values of the predictor variable
     * @param y
     *            the corresponding values of the response variable
     * @param degree
     *            the degree of the polynomial to fit
     */
    public PolynomialRegression(double[] x, double[] y, int degree) {
        Solve(x, y, degree);
    }

    private void Solve(double[] x, double[] y, int degree) {
        this.degree = degree;

        int n = x.length;
        QRDecomposition qr = null;
        Matrix matrixX = null;

        // in case Vandermonde matrix does not have full rank, reduce degree until it does
        while (true) {

            // build Vandermonde matrix
            double[][] vandermonde = new double[n][this.degree + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= this.degree; j++) {
                    vandermonde[i][j] = Math.pow(x[i], j);
                }
            }
            matrixX = new Matrix(vandermonde);

            // find least squares solution
            qr = new QRDecomposition(matrixX);
            if (qr.isFullRank())
                break;

            // decrease degree and try again
            this.degree--;
        }

        // create matrix from vector
        Matrix matrixY = new Matrix(y, n);

        // linear regression coefficients
        beta = qr.solve(matrixY);

        // mean of y[] values
        double sum = 0.0;
        for (int i = 0; i < n; i++)
            sum += y[i];
        double mean = sum / n;

        // total variation to be accounted for
        for (int i = 0; i < n; i++) {
            double dev = y[i] - mean;
            sst += dev * dev;
        }

        // variation not accounted for
        Matrix residuals = matrixX.times(beta).minus(matrixY);
        sse = residuals.norm2() * residuals.norm2();
    }

    /**
     * Returns the {@code j}th regression coefficient.
     *
     * @param j
     *            the index
     * @return the {@code j}th regression coefficient
     */
    public double GetBeta(int j) {
        // to make -0.0 print as 0.0
        if (Math.abs(beta.get(j, 0)) < 1E-4)
            return 0.0;
        return beta.get(j, 0);
    }

    /**
     * Returns the degree of the polynomial to fit.
     * @return the degree of the polynomial to fit
     */
    public int GetDegree() {
        return degree;
    }

    /**
     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
     * @return the coefficient of determination <em>R</em><sup>2</sup>, which is a real number between 0 and 1
     */
    public double GetR2() {
        if (sst == 0.0)
            return 1.0; // constant function
        return 1.0 - sse / sst;
    }

    /**
     * Returns the expected response {@code y} given the value of the predictor variable {@code x}.
     *
     * @param x
     *            the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor variable {@code x}
     */
    public double Evaluate(double x) {
        // horner's method
        double y = 0.0;
        for (int j = degree; j >= 0; j--)
            y = GetBeta(j) + (x * y);
        return y;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int j = degree;

        // ignoring leading zero coefficients
        while (j >= 0 && Math.abs(GetBeta(j)) < 1E-5)
            j--;

        // create remaining terms
        while (j >= 0) {
            if (j == 0)
                s.append(String.format("%.2f ", GetBeta(j)));
            else if (j == 1)
                s.append(String.format("%.2f x + ", GetBeta(j)));
            else
                s.append(String.format("%.2f x^%d + ", GetBeta(j), j));
            j--;
        }
        s = s.append("  (R^2 = " + String.format("%.3f", GetR2()) + ")");
        return s.toString();
    }
}