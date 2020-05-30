import club.ncr.etl.TCMotorLoad;

public class TCExtract {

    public static void main(String[] args) {
        TCMotorLoad loader= new TCMotorLoad();

        for (char impulse= 'A'; impulse < 'T'; impulse++) {
            loader.execute(""+ impulse);
        }
    }
}
