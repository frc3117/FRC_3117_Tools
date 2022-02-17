package frc.robot.Library.FRC_3117.Math;

import frc.robot.Library.FRC_3117.Interface.BaseFilter;

/**
 * A highpass and lowpass filter using the butterworth algorithm
 */
public class ButterworthFilter implements BaseFilter
{
    public enum PassType
    {
        Highpass,
        Lowpass
    }

    public ButterworthFilter(double Frequency, int SampleRate, PassType FilterType)
    {
        _resonance = 1.41421;
        _frequency = Frequency;
        _sampleRate = SampleRate;
        _passType = FilterType;

        switch (_passType)
        {
            case Lowpass:
                _c = 1.0 / (double)Math.tan(Math.PI * _frequency / _sampleRate);
                _a1 = 1.0 / (1.0 + _resonance * _c + Math.pow(_c, 2));
                _a2 = 2 * _a1;
                _a3 = _a1;
                _b1 = 2.0 * (1.0f - Math.pow(_c, 2)) * _a1;
                _b2 = (1.0 - _resonance * _c + Math.pow(_c, 2)) * _a1;
                break;
            case Highpass:
                _c = (double)Math.tan(Math.PI * _frequency / _sampleRate);
                _a1 = 1.0f / (1.0f + _resonance * _c + Math.pow(_c, 2));
                _a2 = -2f * _a1;
                _a3 = _a1;
                _b1 = 2.0f * (Math.pow(_c, 2) - 1.0f) * _a1;
                _b2 = (1.0f - _resonance * _c + Math.pow(_c, 2)) * _a1;
                break;
        }
    }
    public ButterworthFilter(double Frequency, int SampleRate, PassType FilterType, double Resonance)
    {
        _resonance = Resonance;
        _frequency = Frequency;
        _sampleRate = SampleRate;
        _passType = FilterType;

        switch (_passType)
        {
            case Lowpass:
                _c = 1.0 / (double)Math.tan(Math.PI * _frequency / _sampleRate);
                _a1 = 1.0 / (1.0 + _resonance * _c + Math.pow(_c, 2));
                _a2 = 2 * _a1;
                _a3 = _a1;
                _b1 = 2.0 * (1.0f - Math.pow(_c, 2)) * _a1;
                _b2 = (1.0 - _resonance * _c + Math.pow(_c, 2)) * _a1;
                break;
            case Highpass:
                _c = (double)Math.tan(Math.PI * _frequency / _sampleRate);
                _a1 = 1.0f / (1.0f + _resonance * _c + Math.pow(_c, 2));
                _a2 = -2f * _a1;
                _a3 = _a1;
                _b1 = 2.0f * (Math.pow(_c, 2) - 1.0f) * _a1;
                _b2 = (1.0f - _resonance * _c + Math.pow(_c, 2)) * _a1;
                break;
        }
    }

    // from sqrt(2) to ~ 0.1
    private double _resonance;

    private double _frequency;
    private int _sampleRate;
    private PassType _passType;

    private double _c;

    private double _a1;
    private double _a2;
    private double _a3;

    private double _b1;
    private double _b2;

    private double[] _lastInput = new double[2];
    private double[] _lastOutput = new double[3];

    /**
     * Update the current value of the butterworth filter
     * @param newInput The new input to add to the filter
     * @return The current value of the butterworth filter
     */
    public double Update(double newInput)
    {
        var newOutput = _a1 * newInput + _a2 * _lastInput[0] + _a3 * _lastInput[1] - _b1 * _lastOutput[0] - _b2 * _lastOutput[1];

        _lastInput[1] = _lastInput[0];
        _lastInput[0] = newInput;

        _lastOutput[2] = _lastOutput[1];
        _lastOutput[1] = _lastOutput[0];
        _lastOutput[0] = newOutput;

        return newOutput;
    }

    /**
     * Get the current value of the butterworth filter
     * @return The current value of the butterworth filter
     */
    public double GetCurrent()
    {
        return _lastOutput[0];
    }
}
