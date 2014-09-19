package de.mabe.roulette.model;

public class RouletteKesselNumbers {

    public static final int COUNT_FOREWARD = 1;
    public static final int COUNT_BACKWARD = 2;
    public static final int ORIGINAL_COUNT = 3;
    private int[] numbers;

    public RouletteKesselNumbers(int countMethod) {
        this.numbers = new int[37];
        switch (countMethod) {
            case COUNT_FOREWARD:
                for (int i = 0; i < this.numbers.length; i++) {
                    this.numbers[i] = i;
                }
                break;
            case COUNT_BACKWARD:
                for (int i = 0; i < this.numbers.length; i++) {
                    this.numbers[i] = this.numbers.length - 1 - i;
                }
                break;
            case ORIGINAL_COUNT:
                this.numbers[0] = 0;
                this.numbers[1] = 26;
                this.numbers[2] = 3;
                this.numbers[3] = 35;
                this.numbers[4] = 12;
                this.numbers[5] = 28;
                this.numbers[6] = 7;
                this.numbers[7] = 29;
                this.numbers[8] = 18;
                this.numbers[9] = 22;
                this.numbers[10] = 9;
                this.numbers[11] = 31;
                this.numbers[12] = 14;
                this.numbers[13] = 20;
                this.numbers[14] = 1;
                this.numbers[15] = 33;
                this.numbers[16] = 16;
                this.numbers[17] = 24;
                this.numbers[18] = 5;
                this.numbers[19] = 10;
                this.numbers[20] = 23;
                this.numbers[21] = 8;
                this.numbers[22] = 30;
                this.numbers[23] = 11;
                this.numbers[24] = 36;
                this.numbers[25] = 13;
                this.numbers[26] = 27;
                this.numbers[27] = 6;
                this.numbers[28] = 34;
                this.numbers[29] = 17;
                this.numbers[30] = 25;
                this.numbers[31] = 2;
                this.numbers[32] = 21;
                this.numbers[33] = 4;
                this.numbers[34] = 19;
                this.numbers[35] = 15;
                this.numbers[36] = 32;
                break;
        }
    }

    public int[] get(){
        return this.numbers;
    }
}
