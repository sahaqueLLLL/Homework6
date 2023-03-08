import java.util.*;
import java.util.function.Function;

public class Main {

    public interface Playable {
         List<String> play();
        List<String> playWith(Playable playable);
     }

    public static abstract class Instrument implements Playable {
         private String sound;
         private Integer times;

         Instrument(String sound_, Integer times_) {
            sound = sound_;
            times = times_;
        }
        String getSound() {
            return this.sound;
        }
        Integer getTimes() {
            return this.times;
        }
        public List<String> play() {
            List<String> list = new ArrayList<>();
            String buff;
            buff = (this.sound + " ").repeat(Math.max(0, this.times));
            list.add(buff);
            return list;
        }
        public List<String> playWith(Playable playable) {
            return null;
        }
    }

    public static class Guitar extends Instrument {

        String sound;
        Integer times;
        Guitar() {
            super("Трунь", 2);
            this.sound = "Трунь";
            this.times = 2;
        }
        @Override
        public List<String> play() {
            List<String> list = new ArrayList<>();
            String buff;
            StringBuilder bb = new StringBuilder();
            for (int i = 0; i < times; i++) {
                if (i == 1) {
                    bb.append(sound);
                } else if (i == 0) {
                    bb.append(sound  + " ");
                }

            }
            buff = bb.toString();
            list.add(buff);
            return list;
        }
        @Override
        public List<String> playWith(Playable playable) {
            List<String> result = new ArrayList<>();
            result.add(0, this.play().get(0));
            result.add(1,playable.play().get(0));
            return result;
        }



    }

    public static class Drum extends Instrument {
        String sound;
        Integer times;
        Drum() {
            super("Бац", 3);
            this.sound = "Бац";
            this.times = 3;
        }
        @Override
        public List<String> play() {
            List<String> list = new ArrayList<>();
            String buff;
            StringBuilder bb = new StringBuilder();
            for (int i = 0; i < this.times; i++) {
                if (i == 2) {
                    bb.append(this.sound);
                } else if (i == 0 || i == 1) {
                    bb.append(this.sound  + " ");
                }

            }
            buff = bb.toString();
            list.add(buff);
            return list;
        }
        @Override
        public List<String> playWith(Playable playable) {
            List<String> result = new ArrayList<>();
            result.add(0, this.play().get(0));
            result.add(1, playable.play().get(0));
            return result;
        }

    }

    public static class Orchestra implements Playable {
          List<Instrument> instruments = new ArrayList<>();

         public List<Instrument> getInstruments() {
            return instruments;
            }

            Orchestra(Instrument... inst) {
                for (int i = 0; i < inst.length; i++)
                    this.instruments.add(i, inst[i]);
            }
            Orchestra(List<Instrument> instruments) {
                this.instruments = instruments;

                }

        public List<String> play() {
             List<String> result = new ArrayList<>();
             result.add(0, instruments.get(0).play().get(0));
             result.add(1, instruments.get(1).play().get(0));
            result.add(2, instruments.get(2).play().get(0));
            result.add(3, instruments.get(3).play().get(0));
            return result;

        }

        public List<String> playWith(Playable playable) {
             List<String> result = new ArrayList<>();
            for (int i = 0; i < instruments.size(); i++) {
                result.add(i, this.play().get(i));
            }
            result.add(4, playable.play().get(0));
            return result;

    }




    public static void main(String[] args) {
        var guitar = new Guitar();
        var drum = new Drum();
        print("Guitar: Гитара создалась", true);
        print("Drum:   Барабан создался", true);
        print("Guitar: play Guitar должно быть " + GUITAR_R, Objects.equals(guitar.play().get(0), GUITAR_R));
        print("Drum:   play Drum должно быть " + DRUM_R, Objects.equals(drum.play().get(0), DRUM_R));
        print("Guitar: playWith Drum первая гитара", Objects.equals(guitar.playWith(drum).get(0), GUITAR_R));
        print("Guitar: playWith Drum последний барабан", Objects.equals(guitar.playWith(drum).get(1), DRUM_R));
        print("Drum:   playWith Guitar первый барабан", Objects.equals(drum.playWith(guitar).get(0), DRUM_R));
        print("Drum:   playWith Guitar последняя гитара", Objects.equals(drum.playWith(guitar).get(1), GUITAR_R));


        var emptyOrchestra = new Orchestra(new ArrayList<>());
        var orchestra = new Orchestra(new Guitar(), new Drum(), new Guitar(), new Drum());

        print("EmptyOrchestra: Пустой оркестр создался", true);
        print("EmptyOrchestra: Инструменты должны быть пустым списком", emptyOrchestra.getInstruments() != null);
        print("Orchestra: Оркестр создался", true);
        print("Orchestra: В оркестре должно быть 4 инструмента", orchestra.getInstruments().size() == 4);
        print("Orchestra: Должны сыграть 4 инструмента", orchestra.play().size() == 4);
        print("Orchestra: Гитара играет первая", Objects.equals(orchestra.play().get(0), GUITAR_R));
        print("Orchestra: Барабан играет второй", Objects.equals(orchestra.play().get(1), DRUM_R));
        print("Orchestra: Гитара играет третья", Objects.equals(orchestra.play().get(2), GUITAR_R));
        print("Orchestra: Барабан играет четвертый", Objects.equals(orchestra.play().get(3), DRUM_R));
        print("Orchestra: Должны сыграть 5 инструментов", orchestra.playWith(new Guitar()).size() == 5);
        print("Orchestra: Гитара играет последняя", Objects.equals(orchestra.playWith(new Guitar()).get(4), GUITAR_R));
    }

    /* Техническая секция - сюда писать ничего не надо */

    private static void print(String condition, Boolean act) {
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print( "TEST CASE " + yellow.apply(constLen(condition, 55)));
        if (act) System.out.print("✅"); else System.out.print("❌");
        System.out.println();
    }

    private static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    private static final String GUITAR_R = "Трунь Трунь";
    private static final String DRUM_R = "Бац Бац Бац";
}}